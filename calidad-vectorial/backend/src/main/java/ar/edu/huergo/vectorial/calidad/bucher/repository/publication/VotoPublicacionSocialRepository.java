package ar.edu.huergo.vectorial.calidad.bucher.repository.publication;

import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.VotoPublicacionSocial;
import jakarta.persistence.LockModeType;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

public interface VotoPublicacionSocialRepository
    extends JpaRepository<VotoPublicacionSocial, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<VotoPublicacionSocial> findByUsuarioIdAndPublicacionSocialId(
        Long usuarioId,
        Long publicacionSocialId
    );
    List<VotoPublicacionSocial> findAllByUsuarioIdAndPublicacionSocialIdIn(
        Long usuarioId,
        Collection<Long> publicacionesIds
    );
}