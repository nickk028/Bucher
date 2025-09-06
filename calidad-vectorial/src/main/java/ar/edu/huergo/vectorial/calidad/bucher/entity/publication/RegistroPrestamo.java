package ar.edu.huergo.vectorial.calidad.bucher.entity.publication;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "registro_prestamos")
public class RegistroPrestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "La fecha de préstamo es obligatoria.")
    private LocalDate fechaPrestamo;

    @Column(nullable = false)
    @NotNull(message = "La fecha de devolución es obligatoria.")
    private LocalDate fechaDevolucion;

    @OneToOne
    @JoinTable(name = "registroPrestamo_publicaciones",
    joinColumns = @JoinColumn(name = "registroPrestamo_id"),
    inverseJoinColumns = @JoinColumn(name = "publicacion_id"))
    private Publicacion publicacion;
}