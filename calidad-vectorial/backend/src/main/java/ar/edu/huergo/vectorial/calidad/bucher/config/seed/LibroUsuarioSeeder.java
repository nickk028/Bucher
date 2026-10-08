package ar.edu.huergo.vectorial.calidad.bucher.config.seed;

import ar.edu.huergo.vectorial.calidad.bucher.config.seed.dto.LibroUsuarioSeed;
import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Libro;
import ar.edu.huergo.vectorial.calidad.bucher.entity.bookuser.LibroUsuario;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import ar.edu.huergo.vectorial.calidad.bucher.repository.bookuser.LibroUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(8)
@RequiredArgsConstructor
public class LibroUsuarioSeeder implements Seeder {

    private static final String ARCHIVO = "seed/libros-usuario.json";

    private final SeedDataReader reader;
    private final SeedReferences references;
    private final LibroUsuarioRepository libroUsuarioRepository;

    @Override
    public void ejecutar() {
        reader.leer(ARCHIVO, LibroUsuarioSeed.class).forEach(this::crearSiNoExiste);
    }

    private void crearSiNoExiste(LibroUsuarioSeed seed) {
        Usuario usuario = references.usuario(seed.usuario());
        Libro libro = references.libro(seed.libro());

        if (libroUsuarioRepository.findByUsuarioIdAndLibro(usuario.getId(), libro).isPresent()) {
            return;
        }

        LibroUsuario libroUsuario = new LibroUsuario();
        libroUsuario.setUsuario(usuario);
        libroUsuario.setLibro(libro);
        libroUsuario.setPaginaActual(seed.paginaActual());
        libroUsuario.setEstadoLectura(seed.estadoLectura());
        libroUsuario.setPuntuacion(seed.puntuacion());
        libroUsuarioRepository.save(libroUsuario);
    }
}