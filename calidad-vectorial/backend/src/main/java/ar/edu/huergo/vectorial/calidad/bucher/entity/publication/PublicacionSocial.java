package ar.edu.huergo.vectorial.calidad.bucher.entity.publication;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Libro;
import ar.edu.huergo.vectorial.calidad.bucher.entity.deletion.RegistroEliminacionPublicacionSocial;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;

@Entity // Marca la clase como una entidad de JPA
@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
@Table(name = "publicaciones_sociales")
@BatchSize(size = 50) // Carga en lote las referencias lazy para evitar consultas una por una
@EqualsAndHashCode(exclude = { "etiquetas", "publicacionReferenciada", "registroEliminacion" })
// Excluye las relaciones de la comparación de igualdad y hashCode para evitar problemas de recursión infinita
public class PublicacionSocial {

    @Id // Id principal de la entidad
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera el Id automáticamente
    private Long id;

    // Usuario que creó la publicación
    // Relacion Muchos a 1 con la tabla usuario
    @ManyToOne
    @NotNull(message = "El usuario es obligatorio.")
    @JoinColumn(name = "usuario_id", nullable = false)
    @ToString.Exclude // Evita la recursión infinita en toString con Usuario
    private Usuario usuario;

    // Fecha y hora de creación de la publicación
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime fechaCreacion;

    // Texto de la publicación
    @Column(nullable = false, length = 500)
    @NotBlank(message = "El texto de la publicación es obligatorio.")
    @Size(
        min = 1,
        max = 500,
        message = "El texto de la publicación debe tener entre 1 y 500 caracteres."
    )
    private String textoPublicacion;

    // Indica si la publicación contiene spoilers (lo ingresa el usuario)
    @Column(nullable = false)
    private boolean contieneSpoilers;

    // Relación muchos a 1 con Libro (opcional)
    @ManyToOne
    @JoinColumn(name = "libro_id", nullable = true)
    @ToString.Exclude // Evita la recursión infinita en toString con Libro
    private Libro libro;

    // Relación muchos a 1 con la publicación a la que responde (opcional)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publicacion_referenciada_id", nullable = true)
    @ToString.Exclude // Evita la recursión infinita en toString con PublicacionSocial
    private PublicacionSocial publicacionReferenciada;

    // Relación 1 a muchos con EtiquetaPublicacionSocial
    @OneToMany(mappedBy = "publicacionSocial", cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 50)
    @ToString.Exclude // Evita la recursión infinita en toString con EtiquetaPublicacionSocial
    private List<EtiquetaPublicacionSocial> etiquetas = new ArrayList<>();

    // Cantidad de votos positivos
    @Column(nullable = false)
    private int votosPositivos;

    // Cantidad de votos negativos
    @Column(nullable = false)
    private int votosNegativos;

    // Cantidad de reportes
    @Column(nullable = false)
    private int reportes;

    // Registro de eliminación (solo existe en publicaciones eliminadas)
    // Relación 1 a 1 con RegistroEliminacionPublicacionSocial
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "registro_eliminacion_id", nullable = true, unique = true)
    @ToString.Exclude // Evita la recursión infinita en toString con RegistroEliminacionPublicacionSocial
    private RegistroEliminacionPublicacionSocial registroEliminacion;
}