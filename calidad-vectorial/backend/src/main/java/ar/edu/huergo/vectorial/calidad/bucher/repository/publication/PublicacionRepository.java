package ar.edu.huergo.vectorial.calidad.bucher.repository.publication;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Categoria;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.Publicacion;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;

public interface PublicacionRepository extends JpaRepository<Publicacion, Long> {
    List<Publicacion> findAllByUsuario(Usuario usuario);

    @Query("SELECT p FROM Publicacion p WHERE :categoria MEMBER OF p.libro.categoria")
    List<Publicacion> findAllByCategoria(@Param("categoria") Categoria categoria);

}