package ar.edu.huergo.vectorial.calidad.bucher.dto.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Avatar;

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
@EqualsAndHashCode(callSuper = true) // Genera equals y hashCode incluyendo los campos de la clase padre
public class UsuarioResponseDTO extends UsuarioDTO {
    String nickname;
    Avatar avatar;

    String pronombres;
    String descripcion;
    String direccion;
    String piso;
    String codigoPostal;
}