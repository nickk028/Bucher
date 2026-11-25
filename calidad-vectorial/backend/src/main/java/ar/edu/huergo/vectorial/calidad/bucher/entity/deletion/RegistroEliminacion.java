package ar.edu.huergo.vectorial.calidad.bucher.entity.deletion;

import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

@Entity // Marca la clase como una entidad de JPA
@Inheritance(strategy = InheritanceType.JOINED) // Cada subclase concreta tendrá su propia tabla enlazada por Id
@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
@Table(name = "registro_eliminacion")
public abstract class RegistroEliminacion {

    @Id // Id principal de la entidad
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera el Id automáticamente
    private Long id;

    // Fecha y hora en la que se eliminó
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime fechaEliminacion;

    // Razón de la eliminación
    @Column(nullable = false, length = 255)
    @NotBlank(message = "La razón de la eliminación es obligatoria.")
    @Size(max = 255, message = "La razón debe tener como máximo 255 caracteres.")
    private String razon;

    // Usuario que realizó la eliminación
    // Relación muchos a 1 con Usuario
    @ManyToOne
    @JoinColumn(name = "usuario_eliminador_id", nullable = false)
    @NotNull(message = "El usuario que elimina es obligatorio.")
    @ToString.Exclude // Evita la recursión infinita en toString con Usuario
    private Usuario usuarioEliminador;
}