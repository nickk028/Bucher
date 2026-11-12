package ar.edu.huergo.vectorial.calidad.bucher.controller.book;

import ar.edu.huergo.vectorial.calidad.bucher.dto.book.LibroBasicDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.book.LibroCreateDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.book.LibroPrecioUpdateDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.book.LibroResponseDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.payment.PrecioHistoricoLibroResponseDTO;
import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Categoria;
import ar.edu.huergo.vectorial.calidad.bucher.mapper.book.LibroMapper;
import ar.edu.huergo.vectorial.calidad.bucher.mapper.payment.PrecioHistoricoLibroMapper;
import ar.edu.huergo.vectorial.calidad.bucher.service.book.LibroService;
import ar.edu.huergo.vectorial.calidad.bucher.service.payment.PrecioHistoricoLibroService;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/libro")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @Autowired
    private LibroMapper libroMapper;

    @Autowired
    private PrecioHistoricoLibroService precioHistoricoLibroService;

    @Autowired
    private PrecioHistoricoLibroMapper precioHistoricoLibroMapper;

    /**
     * Obtiene todos los libros disponibles
     * @return Lista de todos los libros
     */
    @GetMapping("/todos")
    public ResponseEntity<List<LibroBasicDTO>> obtenerTodosLosLibros() {
        return ResponseEntity.ok(libroMapper.toBasicDTOList(libroService.obtenerTodosLosLibros()));
    }

    /**
     * Obtiene un libro por su ID
     * @param id El ID del libro a obtener
     * @return El libro correspondiente al ID indicado
     */
    @GetMapping("/{id}")
    public ResponseEntity<LibroResponseDTO> obtenerLibroPorId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(libroMapper.toDTO(libroService.obtenerLibroPorId(id)));
    }

    /**
     * Obtiene un libro por su titulo
     * @param titulo El titulo del libro a obtener
     * @return El libro correspondiente al titulo indicado
     */
    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<LibroBasicDTO> obtenerLibroPorTitulo(
        @PathVariable("titulo") String tituloLibro
    ) {
        return ResponseEntity.ok(
            libroMapper.toBasicDTO(libroService.obtenerLibroPorTitulo(tituloLibro))
        );
    }

    /**
     * Obtiene una lista de libros filtrados por categoría
     * @param categoria La categoría por la cual filtrar los libros
     * @return Lista de libros pertenecientes a la categoría indicada
     */
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<LibroBasicDTO>> obtenerLibrosPorCategoria(
        @PathVariable("categoria") Categoria categoria
    ) {
        return ResponseEntity.ok(
            libroMapper.toBasicDTOList(libroService.obtenerLibrosPorCategoria(categoria))
        );
    }

    /**
     * Obtiene los libros más leídos de la semana
     * @return Lista de libros en tendencia según la cantidad de préstamos de la semana
     */
    @GetMapping("/tendencias")
    public ResponseEntity<List<LibroBasicDTO>> obtenerLibrosMasLeidos() {
        List<LibroBasicDTO> librosEnTendencia = libroMapper.toBasicDTOList(
            libroService.obtenerLibrosMasPrestadosDeLaSemana()
        );
        return ResponseEntity.ok(librosEnTendencia);
    }

    /**
     * Obtiene todos los libros agrupados y ordenados por categoría
     * @return Mapa con cada categoría como clave y su lista de libros correspondiente como valor
     */
    @GetMapping("/ordenados")
    public ResponseEntity<Map<Categoria, List<LibroResponseDTO>>> obtenerLibrosOrdenados() {
        Map<Categoria, List<LibroResponseDTO>> librosOrdenados = libroMapper.toDTOMap(
            libroService.obtenerLibrosOrdenados()
        );
        return ResponseEntity.ok(librosOrdenados);
    }

    /**
     * Guarda un nuevo libro creado por el usuario autenticado, con precio base 0
     * @param libroCreateDTO El DTO con los datos del libro a crear
     * @param usuarioAutenticado El usuario autenticado
     * @return El libro creado
     */
    @PostMapping("/crear")
    public ResponseEntity<LibroResponseDTO> crearLibro(
        @Valid @RequestBody LibroCreateDTO libroCreateDTO,
        @AuthenticationPrincipal UserDetails usuarioAutenticado
    ) {
        return ResponseEntity.ok(
            libroMapper.toDTO(
                libroService.crearLibro(
                    libroMapper.toEntity(libroCreateDTO),
                    BigDecimal.ZERO,
                    usuarioAutenticado.getUsername()
                )
            )
        );
    }

    /**
     * Obtiene todos los libros filtrados en base al titulo ingresado
     * @param tituloLibro El titulo del libro a obtener
     * @return La lista de los libros filtrados
     */
    @GetMapping("/filtrar/titulo/{titulo}")
    public ResponseEntity<List<LibroBasicDTO>> obtenerLibrosPorTitulo(
        @PathVariable("titulo") String tituloLibro
    ) {
        return ResponseEntity.ok(
            libroMapper.toBasicDTOList(libroService.filtrarLibrosPorTitulo(tituloLibro))
        );
    }

    /**
     * Modifica el precio de un libro (solo admin) y genera un nuevo registro en su historial de precios
     * @param id El ID del libro a modificar
     * @param libroPrecioUpdateDTO El DTO con el nuevo precio del libro
     * @return El nuevo registro de precio histórico del libro
     */
    @PutMapping("/{id}/precio")
    public ResponseEntity<PrecioHistoricoLibroResponseDTO> modificarPrecioLibro(
        @PathVariable("id") Long id,
        @Valid @RequestBody LibroPrecioUpdateDTO libroPrecioUpdateDTO
    ) {
        return ResponseEntity.ok(
            precioHistoricoLibroMapper.toDTO(
                libroService.modificarPrecioLibro(id, libroPrecioUpdateDTO.getPrecio())
            )
        );
    }

    /**
     * Obtiene el historial de precios de un libro
     * @param id El ID del libro del cual obtener el historial de precios
     * @return La lista de precios históricos del libro
     */
    @GetMapping("/{id}/precio/historico")
    public ResponseEntity<List<PrecioHistoricoLibroResponseDTO>> obtenerHistorialPrecios(
        @PathVariable("id") Long id
    ) {
        return ResponseEntity.ok(
            precioHistoricoLibroMapper.toDTOList(
                precioHistoricoLibroService.obtenerHistorialPrecios(
                    libroService.obtenerLibroPorId(id)
                )
            )
        );
    }
}