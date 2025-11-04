package ar.edu.huergo.vectorial.calidad.bucher.dto.security;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Avatar;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Rol;
import jakarta.validation.constraints.Size;

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
public class UsuarioUpdateDTO {
    // Atributos obligatorios para pasarlo a entidad
    String username;
    Set<Rol> roles;

    // Atributos modificables por el usuario
    Avatar avatar;
    String nickname;
    String pronombres;
    @Size(max = 255, message = "La descripción debe tener como máximo 255 dígitos")
    String descripcion;
    String direccion;
    String piso;
    String codigoPostal;
}