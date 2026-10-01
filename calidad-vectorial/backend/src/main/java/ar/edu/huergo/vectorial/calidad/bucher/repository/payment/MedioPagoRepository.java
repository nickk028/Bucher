package ar.edu.huergo.vectorial.calidad.bucher.repository.payment;

import ar.edu.huergo.vectorial.calidad.bucher.entity.payment.MedioPago;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedioPagoRepository extends JpaRepository<MedioPago, Long> {
    Optional<MedioPago> findByNombreIgnoreCase(String nombre);
}
