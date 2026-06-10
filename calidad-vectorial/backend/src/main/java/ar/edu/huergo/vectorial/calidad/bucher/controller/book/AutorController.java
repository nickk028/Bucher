package ar.edu.huergo.vectorial.calidad.bucher.controller.book;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.huergo.vectorial.calidad.bucher.dto.book.AutorResponseDTO;
import ar.edu.huergo.vectorial.calidad.bucher.mapper.book.AutorMapper;
import ar.edu.huergo.vectorial.calidad.bucher.service.book.AutorService;

@RestController
@RequestMapping("/autor")
public class AutorController {
    @Autowired
    private AutorService autorService;

    @Autowired
    private AutorMapper autorMapper;

    /**
    * Obtiene todos los autores
    * @return Response entity con la lista de todos los autores
    */
    @GetMapping("/todos")
    public ResponseEntity<List<AutorResponseDTO>> obtenerTodosLosAutores() {
        return ResponseEntity.ok(autorMapper.toDTOList(autorService.obtenerTodosLosAutores()));
    }

    /**
    * Obtiene un autor por su ID
    * @param id
    * @return Response entity con el autor encontrado o error si no se encuentra
    */
    @GetMapping("/{id}")
    public ResponseEntity<AutorResponseDTO> obtenerAutorPorId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(autorMapper.toDTO(autorService.obtenerAutorPorId(id)));
    }
}