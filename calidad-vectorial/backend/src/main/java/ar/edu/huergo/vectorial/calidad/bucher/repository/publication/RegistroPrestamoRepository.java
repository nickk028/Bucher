package ar.edu.huergo.vectorial.calidad.bucher.repository.publication;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Categoria;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.Estado;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.Publicacion;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.RegistroPrestamo;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RegistroPrestamoRepository extends JpaRepository<RegistroPrestamo, Long> {
    List<RegistroPrestamo> findAllByUsuario(Usuario usuario);
    Optional<RegistroPrestamo> findByPublicacionAndFechaDevolucionIsNull(Publicacion publicacion);
    Optional<RegistroPrestamo> findByPublicacion(Publicacion publicacion);

    List<RegistroPrestamo> findByFechaPrestamoBetween(
        LocalDate fechaInicioSemana,
        LocalDate fechaActual
    );
    Optional<RegistroPrestamo> findByPublicacionAndUsuario(
        Publicacion publicacion,
        Usuario usuario
    );
    Optional<RegistroPrestamo> findByIdAndUsuario(
        Long id,
        Usuario usuario
    );
    Optional<RegistroPrestamo> findById(
        Long id
    );
    long countByUsuarioAndFechaPrestamoGreaterThanEqualAndFechaPrestamoLessThan(
        Usuario usuario,
        LocalDate fechaDesde,
        LocalDate fechaHasta
    );
    long countByFechaDevolucionIsNull();
    long countByPublicacionEstadoPublicacion(Estado estado);

    @Query(
        "SELECT COUNT(r) FROM RegistroPrestamo r " +
        "WHERE :categoria MEMBER OF r.publicacion.libro.categoria"
    )
    long contarPrestamosPorCategoria(@Param("categoria") Categoria categoria);

    @Query(
        "SELECT MONTH(r.fechaDevolucion) AS mes, COUNT(r) AS cantidad " +
        "FROM RegistroPrestamo r " +
        "WHERE YEAR(r.fechaDevolucion) = :anio " +
        "GROUP BY MONTH(r.fechaDevolucion)"
    )
    List<PublicacionSocialRepository.ConteoMes> contarPrestamosCompletadosPorMes(
        @Param("anio") int anio
    );

    Page<RegistroPrestamo> findAllByPublicacionEstadoPublicacionIn(
        Collection<Estado> estadosPublicacion,
        Pageable pageable
    );
}