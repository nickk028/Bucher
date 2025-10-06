package ar.edu.huergo.vectorial.calidad.bucher.service.bookuser;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.huergo.vectorial.calidad.bucher.entity.bookuser.Biblioteca;
import ar.edu.huergo.vectorial.calidad.bucher.entity.bookuser.EstadoLectura;
import ar.edu.huergo.vectorial.calidad.bucher.entity.bookuser.LibroUsuario;
import ar.edu.huergo.vectorial.calidad.bucher.repository.bookuser.BibliotecaRepository;
import ar.edu.huergo.vectorial.calidad.bucher.repository.bookuser.LibroUsuarioRepository;
import ar.edu.huergo.vectorial.calidad.bucher.service.book.LibroService;
import jakarta.persistence.EntityNotFoundException;

/**
 * Clase que maneja la lógica de Biblioteca
 */
@Service
public class BibliotecaService {

    @Autowired
    private BibliotecaRepository bibliotecaRepository;

    @Autowired
    private LibroUsuarioRepository libroUsuarioRepository;

    @Autowired
    private LibroService libroService;

    /**
     * Obtiene la Biblioteca de un Usuario
     * @param idUsuario El id del Usuario de la Biblioteca
     * @return La Biblioteca encontrada
     * @throws EntityNotFoundException No encuentra la Biblioteca
     */
    public Biblioteca obtenerBiblioteca(Long idUsuario) throws EntityNotFoundException {
        return bibliotecaRepository.findById(idUsuario)
            .orElseThrow(() -> new EntityNotFoundException("Biblioteca no encontrada"));
    }

    /**
     * Sube un nuevo LibroUsuario a la Biblioteca
     * @param bibliotecaUsuario La Biblioteca del Usuario
     * @param libroUsuarioIngresado El LibroUsuario ingresado
     * @param titulo El titulo del Libro al que le corresponde el LibroUsuario
     * @return La Biblioteca actualizada
     */
    public Biblioteca subirLibroUsuario(Biblioteca bibliotecaUsuario, LibroUsuario libroUsuarioIngresado, String titulo) {
        libroUsuarioIngresado.setLibro(libroService.obtenerLibroPorTitulo(titulo));
        libroUsuarioIngresado.setBiblioteca(bibliotecaUsuario);
        bibliotecaUsuario.getLibrosUsuario().add(libroUsuarioIngresado);

        return bibliotecaRepository.save(bibliotecaUsuario);
    }

    /**
     * Obtiene el LibroUsuario según su posición en la Biblioteca
     * @param posicion La posición del LibroUsuario
     * @param biblioteca La Biblioteca ingresada
     * @return El LibroUsuario encontrado
     * @throws EntityNotFoundException No encuentra el LibroUsuario
     */
    public LibroUsuario obtenerLibroUsuarioPorPosicion(int posicion, Biblioteca biblioteca) throws EntityNotFoundException {
        List<LibroUsuario> librosUsuario = biblioteca.getLibrosUsuario();
        if (posicion <= 0 || posicion >= librosUsuario.size() + 1) {
            throw new EntityNotFoundException("Libro usuario no encontrado");
        }
        return biblioteca.getLibrosUsuario().get(posicion - 1);
    }

    /**
     * Obtiene una lista de LibroUsuario filtrado por EstadoLectura
     * @param biblioteca La Biblioteca ingresada
     * @param estado El EstadoLectura ingresado
     * @return La lista de LibroUsuario de un EstadoLectura
     */
    public List<LibroUsuario> obtenerLibrosPorEstado(Biblioteca biblioteca, EstadoLectura estado) {
        return libroUsuarioRepository.findByBibliotecaAndEstadoLectura(biblioteca, estado);
    }

    /**
     * Actualiza la Biblioteca en el repository
     * @param bibliotecaUsuario La Biblioteca a actualizar
     * @return La Biblioteca actualizada
     */
    public Biblioteca actualizarBiblioteca(Biblioteca bibliotecaUsuario) {
        return bibliotecaRepository.save(bibliotecaUsuario);
    }

    /**
     * Elimina el LibroUsuario y guarda la Biblioteca
     * @param biblioteca La Biblioteca a actualizar
     * @param posicion La posicion del LibroUsuario a eliminar
     */
    public void eliminarLibroUsuarioDeBiblioteca(Biblioteca biblioteca, int posicion) {
        biblioteca.getLibrosUsuario().remove(posicion - 1);
        bibliotecaRepository.save(biblioteca);
    }
}