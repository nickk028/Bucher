package ar.edu.huergo.vectorial.calidad.bucher.mapper.bookuser;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import ar.edu.huergo.vectorial.calidad.bucher.dto.bookuser.LibroUsuarioCreateDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.bookuser.LibroUsuarioResponseDTO;
import ar.edu.huergo.vectorial.calidad.bucher.entity.bookuser.LibroUsuario;

@Component
// Mapper de la entidad LibroUsuario utiizada para pasar de entidad a DTO y de DTO a entidad
public class LibroUsuarioMapper {

    /**
     * Pasa de LibroUsuarioResponseDTO a entidad LibroUsuario
     * @param PubicacionCreateDTO El DTO a transformar en entidad
     * @return LibroUsuario como entidad
     */
    public LibroUsuario toEntity(LibroUsuarioCreateDTO libroUsuarioCreateDTO) {
        LibroUsuario libroUsuario = new LibroUsuario();
        libroUsuario.setEstadoLectura(libroUsuarioCreateDTO.getEstadoLectura());
        libroUsuario.setPaginaActual(libroUsuarioCreateDTO.getPaginaActual());
        libroUsuario.setPuntuacion(libroUsuarioCreateDTO.getPuntuacion());

        return libroUsuario;
    }

    public LibroUsuarioResponseDTO toDTO(LibroUsuario libroUsuario) {
        if (libroUsuario == null) {
            return null;
        }
        LibroUsuarioResponseDTO libroUsuarioResponseDTO = new LibroUsuarioResponseDTO();
        libroUsuarioResponseDTO.setTitulo(libroUsuario.getLibro().getTitulo());
        libroUsuarioResponseDTO.setEstadoLectura(libroUsuario.getEstadoLectura());
        libroUsuarioResponseDTO.setPaginaActual(libroUsuario.getPaginaActual());
        libroUsuarioResponseDTO.setPuntuacion(libroUsuario.getPuntuacion());

        return libroUsuarioResponseDTO;
    }

    public List<LibroUsuarioResponseDTO> toDTOList(List<LibroUsuario> librosUsuario) {
        if (librosUsuario == null) {
            return null;
        }
        return librosUsuario
            .stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
}