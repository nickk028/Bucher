package ar.edu.huergo.vectorial.calidad.bucher.mapper.security;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import ar.edu.huergo.vectorial.calidad.bucher.dto.security.RegistrarDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.security.UsuarioResponseDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.security.UsuarioUpdateDTO;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;

@Component // Marca la clase como un componente de Spring
// Mapper de la entidad Usuario utiizada para pasar de entidad a DTO y de DTO a entidad
public class UsuarioMapper {

    /**
    * Convierte una entidad Usuario a un DTO UsuarioResponseDTO
    * @param usuario La entidad Usuario a convertir
    * @return El DTO UsuarioDTO correspondiente
    */
    public UsuarioResponseDTO toDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }

        UsuarioResponseDTO usuarioDTO = new UsuarioResponseDTO();
        usuarioDTO.setUsername(usuario.getUsername());
        usuarioDTO.setNickname(usuario.getNickname());
        usuarioDTO.setAvatar(usuario.getAvatar());
        usuarioDTO.setPronombres(usuario.getPronombres());
        usuarioDTO.setDescripcion(usuario.getDescripcion());
        usuarioDTO.setDireccion(usuario.getDireccion());
        usuarioDTO.setPiso(usuario.getPiso());
        usuarioDTO.setCodigoPostal(usuario.getCodigoPostal());
        usuarioDTO.setRoles(new HashSet<>(usuario.getRoles()));
        return usuarioDTO;
    }

    /**
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

    /**
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
        usuario.setNickname(registrarDTO.nickname());
        usuario.setDireccion(registrarDTO.direccion());
        usuario.setPiso(registrarDTO.piso());
        usuario.setCodigoPostal(registrarDTO.codigoPostal());
        return usuario;
    }

    /**
    * Convierte un UsuarioUpdateDTO en una entidad Usuario
    * @param usuarioDTO El UsuarioUpdateDTO a convertir
    * @return La entidad Usuario con los datos del DTO, o null si el DTO es null
    */
    public Usuario toEntity(UsuarioUpdateDTO usuarioDTO) {
        if (usuarioDTO == null) {
            return null;
        }
        Usuario usuario = new Usuario();
        usuario.setUsername(usuarioDTO.getUsername());
        usuario.setNickname(usuarioDTO.getNickname());
        usuario.setRoles(usuarioDTO.getRoles());
        usuario.setAvatar(usuarioDTO.getAvatar());
        usuario.setPronombres(usuarioDTO.getPronombres());
        usuario.setDescripcion(usuarioDTO.getDescripcion());
        usuario.setDireccion(usuarioDTO.getDireccion());
        usuario.setPiso(usuarioDTO.getPiso());
        usuario.setCodigoPostal(usuarioDTO.getCodigoPostal());
        return usuario;
    }
}