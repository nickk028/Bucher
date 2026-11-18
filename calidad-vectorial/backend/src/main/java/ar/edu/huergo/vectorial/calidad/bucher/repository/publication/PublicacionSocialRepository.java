package ar.edu.huergo.vectorial.calidad.bucher.repository.publication;

import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.PublicacionSocial;
import ar.edu.huergo.vectorial.calidad.bucher.repository.security.UsuarioRepository;
import java.util.Collection;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PublicacionSocialRepository extends JpaRepository<PublicacionSocial, Long> {
    Page<PublicacionSocial> findAllByPublicacionReferenciadaIsNull(Pageable pageable);
    Page<PublicacionSocial> findAllByPublicacionReferenciadaIsNullAndRegistroEliminacionIsNull(
        Pageable pageable
    );
    Page<PublicacionSocial> findAllByLibroIdAndPublicacionReferenciadaIsNull(
        Long libroId,
        Pageable pageable
    );
    Page<PublicacionSocial> findAllByLibroIdAndPublicacionReferenciadaIsNullAndRegistroEliminacionIsNull(
        Long libroId,
        Pageable pageable
    );
    Page<PublicacionSocial> findAllByUsuarioId(Long usuarioId, Pageable pageable);
    Page<PublicacionSocial> findAllByUsuarioIdAndRegistroEliminacionIsNull(
        Long usuarioId,
        Pageable pageable
    );
    Page<PublicacionSocial> findAllByPublicacionReferenciadaId(
        Long publicacionReferenciadaId,
        Pageable pageable
    );
    long countByUsuarioIdAndRegistroEliminacionIsNull(Long usuarioId);

    @Query("SELECT COUNT(DISTINCT p.libro.id) FROM PublicacionSocial p")
    long contarLibrosNombrados();

    @Query(
        "SELECT p.publicacionReferenciada.id AS publicacionId, COUNT(p) AS cantidad " +
        "FROM PublicacionSocial p " +
        "WHERE p.publicacionReferenciada.id IN :ids " +
        "GROUP BY p.publicacionReferenciada.id"
    )
    List<ConteoRespuestas> contarRespuestasPorPublicacion(@Param("ids") Collection<Long> ids);

    @Query(
        "SELECT p.usuario.id AS usuarioId, COUNT(p) AS cantidad " +
        "FROM PublicacionSocial p " +
        "WHERE p.usuario.id IN :ids " +
        "GROUP BY p.usuario.id"
    )
    List<UsuarioRepository.ConteoUsuario> contarPosteosPorUsuario(
        @Param("ids") Collection<Long> ids
    );

    @Query(
        "SELECT p.usuario.id AS usuarioId, SUM(p.reportes) AS cantidad " +
        "FROM PublicacionSocial p " +
        "WHERE p.usuario.id IN :ids " +
        "GROUP BY p.usuario.id"
    )
    List<UsuarioRepository.ConteoUsuario> contarDenunciasPorUsuario(
        @Param("ids") Collection<Long> ids
    );

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(
        "UPDATE PublicacionSocial p SET p.votosPositivos = p.votosPositivos + :cantidad WHERE p.id = :id"
    )
    void sumarVotosPositivos(@Param("id") Long id, @Param("cantidad") int cantidad);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(
        "UPDATE PublicacionSocial p SET p.votosNegativos = p.votosNegativos + :cantidad WHERE p.id = :id"
    )
    void sumarVotosNegativos(@Param("id") Long id, @Param("cantidad") int cantidad);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE PublicacionSocial p SET p.reportes = p.reportes + :cantidad WHERE p.id = :id")
    void sumarReportes(@Param("id") Long id, @Param("cantidad") int cantidad);

    interface ConteoRespuestas {
        Long getPublicacionId();
        Long getCantidad();
    }
}
