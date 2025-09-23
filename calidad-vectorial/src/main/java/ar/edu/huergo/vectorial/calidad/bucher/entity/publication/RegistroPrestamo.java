package ar.edu.huergo.vectorial.calidad.bucher.entity.publication;

import java.time.LocalDate;

import org.hibernate.annotations.CurrentTimestamp;

import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // Marca la clase como una entidad de JPA
@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
@Table(name = "registro_prestamos")
public class RegistroPrestamo {

    @Id // Id principal de la entidad
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera el Id automáticamente
    private Long id;

    // Fecha de inicio del préstamo
    @Column(nullable = false)
    @CurrentTimestamp
    private LocalDate fechaPrestamo;

    // Fecha de devolución del préstamo
    @Column(nullable = true)
    private LocalDate fechaDevolucion;

    // Relación 1 a 1 con Publicacion
    @ManyToOne
    @JoinColumn(name = "publicacion_id")
    private Publicacion publicacion;

    // Relacion Muchos a 1 con Usuario
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}