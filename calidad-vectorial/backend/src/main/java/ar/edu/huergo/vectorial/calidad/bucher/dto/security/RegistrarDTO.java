package ar.edu.huergo.vectorial.calidad.bucher.dto.security;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

// DTO para la solicitud de registro de un nuevo usuario
public record RegistrarDTO(

    // Nombre de usuario
    @NotBlank(message = "El username es obligatorio")
    @Email(message = "El nombre debe ser un mail con un formato valido")
    String username,

    // Contraseña del usuario
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 16, max = 60, message = "La contraseña debe tener entre 16 y 60 digitos")
    @Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!*]).*$",
        message = "La contraseña debe contener al menos una mayuscula, una minuscula, un numero y un caracter especial")
    String password,

    // Verificación de la contraseña del usuario
    @NotBlank(message = "La verificación de la contraseña es obligatoria")
    @Size(min = 16, max = 60, message = "La contraseña debe tener entre 16 y 60 digitos")
    @Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).*$",
        message = "La contraseña debe contener al menos una mayuscula, una minuscula, un numero y un caracter especial")
    String verificationPassword,

    // Nick name de la cuenta del usuario
    @Column(nullable = false, unique = true, length = 100)
    @NotBlank(message = "El nickname es obligatorio.")
    @NotNull(message = "El nickname es obligatorio.")
    @Size(min = 3, max = 100, message = "El nickname debe tener entre 2 y 100 digitos.")
    String nickname,

    //Direccion
    @Column(nullable = true)
    @Size(max = 255, message = "La direccion debe tener como máximo 255 dígitos")
    String direccion,

    //Piso
    @Column(nullable = true)
    @Size(max = 255, message = "El piso debe tener como máximo 255 dígitos")
    String piso,

    //Codigo Postal
    @Column(nullable = true)
    @Size(max = 255, message = "El código postal debe tener como máximo 255 dígitos")
    String codigoPostal) {
}