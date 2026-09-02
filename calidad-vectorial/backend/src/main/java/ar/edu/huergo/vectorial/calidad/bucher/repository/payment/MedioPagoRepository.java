package ar.edu.huergo.vectorial.calidad.bucher.repository.payment;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.huergo.vectorial.calidad.bucher.entity.payment.MedioPago;

public interface MedioPagoRepository extends JpaRepository<MedioPago, Long> {
    Optional<MedioPago> findByNombreIgnoreCase(String nombre);
}