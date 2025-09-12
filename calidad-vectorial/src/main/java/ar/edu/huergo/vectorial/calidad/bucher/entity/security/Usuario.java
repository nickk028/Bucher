package ar.edu.huergo.vectorial.calidad.bucher.entity.security;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ar.edu.huergo.vectorial.calidad.bucher.entity.bookuser.Biblioteca;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.Publicacion;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.RegistroPrestamo;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Entity // Marca la clase como una entidad de JPA
@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
@Table(name = "usuarios")
public class Usuario {

    @Id // Id principal de la entidad
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera el Id automaticamente
    private Long id;

    // Nombre de a cuenta del usuario
    @Column(nullable = false, unique = true, length = 100)
    @NotBlank(message = "El username es obligatorio.")
    @NotNull(message = "El username es obligatorio.")
    @Size(min = 3, max = 100, message = "El username debe tener entre 2 y 100 digitos.")
    @Email(message = "El nombre debe ser un mail con un formato válido.")
    private String username;

    //Contraseña del usuario
    @Column(nullable = false)
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 16, max = 60, message = "La contraseña debe tener entre 16 y 60 digitos.")
    @Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!*]).*$",
        message = "La contraseña debe contener al menos una mayuscula, una minuscula, un numero y un caracter especial.")
    private String password;

    // Set de roles del usuario
    // Relacion Muchos a Muchos con Roles
    @NotNull(message = "Los roles son obligatorios")
    @NotEmpty(message = "Los roles son obligatorios")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "usuario_roles",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    private Set<Rol> roles = new HashSet<>();

    // Relacion 1 a Muchos con Pubicaciones
    @OneToMany(mappedBy = "usuario")
    private List<Publicacion> publicaciones = new ArrayList<>();

    // Reacion 1 a Muchos con Prestamos
    @OneToMany(mappedBy = "usuario")
    private List<RegistroPrestamo> prestamos = new ArrayList<>();

    // Relación 1 a 1 con biblioteca
    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Biblioteca biblioteca;

    // Constructor
    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
        this.roles = new HashSet<>();
    }

    // Funcion equals generada por @Data
    /*@Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        Usuario other = (Usuario)obj;
        return Objects.equals(id,other.id);
    }*/
}