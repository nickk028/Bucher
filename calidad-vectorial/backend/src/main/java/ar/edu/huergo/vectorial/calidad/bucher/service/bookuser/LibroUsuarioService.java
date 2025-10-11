package ar.edu.huergo.vectorial.calidad.bucher.service.bookuser;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.huergo.vectorial.calidad.bucher.entity.bookuser.Biblioteca;
import ar.edu.huergo.vectorial.calidad.bucher.entity.bookuser.EstadoLectura;
import ar.edu.huergo.vectorial.calidad.bucher.entity.bookuser.LibroUsuario;

/**
 * Clase que maneja la lógica de LibroUsuario
 */
@Service
public class LibroUsuarioService {

    /**
     * Extrae la List de LibroUsuario de la biblioteca
     * @param biblioteca la biblioteca que contiene los libros usuario
     * @return la List de LibroUsuario
     */
    public List<LibroUsuario> extraerLibrosUsuario(Biblioteca biblioteca) {
        return biblioteca.getLibrosUsuario()
            .stream()
            .toList();
    }

    /**
     * Modifica los atributos del LibroUsuario
     * @param libroUsuarioAModificar El LibroUsuario a modificar
     * @param libroUsuarioNuevo El LibroUsuario con los nuevos datos
     * @return El LibroUsuario modificado
     */
    public LibroUsuario modificarLibroUsuario(LibroUsuario libroUsuarioAModificar, LibroUsuario libroUsuarioNuevo) {
        if (libroUsuarioNuevo.getEstadoLectura() != EstadoLectura.indefinido) {
            libroUsuarioAModificar.setEstadoLectura(libroUsuarioNuevo.getEstadoLectura());
        }
        if (libroUsuarioAModificar.getPaginaActual() != 0) {
            libroUsuarioAModificar.setPaginaActual(libroUsuarioNuevo.getPaginaActual());
        }
        if (libroUsuarioAModificar.getPuntuacion() != 0) {
            libroUsuarioAModificar.setPuntuacion(libroUsuarioNuevo.getPuntuacion());
        }

        return libroUsuarioAModificar;
    }
}