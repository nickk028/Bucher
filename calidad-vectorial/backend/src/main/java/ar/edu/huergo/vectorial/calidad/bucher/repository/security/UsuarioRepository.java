package ar.edu.huergo.vectorial.calidad.bucher.repository.security;

import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import ar.edu.huergo.vectorial.calidad.bucher.repository.publication.PublicacionSocialRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

// Repositorio JPA para la entidad Usuario
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
    Optional<Usuario> findByNickname(String nickname);
    boolean existsByUsername(String username);
    boolean existsByNickname(String nickname);
    long countByFechaCreacionGreaterThanEqual(LocalDateTime fecha);
    long countByUltimoAccesoBefore(LocalDateTime fecha);

    @Query(
        "SELECT MONTH(u.fechaCreacion) AS mes, COUNT(u) AS cantidad " +
        "FROM Usuario u " +
        "WHERE YEAR(u.fechaCreacion) = :anio " +
        "GROUP BY MONTH(u.fechaCreacion)"
    )
    List<PublicacionSocialRepository.ConteoMes> contarUsuariosNuevosPorMes(
        @Param("anio") int anio
    );

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