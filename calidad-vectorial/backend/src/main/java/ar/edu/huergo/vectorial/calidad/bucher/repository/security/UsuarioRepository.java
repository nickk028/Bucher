package ar.edu.huergo.vectorial.calidad.bucher.repository.security;

import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

// Repositorio JPA para la entidad Usuario
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
    Optional<Usuario> findByNickname(String nickname);
    boolean existsByUsername(String username);
    boolean existsByNickname(String nickname);

    @Query(
        value = "SELECT u FROM Usuario u " +
        "ORDER BY (SELECT COALESCE(SUM(p.reportes), 0) FROM PublicacionSocial p WHERE p.usuario = u) DESC, u.id DESC",
        countQuery = "SELECT COUNT(u) FROM Usuario u"
    )
    Page<Usuario> findAllOrdenadosPorDenuncias(Pageable pageable);

    interface ConteoUsuario {
        Long getUsuarioId();
        Long getCantidad();
    }
}