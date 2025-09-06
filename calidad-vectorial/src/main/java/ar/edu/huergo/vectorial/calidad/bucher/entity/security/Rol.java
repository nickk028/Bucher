package ar.edu.huergo.vectorial.calidad.bucher.entity.security;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity // Marca la clase como una entidad de JPA
@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
@Table(name = "roles")
public class Rol {

    @Id // Id principal de la entidad
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera el Id automáticamente
    private Long id;

    // Nombre del rol
    @Column(nullable = false, unique = true, length = 50)
    private String nombre; // Ej: ADMIN, LECTOR, ESCRITOR

    // Constructor adicional para crear roles con solo el nombre
    public Rol(String nombre) {
        this.nombre = nombre;
    }
}