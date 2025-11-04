package ar.edu.huergo.vectorial.calidad.bucher.dto.security;

import java.util.Set;

import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Avatar;

public record UsuarioResponseDTO(
    String username, 
    String nickname,
    Avatar avatar, 
    Set<String> roles, 
    
    String pronombres, 
    String descripcion,
    String direccion,
    String piso,
    String codigoPostal
    ) {
}