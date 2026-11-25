package ar.edu.huergo.vectorial.calidad.bucher.entity.publication;

import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity // Marca la clase como una entidad de JPA
@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
@EqualsAndHashCode(exclude = { "publicacionSocial", "usuario" })
@Table(name = "etiquetas_publicacion_social")
public class EtiquetaPublicacionSocial {

    @Id // Id principal de la entidad
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera el Id automáticamente
    private Long id;

    // Publicación a la que pertenece la etiqueta
    // Relación muchos a 1 con PublicacionSocial
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publicacion_social_id", nullable = false)
    @NotNull(message = "La publicación es obligatoria.")
    @ToString.Exclude // Evita la recursión infinita en toString con PublicacionSocial
    private PublicacionSocial publicacionSocial;

    // Nickname escrito en la etiqueta
    @Column(nullable = false, length = 32)
    @NotBlank(message = "El nickname de la etiqueta es obligatorio.")
    @Size(max = 32, message = "El nickname debe tener como máximo 32 caracteres.")
    private String nickname;

    // Usuario etiquetado (null si el nickname no corresponde a ningún usuario)
    // Relación muchos a 1 con Usuario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = true)
    @ToString.Exclude // Evita la recursión infinita en toString con Usuario
    private Usuario usuario;
}