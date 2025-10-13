package ar.edu.huergo.vectorial.calidad.bucher.mapper.book;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import ar.edu.huergo.vectorial.calidad.bucher.dto.book.LibroBasicDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.book.LibroResponseDTO;
import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Libro;



@Component // Marca la clase como un componente de Spring
// Mapper de la entidad Libro utiizada para pasar de entidad a DTO y de DTO a entidad
public class LibroMapper {

    /**
     * Pasa de Libro a entidad LibroResponseDTO
     * @param Libro a pasar a DTO
     * @return Libro como ResponseDTO
     */
    public LibroResponseDTO toDTO(Libro libro) {
        if (libro == null) {
            return null;
        }
        LibroResponseDTO libroResponseDTO = new LibroResponseDTO();

        libroResponseDTO.setId(libro.getId());
        libroResponseDTO.setTitulo(libro.getTitulo());
        libroResponseDTO.setUrlFoto(libro.getUrlFoto());
        libroResponseDTO.setDescripcion(libro.getDescripcion());
        libroResponseDTO.setCategorias(libro.getCategoria());
        libroResponseDTO.setNombreAutor(libro.getAutor().getNombre());
        libroResponseDTO.setDescripcionAutor(libro.getAutor().getDescripcion());
        libroResponseDTO.setUrlWikipediaAutor(libro.getAutor().getUrlWikipedia());
        libroResponseDTO.setUrlFotoAutor(libro.getAutor().getUrlFotoAutor());

        return libroResponseDTO;
    }

    public LibroBasicDTO toBasicDTO(Libro libro) {
        if (libro == null) {
            return null;
        }
        LibroBasicDTO libroBasicDTO = new LibroBasicDTO();

        libroBasicDTO.setId(libro.getId());
        libroBasicDTO.setUrlFoto(libro.getUrlFoto());

        return libroBasicDTO;
    }

    public List<LibroBasicDTO> toBasicDTOList(Set<Libro> libros) {
        if (libros == null) {
            return new ArrayList<>();
        }
        return libros
            .stream()
            .map(this::toBasicDTO)
            .collect(Collectors.toList());
    }
}
