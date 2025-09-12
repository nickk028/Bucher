package ar.edu.huergo.vectorial.calidad.bucher.service.bookuser;

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
    private LibroUsuarioService libroUsuarioService;

    @Autowired
    private LibroService libroService;

    public Biblioteca obtenerBiblioteca(Long idUsuario) throws EntityNotFoundException {
        return bibliotecaRepository.findById(idUsuario)
            .orElseThrow(() -> new EntityNotFoundException("Biblioteca no encontrada"));
    }

    public Biblioteca subirLibroUsuario(Long idUsuario, LibroUsuario libroUsuarioIngresado, String titulo) {
        Biblioteca bibliotecaUsuario = obtenerBiblioteca(idUsuario);

        libroUsuarioIngresado.setIdLocal(obtenerCantidadLibros(bibliotecaUsuario) + 1);
        libroUsuarioIngresado.setLibro(libroService.obtenerLibroPorTitulo(titulo));
        libroUsuarioIngresado.setBiblioteca(bibliotecaUsuario);
        bibliotecaUsuario.getLibrosUsuario().add(libroUsuarioService.crearLibroUsuario(libroUsuarioIngresado));

        return bibliotecaRepository.save(bibliotecaUsuario);
    }

    public int obtenerCantidadLibros(Biblioteca biblioteca) {
        return biblioteca.getLibrosUsuario().size();
    }
}