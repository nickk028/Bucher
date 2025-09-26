package ar.edu.huergo.vectorial.calidad.bucher.dto.security;

import java.util.Set;

public record UsuarioResponseDTO(String username, Set<String> roles) {
}