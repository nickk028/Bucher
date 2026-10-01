package ar.edu.huergo.vectorial.calidad.bucher.repository.payment;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Libro;
import ar.edu.huergo.vectorial.calidad.bucher.entity.payment.PrecioHistorico;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrecioHistoricoRepository extends JpaRepository<PrecioHistorico, Long> {
    List<PrecioHistorico> findAllByLibroOrderByFechaModificacionDesc(Libro libro);
    Optional<PrecioHistorico> findFirstByLibroOrderByFechaModificacionDescIdDesc(Libro libro);
}
