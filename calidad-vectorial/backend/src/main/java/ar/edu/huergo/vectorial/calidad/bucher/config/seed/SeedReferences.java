package ar.edu.huergo.vectorial.calidad.bucher.config.seed;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Libro;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import ar.edu.huergo.vectorial.calidad.bucher.repository.book.LibroRepository;
import ar.edu.huergo.vectorial.calidad.bucher.repository.security.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
// Resuelve las referencias por clave natural usadas en los archivos de seed
public class SeedReferences {

    private final UsuarioRepository usuarioRepository;
    private final LibroRepository libroRepository;

    /**
     * Obtiene un usuario por su username
     * @param username El username del usuario
     * @return El usuario encontrado
     * @throws IllegalStateException Si el usuario no existe
     */
    public Usuario usuario(String username) {
        return usuarioRepository
            .findByUsername(username)
            .orElseThrow(() -> new IllegalStateException("Usuario de seed inexistente: " + username));
    }

    /**
     * Obtiene un libro por su título
     * @param titulo El título del libro
     * @return El libro encontrado
     * @throws IllegalStateException Si el libro no existe
     */
    public Libro libro(String titulo) {
        return libroRepository
            .findByTituloIgnoringCase(titulo)
            .orElseThrow(() -> new IllegalStateException("Libro de seed inexistente: " + titulo));
    }
}