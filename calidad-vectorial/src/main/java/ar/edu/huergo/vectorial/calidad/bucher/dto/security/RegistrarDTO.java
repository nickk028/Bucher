package ar.edu.huergo.vectorial.calidad.bucher.dto.security;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegistrarDTO(
    @NotBlank(message = "El username es obligatorio")
    @Email(message = "El nombre debe ser un mail con un formato valido")
    String username,

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 16, max = 60, message = "La contraseña debe tener entre 16 y 60 digitos")
    @Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!*]).*$",
        message = "La contraseña debe contener al menos una mayuscula, una minuscula, un numero y un caracter especial")
    String password,

    @NotBlank(message = "La verificación de la contraseña es obligatoria")
    @Size(min = 16, max = 60, message = "La contraseña debe tener entre 16 y 60 digitos")
    @Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).*$",
        message = "La contraseña debe contener al menos una mayuscula, una minuscula, un numero y un caracter especial")
    String verificationPassword) {
}