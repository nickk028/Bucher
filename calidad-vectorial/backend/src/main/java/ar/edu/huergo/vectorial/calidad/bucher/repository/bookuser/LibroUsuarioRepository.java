package ar.edu.huergo.vectorial.calidad.bucher.repository.bookuser;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Categoria;
import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Libro;
import ar.edu.huergo.vectorial.calidad.bucher.entity.bookuser.EstadoLectura;
import ar.edu.huergo.vectorial.calidad.bucher.entity.bookuser.LibroUsuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LibroUsuarioRepository extends JpaRepository<LibroUsuario, Long> {
    List<LibroUsuario> findByUsuarioIdAndEstadoLectura(
        Long usuarioId,
        EstadoLectura estadoLectura
    );

    boolean existsByUsuarioIdAndLibro(Long usuarioId, Libro libro);
    Optional<LibroUsuario> findByUsuarioIdAndLibro(Long usuarioId, Libro libro);
    long countByUsuarioId(Long usuarioId);

    @Query("SELECT COUNT(lu) FROM LibroUsuario lu WHERE :categoria MEMBER OF lu.libro.categoria")
    long contarPorCategoria(@Param("categoria") Categoria categoria);

    @Query("SELECT COUNT(DISTINCT lu.libro.id) FROM LibroUsuario lu")
    long contarLibrosGuardados();

    @Query(
        "SELECT lu.libro.id AS libroId, COUNT(lu) AS cantidad " +
        "FROM LibroUsuario lu " +
        "GROUP BY lu.libro.id " +
        "ORDER BY COUNT(lu) DESC, lu.libro.id ASC"
    )
    List<ConteoLibro> contarLibrosMasGuardados(Pageable pageable);

    interface ConteoLibro {
        Long getLibroId();
        Long getCantidad();
    }
}