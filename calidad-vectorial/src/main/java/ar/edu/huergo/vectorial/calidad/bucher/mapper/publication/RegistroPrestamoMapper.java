package ar.edu.huergo.vectorial.calidad.bucher.mapper.publication;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.huergo.vectorial.calidad.bucher.dto.publication.PublicacionResponseDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.publication.RegistroPrestamoResponseDTO;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.RegistroPrestamo;

@Component // Marca la clase como un componente de Spring
// Mapper de la entidad Publicacion utiizada para pasar de entidad a DTO y de DTO a entidad
public class RegistroPrestamoMapper {
    @Autowired PublicacionMapper publicacionMapper;
    
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
        PublicacionResponseDTO publicacionResponseDTO = publicacionMapper.toDTO(registroPrestamo.getPublicacion());

        registroPrestamoResponseDTO.setPublicacion(publicacionResponseDTO);
        registroPrestamoResponseDTO.setFechaPrestamo(registroPrestamo.getFechaPrestamo());
        registroPrestamoResponseDTO.setFechaDevolucion(registroPrestamo.getFechaDevolucion());

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
        return registrosPrestamo
            .stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
}
