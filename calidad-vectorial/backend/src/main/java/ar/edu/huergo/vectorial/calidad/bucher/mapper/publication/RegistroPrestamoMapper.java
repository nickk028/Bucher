package ar.edu.huergo.vectorial.calidad.bucher.mapper.publication;

import ar.edu.huergo.vectorial.calidad.bucher.dto.admin.PrestamoControlDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.publication.PublicacionResponseDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.publication.RegistroPrestamoResponseDTO;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.RegistroPrestamo;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component // Marca la clase como un componente de Spring
// Mapper de la entidad Publicacion utiizada para pasar de entidad a DTO y de DTO a entidad
public class RegistroPrestamoMapper {

    @Autowired
    PublicacionMapper publicacionMapper;

    /**
     * Convierte una entidad RegistroPrestamo a un DTO RegistroPrestamoResponseDTO
     * @param registroPrestamo La entidad RegistroPrestamo a convertir
     * @return El DTO RegistroPrestamoResponseDTO resultante
     */
    public RegistroPrestamoResponseDTO toDTO(RegistroPrestamo registroPrestamo) {
        if (registroPrestamo == null) {
            return null;
        }

        RegistroPrestamoResponseDTO registroPrestamoResponseDTO = new RegistroPrestamoResponseDTO();

        PublicacionResponseDTO publicacionResponseDTO = publicacionMapper.toDTO(
            registroPrestamo.getPublicacion()
        );

        registroPrestamoResponseDTO.setId(registroPrestamo.getId());
        registroPrestamoResponseDTO.setPublicacion(publicacionResponseDTO);
        registroPrestamoResponseDTO.setFechaPrestamo(registroPrestamo.getFechaPrestamo());
        registroPrestamoResponseDTO.setFechaDevolucion(registroPrestamo.getFechaDevolucion());
        registroPrestamoResponseDTO.setAvatarUsuarioPrestamista(registroPrestamo.getUsuario().getAvatar().getUrl());
        registroPrestamoResponseDTO.setUsuarioPrestamista(registroPrestamo.getUsuario().getUsername());
        registroPrestamoResponseDTO.setNickanmeUsuarioPrestamista(registroPrestamo.getUsuario().getNickname());

        registroPrestamoResponseDTO.setConfirmacionTitulo(registroPrestamo.getConfirmacionTitulo());
        registroPrestamoResponseDTO.setConfirmacionAutor(registroPrestamo.getConfirmacionAutor());
        registroPrestamoResponseDTO.setConfirmacionDetallesEstado(registroPrestamo.getConfirmacionDetallesEstado());
        registroPrestamoResponseDTO.setConfirmacionDetallesEstadoLibro(
            registroPrestamo.getConfirmacionDetallesEstadoLibro()
        );
        registroPrestamoResponseDTO.setComentariosAdicionales(registroPrestamo.getComentariosAdicionales());
        registroPrestamoResponseDTO.setEstaEnCondiciones(registroPrestamo.getEstaEnCondiciones());
        registroPrestamoResponseDTO.setMotivoNoEnCondiciones(registroPrestamo.getMotivoNoEnCondiciones());

        return registroPrestamoResponseDTO;
    }

    /**
     * Convierte una lista de entidades RegistroPrestamo a una lista de DTOs RegistroPrestamoResponseDTO
     * @param registrosPrestamo La lista de entidades RegistroPrestamo a convertir
     * @return La lista de DTOs RegistroPrestamoResponseDTO resultante
     */
    public List<RegistroPrestamoResponseDTO> toDTOList(List<RegistroPrestamo> registrosPrestamo) {
        if (registrosPrestamo == null) {
            return new ArrayList<>();
        }
        return registrosPrestamo.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public RegistroPrestamo toEntity(PrestamoControlDTO prestamoControlDTO) {
        if (prestamoControlDTO == null) {
            return null;
        }

        RegistroPrestamo registroPrestamo = new RegistroPrestamo();

        registroPrestamo.setId(prestamoControlDTO.getId());
        registroPrestamo.setConfirmacionTitulo(prestamoControlDTO.getConfirmacionTitulo());
        registroPrestamo.setConfirmacionAutor(prestamoControlDTO.getConfirmacionAutor());
        registroPrestamo.setConfirmacionDetallesEstado(prestamoControlDTO.getConfirmacionDetallesEstado());
        registroPrestamo.setConfirmacionDetallesEstadoLibro(
            prestamoControlDTO.getConfirmacionDetallesEstadoLibro()
        );
        registroPrestamo.setComentariosAdicionales(prestamoControlDTO.getComentariosAdicionales());
        registroPrestamo.setEstaEnCondiciones(prestamoControlDTO.getEstaEnCondiciones());
        registroPrestamo.setMotivoNoEnCondiciones(prestamoControlDTO.getMotivoNoEnCondiciones());

        return registroPrestamo;
    }
}
