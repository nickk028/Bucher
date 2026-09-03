package ar.edu.huergo.vectorial.calidad.bucher.service.book;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Categoria;
import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Libro;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.RegistroPrestamo;
import ar.edu.huergo.vectorial.calidad.bucher.repository.book.LibroRepository;
import ar.edu.huergo.vectorial.calidad.bucher.service.payment.PrecioHistoricoService;
import ar.edu.huergo.vectorial.calidad.bucher.service.publication.RegistroPrestamoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Pageable;

//Clase que maneja la lógica de Libro
@Service
public class LibroService {

    @Autowired
    LibroRepository libroRepository;

    @Autowired
    RegistroPrestamoService registroPrestamoService;

    @Autowired
    PrecioHistoricoService precioHistoricoService;

    /**
    * Obtiene todos los libros disponibles
    * @return Conjunto con todos los libros
    */
    public Set<Libro> obtenerTodosLosLibros() {
        return new HashSet<> (libroRepository.findAll());
    }

    /**
    * Obtiene un libro por su ID
    * @param id El ID del libro a obtener
    * @return El libro correspondiente al ID indicado
    * @throws EntityNotFoundException Si no existe un libro con el ID indicado
    */
    public Libro obtenerLibroPorId(Long id) throws EntityNotFoundException {
        return libroRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Libro no encontrado"));
    }

    /**
    * Obtiene un Libro por su titulo
    * @param titulo El titulo del Libro a buscar
    * @return El Libro encontrado
    * @throws EntityNotFoundException No encuentra el Libro
    */
    public Libro obtenerLibroPorTitulo(String titulo) throws EntityNotFoundException {
        return libroRepository.findByTituloIgnoringCase(titulo)
            .orElseThrow(() -> new EntityNotFoundException("Libro no encontrado"));
    }

    /**
    * Obtiene todos los libros del repository filtrados en base al titulo ingresado
    * @return La lista de los libros filtrados
    */
    public Set<Libro> filtrarLibrosPorTitulo(String titulo) {
        Pageable limite = PageRequest.of(0, 20);
        return new HashSet<> (libroRepository.findAllByTituloContainingIgnoreCase(titulo, limite));
    }

    /**
    * Obtiene Libros por su categoria
    * @param categoria La categoria de los libros a buscar
    * @return Los libros de la categoria
    */
    public Set<Libro> obtenerLibrosPorCategoria(Categoria categoria) {
        return new HashSet<> (libroRepository.findAllByCategoriaContaining(categoria));
    }

    /**
    * Obtiene todos los libros agrupados por categoría
    * @return Mapa con cada categoría como clave y su conjunto de libros correspondiente como valor
    */
    public Map<Categoria,Set<Libro>> obtenerLibrosOrdenados() {
        Map<Categoria, Set<Libro>> todosLosLibros = new HashMap<>();
        for (Categoria categoria : Categoria.values()) {
            Set<Libro> librosPorCategoria = obtenerLibrosPorCategoria(categoria);
            todosLosLibros.put(categoria, librosPorCategoria);
        }
        return todosLosLibros;
    }

    /**
    * Obtiene los 10 libros más prestados de la semana actual
    * @return Conjunto con los 10 libros más prestados, ordenados de mayor a menor cantidad de préstamos
    */
    public Set<Libro> obtenerLibrosMasPrestadosDeLaSemana() {
        List<RegistroPrestamo> prestamosDeLaSemana = registroPrestamoService.obtenerRegistrosPrestamosDeLaSemana();

        Map<Libro,Integer> contadorPrestamos = new HashMap<>();

        for (RegistroPrestamo p : prestamosDeLaSemana) {
            Libro libro = p.getPublicacion().getLibro();
            if (contadorPrestamos.containsKey(libro)) {
                contadorPrestamos.put(libro, contadorPrestamos.get(libro) + 1);
            } else {
                contadorPrestamos.put(libro, 1);
            }
        }

        return contadorPrestamos.entrySet().stream().sorted((a, b) -> b.getValue().compareTo(a.getValue())).limit(10).map(Map.Entry::getKey).collect(Collectors.toSet());
    }

    /**
    * Guarda un nuevo libro creado por un escritor
    * @return El libro guardado en el repository
    */
    public Libro crearLibro(Libro libro) {
        libro.setFechaPublicacion(LocalDate.now());
        Libro libroCreado = libroRepository.save(libro);
        precioHistoricoService.crearPrecioHistorico(libroCreado, libroCreado.getPrecio());

        return libroCreado;
    }

    /**
    * Modifica el precio de un libro y genera un nuevo registro en su historial de precios
    * @param id El ID del libro a modificar
    * @param nuevoPrecio El nuevo precio del libro
    * @return El libro con el precio actualizado
    * @throws EntityNotFoundException Si no existe un libro con el ID indicado
    */
    public Libro modificarPrecioLibro(Long id, double nuevoPrecio) throws EntityNotFoundException {
        Libro libro = obtenerLibroPorId(id);
        libro.setPrecio(nuevoPrecio);

        Libro libroActualizado = libroRepository.save(libro);
        precioHistoricoService.crearPrecioHistorico(libroActualizado, nuevoPrecio);

        return libroActualizado;
    }
}