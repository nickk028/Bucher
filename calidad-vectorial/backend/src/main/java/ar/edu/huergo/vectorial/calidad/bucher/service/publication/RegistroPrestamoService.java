package ar.edu.huergo.vectorial.calidad.bucher.service.publication;

import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.Estado;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.Publicacion;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.RegistroPrestamo;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import ar.edu.huergo.vectorial.calidad.bucher.repository.publication.RegistroPrestamoRepository;
import ar.edu.huergo.vectorial.calidad.bucher.service.security.UsuarioService;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistroPrestamoService {

    @Autowired
    private RegistroPrestamoRepository registroPrestamoRepository;

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Crea un nuevo registro de préstamo
     * @param usuario El usuario que realiza el préstamo
     * @param publicacion La publicación que se presta
     * @return El registro de préstamo creado
     */
    public RegistroPrestamo crearRegistroUsuario(Usuario usuario, Publicacion publicacion) {
        RegistroPrestamo registro = new RegistroPrestamo();
        registro.setPublicacion(publicacion);
        registro.setUsuario(usuario);
        registro.setFechaDevolucion(null);

        usuario.getPrestamos().add(registro);
        return registroPrestamoRepository.save(registro);
    }

    /**
     * Marca un registro de préstamo como devuelto
     * @param registro El registro de préstamo a marcar como devuelto
     * @return El registro de préstamo actualizado
     */
    public RegistroPrestamo marcarRegistroDevolucion(RegistroPrestamo registro) {
        registro.setFechaDevolucion(LocalDate.now());
        return registroPrestamoRepository.save(registro);
    }

    /**
     * Obtiene el ultimo registro de préstamo activo para un usuario y publicación específicos
     * @param usuario El usuario del registro de préstamo
     * @param publicacion La publicación del registro de préstamo
     * @return El registro de préstamo activo
     * @throws IllegalArgumentException Si no existe un registro de préstamo activo para el usuario y publicación dados
     */
    public RegistroPrestamo obtenerRegistroPrestamo(Publicacion publicacion)
        throws IllegalArgumentException {
        return registroPrestamoRepository
            .findByPublicacionAndFechaDevolucionIsNull(publicacion)
            .orElseThrow(() ->
                new IllegalArgumentException(
                    "No existe un registro de préstamo activo para este usuario y publicación"
                )
            );
    }

    /**
     * Obtiene la List de registros de prestamo de un usuario
     * @param usuario El usuario de los registros de préstamo
     * @return La List de los registros de préstamo
     */
    public List<RegistroPrestamo> obtenerRegistrosPrestamoPorUsuario(Usuario usuario) {
        return registroPrestamoRepository.findAllByUsuario(usuario);
    }

    /**
     * Obtiene la List de registros de prestamo de un usuario
     * @param usuario El usuario de los registros de préstamo
     * @return La List de los registros de préstamo
     */
    public RegistroPrestamo obtenerRegistroPrestamoPorId(Long id, Usuario usuario) {
        if (UsuarioService.hasRol(usuario, "ADMIN")) {
            return registroPrestamoRepository.findById(id)
                .orElseThrow(() ->
                new IllegalArgumentException(
                    "No existe un registro de préstamo activo para este id"
                )
            );
        }
        return registroPrestamoRepository.findByIdAndUsuario(id, usuario)
            .orElseThrow(() ->
                new IllegalArgumentException(
                    "No existe un registro de préstamo activo para este id y usuario"
                )
            );
    }

    public RegistroPrestamo obtenerRegistroUsuarioPorPublicacion(Usuario usuario, Publicacion publicacion) {
        if (UsuarioService.hasRol(usuario, "ADMIN")) {
            return registroPrestamoRepository.findByPublicacion(publicacion)
                .orElseThrow(() ->
                new IllegalArgumentException(
                    "No existe un registro de préstamo activo para este id de publicación"
                )
            );
        }
        return registroPrestamoRepository.findByPublicacionAndUsuario(publicacion, usuario)
            .orElseThrow(() ->
                new IllegalArgumentException(
                    "No existe un registro de préstamo activo para este id de publicación y usuario"
                )
            );
    }


    /**
     * Obtiene una lista de los registros de prestamos de la ultima semana
     * @return La lista de los registros de prestamo
     */
    public List<RegistroPrestamo> obtenerRegistrosPrestamosDeLaSemana() {
        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaInicioSemana = LocalDate.now().minusDays(7);
        return registroPrestamoRepository.findByFechaPrestamoBetween(
            fechaInicioSemana,
            fechaActual
        );
    }

    public RegistroPrestamo realizarControlRegistroPrestamos(RegistroPrestamo registroNuevo, RegistroPrestamo registro) {
        if (registroNuevo.getConfirmacionTitulo() != null) {
            registro.setConfirmacionTitulo(registroNuevo.getConfirmacionTitulo());
        }
        if (registroNuevo.getConfirmacionAutor() != null) {
            registro.setConfirmacionAutor(registroNuevo.getConfirmacionAutor());
        }
        if (registroNuevo.getConfirmacionDetallesEstado() != null) {
            registro.setConfirmacionDetallesEstado(registroNuevo.getConfirmacionDetallesEstado());
        }
        if (registroNuevo.getConfirmacionDetallesEstadoLibro() != null) {
            registro.setConfirmacionDetallesEstadoLibro(registroNuevo.getConfirmacionDetallesEstadoLibro());
        }
        if (registroNuevo.getComentariosAdicionales() != null) {
            registro.setComentariosAdicionales(registroNuevo.getComentariosAdicionales());
        }
        if (registroNuevo.getEstaEnCondiciones() != null) {
            registro.setEstaEnCondiciones(registroNuevo.getEstaEnCondiciones());
        }
        if (registroNuevo.getMotivoNoEnCondiciones() != null) {
            registro.setMotivoNoEnCondiciones(registroNuevo.getMotivoNoEnCondiciones());
        }
        Publicacion publicacion = registro.getPublicacion();
        publicacion.setEstadoPublicacion(Estado.enrevision);
        registro.setPublicacion(publicacion);

        return registroPrestamoRepository.save(registro);
    }

    public RegistroPrestamo marcarRevisionCompletada(RegistroPrestamo registro) {
        Publicacion publicacion = registro.getPublicacion();
        publicacion.setEstadoPublicacion(Estado.aentregar);

        return registroPrestamoRepository.save(registro);
    }
}