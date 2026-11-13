package ar.edu.huergo.vectorial.calidad.bucher.repository.subscription;

import ar.edu.huergo.vectorial.calidad.bucher.entity.subscription.Suscripcion;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

// Repositorio JPA para la entidad Suscripcion
public interface SuscripcionRepository extends JpaRepository<Suscripcion, Long> {
    Optional<Suscripcion> findByNombreIgnoreCase(String nombre);
    boolean existsByNombreIgnoreCase(String nombre);
}