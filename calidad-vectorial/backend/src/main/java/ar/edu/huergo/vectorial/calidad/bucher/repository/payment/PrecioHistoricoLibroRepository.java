package ar.edu.huergo.vectorial.calidad.bucher.repository.payment;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Libro;
import ar.edu.huergo.vectorial.calidad.bucher.entity.payment.PrecioHistoricoLibro;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrecioHistoricoLibroRepository extends JpaRepository<PrecioHistoricoLibro, Long> {
    List<PrecioHistoricoLibro> findAllByLibroOrderByFechaModificacionDescIdDesc(Libro libro);
    Optional<PrecioHistoricoLibro> findFirstByLibroOrderByFechaModificacionDescIdDesc(Libro libro);
}