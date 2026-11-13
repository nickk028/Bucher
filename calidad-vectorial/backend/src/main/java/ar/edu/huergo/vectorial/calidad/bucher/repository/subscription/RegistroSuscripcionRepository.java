package ar.edu.huergo.vectorial.calidad.bucher.repository.subscription;

import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import ar.edu.huergo.vectorial.calidad.bucher.entity.subscription.RegistroSuscripcion;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

// Repositorio JPA para la entidad RegistroSuscripcion
public interface RegistroSuscripcionRepository extends JpaRepository<RegistroSuscripcion, Long> {
    List<RegistroSuscripcion> findAllByUsuarioOrderByFechaInicioDescIdDesc(Usuario usuario);
    List<RegistroSuscripcion> findAllByUsuarioAndFechaInicioLessThanEqualOrderByFechaInicioDescIdDesc(Usuario usuario, LocalDate fecha);
    Optional<RegistroSuscripcion> findByIdAndUsuario(Long id, Usuario usuario);
    boolean existsByUsuario(Usuario usuario);
    List<RegistroSuscripcion> findAllByUsuarioAndFechaFinAfter(Usuario usuario, LocalDate fecha);
    Optional<RegistroSuscripcion> findFirstByUsuarioAndFechaFinAfterOrderByFechaFinDesc(Usuario usuario, LocalDate fecha);
}