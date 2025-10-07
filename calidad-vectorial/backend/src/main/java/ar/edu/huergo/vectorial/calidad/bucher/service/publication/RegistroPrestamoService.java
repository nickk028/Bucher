package ar.edu.huergo.vectorial.calidad.bucher.service.publication;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.Publicacion;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.RegistroPrestamo;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import ar.edu.huergo.vectorial.calidad.bucher.repository.publication.RegistroPrestamoRepository;

@Service
public class RegistroPrestamoService {

    @Autowired
    private RegistroPrestamoRepository registroPrestamoRepository;

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
    public RegistroPrestamo obtenerRegistroPrestamo(Publicacion publicacion) throws IllegalArgumentException {
        return registroPrestamoRepository.findByPublicacionAndFechaDevolucionIsNull(publicacion)
            .orElseThrow(() -> new IllegalArgumentException("No existe un registro de préstamo activo para este usuario y publicación"));
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
     * Obtiene una lista de los registros de prestamos de la ultima semana
     * @return La lista de los registros de prestamo
    */
    public List<RegistroPrestamo> obtenerRegistrosPrestamosDeLaSemana() {
        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaInicioSemana = LocalDate.now().minusDays(7);
        return registroPrestamoRepository.findByFechaPrestamoBetween(fechaActual, fechaInicioSemana);
    }

    public List<String> obtenerRegistrosPrestamosPodio() {
        List<RegistroPrestamo> registrosSemanales = obtenerRegistrosPrestamosDeLaSemana();
        Map<String, Long> conteoPrestamos = registrosSemanales.stream().collect(Collectors.groupingBy(
            rp -> rp.getPublicacion().getLibro().getTitulo(), Collectors.counting()));
        List<String> podioRegistro =  conteoPrestamos.entrySet().stream().sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
            .limit(10).map(Map.Entry::getKey).toList();
        
        return podioRegistro; 
    }
}