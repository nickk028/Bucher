package ar.edu.huergo.vectorial.calidad.bucher.repository.publication;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.Publicacion;

public interface PublicacionRepository extends JpaRepository<Publicacion, Long> {
}