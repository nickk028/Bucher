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
import ar.edu.huergo.vectorial.calidad.bucher.entity.bookuser.Biblioteca;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Rol;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import ar.edu.huergo.vectorial.calidad.bucher.repository.book.AutorRepository;
import ar.edu.huergo.vectorial.calidad.bucher.repository.book.EditorialRepository;
import ar.edu.huergo.vectorial.calidad.bucher.repository.book.LibroRepository;
import ar.edu.huergo.vectorial.calidad.bucher.repository.bookuser.BibliotecaRepository;
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
            BibliotecaRepository bibliotecaRepository,
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
                Biblioteca biblioteca = new Biblioteca();
                Usuario u = new Usuario("admin@gmail.com", encoder.encode(adminPassword));
                u.setNickname("admin@gmail.com");
                u.setRoles(Set.of(admin));
                u.setBiblioteca(biblioteca);
                biblioteca.setUsuario(u);
                usuarioRepository.save(u);
                bibliotecaRepository.save(biblioteca);
            }

			// Usuario Lector
            if (usuarioRepository.findByUsername("lector@gmail.com").isEmpty()) {
                String clientePassword = "LectorSuperSegura@123";
                PasswordValidator.validate(clientePassword);
                Biblioteca biblioteca = new Biblioteca();
                Usuario u = new Usuario("lector@gmail.com", encoder.encode(clientePassword));
                u.setNickname("lector@gmail.com");
                u.setRoles(Set.of(cliente));
                u.setBiblioteca(biblioteca);
                biblioteca.setUsuario(u);
                usuarioRepository.save(u);
                bibliotecaRepository.save(biblioteca);
            }

            // -----------------------------
            // Inicialización de Editoriales
            // -----------------------------
            Editorial editorialHP = editorialRepository.findByNombreIgnoringCase("Bloomsbury")
                    .orElseGet(() -> editorialRepository.save(
                            new Editorial("Bloomsbury", "https://es.wikipedia.org/wiki/Bloomsbury_Publishing")
                    ));
            Editorial editorialChilton = editorialRepository.findByNombreIgnoringCase("Chilton Books")
                    .orElseGet(() -> editorialRepository.save(
                            new Editorial("Chilton Books", "https://en.wikipedia.org/wiki/Chilton_Books")
                    ));

            Editorial editorialDoubleday = editorialRepository.findByNombreIgnoringCase("Doubleday")
                    .orElseGet(() -> editorialRepository.save(
                            new Editorial("Doubleday", "https://en.wikipedia.org/wiki/Doubleday_(publisher)")
                    ));

            Editorial editorialPlaneta = editorialRepository.findByNombreIgnoringCase("Editorial Planeta")
                    .orElseGet(() -> editorialRepository.save(
                            new Editorial("Editorial Planeta", "https://es.wikipedia.org/wiki/Grupo_Planeta")
                    ));

            Editorial editorialChapman = editorialRepository.findByNombreIgnoringCase("Thomas Egerton")
                    .orElseGet(() -> editorialRepository.save(
                            new Editorial("Thomas Egerton", "https://es.wikipedia.org/wiki/Thomas_Egerton_(editor)")
                    ));

            Editorial editorialViking = editorialRepository.findByNombreIgnoringCase("Viking Press")
                    .orElseGet(() -> editorialRepository.save(
                            new Editorial("Viking Press", "https://en.wikipedia.org/wiki/Viking_Press")
                    ));


            // -------------------------
            // Inicialización de Autores
            // -------------------------
            Autor autorJK = autorRepository.findByNombreIgnoringCase("J.K. Rowling")
                    .orElseGet(() -> autorRepository.save(
                        new Autor(
                            "J.K. Rowling",
                            "Autora británica conocida por la saga de Harry Potter, una de las más exitosas de la historia.",
                            "https://es.wikipedia.org/wiki/J._K._Rowling"
                        )
                    ));

            Autor autorHerbert = autorRepository.findByNombreIgnoringCase("Frank Herbert")
                    .orElseGet(() -> autorRepository.save(
                        new Autor(
                            "Frank Herbert",
                            "Escritor estadounidense de ciencia ficción, célebre por su obra maestra 'Dune'.",
                            "https://es.wikipedia.org/wiki/Frank_Herbert"
                        )
                    ));

            Autor autorBrown = autorRepository.findByNombreIgnoringCase("Dan Brown")
                    .orElseGet(() -> autorRepository.save(
                        new Autor(
                                "Dan Brown",
                                "Novelista estadounidense, conocido por 'El código Da Vinci' y otros thrillers de misterio.",
                                "https://es.wikipedia.org/wiki/Dan_Brown"
                        )
                    ));

            Autor autorChristie = autorRepository.findByNombreIgnoringCase("Agatha Christie")
                    .orElseGet(() -> autorRepository.save(
                        new Autor(
                                "Agatha Christie",
                                "Famosa escritora británica de novelas policíacas, creadora de Hércules Poirot y Miss Marple.",
                                "https://es.wikipedia.org/wiki/Agatha_Christie"
                        )
                    ));

            Autor autorGarciaMarquez = autorRepository.findByNombreIgnoringCase("Gabriel García Márquez")
                    .orElseGet(() -> autorRepository.save(
                        new Autor(
                                "Gabriel García Márquez",
                                "Escritor colombiano, exponente del realismo mágico y autor de 'Cien años de soledad'.",
                                "https://es.wikipedia.org/wiki/Gabriel_Garc%C3%ADa_M%C3%A1rquez"
                        )
                    ));

            Autor autorAusten = autorRepository.findByNombreIgnoringCase("Jane Austen")
                    .orElseGet(() -> autorRepository.save(
                        new Autor(
                                "Jane Austen",
                                "Novelista británica reconocida por sus retratos irónicos de la sociedad inglesa del siglo XIX.",
                                "https://es.wikipedia.org/wiki/Jane_Austen"
                        )
                    ));

            Autor autorKing = autorRepository.findByNombreIgnoringCase("Stephen King")
                    .orElseGet(() -> autorRepository.save(
                        new Autor(
                                "Stephen King",
                                "Autor estadounidense, maestro del terror y el suspense, con más de 60 novelas publicadas.",
                                "https://es.wikipedia.org/wiki/Stephen_King"
                        )
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
                harryPotter.setCategoria(Set.of(Categoria.fantastico, Categoria.aventura));
                harryPotter.setEditorial(editorialHP);
                harryPotter.setAutor(autorJK);

                libroRepository.save(harryPotter);
            }
            if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial("Dune", "Primera edición", autorHerbert, editorialChilton).isEmpty()) {
                Libro dune = new Libro();
                dune.setTitulo("Dune");
                dune.setDescripcion("Clásico de la ciencia ficción, la historia de Paul Atreides en el planeta desértico Arrakis.");
                dune.setPaginas(412);
                dune.setEdicion("Primera edición");
                dune.setCalificacion(97);
                dune.setFechaPublicacion(LocalDate.of(1965, 8, 1));
                dune.setUrlFoto("https://upload.wikimedia.org/wikipedia/commons/8/88/Map_of_Arrakis_from_Dune_first_edition_dust_jacket.jpg");
                dune.setPrecio(39.99);
                dune.setCategoria(Set.of(Categoria.cienciaficcion));
                dune.setEditorial(editorialChilton);
                dune.setAutor(autorHerbert);
                libroRepository.save(dune);
            }

            if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial("El código Da Vinci", "Primera edición", autorBrown, editorialDoubleday).isEmpty()) {
                Libro codigo = new Libro();
                codigo.setTitulo("El código Da Vinci");
                codigo.setDescripcion("Novela de suspenso y misterio protagonizada por Robert Langdon.");
                codigo.setPaginas(489);
                codigo.setEdicion("Primera edición");
                codigo.setCalificacion(85);
                codigo.setFechaPublicacion(LocalDate.of(2003, 3, 18));
                codigo.setUrlFoto("https://upload.wikimedia.org/wikipedia/en/6/6b/DaVinciCode.jpg");
                codigo.setPrecio(25.99);
                codigo.setCategoria(Set.of(Categoria.suspenso));
                codigo.setEditorial(editorialDoubleday);
                codigo.setAutor(autorBrown);
                libroRepository.save(codigo);
            }

            if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial("Asesinato en el Orient Express", "Primera edición", autorChristie, editorialPlaneta).isEmpty()) {
                Libro orientExpress = new Libro();
                orientExpress.setTitulo("Asesinato en el Orient Express");
                orientExpress.setDescripcion("Clásico policial de Agatha Christie con el detective Hércules Poirot.");
                orientExpress.setPaginas(256);
                orientExpress.setEdicion("Primera edición");
                orientExpress.setCalificacion(92);
                orientExpress.setFechaPublicacion(LocalDate.of(1934, 1, 1));
                orientExpress.setUrlFoto("https://upload.wikimedia.org/wikipedia/en/c/c0/Murder_on_the_Orient_Express_First_Edition_Cover_1934.jpg");
                orientExpress.setPrecio(19.99);
                orientExpress.setCategoria(Set.of(Categoria.policial));
                orientExpress.setEditorial(editorialPlaneta);
                orientExpress.setAutor(autorChristie);
                libroRepository.save(orientExpress);
            }

            if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial("Cien años de soledad", "Primera edición", autorGarciaMarquez, editorialPlaneta).isEmpty()) {
                Libro soledad = new Libro();
                soledad.setTitulo("Cien años de soledad");
                soledad.setDescripcion("Obra maestra del realismo mágico que narra la historia de la familia Buendía.");
                soledad.setPaginas(471);
                soledad.setEdicion("Primera edición");
                soledad.setCalificacion(98);
                soledad.setFechaPublicacion(LocalDate.of(1967, 5, 30));
                soledad.setUrlFoto("https://upload.wikimedia.org/wikipedia/commons/a/a1/Cien_a%C3%B1os_de_soledad.png");
                soledad.setPrecio(34.99);
                soledad.setCategoria(Set.of(Categoria.realismomagico));
                soledad.setEditorial(editorialPlaneta);
                soledad.setAutor(autorGarciaMarquez);
                libroRepository.save(soledad);
            }

            if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial("Orgullo y prejuicio", "Primera edición", autorAusten, editorialChapman).isEmpty()) {
                Libro orgullo = new Libro();
                orgullo.setTitulo("Orgullo y prejuicio");
                orgullo.setDescripcion("Novela romántica que retrata la vida de las hermanas Bennet y el Sr. Darcy.");
                orgullo.setPaginas(432);
                orgullo.setEdicion("Primera edición");
                orgullo.setCalificacion(94);
                orgullo.setFechaPublicacion(LocalDate.of(1813, 1, 28));
                orgullo.setUrlFoto("https://upload.wikimedia.org/wikipedia/commons/1/17/PrideAndPrejudiceTitlePage.jpg");
                orgullo.setPrecio(22.99);
                orgullo.setCategoria(Set.of(Categoria.romance));
                orgullo.setEditorial(editorialChapman);
                orgullo.setAutor(autorAusten);
                libroRepository.save(orgullo);
            }

            if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial("It", "Primera edición", autorKing, editorialViking).isEmpty()) {
                Libro it = new Libro();
                it.setTitulo("It");
                it.setDescripcion("Novela de terror de Stephen King que presenta a Pennywise, el payaso bailarín.");
                it.setPaginas(1138);
                it.setEdicion("Primera edición");
                it.setCalificacion(90);
                it.setFechaPublicacion(LocalDate.of(1986, 9, 15));
                it.setUrlFoto("https://upload.wikimedia.org/wikipedia/commons/1/1a/It_%281986%29_front_cover%2C_first_edition.jpg");
                it.setPrecio(29.99);
                it.setCategoria(Set.of(Categoria.terror));
                it.setEditorial(editorialViking);
                it.setAutor(autorKing);
                libroRepository.save(it);
            }
        };
    }
}