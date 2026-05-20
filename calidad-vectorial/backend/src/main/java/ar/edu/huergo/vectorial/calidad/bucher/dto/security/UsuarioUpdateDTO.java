package ar.edu.huergo.vectorial.calidad.bucher.dto.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Avatar;
import jakarta.validation.constraints.Size;

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
@EqualsAndHashCode(callSuper = true)
public class UsuarioUpdateDTO extends UsuarioDTO {
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