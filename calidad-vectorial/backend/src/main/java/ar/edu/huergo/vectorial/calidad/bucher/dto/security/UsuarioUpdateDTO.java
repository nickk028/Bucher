package ar.edu.huergo.vectorial.calidad.bucher.dto.security;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Avatar;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Rol;

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
public class UsuarioUpdateDTO {
    String username;
    Set<Rol> roles;
    Avatar avatar;
    Set<String> pronombres;
    String descripcion;
}