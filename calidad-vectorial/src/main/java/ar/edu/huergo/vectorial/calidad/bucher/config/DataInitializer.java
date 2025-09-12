package ar.edu.huergo.vectorial.calidad.bucher.config;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Autor;
import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Categoria;
import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Editorial;
import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Libro;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Rol;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;

import ar.edu.huergo.vectorial.calidad.bucher.repository.book.AutorRepository;
import ar.edu.huergo.vectorial.calidad.bucher.repository.book.EditorialRepository;
import ar.edu.huergo.vectorial.calidad.bucher.repository.book.LibroRepository;
import ar.edu.huergo.vectorial.calidad.bucher.repository.security.RolRepository;
import ar.edu.huergo.vectorial.calidad.bucher.repository.security.UsuarioRepository;

import ar.edu.huergo.vectorial.calidad.bucher.util.PasswordValidator;

@Configuration // Marca esta clase como una clase de configuración de Spring
// Clase para inicializar datos en la base de datos al iniciar la aplicación
public class DataInitializer {

    @Bean
    CommandLineRunner initData(
            RolRepository rolRepository,
            UsuarioRepository usuarioRepository,
            PasswordEncoder encoder,
            EditorialRepository editorialRepository,
            AutorRepository autorRepository,
            LibroRepository libroRepository) {
        return args -> {

            // ----------------------------------
            // Inicialización de roles y usuarios
            // ----------------------------------
            Rol admin = rolRepository.findByNombre("ADMIN")
                    .orElseGet(() -> rolRepository.save(new Rol("ADMIN")));
            Rol cliente = rolRepository.findByNombre("LECTOR")
                    .orElseGet(() -> rolRepository.save(new Rol("LECTOR")));

            // Usuario Admin
            if (usuarioRepository.findByUsername("admin@gmail.com").isEmpty()) {
                String adminPassword = "AdminSuperSegura@123";
                PasswordValidator.validate(adminPassword);
                Usuario u = new Usuario("admin@gmail.com", encoder.encode(adminPassword));
                u.setRoles(Set.of(admin));
                usuarioRepository.save(u);
            }

			// Usuario Lector
            if (usuarioRepository.findByUsername("lector@gmail.com").isEmpty()) {
                String clientePassword = "LectorSuperSegura@123";
                PasswordValidator.validate(clientePassword);
                Usuario u = new Usuario("lector@gmail.com", encoder.encode(clientePassword));
                u.setRoles(Set.of(cliente));
                usuarioRepository.save(u);
            }

            // -----------------------------
            // Inicialización de Editoriales
            // -----------------------------
            Editorial editorialHP = editorialRepository.findByNombreIgnoringCase("Bloomsbury")
                    .orElseGet(() -> editorialRepository.save(
                            new Editorial("Bloomsbury", "https://es.wikipedia.org/wiki/Bloomsbury_Publishing")
                    ));

            // -------------------------
            // Inicialización de Autores
            // -------------------------
            Autor autorJK = autorRepository.findByNombreIgnoringCase("J.K. Rowling")
                    .orElseGet(() -> autorRepository.save(
                            new Autor("J.K. Rowling", "https://es.wikipedia.org/wiki/J._K._Rowling")
                    ));

            // ------------------------
            // Inicialización de Libros
            // ------------------------
            if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial("Harry Potter y la piedra filosofal", "Primera edición", autorJK, editorialHP).isEmpty()) {
                Libro harryPotter = new Libro();
                harryPotter.setTitulo("Harry Potter y la piedra filosofal");
                harryPotter.setDescripcion("Primer libro de la saga de Harry Potter.");
                harryPotter.setPaginas(223);
                harryPotter.setEdicion("Primera edición");
                harryPotter.setCalificacion(95);
                harryPotter.setFechaPublicacion(LocalDate.of(1997, 6, 26));
                harryPotter.setUrlFoto("https://upload.wikimedia.org/wikipedia/en/6/6b/Harry_Potter_and_the_Philosopher%27s_Stone_Book_Cover.jpg");
                harryPotter.setPrecio(29.99);
                harryPotter.setCategoria(Set.of(Categoria.Fantástico, Categoria.Aventura));
                harryPotter.setEditorial(editorialHP);
                harryPotter.setAutor(autorJK);

                libroRepository.save(harryPotter);
            }
        };
    }
}