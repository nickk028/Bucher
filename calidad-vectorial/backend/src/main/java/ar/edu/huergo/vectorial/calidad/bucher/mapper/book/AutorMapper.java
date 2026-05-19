package ar.edu.huergo.vectorial.calidad.bucher.mapper.book;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import ar.edu.huergo.vectorial.calidad.bucher.dto.book.AutorResponseDTO;
import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Autor;

@Component // Marca la clase como un componente de Spring
// Mapper de la entidad Autor utiizada para pasar de entidad a DTO y de DTO a entidad
public class AutorMapper {

    /**
    * Pasa de Autor a entidad AutorResponseDTO
    * @param Autor a pasar a DTO
    * @return Autor como ResponseDTO
    */
    public AutorResponseDTO toDTO(Autor autor) {
        if (autor == null) {
            return null;
        }
        AutorResponseDTO autorResponseDTO = new AutorResponseDTO();

        autorResponseDTO.setId(autor.getId());
        autorResponseDTO.setNombre(autor.getNombre());
        autorResponseDTO.setDescripcion(autor.getDescripcion());
        autorResponseDTO.setUrlWikipedia(autor.getUrlWikipedia());
        autorResponseDTO.setUrlFotoAutor(autor.getUrlFotoAutor());
        autorResponseDTO.setLibros(new ArrayList<>(autor.getLibros()));
        autorResponseDTO.setUsername(autor.getUsuario().getUsername());

        return autorResponseDTO;
    }
}