package ar.edu.huergo.vectorial.calidad.bucher.service.bookuser;

import java.util.List;

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
}