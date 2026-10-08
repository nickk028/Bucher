package ar.edu.huergo.vectorial.calidad.bucher.config.seed.dto;

import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Avatar;
import java.util.Set;

public record UsuarioSeed(
    String username,
    String password,
    String nickname,
    Avatar avatar,
    Set<String> roles,
    String pronombres,
    String descripcion,
    String direccion,
    String piso,
    String codigoPostal
) {}