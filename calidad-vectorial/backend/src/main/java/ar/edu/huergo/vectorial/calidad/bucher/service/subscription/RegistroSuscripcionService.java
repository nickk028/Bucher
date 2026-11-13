package ar.edu.huergo.vectorial.calidad.bucher.service.subscription;

import ar.edu.huergo.vectorial.calidad.bucher.entity.payment.MedioPago;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import ar.edu.huergo.vectorial.calidad.bucher.entity.subscription.RegistroSuscripcion;
import ar.edu.huergo.vectorial.calidad.bucher.entity.subscription.Suscripcion;
import ar.edu.huergo.vectorial.calidad.bucher.repository.publication.RegistroPrestamoRepository;
import ar.edu.huergo.vectorial.calidad.bucher.repository.security.UsuarioRepository;
import ar.edu.huergo.vectorial.calidad.bucher.repository.subscription.RegistroSuscripcionRepository;
import ar.edu.huergo.vectorial.calidad.bucher.service.payment.MedioPagoService;
import ar.edu.huergo.vectorial.calidad.bucher.service.payment.PrecioHistoricoSuscripcionService;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//Clase que maneja la lógica de RegistroSuscripcion
@Service
public class RegistroSuscripcionService {

    // Duración en meses de una suscripción paga
    private static final int MESES_DURACION = 3;
    private static final BigDecimal CIEN = BigDecimal.valueOf(100);

    // Datos de la tarjeta de prueba (temporal)
    private static final String NUMERO_TARJETA_PRUEBA = "4111111111111111";
    private static final String VENCIMIENTO_TARJETA_PRUEBA = "12/30";
    private static final String CVV_TARJETA_PRUEBA = "123";
    private static final String TITULAR_TARJETA_PRUEBA = "TITULAR DE PRUEBA";

    @Value("${suscripcion.iva-porcentaje}")
    private BigDecimal ivaPorcentaje;

    @Autowired
    private RegistroSuscripcionRepository registroSuscripcionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RegistroPrestamoRepository registroPrestamoRepository;

    @Autowired
    private SuscripcionService suscripcionService;

    @Autowired
    private MedioPagoService medioPagoService;

    @Autowired
    private PrecioHistoricoSuscripcionService precioHistoricoSuscripcionService;

    /**
     * Obtiene el registro de la suscripción activa de un usuario (el más reciente ya iniciado que no terminó)
     * @param usuario El usuario a consultar
     * @return El registro de la suscripción activa
     * @throws EntityNotFoundException Si el usuario no tiene una suscripción activa
     */
    public RegistroSuscripcion obtenerRegistroActivo(Usuario usuario)
        throws EntityNotFoundException {
        LocalDate hoy = LocalDate.now();
        return registroSuscripcionRepository
            .findAllByUsuarioAndFechaInicioLessThanEqualOrderByFechaInicioDescIdDesc(usuario, hoy)
            .stream()
            .filter((registro) -> registro.getFechaFin() == null || registro.getFechaFin().isAfter(hoy))
            .findFirst()
            .orElseThrow(() -> new EntityNotFoundException("Suscripción activa no encontrada"));
    }

    /**
     * Obtiene el registro de la suscripción activa de un usuario por su username
     * @param username El username del usuario
     * @return El registro de la suscripción activa
     * @throws EntityNotFoundException Si no se encuentra el usuario o su suscripción activa
     */
    public RegistroSuscripcion obtenerRegistroActivo(String username)
        throws EntityNotFoundException {
        return obtenerRegistroActivo(obtenerUsuarioPorUsername(username));
    }

    /**
     * Obtiene todos los registros de suscripción de un usuario, del más reciente al más antiguo
     * @param username El username del usuario
     * @return La lista de registros de suscripción del usuario
     * @throws EntityNotFoundException Si no se encuentra el usuario
     */
    public List<RegistroSuscripcion> obtenerRegistrosPorUsuario(String username)
        throws EntityNotFoundException {
        return registroSuscripcionRepository.findAllByUsuarioOrderByFechaInicioDescIdDesc(
            obtenerUsuarioPorUsername(username)
        );
    }

    /**
     * Obtiene un registro de suscripción por su ID (de cualquier usuario)
     * @param id El ID del registro
     * @return El registro de suscripción
     * @throws EntityNotFoundException Si no se encuentra el registro
     */
    public RegistroSuscripcion obtenerRegistroPorId(Long id) throws EntityNotFoundException {
        return registroSuscripcionRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Registro de suscripción no encontrado"));
    }

