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
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Avatar;
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
                u.setAvatar(Avatar.ALICIA);
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
                u.setAvatar(Avatar.RAPUNZEL);
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
                            "https://es.wikipedia.org/wiki/J._K._Rowling",
                            "https://commons.wikimedia.org/wiki/File:J._K._Rowling_2010.jpg"
                            
                        )
                    ));

            Autor autorHerbert = autorRepository.findByNombreIgnoringCase("Frank Herbert")
                    .orElseGet(() -> autorRepository.save(
                        new Autor(
                            "Frank Herbert",
                            "Escritor estadounidense de ciencia ficción, célebre por su obra maestra 'Dune'.",
                            "https://es.wikipedia.org/wiki/Frank_Herbert",
                            "https://commons.wikimedia.org/wiki/File:Frank_Herbert_1984_(square).jpg"
                        )
                    ));

            Autor autorBrown = autorRepository.findByNombreIgnoringCase("Dan Brown")
                    .orElseGet(() -> autorRepository.save(
                        new Autor(
                                "Dan Brown",
                                "Novelista estadounidense, conocido por 'El código Da Vinci' y otros thrillers de misterio.",
                                "https://es.wikipedia.org/wiki/Dan_Brown",
                                "https://commons.wikimedia.org/wiki/File:Dan_Brown_bookjacket_cropped.jpg"
                        )
                    ));

            Autor autorChristie = autorRepository.findByNombreIgnoringCase("Agatha Christie")
                    .orElseGet(() -> autorRepository.save(
                        new Autor(
                                "Agatha Christie",
                                "Famosa escritora británica de novelas policíacas, creadora de Hércules Poirot y Miss Marple.",
                                "https://es.wikipedia.org/wiki/Agatha_Christie",
                                "https://commons.wikimedia.org/wiki/File:Agatha_Christie.png"
                        )
                    ));

            Autor autorGarciaMarquez = autorRepository.findByNombreIgnoringCase("Gabriel García Márquez")
                    .orElseGet(() -> autorRepository.save(
                        new Autor(
                                "Gabriel García Márquez",
                                "Escritor colombiano, exponente del realismo mágico y autor de 'Cien años de soledad'.",
                                "https://es.wikipedia.org/wiki/Gabriel_Garc%C3%ADa_M%C3%A1rquez",
                                "https://commons.wikimedia.org/wiki/File:Gabriel_Garcia_Marquez.jpg"
                        )
                    ));

            Autor autorAusten = autorRepository.findByNombreIgnoringCase("Jane Austen")
                    .orElseGet(() -> autorRepository.save(
                        new Autor(
                                "Jane Austen",
                                "Novelista británica reconocida por sus retratos irónicos de la sociedad inglesa del siglo XIX.",
                                "https://es.wikipedia.org/wiki/Jane_Austen",
                                "https://cdn.britannica.com/12/172012-050-DAA7CE6B/Jane-Austen-Cassandra-engraving-portrait-1810.jpg"
                        )
                    ));

            Autor autorKing = autorRepository.findByNombreIgnoringCase("Stephen King")
                    .orElseGet(() -> autorRepository.save(
                        new Autor(
                                "Stephen King",
                                "Autor estadounidense, maestro del terror y el suspense, con más de 60 novelas publicadas.",
                                "https://es.wikipedia.org/wiki/Stephen_King",
                                "https://commons.wikimedia.org/wiki/File:Stephen_King_at_the_2024_Toronto_International_Film_Festival_2_(cropped).jpg"
                        )
                    ));

            // ------------------------
            // Inicialización de Libros
            // ------------------------
            if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial("Harry Potter y la piedra filosofal", "Primera edición", autorJK, editorialHP).isEmpty()) {
                Libro harryPotter = new Libro();
                harryPotter.setTitulo("Harry Potter y la piedra filosofal");
                harryPotter.setDescripcion("Harry Potter vive bajo la escalera en la casa de sus tíos y desconoce por completo su origen. En su cumpleaños número once, recibe la visita de Hagrid, quien le revela que es un mago y ha sido aceptado en Hogwarts, la escuela de magia. Allí hará nuevos amigos y aprenderá hechizos, clases mágicas y los secretos del colegio, mientras descubre que alguien intenta apoderarse de la legendaria Piedra Filosofal, un objeto capaz de conferir inmortalidad y poder inimaginable.");
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
                dune.setDescripcion("En un futuro muy lejano, el desértico planeta Arrakis se convierte en epicentro de conflictos interplanetarios: es el único lugar donde se produce la ‘melange’ o especia, sustancia extremadamente valiosa que alarga la vida y permite los viajes espaciales. La Casa Atreides recibe la administración de Arrakis, enfrentándose a traiciones, intrigas políticas, amenazas del emperador y de casas rivales. Paul Atreides, joven heredero, deberá adaptarse a la hostilidad del planeta, forjar alianzas con los Fremen —habitantes del desierto—, y hacerse cargo de un destino que trasciende su propia supervivencia…");
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
                codigo.setDescripcion("El conservador del Louvre, Jacques Saunière, aparece asesinado en circunstancias misteriosas, acompañado de un rastro de símbolos y pistas cifradas. Robert Langdon, profesor de simbología, y Sophie Neveu, criptóloga e hija adoptiva de Saunière, se embarcan en una investigación que los llevará por monumentos, obras de arte y antiguos secretos religiosos. Descubrirán que existe una sociedad secreta, el Priorato de Sión, que ha protegido durante siglos un secreto capaz de cambiar la interpretación de la historia cristiana y sacudir creencias fundamentales. Mientras desentrañan enigmas y códigos, se enfrentan también a quienes harían cualquier cosa para que esas verdades permanezcan ocultas.");
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
                orientExpress.setDescripcion("Durante un gélido invierno en Estambul, el detective Hércules Poirot aborda el lujoso tren Orient Express, que suele viajar casi vacío en esa temporada. Sin embargo, en esta ocasión el tren está sorprendentemente lleno, y sólo logra conseguir un lugar gracias a una amiga. A la mañana siguiente, una tormenta de nieve detiene el convoy y se descubre que un pasajero estadounidense, llamado Ratchett, ha sido asesinado brutalmente dentro de su compartimento. Nadie ha entrado ni salido durante la noche, por lo que el asesino debe estar entre los viajeros: una princesa rusa, una institutriz inglesa y otros sospechosos tan enigmáticos como el crimen mismo.");
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
                soledad.setDescripcion("Durante siete generaciones, la familia Buendía vive y sufre en el mítico pueblo de Macondo, fundado por José Arcadio Buendía y Úrsula Iguarán. En ese universo mágico donde lo real y lo fantástico coexisten, la soledad es fuerza y condena: amores imposibles, obsesiones, guerras, muertes, nacimientos… hechos extraordinarios conviven con lo cotidiano. Crece una ciudad aparte del mundo, se tejen mitos, llegan gitanos con inventos, resurgen fantasmas, y el destino parece marcar cada vida. Y a lo largo de cien años, el paso del tiempo revela la fragilidad de las memorias, la repetición de los errores y, sobre todo, la belleza y el dolor de lo humano.");
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
                orgullo.setDescripcion("La familia Bennet vive en una sociedad inglesa del siglo XIX donde las mujeres jóvenes tienen pocas opciones que el matrimonio. Cuando llega el señor Charles Bingley, joven soltero y acomodado, su presencia sacude la tranquila vida de las cinco hermanas Bennet. Elizabeth, la segunda hija, destaca por su ingenio, honestidad y espíritu independiente. Su relación con el reservado señor Darcy comienza marcada por malentendidos, críticas sociales y diferencias de clase. A través del orgullo, los prejuicios y las expectativas familiares, ambos deberán superar sus propias creencias para hallar la verdad del amor, la comprensión y la autenticidad personal.");
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
                it.setDescripcion("En el pueblo de Derry, algo terriblemente maligno acecha. Cada 27 años, una entidad deformada aparece con la forma de Pennywise, un payaso aterrador, para aterrorizar y asesinar a niños. Un grupo de amigos, que hace décadas enfrentó aquel horror infantil, debe volver al pueblo cuando los asesinatos resurgen. Con recuerdos fragmentados y miedos reavivados, deberán enfrentarse no sólo al monstruo sino a sus propios traumas para poner fin al ciclo de terror que ha marcado sus vidas.");
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