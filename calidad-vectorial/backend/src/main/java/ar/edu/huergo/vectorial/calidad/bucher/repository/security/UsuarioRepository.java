package ar.edu.huergo.vectorial.calidad.bucher.repository.security;

import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

// Repositorio JPA para la entidad Usuario
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByNickname(String nickname);
}
