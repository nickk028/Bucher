package ar.edu.huergo.vectorial.calidad.bucher.entity.publication;

import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity // Marca la clase como una entidad de JPA
@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
@EqualsAndHashCode(exclude = { "usuario", "publicacionSocial" })
@Table(
    name = "reportes_publicacion_social",
    uniqueConstraints = @UniqueConstraint(columnNames = { "usuario_id", "publicacion_social_id" })
)
public class ReportePublicacionSocial {

    @Id // Id principal de la entidad
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera el Id automáticamente
    private Long id;

    // Usuario que reportó
    // Relación muchos a 1 con Usuario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    @NotNull(message = "El usuario es obligatorio.")
    @ToString.Exclude // Evita la recursión infinita en toString con Usuario
    private Usuario usuario;

    // Publicación reportada
    // Relación muchos a 1 con PublicacionSocial
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publicacion_social_id", nullable = false)
    @NotNull(message = "La publicación es obligatoria.")
    @ToString.Exclude // Evita la recursión infinita en toString con PublicacionSocial
    private PublicacionSocial publicacionSocial;
}