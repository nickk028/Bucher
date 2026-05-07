package ar.edu.huergo.vectorial.calidad.bucher.mapper.bookuser;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.huergo.vectorial.calidad.bucher.dto.bookuser.BibliotecaResponseDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.bookuser.BibliotecaBasicDTO;
import ar.edu.huergo.vectorial.calidad.bucher.entity.bookuser.Biblioteca;

@Component
// Mapper de la entidad LibroUsuario utiizada para pasar de entidad a DTO y de DTO a entidad
public class BibliotecaMapper {
    @Autowired LibroUsuarioMapper libroUsuarioMapper;

    /**
    * Pasa de entidad Biblioteca a BibliotecaBasicDto
    * @param biblioteca La entidad a transformar a DTO
    * @return biblioteca como BasicDto
    */
    public BibliotecaBasicDTO toBasicDTO(Biblioteca biblioteca) {
        return new BibliotecaBasicDTO(
            biblioteca.getId(),
            biblioteca.getNombre()
        );
    }

    /**
    * Pasa de entidad Biblioteca a BibliotecaResponseDto
    * @param biblioteca La entidad a transformar a DTO
    * @return biblioteca como ResponseDto
    */
    public BibliotecaResponseDTO toDTO(Biblioteca biblioteca) {
        return new BibliotecaResponseDTO(
            biblioteca.getId(),
            biblioteca.getNombre(),
            libroUsuarioMapper.toDTOList(biblioteca.getLibrosUsuario())
        );
    }

    /**
    * Pasa de una lista de entidades Biblioteca a una lista de BibliotecaBasicDTO
    * @param bibliotecas La lista de entidades a transformar a DTOs
    * @return Una lista de Dtos transformados
    */
    public List<BibliotecaBasicDTO> toDTOList(List<Biblioteca> bibliotecas) {
        return bibliotecas.stream()
            .map(this::toBasicDTO)
            .collect(Collectors.toList());
    }

    /**
    * Pasa de un BibliotecaResponseDto a una entidad Biblioteca
    * @param bibliotecaResponseDTO El BibliotecaResponseDTO a transofrmar
    * @return Una entidad biblioteca transformada
    */
    public Biblioteca toEntity(BibliotecaResponseDTO bibliotecaResponseDTO) {
        Biblioteca biblioteca = new Biblioteca();

        biblioteca.setId(bibliotecaResponseDTO.getId());
        biblioteca.setNombre(bibliotecaResponseDTO.getNombre());

        return biblioteca;
    }
}