    /**
     * Obtiene un registro de suscripción por su ID, solo si pertenece al usuario
     * @param id El ID del registro
     * @param username El username del usuario dueño del registro
     * @return El registro de suscripción
     * @throws EntityNotFoundException Si no se encuentra el registro o no pertenece al usuario
     */
    public RegistroSuscripcion obtenerRegistroPorIdYUsuario(Long id, String username)
        throws EntityNotFoundException {
        return registroSuscripcionRepository
            .findByIdAndUsuario(id, obtenerUsuarioPorUsername(username))
            .orElseThrow(() -> new EntityNotFoundException("Registro de suscripción no encontrado"));
    }

    /**
     * Asigna la suscripción gratuita a un usuario
     * @param usuario El usuario al que se le asigna la suscripción gratuita
     * @return El registro de suscripción creado
     */
    @Transactional
    public RegistroSuscripcion asignarSuscripcionGratuita(Usuario usuario) {
        return registroSuscripcionRepository.save(crearRegistroGratuito(usuario, LocalDate.now()));
    }

    /**
     * Contrata una suscripción paga. Si el usuario ya tiene una suscripción paga vigente, la nueva
     * comienza cuando termina la última.
     * @param username El username del usuario que contrata
     * @param suscripcionId El ID de la suscripción a contratar
     * @param medioPagoId El ID del medio de pago utilizado
     * @param numeroTarjeta El número de la tarjeta
     * @param vencimiento El vencimiento de la tarjeta
     * @param cvv El código de seguridad de la tarjeta
     * @param titular El titular de la tarjeta
     * @return El registro de suscripción creado
     * @throws EntityNotFoundException Si no se encuentra el usuario, la suscripción o el medio de pago
     * @throws IllegalArgumentException Si la suscripción es gratuita, el medio de pago no es Visa, la tarjeta no es válida o la suscripción no tiene precio
     */
    @Transactional
    public RegistroSuscripcion contratarSuscripcion(
        String username,
        Long suscripcionId,
        Long medioPagoId,
        String numeroTarjeta,
        String vencimiento,
        String cvv,
        String titular
    ) throws EntityNotFoundException {
        Usuario usuario = obtenerUsuarioPorUsername(username);
        Suscripcion suscripcion = suscripcionService.obtenerSuscripcionPorId(suscripcionId);

        if (suscripcionService.esSuscripcionGratuita(suscripcion)) {
            throw new IllegalArgumentException("La suscripción gratuita no se puede contratar");
        }

        MedioPago medioPago = medioPagoService.obtenerMedioPagoPorId(medioPagoId);
        if (!medioPagoService.esMedioPagoVisa(medioPago)) {
            throw new IllegalArgumentException("Solo se admite Visa como medio de pago");
        }
        validarTarjeta(numeroTarjeta, vencimiento, cvv, titular);

        BigDecimal precioBase = precioHistoricoSuscripcionService.obtenerPrecioActual(suscripcion);
        if (precioBase == null) {
            throw new IllegalArgumentException("La suscripción no tiene un precio registrado");
        }

        BigDecimal montoPagado = precioBase
            .add(precioBase.multiply(ivaPorcentaje).divide(CIEN, 2, RoundingMode.HALF_UP))
            .setScale(2, RoundingMode.HALF_UP);
        BigDecimal comision = montoPagado
            .multiply(BigDecimal.valueOf(medioPago.getPorcentajeComision()))
            .divide(CIEN, 2, RoundingMode.HALF_UP);

        LocalDate hoy = LocalDate.now();
        LocalDate fechaInicio = registroSuscripcionRepository
            .findFirstByUsuarioAndFechaFinAfterOrderByFechaFinDesc(usuario, hoy)
            .map(RegistroSuscripcion::getFechaFin)
            .orElse(hoy);

        return registroSuscripcionRepository.save(
            construirRegistro(
                usuario,
                suscripcion,
                medioPago,
                fechaInicio,
                fechaInicio.plusMonths(MESES_DURACION),
                precioBase,
                montoPagado,
                montoPagado.subtract(comision)
            )
        );
    }

