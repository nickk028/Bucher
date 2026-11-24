package ar.edu.huergo.vectorial.calidad.bucher.entity.deletion;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity // Marca la clase como una entidad de JPA
@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@EqualsAndHashCode(callSuper = true) // Genera equals y hashCode incluyendo los campos de la clase padre
@ToString(callSuper = true) // Genera toString incluyendo los campos de la clase padre
@Table(name = "registro_eliminacion_publicacion_social")
public class RegistroEliminacionPublicacionSocial extends RegistroEliminacion {}