package ar.edu.huergo.vectorial.calidad.bucher.service.bookuser;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import ar.edu.huergo.vectorial.calidad.bucher.entity.bookuser.Biblioteca;
import ar.edu.huergo.vectorial.calidad.bucher.entity.bookuser.LibroUsuario;
import jakarta.persistence.EntityNotFoundException;

@Service
public class LibroUsuarioService {

    public List<LibroUsuario> extraerLibrosUsuario(Biblioteca biblioteca) {
        return biblioteca.getLibrosUsuario()
            .stream()
            .toList();
    }

    public LibroUsuario modificarLibroUsuario(LibroUsuario libroUsuarioAModificar, LibroUsuario libroUsuarioNuevo) {
        libroUsuarioAModificar.setEstadoLectura(libroUsuarioNuevo.getEstadoLectura());
        libroUsuarioAModificar.setPaginaActual(libroUsuarioNuevo.getPaginaActual());
        libroUsuarioAModificar.setPuntuacion(libroUsuarioNuevo.getPuntuacion());

        return libroUsuarioAModificar;
    }
}