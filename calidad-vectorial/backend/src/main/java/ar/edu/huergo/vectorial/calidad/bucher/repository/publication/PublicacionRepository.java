package ar.edu.huergo.vectorial.calidad.bucher.repository.publication;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Categoria;
import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Libro;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.Estado;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.Publicacion;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;

public interface PublicacionRepository extends JpaRepository<Publicacion, Long> {
    List<Publicacion> findAllByUsuario(Usuario usuario);

    @Query("SELECT p FROM Publicacion p WHERE :categoria MEMBER OF p.libro.categoria")
    List<Publicacion> findAllByCategoria(@Param("categoria") Categoria categoria);

    List<Publicacion> findAllByEstadoPublicacion(Estado estado);
    List<Publicacion> findAllByLibroCategoriaContaining(Categoria categoria);

    Optional<Publicacion> findByUsuarioAndLibroAndFechaCreacion(Usuario usuario, Libro libro, LocalDate fechaCreacion);
    Optional<Publicacion> findByLibroTituloIgnoreCase(String libro);
}