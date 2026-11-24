package ar.edu.huergo.vectorial.calidad.bucher.repository.publication;

import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.ReportePublicacionSocial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportePublicacionSocialRepository
    extends JpaRepository<ReportePublicacionSocial, Long> {
    boolean existsByUsuarioIdAndPublicacionSocialId(Long usuarioId, Long publicacionSocialId);
}