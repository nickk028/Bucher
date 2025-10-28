package ar.edu.huergo.vectorial.calidad.bucher.dto.security;

import java.util.Set;

import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Avatar;

public record UsuarioResponseDTO(String username, Avatar avatar, Set<String> roles, String pronombres, String descripcion) {
}