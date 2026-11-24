package ar.edu.huergo.vectorial.calidad.bucher.service.book;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Categoria;
import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Libro;
import ar.edu.huergo.vectorial.calidad.bucher.entity.payment.PrecioHistoricoLibro;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.RegistroPrestamo;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import ar.edu.huergo.vectorial.calidad.bucher.repository.book.LibroRepository;
import ar.edu.huergo.vectorial.calidad.bucher.service.payment.PrecioHistoricoLibroService;
import ar.edu.huergo.vectorial.calidad.bucher.service.publication.RegistroPrestamoService;
import ar.edu.huergo.vectorial.calidad.bucher.service.security.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//Clase que maneja la lógica de Libro
@Service
public class LibroService {

    @Autowired
    LibroRepository libroRepository;

    @Autowired
    RegistroPrestamoService registroPrestamoService;

    @Autowired
    PrecioHistoricoLibroService precioHistoricoLibroService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    AutorService autorService;

    @Autowired
    EditorialService editorialService;

    /**
     * Obtiene todos los libros disponibles
     * @return Conjunto con todos los libros
     */
    public Set<Libro> obtenerTodosLosLibros() {
        return new HashSet<>(libroRepository.findAll());
    }

    /**
     * Obtiene un libro por su ID
     * @param id El ID del libro a obtener
     * @return El libro correspondiente al ID indicado
     * @throws EntityNotFoundException Si no existe un libro con el ID indicado
     */
    public Libro obtenerLibroPorId(Long id) throws EntityNotFoundException {
        return libroRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Libro no encontrado"));
    }

    /**
     * Obtiene un Libro por su titulo
     * @param titulo El titulo del Libro a buscar
     * @return El Libro encontrado
     * @throws EntityNotFoundException No encuentra el Libro
     */
    public Libro obtenerLibroPorTitulo(String titulo) throws EntityNotFoundException {
        return libroRepository
            .findByTituloIgnoringCase(titulo)
            .orElseThrow(() -> new EntityNotFoundException("Libro no encontrado"));
    }

    /**
     * Obtiene todos los libros del repository filtrados en base al titulo ingresado
     * @return La lista de los libros filtrados
     */
    public Set<Libro> filtrarLibrosPorTitulo(String titulo) {
        Pageable limite = PageRequest.of(0, 20);
        return new HashSet<>(libroRepository.findAllByTituloContainingIgnoreCase(titulo, limite));
    }

    /**
     * Obtiene Libros por su categoria
     * @param categoria La categoria de los libros a buscar
     * @return Los libros de la categoria
     */
    public Set<Libro> obtenerLibrosPorCategoria(Categoria categoria) {
        return new HashSet<>(libroRepository.findAllByCategoriaContaining(categoria));
    }

    /**
     * Obtiene todos los libros agrupados por categoría
     * @return Mapa con cada categoría como clave y su conjunto de libros correspondiente como valor
     */
    public Map<Categoria, Set<Libro>> obtenerLibrosOrdenados() {
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
        List<RegistroPrestamo> prestamosDeLaSemana =
            registroPrestamoService.obtenerRegistrosPrestamosDeLaSemana();

        Map<Libro, Integer> contadorPrestamos = new HashMap<>();

        for (RegistroPrestamo p : prestamosDeLaSemana) {
            Libro libro = p.getPublicacion().getLibro();
            if (contadorPrestamos.containsKey(libro)) {
                contadorPrestamos.put(libro, contadorPrestamos.get(libro) + 1);
            } else {
                contadorPrestamos.put(libro, 1);
            }
        }

        return contadorPrestamos
            .entrySet()
            .stream()
            .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
            .limit(10)
            .map(Map.Entry::getKey)
            .collect(Collectors.toSet());
    }

    /**
     * Guarda un nuevo libro creado por un usuario, asociándolo al autor del usuario y a la editorial propia
     * @param libro El libro a crear
     * @param precioBase El precio inicial del libro
     * @param username El username del usuario que crea el libro
     * @return El libro guardado en el repository
     * @throws EntityNotFoundException No encuentra el Usuario
     */
    @Transactional
    public Libro crearLibro(Libro libro, BigDecimal precioBase, String username)
        throws EntityNotFoundException {
        Usuario usuario = usuarioService.obtenerUsuarioPorNombre(username);

        libro.setAutor(autorService.obtenerOCrearAutorDeUsuario(usuario));
        libro.setEditorial(editorialService.obtenerEditorialPropia());
        libro.setFechaPublicacion(LocalDate.now());
        libro.setCalificacion(0);

        Libro libroCreado = libroRepository.save(libro);
        precioHistoricoLibroService.crearPrecioHistorico(libroCreado, precioBase);

        return libroCreado;
    }

    /**
     * Modifica el precio de un libro generando un nuevo registro en su historial de precios
     * @param id El ID del libro a modificar
     * @param nuevoPrecio El nuevo precio del libro
     * @return El nuevo registro de precio histórico
     * @throws EntityNotFoundException Si no existe un libro con el ID indicado
     * @throws IllegalArgumentException Si el precio es igual al actual, o si es 0 en un libro que no es propio de los usuarios
     */
    public PrecioHistoricoLibro modificarPrecioLibro(Long id, BigDecimal nuevoPrecio)
        throws EntityNotFoundException {
        Libro libro = obtenerLibroPorId(id);
        BigDecimal precioActual = precioHistoricoLibroService.obtenerPrecioActual(libro);

        if (precioActual != null && precioActual.compareTo(nuevoPrecio) == 0) {
            throw new IllegalArgumentException("El nuevo precio no puede ser igual al precio actual");
        }
        if (nuevoPrecio.signum() == 0 && !editorialService.esEditorialPropia(libro.getEditorial())) {
            throw new IllegalArgumentException(
                "Solo los libros propios de los usuarios pueden tener precio 0"
            );
        }

        return precioHistoricoLibroService.crearPrecioHistorico(libro, nuevoPrecio);
    }
}