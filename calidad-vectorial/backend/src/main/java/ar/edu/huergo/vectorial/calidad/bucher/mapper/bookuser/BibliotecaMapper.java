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

    public BibliotecaBasicDTO toBasicDTO(Biblioteca biblioteca) {
        return new BibliotecaBasicDTO(
            biblioteca.getId(),
            biblioteca.getNombre()
        );
    }

    public BibliotecaResponseDTO toDTO(Biblioteca biblioteca) {
        return new BibliotecaResponseDTO(
            biblioteca.getId(),
            biblioteca.getNombre(),
            libroUsuarioMapper.toDTOList(biblioteca.getLibrosUsuario())
        );
    }

    public List<BibliotecaBasicDTO> toDTOList(List<Biblioteca> bibliotecas) {
        return bibliotecas.stream()
            .map(this::toBasicDTO)
            .collect(Collectors.toList());
    }

    public Biblioteca toEntity(BibliotecaResponseDTO bibliotecaResponseDTO) {
        Biblioteca biblioteca = new Biblioteca();

        biblioteca.setId(bibliotecaResponseDTO.getId());
        biblioteca.setNombre(bibliotecaResponseDTO.getNombre());

        return biblioteca;
    }
}