package ar.edu.huergo.vectorial.calidad.bucher.service.bookuser;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

import ar.edu.huergo.vectorial.calidad.bucher.entity.bookuser.Biblioteca;
import ar.edu.huergo.vectorial.calidad.bucher.entity.bookuser.LibroUsuario;
import ar.edu.huergo.vectorial.calidad.bucher.repository.bookuser.BibliotecaRepository;
import ar.edu.huergo.vectorial.calidad.bucher.service.book.LibroService;

@Service
public class BibliotecaService {

    @Autowired
    private BibliotecaRepository bibliotecaRepository;

    @Autowired
    private LibroService libroService;

    public Biblioteca obtenerBiblioteca(Long idUsuario) throws EntityNotFoundException {
        return bibliotecaRepository.findById(idUsuario)
            .orElseThrow(() -> new EntityNotFoundException("Biblioteca no encontrada"));
    }

    public Biblioteca subirLibroUsuario(Long idUsuario, LibroUsuario libroUsuarioIngresado, String titulo) {
        Biblioteca bibliotecaUsuario = obtenerBiblioteca(idUsuario);

        libroUsuarioIngresado.setLibro(libroService.obtenerLibroPorTitulo(titulo));
        libroUsuarioIngresado.setBiblioteca(bibliotecaUsuario);
        bibliotecaUsuario.getLibrosUsuario().add(libroUsuarioIngresado);

        return bibliotecaRepository.save(bibliotecaUsuario);
    }

    public LibroUsuario obtenerLibroUsuarioPorPosicion(int posicion, Biblioteca biblioteca) throws EntityNotFoundException {
        List<LibroUsuario> librosUsuario = biblioteca.getLibrosUsuario();
        if (posicion <= 0 || posicion >= librosUsuario.size() + 1) {
            throw new EntityNotFoundException("Libro usuario no encontrado");
        }
        return biblioteca.getLibrosUsuario().get(posicion - 1);
    }

    public Biblioteca actualizarLibroUsuario(Biblioteca bibliotecaUsuario) {
        return bibliotecaRepository.save(bibliotecaUsuario);
    }
}