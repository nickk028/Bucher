package ar.edu.huergo.vectorial.calidad.bucher.repository.publication;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.RegistroPrestamo;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;

public interface RegistroPrestamoRepository extends JpaRepository<RegistroPrestamo, Long> {
    List<RegistroPrestamo> findByUsuario(Usuario usuario);
    Optional<RegistroPrestamo> findByUsuarioAndPublicacionAndFechaDevolucionIsNull(Usuario usuario, ar.edu.huergo.vectorial.calidad.bucher.entity.publication.Publicacion publicacion);
}