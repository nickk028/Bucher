package ar.edu.huergo.vectorial.calidad.bucher.repository.payment;

import ar.edu.huergo.vectorial.calidad.bucher.entity.payment.PrecioHistoricoSuscripcion;
import ar.edu.huergo.vectorial.calidad.bucher.entity.subscription.Suscripcion;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrecioHistoricoSuscripcionRepository
    extends JpaRepository<PrecioHistoricoSuscripcion, Long> {
    Optional<PrecioHistoricoSuscripcion> findFirstBySuscripcionOrderByFechaModificacionDescIdDesc(
        Suscripcion suscripcion
    );
}