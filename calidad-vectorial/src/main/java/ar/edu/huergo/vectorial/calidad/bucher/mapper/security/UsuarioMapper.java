package ar.edu.huergo.vectorial.calidad.bucher.mapper.security;

import java.util.Set;
import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Component;

import ar.edu.huergo.vectorial.calidad.bucher.dto.security.RegistrarDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.security.UsuarioResponseDTO;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Rol;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;

@Component
public class UsuarioMapper {
    public UsuarioResponseDTO toDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        return new UsuarioResponseDTO(
            usuario.getUsername(),
            new HashSet<>(
                usuario.getRoles()
                    .stream()
                    .map(Rol::getNombre)
                    .toList()));
    }

    public List<UsuarioResponseDTO> toDTOList(Set<Usuario> usuarios) {
        return usuarios
            .stream()
            .map(this::toDTO)
            .toList();
    }

    public Usuario toEntity(RegistrarDTO registrarDTO) {
        if (registrarDTO == null) {
            return null;
        }
        Usuario usuario = new Usuario();
        usuario.setUsername(registrarDTO.username());
        return usuario;
    }
}