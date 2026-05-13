package ar.edu.huergo.vectorial.calidad.bucher.controller.book;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.huergo.vectorial.calidad.bucher.dto.book.LibroBasicDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.book.LibroCreateDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.book.LibroResponseDTO;
import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Categoria;
import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Libro;
import ar.edu.huergo.vectorial.calidad.bucher.mapper.book.LibroMapper;
import ar.edu.huergo.vectorial.calidad.bucher.service.book.LibroService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/libro")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @Autowired
    private LibroMapper libroMapper;

    /**
    * Obtiene todos los libros disponibles
    * @return Lista de todos los libros
    */
    @GetMapping("/todos")
    public ResponseEntity<List<LibroBasicDTO>> obtenerTodosLosLibros() {
        return ResponseEntity.ok(
            libroMapper.toBasicDTOList(libroService.obtenerTodosLosLibros()));
    }

    /**
    * Obtiene un libro por su ID
    * @param id El ID del libro a obtener
    * @return El libro correspondiente al ID indicado
    */
    @GetMapping("/{id}")
    public ResponseEntity<LibroResponseDTO> obtenerLibroPorId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
            libroMapper.toDTO(libroService.obtenerLibroPorId(id)));
    }

    /**
    * Obtiene una lista de libros filtrados por categoría
    * @param categoria La categoría por la cual filtrar los libros
    * @return Lista de libros pertenecientes a la categoría indicada
    */
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<LibroBasicDTO>> obtenerLibrosPorCategoria(@PathVariable("categoria") Categoria categoria) {
        return ResponseEntity.ok(
            libroMapper.toBasicDTOList(libroService.obtenerLibrosPorCategoria(categoria)));
    }

    /**
    * Obtiene los libros más leídos de la semana
    * @return Lista de libros en tendencia según la cantidad de préstamos de la semana
    */
    @GetMapping("/tendencias")
    public ResponseEntity<List<LibroBasicDTO>> obtenerLibrosMasLeidos() {
        List<LibroBasicDTO> librosEnTendencia = libroMapper.toBasicDTOList(libroService.obtenerLibrosMasPrestadosDeLaSemana());
        return ResponseEntity.ok(librosEnTendencia);
    }

    /**
    * Obtiene todos los libros agrupados y ordenados por categoría
    * @return Mapa con cada categoría como clave y su lista de libros correspondiente como valor
    */
    @GetMapping("/ordenados")
    public ResponseEntity<Map<Categoria, List<LibroResponseDTO>>> obtenerLibrosOrdenados() {
        Map<Categoria, List<LibroResponseDTO>> librosOrdenados = libroMapper.toDTOMap(libroService.obtenerLibrosOrdenados());
        return ResponseEntity.ok(librosOrdenados);
    }

    @PostMapping("/crear")
    public ResponseEntity<LibroResponseDTO> crearLibro(LibroCreateDTO libroCreateDTO) {

        Libro libroNuevo = libroMapper.toEntity(libroCreateDTO);
        return ResponseEntity.ok(libroMapper.toDTO(libroNuevo));
    }
}