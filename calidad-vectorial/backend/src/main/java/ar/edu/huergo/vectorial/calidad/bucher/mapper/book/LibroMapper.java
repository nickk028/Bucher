package ar.edu.huergo.vectorial.calidad.bucher.mapper.book;

import org.springframework.stereotype.Component;

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

        return libroResponseDTO;
    }
}
