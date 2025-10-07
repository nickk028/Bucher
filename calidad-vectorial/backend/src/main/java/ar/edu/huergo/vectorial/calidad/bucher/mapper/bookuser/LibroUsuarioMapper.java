package ar.edu.huergo.vectorial.calidad.bucher.mapper.bookuser;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import ar.edu.huergo.vectorial.calidad.bucher.dto.bookuser.LibroUsuarioCreateDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.bookuser.LibroUsuarioResponseDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.bookuser.LibroUsuarioUpdateDTO;
import ar.edu.huergo.vectorial.calidad.bucher.entity.bookuser.LibroUsuario;

@Component
// Mapper de la entidad LibroUsuario utiizada para pasar de entidad a DTO y de DTO a entidad
public class LibroUsuarioMapper {

    /**
     * Pasa de LibroUsuarioResponseDTO a entidad LibroUsuario
     * @param libroUsuarioCreateDTO El DTO a transformar en entidad
     * @return LibroUsuario como entidad
     */
    public LibroUsuario toEntity(LibroUsuarioCreateDTO libroUsuarioCreateDTO) {
        LibroUsuario libroUsuario = new LibroUsuario();
        libroUsuario.setEstadoLectura(libroUsuarioCreateDTO.getEstadoLectura());
        libroUsuario.setPaginaActual(libroUsuarioCreateDTO.getPaginaActual());
        libroUsuario.setPuntuacion(libroUsuarioCreateDTO.getPuntuacion());

        return libroUsuario;
    }

    /**
     * Pasa de LibroUsuarioUpdateDTO a entidad LibroUsuario
     * @param libroUsuarioUpdateDTO El DTO a transformar en entidad
     * @return LibroUsuario como entidad
     */
    public LibroUsuario toEntity(LibroUsuarioUpdateDTO libroUsuarioUpdateDTO) {
        LibroUsuario libroUsuario = new LibroUsuario();

        libroUsuario.setId(libroUsuarioUpdateDTO.getId());
        libroUsuario.setEstadoLectura(libroUsuarioUpdateDTO.getEstadoLectura());
        libroUsuario.setPaginaActual(libroUsuarioUpdateDTO.getPaginaActual());
        libroUsuario.setPuntuacion(libroUsuarioUpdateDTO.getPuntuacion());

        return libroUsuario;
    }

    /**
     * Pasa de entidad libroUsuario a LibroUsuarioResponseDTO 
     * @param libroUsuario La entidad a transformar en DTO
     * @return LibroUsuarioResponseDTO como entidad
     */
    public LibroUsuarioResponseDTO toDTO(LibroUsuario libroUsuario) {
        if (libroUsuario == null) {
            return null;
        }
        LibroUsuarioResponseDTO libroUsuarioResponseDTO = new LibroUsuarioResponseDTO();
        libroUsuarioResponseDTO.setId(libroUsuario.getId());
        libroUsuarioResponseDTO.setTitulo(libroUsuario.getLibro().getTitulo());
        libroUsuarioResponseDTO.setEstadoLectura(libroUsuario.getEstadoLectura());
        libroUsuarioResponseDTO.setPaginaActual(libroUsuario.getPaginaActual());
        libroUsuarioResponseDTO.setPuntuacion(libroUsuario.getPuntuacion());

        return libroUsuarioResponseDTO;
    }

    /**
     * Pasa de una lista de entidades LibroUsuario a una lista de LibroUsuarioResponseDTO
     * @param librosUsuario La lista de entidades a transformar en DTOs
     * @return Lista de LibroUsuarioResponseDTO
     */
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