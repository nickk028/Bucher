package ar.edu.huergo.vectorial.calidad.bucher.mapper.book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import ar.edu.huergo.vectorial.calidad.bucher.dto.book.LibroBasicDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.book.LibroCreateDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.book.LibroResponseDTO;
import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Categoria;
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
        libroResponseDTO.setPrecio(libro.getPrecio());
        libroResponseDTO.setCategorias(libro.getCategoria());
        libroResponseDTO.setNombreAutor(libro.getAutor().getNombre());
        libroResponseDTO.setDescripcionAutor(libro.getAutor().getDescripcion());
        libroResponseDTO.setUrlWikipediaAutor(libro.getAutor().getUrlWikipedia());
        libroResponseDTO.setUrlFotoAutor(libro.getAutor().getUrlFotoAutor());

        return libroResponseDTO;
    }

    /**
    * Convierte un mapa de categorías con sus libros en un mapa de categorías con sus DTOs
    * @param original El mapa original con categorías y sus conjuntos de libros
    * @return Mapa con cada categoría y su lista de LibroResponseDTO correspondiente
    */
    public Map<Categoria, List<LibroResponseDTO>> toDTOMap(Map<Categoria, Set<Libro>> original) {
        Map<Categoria, List<LibroResponseDTO>> resultado = new HashMap<>();
        for (Map.Entry<Categoria, Set<Libro>> entry : original.entrySet()) {
            Set<Libro> librosDeLaCategoria = entry.getValue();
            List<LibroResponseDTO> dtos = toResponseDTOList(librosDeLaCategoria);
            Categoria categoria = entry.getKey();
            resultado.put(categoria, dtos);
        }
        return (resultado);
    }

    /**
    * Convierte un Libro en un LibroBasicDTO
    * @param libro El libro a convertir
    * @return El LibroBasicDTO con los datos básicos del libro, o null si el libro es null
    */
    public LibroBasicDTO toBasicDTO(Libro libro) {
        if (libro == null) {
            return null;
        }
        LibroBasicDTO libroBasicDTO = new LibroBasicDTO();

        libroBasicDTO.setId(libro.getId());
        libroBasicDTO.setTitulo(libro.getTitulo());
        libroBasicDTO.setUrlFoto(libro.getUrlFoto());

        return libroBasicDTO;
    }

    /**
    * Convierte un conjunto de Libros en una lista de LibroBasicDTO
    * @param libros El conjunto de libros a convertir
    * @return Lista de LibroBasicDTO, o una lista vacía si el conjunto es null
    */
    public List<LibroBasicDTO> toBasicDTOList(Set<Libro> libros) {
        if (libros == null) {
            return new ArrayList<>();
        }
        return libros
            .stream()
            .map(this::toBasicDTO)
            .collect(Collectors.toList());
    }

    /**
    * Convierte un conjunto de Libros en una lista de LibroResponseDTO
    * @param libros El conjunto de libros a convertir
    * @return Lista de LibroResponseDTO, o una lista vacía si el conjunto es null
    */
    public List<LibroResponseDTO> toResponseDTOList(Set<Libro> libros) {
        if (libros == null) {
            return new ArrayList<>();
        }
        return libros
            .stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    public Libro toEntity(LibroCreateDTO libroCreateDTO) {
        if (libroCreateDTO == null) {
            return null;
        }
        Libro libro = new Libro();

        libro.setId(libroCreateDTO.getId());
        libro.setTitulo(libroCreateDTO.getTitulo());
        libro.setDescripcion(libroCreateDTO.getDescripcion());
        libro.setCategoria(libroCreateDTO.getCategorias());
        libro.setPaginas(libroCreateDTO.getPaginas());
        libro.setEdicion(libroCreateDTO.getEdicion());
        libro.setFechaPublicacion(libroCreateDTO.getFechaPublicacion());
        return libro;
    }
}
