package ar.edu.huergo.vectorial.calidad.bucher.controller.book;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.huergo.vectorial.calidad.bucher.dto.book.LibroBasicDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.book.LibroResponseDTO;
import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Categoria;
import ar.edu.huergo.vectorial.calidad.bucher.mapper.book.LibroMapper;
import ar.edu.huergo.vectorial.calidad.bucher.service.book.LibroService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/libro")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @Autowired
    private LibroMapper libroMapper;

    @GetMapping("/todos")
    public ResponseEntity<List<LibroBasicDTO>> obtenerTodosLosLibros() {
        return ResponseEntity.ok(
            libroMapper.toBasicDTOList(libroService.obtenerTodosLosLibros()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibroResponseDTO> obtenerLibroPorId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
            libroMapper.toDTO(libroService.obtenerLibroPorId(id)));
    }

    @GetMapping("categoria/{categoria}")
    public ResponseEntity<List<LibroBasicDTO>> obtenerLibrosPorCategoria(@PathVariable("categoria") Categoria categoria) {
        return ResponseEntity.ok(
            libroMapper.toBasicDTOList(libroService.obtenerLibrosPorCategoria(categoria)));
    }
}