package ar.edu.huergo.vectorial.calidad.bucher.repository.security;

import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Rol;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

// Repositorio JPA para la entidad Rol
public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByNombre(String nombre);
}
