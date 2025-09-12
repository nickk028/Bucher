package ar.edu.huergo.vectorial.calidad.bucher.mapper.bookuser;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.huergo.vectorial.calidad.bucher.dto.bookuser.BibliotecaResponseDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.bookuser.LibroUsuarioResponseDTO;
import ar.edu.huergo.vectorial.calidad.bucher.entity.bookuser.Biblioteca;

@Component
// Mapper de la entidad Biblioteca utiizada para pasar de entidad a DTO y de DTO a entidad
public class BibliotecaMapper {

    @Autowired LibroUsuarioMapper libroUsuarioMapper;

    public BibliotecaResponseDTO toDTO(Biblioteca biblioteca) {
        if (biblioteca == null) {
            return null;
        }
        List<LibroUsuarioResponseDTO> librosUsuarioResponseDTO = libroUsuarioMapper.toDTOList(biblioteca.getLibrosUsuario());
        return new BibliotecaResponseDTO(librosUsuarioResponseDTO);
    }
}