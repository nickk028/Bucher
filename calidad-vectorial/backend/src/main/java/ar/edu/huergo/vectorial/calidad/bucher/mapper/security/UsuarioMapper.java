package ar.edu.huergo.vectorial.calidad.bucher.mapper.security;

import java.util.Set;
import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Component;

import ar.edu.huergo.vectorial.calidad.bucher.dto.security.RegistrarDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.security.UsuarioResponseDTO;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Rol;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;

@Component // Marca la clase como un componente de Spring
// Mapper de la entidad Usuario utiizada para pasar de entidad a DTO y de DTO a entidad
public class UsuarioMapper {

    /*
        * Convierte una entidad Usuario a un DTO UsuarioResponseDTO
        * @param usuario La entidad Usuario a convertir
        * @return El DTO UsuarioDTO correspondiente
    */
    public UsuarioResponseDTO toDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        return new UsuarioResponseDTO(
            usuario.getUsername(),
            usuario.getAvatar(),
            new HashSet<>(
                usuario.getRoles()
                    .stream()
                    .map(Rol::getNombre)
                    .toList()));
    }

    /*
        * Convierte un conjunto de entidades Usuario a una lista de DTOs UsuarioDTO
        * @param usuarios El Set de Usuarios a convertir
        * @return La lista de DTOs UsuarioDTO correspondientes
    */
    public List<UsuarioResponseDTO> toDTOList(Set<Usuario> usuarios) {
        return usuarios
            .stream()
            .map(this::toDTO)
            .toList();
    }

    /*
        * Convierte un DTO RegistrarDTO a una entidad Usuario
        * @param registrarDTO El DTO RegistrarDTO a convertir
        * @return La entidad Usuario correspondiente
    */
    public Usuario toEntity(RegistrarDTO registrarDTO) {
        if (registrarDTO == null) {
            return null;
        }
        Usuario usuario = new Usuario();
        usuario.setUsername(registrarDTO.username());
        return usuario;
    }
}