    /**
     * Cancela las suscripciones pagas del usuario (la vigente y las encadenadas) con la fecha
     * actual como fecha de fin y lo vuelve a la suscripción gratuita desde hoy
     * @param username El username del usuario
     * @return El registro de la suscripción gratuita creado
     * @throws EntityNotFoundException Si no se encuentra el usuario
     * @throws IllegalArgumentException Si el usuario no tiene una suscripción paga para cancelar
     */
    @Transactional
    public RegistroSuscripcion cancelarSuscripcion(String username)
        throws EntityNotFoundException {
        Usuario usuario = obtenerUsuarioPorUsername(username);
        LocalDate hoy = LocalDate.now();

        List<RegistroSuscripcion> registrosPagos =
            registroSuscripcionRepository.findAllByUsuarioAndFechaFinAfter(usuario, hoy);
        if (registrosPagos.isEmpty()) {
            throw new IllegalArgumentException("No hay una suscripción paga para cancelar");
        }

        registrosPagos.forEach((registro) -> registro.setFechaFin(hoy));
        registroSuscripcionRepository.saveAll(registrosPagos);

        return registroSuscripcionRepository.save(crearRegistroGratuito(usuario, hoy));
    }

    /**
     * Verifica si el usuario puede pedir un libro prestado según el límite mensual de su
     * suscripción activa. El mes se cuenta desde la fecha de inicio de la suscripción.
     * @param usuario El usuario que quiere pedir el préstamo
     * @return true si no alcanzó el límite del mes, false en caso contrario
     * @throws EntityNotFoundException Si el usuario no tiene una suscripción activa
     */
    public boolean puedePedirPrestado(Usuario usuario) throws EntityNotFoundException {
        RegistroSuscripcion registroActivo = obtenerRegistroActivo(usuario);
        LocalDate fechaInicio = registroActivo.getFechaInicio();

        long mesesTranscurridos = ChronoUnit.MONTHS.between(fechaInicio, LocalDate.now());
        LocalDate inicioCiclo = fechaInicio.plusMonths(mesesTranscurridos);
        LocalDate finCiclo = inicioCiclo.plusMonths(1);

        long prestamosDelCiclo =
            registroPrestamoRepository.countByUsuarioAndFechaPrestamoGreaterThanEqualAndFechaPrestamoLessThan(
                usuario,
                inicioCiclo,
                finCiclo
            );

        return prestamosDelCiclo < registroActivo.getSuscripcion().getLimiteLibros();
    }

    /**
     * Valida los datos de la tarjeta contra la tarjeta de prueba
     * @throws IllegalArgumentException Si los datos no coinciden con la tarjeta de prueba
     */
    private void validarTarjeta(
        String numeroTarjeta,
        String vencimiento,
        String cvv,
        String titular
    ) {
        boolean tarjetaValida =
            NUMERO_TARJETA_PRUEBA.equals(numeroTarjeta.replaceAll("[\\s-]", "")) &&
            VENCIMIENTO_TARJETA_PRUEBA.equals(vencimiento.trim()) &&
            CVV_TARJETA_PRUEBA.equals(cvv.trim()) &&
            TITULAR_TARJETA_PRUEBA.equalsIgnoreCase(titular.trim());

        if (!tarjetaValida) {
            throw new IllegalArgumentException("Los datos de la tarjeta no son válidos");
        }
    }

    /**
     * Construye el registro de la suscripción gratuita para un usuario
     */
    private RegistroSuscripcion crearRegistroGratuito(Usuario usuario, LocalDate fechaInicio) {
        return construirRegistro(
            usuario,
            suscripcionService.obtenerSuscripcionGratuita(),
            medioPagoService.obtenerMedioPagoSinPago(),
            fechaInicio,
            null,
            BigDecimal.ZERO,
            BigDecimal.ZERO,
            BigDecimal.ZERO
        );
    }

    /**
     * Construye un registro de suscripción con los datos ingresados
     */
    private RegistroSuscripcion construirRegistro(
        Usuario usuario,
        Suscripcion suscripcion,
        MedioPago medioPago,
        LocalDate fechaInicio,
        LocalDate fechaFin,
        BigDecimal precioBase,
        BigDecimal montoPagado,
        BigDecimal montoAcreditado
    ) {
        RegistroSuscripcion registro = new RegistroSuscripcion();
        registro.setUsuario(usuario);
        registro.setSuscripcion(suscripcion);
        registro.setMedioPago(medioPago);
        registro.setFechaInicio(fechaInicio);
        registro.setFechaFin(fechaFin);
        registro.setPrecioBase(precioBase);
        registro.setMontoPagado(montoPagado);
        registro.setMontoAcreditado(montoAcreditado);

        return registro;
    }

    private Usuario obtenerUsuarioPorUsername(String username) throws EntityNotFoundException {
        return usuarioRepository
            .findByUsername(username)
            .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
    }
}