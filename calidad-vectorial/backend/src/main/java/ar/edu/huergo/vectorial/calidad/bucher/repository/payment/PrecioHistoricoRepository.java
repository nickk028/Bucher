package ar.edu.huergo.vectorial.calidad.bucher.repository.payment;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Libro;
import ar.edu.huergo.vectorial.calidad.bucher.entity.payment.PrecioHistorico;

public interface PrecioHistoricoRepository extends JpaRepository<PrecioHistorico, Long> {
    List<PrecioHistorico> findAllByLibroOrderByFechaModificacionDesc(Libro libro);
    Optional<PrecioHistorico> findFirstByLibroOrderByFechaModificacionDescIdDesc(Libro libro);
}