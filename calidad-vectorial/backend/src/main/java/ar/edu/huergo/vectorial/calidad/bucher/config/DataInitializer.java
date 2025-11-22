package ar.edu.huergo.vectorial.calidad.bucher.config;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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
import ar.edu.huergo.vectorial.calidad.bucher.entity.bookuser.EstadoLectura;
import ar.edu.huergo.vectorial.calidad.bucher.entity.bookuser.LibroUsuario;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.Estado;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.Publicacion;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.RegistroPrestamo;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Avatar;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Rol;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import ar.edu.huergo.vectorial.calidad.bucher.repository.book.AutorRepository;
import ar.edu.huergo.vectorial.calidad.bucher.repository.book.EditorialRepository;
import ar.edu.huergo.vectorial.calidad.bucher.repository.book.LibroRepository;
import ar.edu.huergo.vectorial.calidad.bucher.repository.bookuser.BibliotecaRepository;
import ar.edu.huergo.vectorial.calidad.bucher.repository.bookuser.LibroUsuarioRepository;
import ar.edu.huergo.vectorial.calidad.bucher.repository.publication.PublicacionRepository;
import ar.edu.huergo.vectorial.calidad.bucher.repository.publication.RegistroPrestamoRepository;
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
            LibroRepository libroRepository, 
			PublicacionRepository publicacionRepository,
			RegistroPrestamoRepository registroPrestamoRepository,
			LibroUsuarioRepository libroUsuarioRepository) {
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

			// Usuario Lector - 1
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
			// Usuario Lector - 2
			if (usuarioRepository.findByUsername("paulamartinez@gmail.com").isEmpty()) {
                String clientePassword = "LectorSuperSegura@123";
                PasswordValidator.validate(clientePassword);
                Biblioteca biblioteca = new Biblioteca();
                Usuario u = new Usuario("paulamartinez@gmail.com", encoder.encode(clientePassword));
                u.setAvatar(Avatar.BRUJA);
                u.setNickname("Paula_03");
                u.setRoles(Set.of(cliente));
                u.setBiblioteca(biblioteca);
                biblioteca.setUsuario(u);
                usuarioRepository.save(u);
                bibliotecaRepository.save(biblioteca);
            }
			// Usuario Lector - 3
			if (usuarioRepository.findByUsername("jlopez@gmail.com").isEmpty()) {
                String clientePassword = "LectorSuperSegura@123";
                PasswordValidator.validate(clientePassword);
                Biblioteca biblioteca = new Biblioteca();
                Usuario u = new Usuario("jlopez@gmail.com", encoder.encode(clientePassword));
                u.setAvatar(Avatar.HARRYPOTTER);
                u.setNickname("Juan_Lopez");
                u.setRoles(Set.of(cliente));
                u.setBiblioteca(biblioteca);
                biblioteca.setUsuario(u);
                usuarioRepository.save(u);
                bibliotecaRepository.save(biblioteca);
            }
			// Usuario Lector - 4
			if (usuarioRepository.findByUsername("mljueguen@gmail.com").isEmpty()) {
                String clientePassword = "LectorSuperSegura@123";
                PasswordValidator.validate(clientePassword);
                Biblioteca biblioteca = new Biblioteca();
                Usuario u = new Usuario("mljueguen@gmail.com", encoder.encode(clientePassword));
                u.setAvatar(Avatar.PINOCHO);
                u.setNickname("MaLu");
                u.setRoles(Set.of(cliente));
                u.setBiblioteca(biblioteca);
                biblioteca.setUsuario(u);
                usuarioRepository.save(u);
                bibliotecaRepository.save(biblioteca);
            }
			// Usuario Lector - 5 / Ejemplo expo
			if (usuarioRepository.findByUsername("jrolando@gmail.com").isEmpty()) {
                String clientePassword = "LectorSuperSegura@123";
                PasswordValidator.validate(clientePassword);
                Biblioteca biblioteca = new Biblioteca();
                Usuario u = new Usuario("jrolando@gmail.com", encoder.encode(clientePassword));
                u.setAvatar(Avatar.REINADECORAZONES);
                u.setNickname("Juli_Lectora");
                u.setRoles(Set.of(cliente));
                u.setBiblioteca(biblioteca);
				u.setDireccion("Av. Independencia 1562");
				u.setPiso("9 A");
				u.setCodigoPostal("1100");
				u.setPronombres("Ella");
				u.setDescripcion("Lectora desde hace 10 años, amante del drama y el romance. Mi libro favorito es Orgullo y Prejuicio y siempre busco nuevas lecturas.");
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

			Editorial editorialSalamandra = editorialRepository.findByNombreIgnoringCase("Salamandra")
                    .orElseGet(() -> editorialRepository.save(
                            new Editorial("Salamandra", "https://es.wikipedia.org/wiki/Ediciones_Salamandra")
                    ));

			Editorial editorialVR = editorialRepository.findByNombreIgnoringCase("VR editoras")
                    .orElseGet(() -> editorialRepository.save(
                            new Editorial("VR editoras", "https://vreditoras.com.ar")
                    ));

			Editorial editorialSudamericana = editorialRepository.findByNombreIgnoringCase("Editorial Sudamericana")
                    .orElseGet(() -> editorialRepository.save(
                            new Editorial("Editorial Sudamericana", "https://es.wikipedia.org/wiki/Editorial_Sudamericana")
                    ));
					
			Editorial editorialPenguin = editorialRepository.findByNombreIgnoringCase("Penguin Random House Grupo Editorial")
                    .orElseGet(() -> editorialRepository.save(
                            new Editorial("Penguin Random House Grupo Editorial", "https://es.wikipedia.org/wiki/Penguin_Random_House_Grupo_Editorial")
                    ));

			Editorial editorialPlaza = editorialRepository.findByNombreIgnoringCase("Plaza & Janés")
					.orElseGet(() -> editorialRepository.save(
						new Editorial("Plaza & Janés", "https://es.wikipedia.org/wiki/Plaza_%26_Jan%C3%A9s")
				));
				
			Editorial editorialTitania = editorialRepository.findByNombreIgnoringCase("Titania")
					.orElseGet(() -> editorialRepository.save(
						new Editorial("Titania", "https://www.titania.org/")
				));

			Editorial editorialNubeDeTinta = editorialRepository.findByNombreIgnoringCase("Nube de tinta")
					.orElseGet(() -> editorialRepository.save(
						new Editorial("Nube de tinta", "https://www.penguinrandomhousegrupoeditorial.com/sello/nube-de-tinta/")
				));

			Editorial editorialMolino = editorialRepository.findByNombreIgnoringCase("Editorial Molino")
					.orElseGet(() -> editorialRepository.save(
						new Editorial("Editorial Molino", "https://es.wikipedia.org/wiki/Editorial_Molino")
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
                            "https://upload.wikimedia.org/wikipedia/commons/5/5d/J._K._Rowling_2010.jpg"
                            
                        )
                    ));

            Autor autorHerbert = autorRepository.findByNombreIgnoringCase("Frank Herbert")
                    .orElseGet(() -> autorRepository.save(
                        new Autor(
                            "Frank Herbert",
                            "Escritor estadounidense de ciencia ficción, célebre por su obra maestra 'Dune'.",
                            "https://es.wikipedia.org/wiki/Frank_Herbert",
                            "https://upload.wikimedia.org/wikipedia/commons/8/8e/Frank_Herbert_1984_%28square%29.jpg"
                        )
                    ));

            Autor autorBrown = autorRepository.findByNombreIgnoringCase("Dan Brown")
                    .orElseGet(() -> autorRepository.save(
                        new Autor(
                                "Dan Brown",
                                "Novelista estadounidense, conocido por 'El código Da Vinci' y otros thrillers de misterio.",
                                "https://es.wikipedia.org/wiki/Dan_Brown",
                                "https://upload.wikimedia.org/wikipedia/commons/8/8b/Dan_Brown_bookjacket_cropped.jpg"
                        )
                    ));

            Autor autorChristie = autorRepository.findByNombreIgnoringCase("Agatha Christie")
                    .orElseGet(() -> autorRepository.save(
                        new Autor(
                                "Agatha Christie",
                                "Famosa escritora británica de novelas policíacas, creadora de Hércules Poirot y Miss Marple.",
                                "https://es.wikipedia.org/wiki/Agatha_Christie",
                                "https://upload.wikimedia.org/wikipedia/commons/c/cf/Agatha_Christie.png"
                        )
                    ));

            Autor autorGarciaMarquez = autorRepository.findByNombreIgnoringCase("Gabriel García Márquez")
                    .orElseGet(() -> autorRepository.save(
                        new Autor(
                                "Gabriel García Márquez",
                                "Escritor colombiano, exponente del realismo mágico y autor de 'Cien años de soledad'.",
                                "https://es.wikipedia.org/wiki/Gabriel_Garc%C3%ADa_M%C3%A1rquez",
                                "https://upload.wikimedia.org/wikipedia/commons/0/0f/Gabriel_Garcia_Marquez.jpg"
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
                                "https://upload.wikimedia.org/wikipedia/commons/2/24/Stephen_King_at_the_2024_Toronto_International_Film_Festival_2_%28cropped%29.jpg"
                        )
                    ));

			Autor autorRolon = autorRepository.findByNombreIgnoringCase("Gabriel Rolon")
                    .orElseGet(() -> autorRepository.save(
                        new Autor(
                                "Gabriel Rolon",
                                "Psicólogo y escritor argentino reconocido por sus libros que exploran el mundo emocional y los conflictos humanos desde una mirada clínica y sensible.",
                                "https://es.wikipedia.org/wiki/Gabriel_Rol%C3%B3n",
                                "https://upload.wikimedia.org/wikipedia/commons/e/e5/Gabriel_Rol%C3%B3n_-_distinci%C3%B3n_Amigo_de_las_Bibliotecas_Populares_2025_%28cropped%29.jpg"
                        )
                    ));
		
			Autor autorRivero = autorRepository.findByNombreIgnoringCase("Viviana Rivero")
                    .orElseGet(() -> autorRepository.save(
                        new Autor(
                                "Viviana Rivero",
                                "Escritora argentina destacada por sus novelas históricas y románticas, donde combina rigor documental con relatos apasionados.",
                                "https://es.wikipedia.org/wiki/Viviana_Rivero",
                                "https://upload.wikimedia.org/wikipedia/commons/thumb/2/25/Viviana_Rivero.jpg/330px-Viviana_Rivero.jpg"
                        )
                    ));

			Autor autorAllende = autorRepository.findByNombreIgnoringCase("Isabel Allende")
                    .orElseGet(() -> autorRepository.save(
                        new Autor(
                                "Isabel Allende",
                                "Escritora chilena reconocida internacionalmente por sus novelas que combinan realismo mágico, historia y memorias personales con una voz profundamente emotiva.",
                                "https://es.wikipedia.org/wiki/Isabel_Allende",
                                "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d0/Isabel_Allende_Frankfurter_Buchmesse_2015_%28cropped%29.JPG/330px-Isabel_Allende_Frankfurter_Buchmesse_2015_%28cropped%29.JPG"
                        )
                    ));

			Autor autorSchweblin = autorRepository.findByNombreIgnoringCase("Samanta Schweblin")
                    .orElseGet(() -> autorRepository.save(
                        new Autor(
                                "Samanta schweblin",
                                "Escritora argentina reconocida por su narrativa inquietante y minimalista, donde combina el suspenso psicológico con lo fantástico y lo perturbador.",
                                "https://es.wikipedia.org/wiki/Samanta_Schweblin",
                                "https://upload.wikimedia.org/wikipedia/commons/thumb/6/69/Samanta_Schweblin_en_el_FILBA_Montevideo_2018.jpg/330px-Samanta_Schweblin_en_el_FILBA_Montevideo_2018.jpg"
                        )
                    ));

			Autor autorKellen = autorRepository.findByNombreIgnoringCase("Alice Kellen")
                    .orElseGet(() -> autorRepository.save(
                        new Autor(
                                "Alice Kellen",
                                "Escritora española de literatura romántica juvenil y adulta. Comenzó su carrera como escritora en 2013 con Llévame a cualquier lugar.",
                                "https://es.wikipedia.org/wiki/Alice_Kellen",
                                "https://upload.wikimedia.org/wikipedia/commons/thumb/3/3a/Alicekellenimagen.jpg/330px-Alicekellenimagen.jpg"
                        )
                    ));

			Autor autorMeyer = autorRepository.findByNombreIgnoringCase("Marissa Meyer")
                    .orElseGet(() -> autorRepository.save(
                        new Autor(
                                "Marissa Meyer",
                                "Escritora estadounidense nacida en Tacoma, sus obras más conocidas son las novelas Cinder y Winter primer y ante penúltimo libro de la saga literaria futurista Crónicas Lunares.",
                                "https://es.wikipedia.org/wiki/Marissa_Meyer",
                                "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4c/Marissa_Meyer_%282018%29.jpg/330px-Marissa_Meyer_%282018%29.jpg"
                        )
                    ));

			Autor autorGreen = autorRepository.findByNombreIgnoringCase("John Green")
					.orElseGet(() -> autorRepository.save(
						new Autor(
						"John Green",
						"Escritor estadounidense de literatura juvenil, Video bblogger en YouTube y productor ejecutivo. Es reconocido mayormente por su libro Bajo la misma estrella y Ciudades de papel.",
						"https://es.wikipedia.org/wiki/John_Green",
						"https://upload.wikimedia.org/wikipedia/commons/thumb/c/c0/Let%27s_Talk_about_Money_-_Vlogbrothers_YouTube_at_0009_%28cropped%29.png/330px-Let%27s_Talk_about_Money_-_Vlogbrothers_YouTube_at_0009_%28cropped%29.png"
						)
					));

			Autor autorMoyes = autorRepository.findByNombreIgnoringCase("Jojo Moyes")
					.orElseGet(() -> autorRepository.save(
						new Autor(
						"Jojo Moyes",
						"Periodista y novelista romántica británica. Es una de las pocas autoras que ha ganado dos veces el Premio de Novela Romántica del Año por la Romantic Novelists' Association.",
						"https://es.wikipedia.org/wiki/Jojo_Moyes",
						"https://upload.wikimedia.org/wikipedia/commons/thumb/c/ce/Paris_-_Salon_du_livre_de_Paris_2017_-_Jojo_Moyes_-_001_%28cropped%29.jpg/330px-Paris_-_Salon_du_livre_de_Paris_2017_-_Jojo_Moyes_-_001_%28cropped%29.jpg"
						)
					));
			
			Autor autorRoth = autorRepository.findByNombreIgnoringCase("Veronica Roth")
					.orElseGet(() -> autorRepository.save(
						new Autor(
						"Veronica Roth",
						"Escritora estadounidense conocida por su novela superventas Divergente, y su secuela Insurgente.",
						"https://es.wikipedia.org/wiki/Veronica_Roth",
						"https://upload.wikimedia.org/wikipedia/commons/thumb/6/67/Veronica_Roth_March_18%2C_2014_%28cropped%29.jpg/330px-Veronica_Roth_March_18%2C_2014_%28cropped%29.jpg"
						)
					));

			Autor autorCollins = autorRepository.findByNombreIgnoringCase("Suzanne Collins")
					.orElseGet(() -> autorRepository.save(
						new Autor(
						"Suzanne Collins",
						"Escritora y guionista estadounidense, creadora de la famosa serie de Los juegos del hambre. ",
						"https://es.wikipedia.org/wiki/Suzanne_Collins",
						"https://upload.wikimedia.org/wikipedia/commons/thumb/b/b9/Suzanne_Collins_David_Shankbone_2010.jpg/330px-Suzanne_Collins_David_Shankbone_2010.jpg"
						)
					));

			Autor autorBenavent = autorRepository.findByNombreIgnoringCase("Elísabet Benavent")
					.orElseGet(() -> autorRepository.save(
						new Autor(
						"Elísabet Benavent",
						"Escritora española de novelas de comedia romántica. Es la autora de varias novelas en los que destaca la saga En los zapatos de Valeria.",
						"https://es.wikipedia.org/wiki/El%C3%ADsabet_Benavent",
						"https://upload.wikimedia.org/wikipedia/commons/7/71/Elisabet_Benavent_%2821620654366%29.jpg"
						)
					));

			Autor autorMcdowell = autorRepository.findByNombreIgnoringCase("Michael McDowell")
					.orElseGet(() -> autorRepository.save(
						new Autor(
						"Michael McDowell",
						"Fue un escritor y guionista estadounidense. Autor de novelas de terror góticas sureñas, fue llamado «el escritor más refinado de los Estados Unidos» por Stephen King.",
						"https://es.wikipedia.org/wiki/Michael_McDowell",
						"https://imagessl.casadellibro.com/img/autores/w/123009.webp"
						)
					));

			Autor autorHoover = autorRepository.findByNombreIgnoringCase("Colleen Hoover")
					.orElseGet(() -> autorRepository.save(
						new Autor(
						"Colleen Hoover",
						"Escritora estadounidense de gran éxito comercial, que ha vendido más de veinte millones de libros, centrados en los géneros de la novela romántica y la literatura juvenil.",
						"https://es.wikipedia.org/wiki/Colleen_Hoover",
						"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQDOBFYj9IgYy5PFeMJywQHzG3v6WR_m4OpVg&s"
						)
					));
			
			Autor autorDami = autorRepository.findByNombreIgnoringCase("Elisabetta Dami")
					.orElseGet(() -> autorRepository.save(
						new Autor(
						"Elisabetta Dami",
						"Escritora de literatura infantil italiana, famosa por la creación del personaje Geronimo y Tea Stilton.",
						"https://es.wikipedia.org/wiki/Elisabetta_Dami",
						"https://upload.wikimedia.org/wikipedia/commons/6/67/Elisabettadami-2019_%28cropped%29.jpg"
						)
					));
					
			Autor autorJeans = autorRepository.findByNombreIgnoringCase("Blue Jeans")
					.orElseGet(() -> autorRepository.save(
						new Autor(
						"Blue Jeans",
						"Pseudónimo de Francisco de Paula Fernández González, es un escritor español de literatura romántica y policíaca para adolescentes.",
						"https://es.wikipedia.org/wiki/Blue_Jeans_(escritor)",
						"https://upload.wikimedia.org/wikipedia/commons/thumb/1/1c/Blue_Jeans_escritor.jpg/330px-Blue_Jeans_escritor.jpg"
						)
					));

			Autor autorPescetti = autorRepository.findByNombreIgnoringCase("Luis Pescetti")
					.orElseGet(() -> autorRepository.save(
						new Autor(
						"Luis Pescetti",
						"Luis María Pescetti es un escritor, músico y cantante argentino. Ha publicado obras para niños y adultos.",
						"https://es.wikipedia.org/wiki/Luis_Pescetti",
						"https://upload.wikimedia.org/wikipedia/commons/thumb/2/23/Luis_Mar%C3%ADa_Pescetti.jpg/500px-Luis_Mar%C3%ADa_Pescetti.jpg"
						)
					));

			Autor autorTolkien = autorRepository.findByNombreIgnoringCase("Christopher Tolkien")
					.orElseGet(() -> autorRepository.save(
						new Autor(
						"Christopher Tolkien",
						"Escritor, poeta, filólogo, lingüista y profesor universitario británico, nacido en el Estado Libre de Orange. Conocido principalmente por ser el autor de El hobbit y El Señor de los Anillos.",
						"https://es.wikipedia.org/wiki/J._R._R._Tolkien",
						"https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/J._R._R._Tolkien%2C_ca._1925.jpg/330px-J._R._R._Tolkien%2C_ca._1925.jpg"
						)
					));

			Autor autorRiordan = autorRepository.findByNombreIgnoringCase("Rick Riordan")
					.orElseGet(() -> autorRepository.save(
						new Autor(
						"Rick Riordan",
						"Richard Russell \"Rick\" Riordan ​ es un escritor estadounidense. Es conocido por ser el autor de la saga Percy Jackson y los dioses del Olimpo y Los Héroes del Olimpo.",
						"https://es.wikipedia.org/wiki/Rick_Riordan",
						"https://upload.wikimedia.org/wikipedia/commons/thumb/f/f7/Rick_riordan_2007.jpg/500px-Rick_riordan_2007.jpg"
						)
					));

            // ------------------------
            // Inicialización de Libros
            // ------------------------
			//1
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
			//2
            if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial("Dune", "Primera edición", autorHerbert, editorialPenguin).isEmpty()) {
                Libro dune = new Libro();
                dune.setTitulo("Dune");
                dune.setDescripcion("En un futuro muy lejano, el desértico planeta Arrakis se convierte en epicentro de conflictos interplanetarios: es el único lugar donde se produce la ‘melange’ o especia, sustancia extremadamente valiosa que alarga la vida y permite los viajes espaciales. La Casa Atreides recibe la administración de Arrakis, enfrentándose a traiciones, intrigas políticas, amenazas del emperador y de casas rivales. Paul Atreides, joven heredero, deberá adaptarse a la hostilidad del planeta, forjar alianzas con los Fremen —habitantes del desierto—, y hacerse cargo de un destino que trasciende su propia supervivencia…");
                dune.setPaginas(412);
                dune.setEdicion("Primera edición");
                dune.setCalificacion(97);
                dune.setFechaPublicacion(LocalDate.of(1965, 8, 1));
                dune.setUrlFoto("https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/6b96984b-05dd-4f30-80bc-15ef99834924/9789877254112.jpg");
                dune.setPrecio(39.99);
                dune.setCategoria(Set.of(Categoria.cienciaficcion));
                dune.setEditorial(editorialPenguin);
                dune.setAutor(autorHerbert);
                libroRepository.save(dune);
            }
			//3
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
			//4
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
			//5
            if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial("Cien años de soledad", "Primera edición", autorGarciaMarquez, editorialPlaneta).isEmpty()) {
                Libro soledad = new Libro();
                soledad.setTitulo("Cien años de soledad");
                soledad.setDescripcion("Durante siete generaciones, la familia Buendía vive y sufre en el mítico pueblo de Macondo, fundado por José Arcadio Buendía y Úrsula Iguarán. En ese universo mágico donde lo real y lo fantástico coexisten, la soledad es fuerza y condena: amores imposibles, obsesiones, guerras, muertes, nacimientos… hechos extraordinarios conviven con lo cotidiano. Crece una ciudad aparte del mundo, se tejen mitos, llegan gitanos con inventos, resurgen fantasmas, y el destino parece marcar cada vida. Y a lo largo de cien años, el paso del tiempo revela la fragilidad de las memorias, la repetición de los errores y, sobre todo, la belleza y el dolor de lo humano.");
                soledad.setPaginas(471);
                soledad.setEdicion("Primera edición");
                soledad.setCalificacion(98);
                soledad.setFechaPublicacion(LocalDate.of(1967, 5, 30));
                soledad.setUrlFoto("https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/b3735ff2-ec78-49a6-8371-83c0afc30ff8/9788466379717_ac1359a8-df49-431d-a6b2-e0e6d8786cd7.webp");
                soledad.setPrecio(34.99);
                soledad.setCategoria(Set.of(Categoria.realismomagico));
                soledad.setEditorial(editorialPlaneta);
                soledad.setAutor(autorGarciaMarquez);
                libroRepository.save(soledad);
            }
			//6
            if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial("Orgullo y prejuicio", "Primera edición", autorAusten, editorialChapman).isEmpty()) {
                Libro orgullo = new Libro();
                orgullo.setTitulo("Orgullo y prejuicio");
                orgullo.setDescripcion("La familia Bennet vive en una sociedad inglesa del siglo XIX donde las mujeres jóvenes tienen pocas opciones que el matrimonio. Cuando llega el señor Charles Bingley, joven soltero y acomodado, su presencia sacude la tranquila vida de las cinco hermanas Bennet. Elizabeth, la segunda hija, destaca por su ingenio, honestidad y espíritu independiente. Su relación con el reservado señor Darcy comienza marcada por malentendidos, críticas sociales y diferencias de clase. A través del orgullo, los prejuicios y las expectativas familiares, ambos deberán superar sus propias creencias para hallar la verdad del amor, la comprensión y la autenticidad personal.");
                orgullo.setPaginas(432);
                orgullo.setEdicion("Primera edición");
                orgullo.setCalificacion(94);
                orgullo.setFechaPublicacion(LocalDate.of(1813, 1, 28));
                orgullo.setUrlFoto("https://www.penguinlibros.com/co/288462/orgullo-y-prejuicio.jpg");
                orgullo.setPrecio(22.99);
                orgullo.setCategoria(Set.of(Categoria.romance));
                orgullo.setEditorial(editorialChapman);
                orgullo.setAutor(autorAusten);
                libroRepository.save(orgullo);
            }
			//7
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
			//8
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial("Harry Potter y el misterio del principe", "Primera edición", autorJK, editorialSalamandra).isEmpty()) {
                Libro libro = new Libro();
                libro.setTitulo("Harry Potter y el misterio del principe");
                libro.setDescripcion("Con dieciséis años cumplidos, Harry inicia el sexto curso en Hogwarts en medio de terribles acontecimientos que asolan Inglaterra. Elegido capitán del equipo de quidditch, los ensayos, los exámenes y las chicas ocupan todo su tiempo, pero la tranquilidad dura poco. A pesar de los férreos controles de seguridad que protegen la escuela, dos alumnos son brutalmente atacados. Dumbledore sabe que se acerca el momento, anunciado por la Profecía, en que Harry y Voldemort se enfrentarán a muerte: «El único con poder para vencer al Señor Tenebroso se acerca… Uno de los dos debe morir a manos del otro, pues ninguno de los dos podrá vivir mientras siga el otro con vida.» El anciano director solicitará la ayuda de Harry y juntos emprenderán peligrosos viajes para intentar debilitar al enemigo, para lo cual el joven mago contará con un viejo libro de pociones perteneciente a un misterioso personaje, alguien que se hace llamar Príncipe Mestizo.");
                libro.setPaginas(579);
                libro.setEdicion("Primera edición");
                libro.setCalificacion(90);
                libro.setFechaPublicacion(LocalDate.of(2005, 7, 16));
                libro.setUrlFoto("https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/dfb31119-5232-4ef4-b34b-26261fbfa924/9788498389142.jpg");
                libro.setPrecio(36.97);
                libro.setCategoria(Set.of(Categoria.cienciaficcion));
				libro.setEditorial(editorialSalamandra);
				libro.setAutor(autorJK);
                libroRepository.save(libro);
            }
			//9
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial("Harry Potter y la cámara secreta", "Primera edición", autorJK, editorialSalamandra).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Harry Potter y la cámara secreta");
				libro.setDescripcion("El verano antes de empezar su segundo curso en Hogwarts no ha podido ser más caótico para Harry. Primero pasa el peor cumpleaños de su vida, después le visita de un elfo doméstico llamado Dobby con un montón de advertencias extrañas, y luego su amigo Ron lo rescata de casa de los Dursley con un coche volador. Una vez en la escuela, Harry oye unos misteriosos susurros que resuenan por los pasillos vacíos. Entonces empiezan los ataques y varios alumnos aparecen petrificados... Por lo visto, las siniestras predicciones de Dobby se están cumpliendo...");
				libro.setPaginas(288);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(90);
				libro.setFechaPublicacion(LocalDate.of(1998, 7, 2));
				libro.setUrlFoto("https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/a1e4fdc4-de78-4d5e-8958-87d070834e5b/9789878002330_17f7f20d-8ddc-454f-bc30-55bdcf5e4dbf.webp");
				libro.setPrecio(31.50);
				libro.setCategoria(Set.of(Categoria.fantastico, Categoria.aventura));
				libro.setEditorial(editorialSalamandra);
				libro.setAutor(autorJK);

				libroRepository.save(libro);
			}
			//10
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial("Harry Potter y la Orden del Fénix", "Primera edición", autorJK, editorialSalamandra).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Harry Potter y la Orden del Fénix");
				libro.setDescripcion("Estiraba el brazo y cogía el picaporte de la puerta… Se quedaba mirándola, desesperado por entrar… Detrás de aquella puerta había algo que él deseaba con toda su alma… Un premio que superaba todos sus sueños… En el espléndido y decadente hogar ancestral de Sirius Black, la Orden del Fénix se reúne entre las sombras de una guerra que está cada vez más cerca. A medida que lord Voldemort se hace más poderoso,sus pensamientos empiezan a irrumpir en los sueños de Harry Potter, revelando una obsesión por la magia que se oculta en las profundidades del Departamento de Misteriosa. En un curso repleto de secretos y subterfugios, Dolores Umbridge, Suma Inquisidora de Hogwarts, escudriña cada detalle de la vida en la escuela como parte del encargo del Ministerio de Magia de vigilar a Dumbledore y a su célebre alumno.Pero el valor llama a un leal grupo de estudiantes a formar el Ejército de Dumbledore, con el objetivo de detener a Umbridge y conseguir dominar la clase de magia que necesitarán para enfrentarse a las fuerzas oscuras. En un auténtico festín visual, esta espectacular edición ilustrada aúna los talentos de Jim Kay, galardonado con la medalla Kate Greenaway, y Neil Packer, ganador del premio Bologna Ragazzi en 2021. Mientras lees cómo La Orden del Fénix vela por Harry en su quinto curso en Hogwarts.");
				libro.setPaginas(896);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(89);
				libro.setFechaPublicacion(LocalDate.of(2003, 6, 21));
				libro.setUrlFoto("https://contentv2.tap-commerce.com/cover/large/9789878000145_1.jpg?id_com=1156");
				libro.setPrecio(45.50);
				libro.setCategoria(Set.of(Categoria.fantastico, Categoria.aventura));
				libro.setEditorial(editorialSalamandra);
				libro.setAutor(autorJK);

				libroRepository.save(libro);
			}
			//11
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial("Harry Potter y el cáliz de fuego", "Primera edición", autorJK, editorialSalamandra).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Harry Potter y el cáliz de fuego");
				libro.setDescripcion("Habrá tres pruebas, espaciadas en el curso escolar, que medirán a los campeones en muchos aspectos diferentes: sus habilidades mágicas, su osadía, sus dotes de deducción y, por supuesto, su capacidad para sortear el peligro.» Se va a celebrar en Hogwarts el Torneo de los Tres Magos. Sólo los alumnos mayores de diecisiete años pueden participar en esta comp etición, pero, aun así, Harry sueña con ganarla. En Halloween, cuando el cáliz de fuego elige a los campeones, Harry se lleva una sorpresa al ver que su nombre es uno de los escogidos por el cáliz mágico. Durante el torneo deberá enfrentarse a desafíos mortales, d ragones y magos tenebrosos, pero con la ayuda de Ron y Hermione, sus mejores amigos, íquizá logre salir con vida!");
				libro.setPaginas(636);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(92);
				libro.setFechaPublicacion(LocalDate.of(2000, 7, 8));
				libro.setUrlFoto("https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/3a8659fb-82b1-4480-b06c-dc733e56b64a/9789878000138.jpg");
				libro.setPrecio(39.99);
				libro.setCategoria(Set.of(Categoria.fantastico, Categoria.aventura));
				libro.setEditorial(editorialSalamandra);
				libro.setAutor(autorJK);

				libroRepository.save(libro);
			}
			//12
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial("Harry Potter y el prisionero de Azkaban", "Primera edición", autorJK, editorialSalamandra).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Harry Potter y el prisionero de Azkaban");
				libro.setDescripcion("Cuando el autobús noctámbulo irrumpe en una calle oscura y frena en seco delante de Harry, comienza para él un nuevo año en Hogwarts, lleno de acontecimientos extraños. El temido asesino Sirius Black se ha escapado de la prisión de Azkaban, y dicen que va en busca de Harry. Por si fuera poco, en clase de Adivinación, la profesora ve un augurio de muerte en su taza de té, y por el colegio patrullan dementores terroríficos! ¿Conseguirá Harry terminar el curso sano y salvo? ");
				libro.setPaginas(312);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(94);
				libro.setFechaPublicacion(LocalDate.of(1999, 7, 8));
				libro.setUrlFoto("https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/4bdebe8b-640e-4ef1-8ee3-683af8359a4e/9789878000121.jpg");
				libro.setPrecio(33.90);
				libro.setCategoria(Set.of(Categoria.fantastico, Categoria.aventura));
				libro.setEditorial(editorialSalamandra);
				libro.setAutor(autorJK);

				libroRepository.save(libro);
			}
			//13
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Secreto bien guardado", "Primera edición", autorRivero, editorialVR).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Secreto bien guardado");
				libro.setDescripcion("Un amor puede ser un gran testimonio de vida para las generaciones futuras. Hotel Edén, La Falda, Córdoba. Verano de 1940. En tiempos de la Segunda Guerra Mundial, el amor se abre camino de modos inimaginables. Desconociendo sus orígenes, Amalia Peres Kiev, una joven judía de familia de alcurnia, extrovertida y apasionada por la escritura, y Marthin M ¼ller, miembro del partido nazi con una misión que cumplir en Argentina, se enamoran de un modo irrefrenable, pero ambos tendrán que sufrir varias batallas personales, esperar años y guardar peligrosos secretos para darle forma a la pasión que sienten desde que cruzaron miradas en el famoso Hotel. En esta, su primera novela, y con gran rigor histórico, Viviana Rivero se atreve a contar una trama compleja entre dos personajes que nadie imaginaría unidos por el destino. Una historia arrolladora donde el amor resiste el paso del tiempo y los acontecimientos inesperados que llegan hasta el presente. Secreto bien guardado cuenta vidas atravesadas por el verdadero amor, que no sabe de fronteras ni de conflictos.");
				libro.setPaginas(320);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(88);
				libro.setFechaPublicacion(LocalDate.of(2010, 1, 1));
				libro.setUrlFoto("https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/26772dca-bb76-403a-abbc-9b13afd69377/9789506447823_0de83366-5a78-40ed-9b91-cadc210985a2.webp");
				libro.setPrecio(25.00);
				libro.setCategoria(Set.of(Categoria.historico, Categoria.romance, Categoria.drama));
				libro.setEditorial(editorialVR);
				libro.setAutor(autorRivero);
				libroRepository.save(libro);
			}
			//14
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Y ellos se fueron", "Primera edición", autorRivero, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Y ellos se fueron");
				libro.setDescripcion("Corre 1906 y los Ayala sufren las consecuencias de una plaga que arrasa con los viñedos de España. Isabel, casi una adolescente, está enamorada de Antonio Ruiz, otro hijo de viñateros en desgracia. Pero su familia decide casarla con Paco Reyes, uno de los pocos lugareños que logró vender sus tierras antes de la plaga. Isabel deja atrás su patria, su familia y su amor para embarcarse rumbo a la Argentina. En Mendoza funda con Paco un viñedo y se entrega al trabajo. Hasta que inesperadamente Antonio llega a Mendoza y la pasión resurge. Así, la protagonista se debatirá entre su vocación de levantar un imperio bodeguero y su anhelo de realizar por fin el sueño de su corazón.");
				libro.setPaginas(340);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(85);
				libro.setFechaPublicacion(LocalDate.of(2011, 1, 1));
				libro.setUrlFoto("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTlaieDqNfJAaQWuJm4ZytKTIlRQ1r3HOZrYA&s");
				libro.setPrecio(26.50);
				libro.setCategoria(Set.of(Categoria.historico, Categoria.romance, Categoria.drama));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorRivero);
				libroRepository.save(libro);
			}
			//15
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Lo que no se dice", "Primera edición", autorRivero, editorialVR).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Lo que no se dice");
				libro.setDescripcion("Las familias guardan secretos que se convierten en heridas y solo la verdad podrá curarlas. A principios del siglo XX, en la Patagonia argentina, entre huelgas obreras y el descubrimiento del petróleo en Comodoro Rivadavia, una foto guarda el secreto de un amor tan prohibido como negado. Un siglo después, el inesperado encuentro entre Elena Wilson Garrott, heredera de extensas tierras en el sur del país, y Omar González, un geólogo encantador, logrará cerrar heridas punzantes silenciadas por generaciones entre ambas familias. Con el pulso de un policial y la emoción del romance, Lo que no se dice recorre cincuenta años de historia argentina, donde una mujer se convierte en detective de su propio pasado, para revelar que solo el amor y la bondad pueden torcer un destino marcado por malas decisiones. Viviana Rivero construye una novela atrapante sobre la unión de culturas entre criollos e inmigrantes, con el acento puesto en el poder sanador de la verdad.");
				libro.setPaginas(300);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(87);
				libro.setFechaPublicacion(LocalDate.of(2012, 1, 1));
				libro.setUrlFoto("https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/3cfbc9fd-f8fd-4878-b625-8c0e3d19ea31/9789506447847_d84e2001-a693-4610-b2fc-c5f8829dc6a8.webp");
				libro.setPrecio(24.90);
				libro.setCategoria(Set.of(Categoria.drama, Categoria.romance));
				libro.setEditorial(editorialVR);
				libro.setAutor(autorRivero);
				libroRepository.save(libro);
			}
			//16
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"La dama de noche", "Primera edición", autorRivero, editorialVR).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("La dama de noche");
				libro.setDescripcion("Buenos Aires, 1935. Juan Bautista Fernán, joven abogado de un grupo de pequeños hacendados, se enfrenta a Héctor Argañaraz, hombre fuerte de la oligarquía ganadera. Invitado a cenar a la mansión de su oponente, Juan Bautista tendrá dos revelaciones perturbadoras: el encuentro con Abril, la hermosa hija, y la visión del extraño cuadro de un pintor italiano que le resulta familiar. Desde esa noche, para él nada será igual.Sus denuncias sobre maniobras fraudulentas lo transforman en un indeseable para la familia Argañaraz, a excepción de Abril. La muchacha, a punto de contraer matrimonio con Aldo Urizábal, recorre las calles de Buenos Aires de la mano de Juan Bautista y descubre una realidad social nueva. Pero mientras ellos viven su idilio imposible, un asesinato político conmueve a la Argentina. Triste y desilusionado, Juan Bautista emprenderá un viaje a Italia para desentrañar los orígenes del cuadro que lo obsesiona y alejarse de la caprichosa Abril. En La Mamma, restaurante florentino de Rosa Pieri, escuchará un largo relato que comienza en 1903 y que determinará su futuro.");
				libro.setPaginas(310);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(84);
				libro.setFechaPublicacion(LocalDate.of(2013, 1, 1));
				libro.setUrlFoto("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQNEeSvwnD0A4wwigp6ZyrxsgD0JYGfiTAfDQ&s");
				libro.setPrecio(23.50);
				libro.setCategoria(Set.of(Categoria.romance, Categoria.suspenso, Categoria.drama));
				libro.setEditorial(editorialVR);
				libro.setAutor(autorRivero);
				libroRepository.save(libro);
			}
			//17
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"La magia de la vida", "Primera edición", autorRivero, editorialVR).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("La magia de la vida");
				libro.setDescripcion("La magia de la vida es una novela luminosa que habla del coraje y del amor verdadero, ese que sana y que cree en las segundas oportunidades. Emilia Fernán, joven periodista argentina, viaja a Florencia para escribir una serie de artículos sobre comida europea y, de paso, aliviar la pena por la partida de su novio Manuel. Allí deberá cumplir con un pedido de su padre: encontrar alguna pista que los conduzca hasta el cuadro perdido del maestro Camilo Fiore. Mientras descubre los placeres de la comida italiana y sale en busca de la pintura, Emilia conoce a Fedele, el atractivo dueño del restaurante Buon Giorno, que la hará vivir un amor como nunca soñó. Los hilos invisibles que atraviesan la existencia terminarán llevándolos hasta un castillo en Piacenza, propiedad de un viejo conde que guarda secretos ocultos desde la Segunda Guerra Mundial y que estará más unido a ellos de lo que creían.");
				libro.setPaginas(280);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(86);
				libro.setFechaPublicacion(LocalDate.of(2014, 1, 1));
				libro.setUrlFoto("https://www.penguinlibros.com/ar/6721291-large_default/la-magia-de-la-vida.webp");
				libro.setPrecio(22.00);
				libro.setCategoria(Set.of(Categoria.romance, Categoria.drama));
				libro.setEditorial(editorialVR);
				libro.setAutor(autorRivero);
				libroRepository.save(libro);
			}
			//18
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Los colores de la felicidad", "Primera edición", autorRivero, editorialVR).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Los colores de la felicidad");
				libro.setDescripcion("La joven fotógrafa argentina Brisa Giulli viaja a Cuba como integrante de la comitiva que acompañará al quíntuple campeón de Fórmula 1 Juan Manuel Fangio durante el Gran Prix de La Habana. Hijo de un poderoso tabacalero y artista polifacético, Joel Fernández trabaja en secreto para la revolución en ciernes. Cuando Brisa y Joel se conocen durante una cálida noche de febrero de 1958, saben que ya nada será igual para ellos. Atravesados por los dramáticos acontecimientos políticos, vivirán un amor intenso, desmedido y ardiente. Pero, al calor de las transformaciones en el país caribeño, quedarán atrapados en una serie de situaciones límite que los obligarán a tomar terribles decisiones.");
				libro.setPaginas(320);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(88);
				libro.setFechaPublicacion(LocalDate.of(2015, 1, 1));
				libro.setUrlFoto("https://www.penguinlibros.com/ar/6709964-home_default/los-colores-de-la-felicidad.webp");
				libro.setPrecio(27.00);
				libro.setCategoria(Set.of(Categoria.romance, Categoria.drama));
				libro.setEditorial(editorialVR);
				libro.setAutor(autorRivero);
				libroRepository.save(libro);
			}
			//19
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Sí", "Primera edición", autorRivero, editorialVR).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Sí");
				libro.setDescripcion("A comienzos de 1920, Melisa Loyola, hija del sastre más renombrado de París, ansía convertirse en bailarina y cantante. Desde pequeña ayuda a su padre en el taller, y así entra en contacto con los rutilantes elencos del Moulin Rouge y del Folies Bergère. Sueña con brillar en un escenario, aunque no sea el futuro que su madre quiere para ella. Rico como un argentino» es la frase acuñada en el París de principios del siglo XX para referirse a los estancieros que se instalan allí durante largas temporadas y disfrutan de una vida ostentosa. El dinero parece manar de fuentes inagotables, y Nikolai Martínez Romanov es uno de esos privilegiados. Cuando conoce a Melisa, surge entre ellos un amor que parece indestructible y que se desplegará entre París y  Buenos Aires. Pero los vientos cambiarán de dirección y la pareja vivirá situaciones límite, que exigirán una valentía extraordinaria.");
				libro.setPaginas(250);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(83);
				libro.setFechaPublicacion(LocalDate.of(2017, 1, 1));
				libro.setUrlFoto("https://pdlibrosarg.cdnstatics2.com/usuaris/libros/thumbs/5271cfcf-cfc6-4eb5-8936-0ab265f05da2/d_360_620/portada_si_viviana-rivero_201703221947.webp");
				libro.setPrecio(21.00);
				libro.setCategoria(Set.of(Categoria.romance));
				libro.setEditorial(editorialVR);
				libro.setAutor(autorRivero);
				libroRepository.save(libro);
			}
			//20
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Zafiros en la piel", "Primera edición", autorRivero, editorialVR).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Zafiros en la piel");
				libro.setDescripcion("El amor, ese sentimiento poderoso como ninguno, recorre las páginas de los doce relatos que integran Zafiros en la piel. Desinteresado, erótico, altruista, fraternal, obsesivo, apasionado, sacrificado, abrasador: sus variantes son múltiples como los personajes que encarnan estas deliciosas historias. Mujeres resueltas a atravesar los océanos y enfrentar peligros para encontrar la felicidad; madres devotas de sus hijos hasta límites insospechados; esposas audaces, que desean reavivar el fuego de sus matrimonios; viudas que descubren que, entre los restos del pasado, hay todavía una promesa de futuro; señoras “de su casa” capaces de rebelarse a los mandatos sociales; muchachas convencidas de sus ideales y dispuestas a luchar hasta el final para defenderlos... Todas ellas tienen en común la fe en el amor que, como una moneda de oro, pasa de generación en generación para asegurarles que siempre habrá un nuevo comienzo.");
				libro.setPaginas(290);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(85);
				libro.setFechaPublicacion(LocalDate.of(2018, 1, 1));
				libro.setUrlFoto("https://sbslibreria.vtexassets.com/arquivos/ids/5060650-1200-auto?v=638852946653200000&width=1200&height=auto&aspect=true");
				libro.setPrecio(23.75);
				libro.setCategoria(Set.of(Categoria.romance, Categoria.drama));
				libro.setEditorial(editorialVR);
				libro.setAutor(autorRivero);
				libroRepository.save(libro);
			}
			//21
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"El alma de las flores", "Primera edición", autorRivero, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("El alma de las flores");
				libro.setDescripcion("Cuando la joven y atractiva María Álvarez aparece en la vida de los gemelos Díaz Montero, herederos de un emporio jamonero, estos están enfrentados por diferencias ideológicas. Con ella de por medio, la relación entre ellos no volverá a ser la misma, como tampoco lo será el aristocrático jardín de doña Encarnación, su madre. Las plantas han comenzado a sufrir cambios inexplicables: las rosas de Provenza muestran una debilidad extrema, los crisantemos son insuficientes para las tisanas, el narciso se desvanece misteriosamente... Lo que Encarnación no sabe es que la debacle de su jardín presagia la trágica grieta que dividirá a su familia y a toda España tras los acontecimientos políticos que llevarán a la guerra civil. Años después, Rafael Becerra, un argentino en crisis existencial, decide afincarse en Madrid para curar sus heridas. Atrás quedarán su trabajo de profesor de música, la mujer que alguna vez amó y su pequeño hijo. Para convertirse en un hombre nuevo, deberá enfrentarse a los desafíos propios de ser inmigrante en la tierra de su abuela María. Mientras tanto, una sucesión de hechos fortuitos le permitirá comprender por qué su abuela nunca hablaba sobre los años vividos en España, cuando algo terrible la empujó al exilio.");
				libro.setPaginas(330);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(89);
				libro.setFechaPublicacion(LocalDate.of(2019, 1, 1));
				libro.setUrlFoto("https://tienda.planetadelibros.com.ar/cdn/shop/files/D_641865-MLA82393274720_022025-O_900x.jpg?v=1740409978");
				libro.setPrecio(28.00);
				libro.setCategoria(Set.of(Categoria.historico, Categoria.romance, Categoria.drama));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorRivero);
				libroRepository.save(libro);
			}
			//23
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Apia de Roma", "Primera edición", autorRivero, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Apia de Roma");
				libro.setDescripcion("Apia Pópulus está a punto de perder a su madre. A pesar de su corta edad, conforme a las costumbres imperantes, contrae matrimonio con Salvio Sextus, un próspero comerciante de perlas de edad avanzada. En el Palatino, el barrio más encumbrado de la ciudad, la joven debe revestirse con una coraza para enfrentar la vida con alguien a quien no ama, cumplir con sus obligaciones de ama y defender su casa de las confabulaciones políticas. Roma es un hervidero repleto de intrigas. Tras el asesinato de Julio César en el Senado, Marco Antonio y Octavio, el futuro emperador, se disputan el poder. Los crímenes están a la orden del día y Salvio Sextus teme por su vida. Apia soporta con estoicismo la tiranía de su esposo y el ataque constante de Senecio, el hijo de éste. Como si fuera poco, se ve forzada a aceptar los humillantes juegos de dominación a los que, en su condición de mujer, la somete uno de los poderosos de la ciudad. Su único apoyo es su fiel esclava Furnilla. Juntas forjarán una alianza inquebrantable y lucharán por abrirse camino en un medio hostil. Hasta que la aparición de Manius Marcio, un atractivo centurión, trastoca la vida de Apia y le permite descubrir sentimientos y sensaciones que jamás había experimentado. A partir de ese momento tendrá que hacer equilibrio entre dar rienda suelta a su apasionado romance y pelear su lugar en un mundo de negocios patriarcal y misógino.");
				libro.setPaginas(360);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(90);
				libro.setFechaPublicacion(LocalDate.of(2023, 1, 1));
				libro.setUrlFoto("https://acdn-us.mitiendanube.com/stores/004/088/117/products/705737-4aaedf08a0d5bd09a217275426972682-1024-1024.webp");
				libro.setPrecio(30.00);
				libro.setCategoria(Set.of(Categoria.historico, Categoria.romance));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorRivero);
				libroRepository.save(libro);
			}
			//24
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial("Historias de diván", "Primera edición", autorRolon, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Historias de diván");
				libro.setDescripcion("Presenta relatos basados en casos reales de sus pacientes, enfocados en temas como el amor, el duelo, la culpa, los celos y la sexualidad. El libro busca mostrar la realidad del psicoanálisis, desmitificarlo y ofrecer una herramienta de autocomprensión al lector general, no solo a profesionales de la salud mental. ");
				libro.setPaginas(240);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(88);
				libro.setFechaPublicacion(LocalDate.of(2007, 1, 1));
				libro.setUrlFoto("https://pdlibrosarg.cdnstatics2.com/usuaris/libros/thumbs/f3663089-ba46-4318-9af2-72e41f5df5b9/d_360_620/portada_historias-de-divan_gabriel-rolon_201709121643.webp");
				libro.setPrecio(23.50);
				libro.setCategoria(Set.of(Categoria.psicologia));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorRolon);
				libroRepository.save(libro);
			}
			//25
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Palabras cruzadas", "Primera edición", autorRolon, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Palabras cruzadas");
				libro.setDescripcion("En esta edición definitiva y revisada, que incluye un caso nuevo, Rolón se lee a sí mismo. Se anima a interpelar ese reflejo que le devuelve un espejo anclado hace más de una década, en una sociedad otra en la que los debates por las políticas de género, la legalidad del consumo de marihuana y los usos disruptivos de la lengua de un idioma, entre otras cosas, no tenían ni la visibilidad ni el peso específico que tienen hoy. Y es ahí, en ese gesto genuino, tan franco y veraz hacia los lectores (los de entonces, los de ahora), en donde el autor hace que Palabras cruzadas se vuelva, una vez más, un libro vital, orgánico, indispensable.");
				libro.setPaginas(260);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(85);
				libro.setFechaPublicacion(LocalDate.of(2009, 1, 1));
				libro.setUrlFoto("https://pdlibrosarg.cdnstatics2.com/usuaris/libros/thumbs/92f861f4-0dcf-4c51-a4ee-159440191f46/d_1200_1200/palabras-cruzadas-ne_9789875806672.webp");
				libro.setPrecio(24.00);
				libro.setCategoria(Set.of(Categoria.psicologia));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorRolon);
				libroRepository.save(libro);
			}
			//26
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Los padecientes", "Primera edición", autorRolon, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Los padecientes");
				libro.setDescripcion("Hace poco menos de un año que Pablo Rouviot, un reconocido psicoanalista, termin una turbulenta historia de amor; desde entonces, se ha transformado en un hombre taciturno y solitario. Una noche, una joven de veintisiete años llega a su consultorio y le formula un pedido particular. El cuerpo de su padre, un poderoso empresario, fue encontrado acuchillado en un descampado, y su hermano, un joven con graves problemas psicolgicos, está acusado de haber sido el responsable del crimen. Ella necesita de-mostrar que su hermano es inimputable. Sin embargo, antes de responderle, Pablo decide hacer algunas averiguaciones y, de a poco, recompone una trama siniestra en la que nada es lo que parece.");
				libro.setPaginas(320);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(90);
				libro.setFechaPublicacion(LocalDate.of(2010, 1, 1));
				libro.setUrlFoto("https://pdlibrosarg.cdnstatics2.com/usuaris/libros/thumbs/339ea3db-f1e9-4097-831e-788a3b6ab487/d_360_620/portada_los-padecientes__201902061517.webp");
				libro.setPrecio(27.50);
				libro.setCategoria(Set.of(Categoria.psicologia));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorRolon);
				libroRepository.save(libro);
			}
			//27
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Encuentros", "Primera edición", autorRolon, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Encuentros");
				libro.setDescripcion("Este .lado B del amor. cuestiona sin miramientos los lugares comunes y construcciones cotidianas que, mas de una vez, nos precipitan con su vertigo hacia la angustia, el dolor y la desilusion. Y es en ese desandar el camino que Encuentros se vuelve un libro fundamental: un material indispensable para entender que, a pesar de sus asperezas, el amor sigue siendo el motor mas importante de nuestra vida. En esta version definitiva y aumentada, el lector encontrara un desarrollo mucho mas acabado de la teoria vertida en la obra original, asi como tambien un caso clinico inedito y un nuevo capitulo dedicado a los conceptos de necesidad, demanda y deseo.");
				libro.setPaginas(250);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(84);
				libro.setFechaPublicacion(LocalDate.of(2012, 1, 1));
				libro.setUrlFoto("https://pdlibrosarg.cdnstatics2.com/usuaris/libros/thumbs/eb1e5759-dec7-4f66-9e53-40ecb997bc9d/d_360_620/portada_encuentros_gabriel-rolon_201702201949.webp");
				libro.setPrecio(22.80);
				libro.setCategoria(Set.of(Categoria.psicologia));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorRolon);
				libroRepository.save(libro);
			}
			//28
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Historias inconscientes", "Primera edición", autorRolon, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Historias inconscientes");
				libro.setDescripcion("He asumido hace tiempo que jamás lograr extirpar el dolor de mis pacientes, porque el dolor es parte constitutiva de la vida. No importa cunto alguien se analice, de todos modos sufrir si pierde un amor o si muere un ser querido. El dolor es inevitable, pero no el padecimiento. Y esa diferencia es la que hace que cada da vuelva al consultorio.. Gabriel Rolón toma el riesgo de ir un poco más lejos, hacia una zona en la que quien padece llega a una situación límite. Por eso, por estas páginas transitan las adicciones, la discapacidad, el incesto, la mentira, la culpa, una histeria grave y sufriente, y un amor desmesurado al borde mismo de la locura. Y es ahí, en esa zona entre perturbadora y turbulenta, donde sale al cruce Historias inconscientes.");
				libro.setPaginas(280);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(86);
				libro.setFechaPublicacion(LocalDate.of(2014, 1, 1));
				libro.setUrlFoto("https://tienda.planetadelibros.com.ar/cdn/shop/files/D_981107-MLA80452464507_112024-O_900x.jpg?v=1730923062");
				libro.setPrecio(25.20);
				libro.setCategoria(Set.of(Categoria.psicologia));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorRolon);
				libroRepository.save(libro);
			}
			//29
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Cara a cara", "Primera edición", autorRolon, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Cara a cara");
				libro.setDescripcion("El nuevo libro de Gabriel Rolón es una aventura. No reúne casos clínicos ni relatos de ficción, tampoco es un libro de teoría: es algo distinto. Es un trabajo en el que decide hablar consigo mismo para pensar sobre cada uno de los enigmas que lo desvelan: el dese, el desamor, la muerte, los hijos, la pasión, la felicidad, el recuerdo y el olvido, entre otros. El desafío no es menor y «Cara a cara» va de frente. Porque este es, más que nada, un diálogo íntimo del analista con el analista, del hombre con el chico que fue, del licenciado que ahora sabe que ese título se juega en otra parte. Es un viaje que va del Psicoanálisis a la vida, y de la vida a un café. Y es ahí, en esa travesía con el corazón a flor de piel, donde nos encontramos con Gabriel Rolón como nunca antes, mano a mano en un espejo que le devuelve una imagen que lo interpela. Porque la verdad está muy lejos de cualquier reflejo, magia o inmediatez; él lo sabe. Eso es el análisis en una escala sensible.");
				libro.setPaginas(230);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(83);
				libro.setFechaPublicacion(LocalDate.of(2015, 1, 1));
				libro.setUrlFoto("https://www.planetadelibros.com.ar/usuaris/libros/fotos/207/original/portada_cara-a-cara_gabriel-rolon_201509301838.jpg");
				libro.setPrecio(21.75);
				libro.setCategoria(Set.of(Categoria.psicologia));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorRolon);
				libroRepository.save(libro);
			}
			//30
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"La voz ausente", "Primera edición", autorRolon, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("La voz ausente");
				libro.setDescripcion("Cuanto más cerca aparenta estar el mundo de cierta calma, mayor suele ser la tormenta que se gesta en lo inesperado. Un rayo acaba en segundos con la ensoación del psicoanalista Pablo Rouviot después de disfrutar de un concierto de violín: su mejor amigo, Jos, fue encontrado al borde de la muerte en su consultorio con un tiro en la cabeza. Todo parece indicar un intento de suicidio. Pero Pablo sospecha que la verdad es otra. Llevado por un impulso que lo empuja al lmite de lo irracional, y con la ayuda de su fiel compañero, el subcomisario Bermdez, se interna en una trama incierta, un universo critico y siniestro en el que el develamiento de un secreto familiar lo deja cara a cara con un enemigo oscuro, inteligente y peligroso. Así, sin saberlo, se sumerge en una historia en la que deber poner en juego sus herramientas analíticas para esclarecer la investigación policial, sin sospechar que, a cada paso, se ir convirtiendo en una potencial víctima. Vértigo, erotismo y juegos riesgosos que tienen a la muerte como protagonista hacen de La voz ausente, la segunda novela de Gabriel Rolón, un thriller psicolgico trepidante que genera en el lector sensaciones tan extremas como desconocidas.");
				libro.setPaginas(260);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(85);
				libro.setFechaPublicacion(LocalDate.of(2018, 1, 1));
				libro.setUrlFoto("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRe9K8sYGhaeKcDdZGSpn2cuY_uOYEk1zXgyw&s");
				libro.setPrecio(24.50);
				libro.setCategoria(Set.of(Categoria.psicologia));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorRolon);
				libroRepository.save(libro);
			}
			//31
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"El precio de la pasión", "Primera edición", autorRolon, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("El precio de la pasión");
				libro.setDescripcion("Gabriel Rolón echa anclas en las aguas profundas de la pasin. Un territorio habitado por dioses y demonios, por héroes picos que han dado batallas antolgicas, y esos otros cotidianos y de a pie, héroes anónimos que dan pelea poniendo en juego su piel con el mismo arrebato. El precio de la pasión recurre a la mitología, la literatura, la música, el cine y, cómo no, al consultorio del analista para llevar algo de luz a la penumbra de ese abismo que nos atrae de manera irresistible. El amor, el desamor, la soledad, la desesperación, el deseo, la angustia, el duelo, el exito, el fracaso y la felicidad se dan cita en este ensayo nocturno. en el que Rolón encuentra ese tono ajustado, preciso, para decirnos que cielo e infierno estn muy cerca, pero que entre ellos hay toda una vida para sentir.");
				libro.setPaginas(270);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(87);
				libro.setFechaPublicacion(LocalDate.of(2019, 1, 1));
				libro.setUrlFoto("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcToFnA55-0SQT56vsQq-NXIfPse0w0bHvVygA&s");
				libro.setPrecio(26.00);
				libro.setCategoria(Set.of(Categoria.psicologia));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorRolon);
				libroRepository.save(libro);
			}
			//32
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"El duelo", "Primera edición", autorRolon, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("El duelo");
				libro.setDescripcion("El Duelo es un territorio oscuro, misterioso, casi inaccesible. Una conmoción que nos sorprende, nos toma desprevenidos y cambia nuestro entorno en un instante. No importa lo preparados que creamos estar para enfrentar una prdida, esa preparación jamás será suficiente. Cuando ocurre, todo se desmorona y por un tiempo nada tiene sentido. Algo se quiebra en nosotros, el mundo se derrumba y nos muestra su aspecto más cruel.. Gabriel Rolón describe con estas palabras cuál es el camino a transitar en este ensayo: la pérdida. Sí, la muerte, sin rodeos (la propia y la de los que amamos), pero también la falta imprevista (o no tanto) de todo aquello que nos sostiene anclados a la vida.");
				libro.setPaginas(300);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(89);
				libro.setFechaPublicacion(LocalDate.of(2020, 1, 1));
				libro.setUrlFoto("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT1AwOm7oBFyMKcsDVzW8fq2ItBx69O0z3IFg&s");
				libro.setPrecio(28.00);
				libro.setCategoria(Set.of(Categoria.psicologia));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorRolon);
				libroRepository.save(libro);
			}
			//33
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"La felicidad", "Primera edición", autorRolon, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("La felicidad");
				libro.setDescripcion("Como imposible y como quimera, como fin y también como imperativo, la idea de la felicidad nos interpela más que nunca en los tiempos que corren. \"¿Cómo ser felices?\", esa sentencia que nos sobrevuela como mandato del mundo moderno se impuso para encandilarnos y hacernos perder de vista aquella que debería ser la pregunta \"¿Qué es la felicidad?\".");
				libro.setPaginas(290);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(90);
				libro.setFechaPublicacion(LocalDate.of(2023, 1, 1));
				libro.setUrlFoto("https://pdlibrosarg.cdnstatics2.com/usuaris/libros/thumbs/7068244d-dc40-4a5c-9432-3d0d8bf96447/d_360_620/portada_la-felicidad_gabriel-rolon_202311082001.webp");
				libro.setPrecio(29.00);
				libro.setCategoria(Set.of(Categoria.psicologia));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorRolon);
				libroRepository.save(libro);
			}
			//34
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"La casa de los espíritus", "Primera edición", autorAllende, editorialSudamericana).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("La casa de los espíritus");
				libro.setDescripcion("La casa de los espíritus narra la saga familiar de los Trueba, desde principios de siglo hasta el presente. Magistralmente ambientada en algún lugar de América Latina, la novela sigue paso a paso el dramático y extravagante destino de unos personajes atrapados en un entorno sorprendente y exótico.");
				libro.setPaginas(400);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(95);
				libro.setFechaPublicacion(LocalDate.of(1982, 1, 1));
				libro.setUrlFoto("https://contentv2.tap-commerce.com/cover/large/9789500745093_1.jpg?id_com=717");
				libro.setPrecio(32.50);
				libro.setCategoria(Set.of(Categoria.realismomagico, Categoria.historico));
				libro.setEditorial(editorialSudamericana);
				libro.setAutor(autorAllende);
				libroRepository.save(libro);
			}
			//35
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
        		"De amor y de sombra", "Primera edición", autorAllende, editorialSudamericana).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("De amor y de sombra");
				libro.setDescripcion("Esta obra encierra los temas sobre los que giran el amor y el odio. Sobre estos dos sentimientos se desarrollan los acontecimientos de la novela como en dos caminos probables que pelean por sobreponerse uno al otro. Finalmente vence el amor con la fe puesta en la libertad y en la dignidad humana. El inicio de un amor, el sentimiento patriótico aun en el exilio.");
				libro.setPaginas(320);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(90);
				libro.setFechaPublicacion(LocalDate.of(1984, 1, 1));
				libro.setUrlFoto("https://www.penguinlibros.com/ar/1595935-large_default/de-amor-y-de-sombra.webp");
				libro.setPrecio(28.90);
				libro.setCategoria(Set.of(Categoria.romance, Categoria.historico));
				libro.setEditorial(editorialSudamericana);
				libro.setAutor(autorAllende);
				libroRepository.save(libro);
			}
			//36
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
        		"Eva Luna", "Primera edición", autorAllende, editorialSudamericana).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Eva Luna");
				libro.setDescripcion("Eva Luna es hija de un ayudante de profesor y un jardinero mordido por una serpiente; nació pobre, quedó huérfana a temprana edad y trabajó como sirvienta. Eva posee un don natural e imaginativo para contar historias y conoce a personas de toda condición social. Aunque carece de riquezas, intercambia sus historias como si fueran moneda de cambio con quienes son amables con ella. En esta novela, comparte la historia de su vida y presenta a los lectores un elenco diverso y excéntrico de personajes, entre ellos el emigrante libanés que se hace amigo suyo y la acoge; su desafortunada madrina, cuya mente está alterada por el ron y que cree en todos los santos católicos y en algunos de su propia invención; un niño de la calle que se convierte en delincuente menor y, más tarde, en líder de la guerrilla; Una célebre artista transexual que la instruye en las costumbres del mundo adulto; y un joven refugiado cuya huida de la Europa de posguerra resultará crucial para el destino de Eva.");
				libro.setPaginas(360);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(92);
				libro.setFechaPublicacion(LocalDate.of(1987, 1, 1));
				libro.setUrlFoto("https://www.penguinlibros.com/ar/2127572-home_default/eva-luna.jpg");
				libro.setPrecio(30.70);
				libro.setCategoria(Set.of(Categoria.realismomagico, Categoria.romance));
				libro.setEditorial(editorialSudamericana);
				libro.setAutor(autorAllende);
				libroRepository.save(libro);
			}
			//37
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
        		"Paula", "Primera edición", autorAllende, editorialSudamericana).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Paula");
				libro.setDescripcion("Paula es el libro más conmovedor, más personal y más íntimo de Isabel Allende. Junto al lecho en que agonizaba su hija Paula, la gran narradora chilena escribió la historia de su familia y de sí misma con el propósito de regalársela a Paula cuando ésta superara el dramático trance. El resultado se convirtió en un autorretrato de insólita emotividad y en una exquisita recreación de la sensibilidad de las mujeres de nuestra época.");
				libro.setPaginas(430);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(96);
				libro.setFechaPublicacion(LocalDate.of(1994, 1, 1));
				libro.setUrlFoto("https://images.cdn1.buscalibre.com/fit-in/360x360/29/d3/29d316397a0d1aa602a5478c9202ea41.jpg");
				libro.setPrecio(34.50);
				libro.setCategoria(Set.of(Categoria.historico));
				libro.setEditorial(editorialSudamericana);
				libro.setAutor(autorAllende);
				libroRepository.save(libro);
			}
			//38
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
        		"Hija de la fortuna", "Primera edición", autorAllende, editorialSudamericana).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Hija de la fortuna");
				libro.setDescripcion("Eliza Sommers es una joven chilena que vive en Valparaíso en 1849, el año en que se descubre oro en California. Su amante, Joaquín Andieta, parte hacia el norte decidido a encontrar fortuna, y ella decide seguirlo. El viaje infernal, escondida en la cala de un velero, y la búsqueda de su amante en una tierra de hombres solos y prostitutas atraídos por la fiebre del oro, transforman a la joven inocente en una mujer fuera de lo común. Eliza recibe ayuda y afecto de Tao Chi'en, un médico chino, quien la conducirá de la mano en un itinerario memorable por los misterios y contradicciónes de la condición humana. hija de la fortuna es un retrato palpitante de una época marcada por la violencia y la codicia en la cual los protagonistas rescatan el amor, la amistad, la compasión y el valor.");
				libro.setPaginas(380);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(94);
				libro.setFechaPublicacion(LocalDate.of(1999, 1, 1));
				libro.setUrlFoto("https://www.penguinlibros.com/ar/3494651-large_default/hija-de-la-fortuna-trilogia-la-casa-de-los-espiritus-i.webp");
				libro.setPrecio(31.20);
				libro.setCategoria(Set.of(Categoria.historico, Categoria.aventura));
				libro.setEditorial(editorialSudamericana);
				libro.setAutor(autorAllende);
				libroRepository.save(libro);
			}
			//39
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Retrato en sepia", "Primera edición", autorAllende, editorialSudamericana).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Retrato en sepia");
				libro.setDescripcion("A finales del siglo XIX en Chile, Aurora del Valle sufre de un trauma brutal que borra de su mente los primeros cinco a;os de su vida. Criada por su ambiciosa abuela, Paulina del Valle, crece en un ambiente privilegiado, pero se ve atormentada por horribles pesadillas. Cuando debe afrontar la traici;n del hombre que ama, y la soledad, decide explorar el misterio de su pasado.");
				libro.setPaginas(350);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(91);
				libro.setFechaPublicacion(LocalDate.of(2000, 1, 1));
				libro.setUrlFoto("https://www.penguinlibros.com/ar/1595943/retrato-en-sepia.jpg");
				libro.setPrecio(29.50);
				libro.setCategoria(Set.of(Categoria.historico, Categoria.romance));
				libro.setEditorial(editorialSudamericana);
				libro.setAutor(autorAllende);
				libroRepository.save(libro);
			}
			//40
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
				"La ciudad de las bestias", "Primera edición", autorAllende, editorialSudamericana).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("La ciudad de las bestias");
				libro.setDescripcion("Alexander Cold, un joven de quince años está a punto de embarcarse con su temeraria abuela, en el viaje de su vida. Una expedición de la International Geographic se dirige hacia la remotas y peligrosas tierras salvajes de Suramérica para documentar al legendario Yeti del Amazonas, más conocido como \"La Bestia.\" Alex y su amiga Nadia descubrirán que el impenetrable mundo de la selva tropical esconde mucho más de lo que jamás hubieran imaginado.");
				libro.setPaginas(320);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(88);
				libro.setFechaPublicacion(LocalDate.of(2002, 1, 1));
				libro.setUrlFoto("https://images.cdn3.buscalibre.com/fit-in/360x360/fa/03/fa03a336fefcbc33e162462cb0ff1b76.jpg");
				libro.setPrecio(27.80);
				libro.setCategoria(Set.of(Categoria.aventura, Categoria.fantastico));
				libro.setEditorial(editorialSudamericana);
				libro.setAutor(autorAllende);
				libroRepository.save(libro);
			}
			//41
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"El cuaderno de Maya", "Primera edición", autorAllende, editorialPenguin).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("El cuaderno de Maya");
				libro.setDescripcion("“Soy Maya Vidal, diecinueve años, sexo femenino, soltera, sin un enamorado, por falta de oportunidades y no por quisquillosa, nacida en Berkeley, California, pasaporte estadounidense, temporalmente refugiada en una isla al sur del mundo. Me pusieron Maya porque a mi Nini le atrae la India y a mis padres no se les ocurrió otro nombre, aunque tuvieron nueve meses para pensarlo. En hindi, maya significa ‘hechizo, ilusión, sueño’. Nada que ver con mi carácter. Atila me calzaría mejor, porque donde pongo el pie no sale más pasto”.");
				libro.setPaginas(440);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(88);
				libro.setFechaPublicacion(LocalDate.of(2011, 1, 1));
				libro.setUrlFoto("https://www.penguinlibros.com/ar/1596128-large_default/el-cuaderno-de-maya.webp");
				libro.setPrecio(32.99);
				libro.setCategoria(Set.of(Categoria.historico, Categoria.romance));
				libro.setEditorial(editorialPenguin);
				libro.setAutor(autorAllende);
				libroRepository.save(libro);
			}
			//42
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Violeta", "Primera edición", autorAllende, editorialPlaza).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Violeta");
				libro.setDescripcion("La épica historia de Violeta del Valle, una mujer cuya vida abarca cien años y es testigo de las mayores convulsiones del siglo XX. Violeta llega al mundo en un día tormentoso de 1920, la primera niña de una familia de cinco hijos varones bulliciosos. Desde el principio, su vida estará marcada por acontecimientos extraordinarios, pues aún se sienten las repercusiones de la Gran Guerra, mientras que la gripe española llega a las costas de su Sudamérica natal casi al mismo tiempo que ella nace. Gracias a la clarividencia de su padre, la familia superará esa crisis ilesa, solo para enfrentarse a una nueva cuando la Gran Depresión transforma la refinada vida urbana que ella conocía. Su familia lo pierde todo y se ve obligada a refugiarse en una región salvaje y hermosa, pero remota, del país. Allí, Violeta alcanzará la mayoría de edad y su primer pretendiente la visitará…");
				libro.setPaginas(336);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(92);
				libro.setFechaPublicacion(LocalDate.of(2022, 1, 1));
				libro.setUrlFoto("https://acdn-us.mitiendanube.com/stores/001/029/689/products/violeta1-45d7a543d128ffd5d716431201163575-1024-1024.webp");
				libro.setPrecio(40.99);
				libro.setCategoria(Set.of(Categoria.historico, Categoria.romance));
				libro.setEditorial(editorialPlaza);
				libro.setAutor(autorAllende);
				libroRepository.save(libro);
			}
			//43
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Mi nombre es Emilia del Valle", "Primera edición", autorAllende, editorialPlaza).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Mi nombre es Emilia del Valle");
				libro.setDescripcion("En San Francisco, en 1866, una monja irlandesa, embarazada y abandonada tras una apasionada relación con un aristócrata chileno, da a luz a una hija llamada Emilia Del Valle. Criada por un cariñoso padrastro, Emilia se convierte en una joven independiente y autosuficiente.Para perseguir su pasión por la escritura, está dispuesta a desafiar las normas sociales. A los dieciséis años, comienza a publicar novelas populares bajo el seudónimo de un hombre. Cuando estos mundos ficticios ya no pueden contener su espíritu aventurero, se dedica al periodismo y convence a un editor del San Francisco Examiner para que la contrate. Allí forma equipo con otro talentoso reportero, Eric Whelan.");
				libro.setPaginas(384);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(90);
				libro.setFechaPublicacion(LocalDate.of(2005, 1, 1));
				libro.setUrlFoto("https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/e8afc1c5-a0e5-4db5-afd5-6ad3ace888f3/9789500771870_fc43a029-ebe3-43d1-a6ae-b174e283ee70.jpg");
				libro.setPrecio(33.99);
				libro.setCategoria(Set.of(Categoria.historico, Categoria.realismomagico, Categoria.romance));
				libro.setEditorial(editorialPlaza);
				libro.setAutor(autorAllende);
				libroRepository.save(libro);
			}
			//44
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Amor", "Primera edición", autorAllende, editorialSudamericana).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Amor");
				libro.setDescripcion("Mi vida sexual comenzó temprano, más o menos a los cinco años, en el jardín de infantes de las monjas ursulinas, en Santiago de Chile.\" Con estas palabras, Isabel Allende inicia este compendio sobre amor y eros compuesto por fragmentos escogidos de sus obras, que esbozan a través de sus personajes la propia trayectoria vital de la autora.");
				libro.setPaginas(320);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(87);
				libro.setFechaPublicacion(LocalDate.of(2010, 1, 1));
				libro.setUrlFoto("https://acdn-us.mitiendanube.com/stores/881/926/products/amor-ef2674576394f21e6a17008762545646-480-0.jpg");
				libro.setPrecio(29.99);
				libro.setCategoria(Set.of(Categoria.romance, Categoria.historico));
				libro.setEditorial(editorialSudamericana);
				libro.setAutor(autorAllende);
				libroRepository.save(libro);
			}
			//45
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"El viento conoce mi nombre", "Primera edición", autorAllende, editorialPlaza).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("El viento conoce mi nombre");
				libro.setDescripcion("Viena, 1938. Samuel Adler es un niño judío de seis años cuyo padre desaparece durante la Noche de los Cristales Rotos, en la que su familia lo pierde todo. Su madre, desesperada, le consigue una plaza en un tren que le llevará desde la Austria nazi hasta Inglaterra. Samuel emprende una nueva etapa con su fiel violín y con el peso de la soledad y la incertidumbre, que lo acompañarán siempre en su dilatada vida. Arizona, 2019. Ocho décadas más tarde, Anita Díaz, de siete años, sube con su madre a bordo de otro tren para escapar de un peligro inminente en El Salvador y exiliarse en Estados Unidos. Su llegada coincide con una nueva e implacable política gubernamental que la separa de su madre en la frontera. Sola y asustada, lejos de todo lo que le es familiar, Anita se refugia en Azabahar, el mundo mágico que solo existe en su imaginación. Mientras tanto, Selena Durán, una joven trabajadora social, y Frank Angileri, un abogado exitoso, luchan por reunir a la niña con su madre y por ofrecerle un futuro mejor.");
				libro.setPaginas(352);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(93);
				libro.setFechaPublicacion(LocalDate.of(2023, 6, 1));
				libro.setUrlFoto("https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/c90f236d-7208-4673-b5b0-63c1ff8e9cf0/9789500769013.jpg");
				libro.setPrecio(42.99);
				libro.setCategoria(Set.of(Categoria.historico, Categoria.romance));
				libro.setEditorial(editorialPlaza);
				libro.setAutor(autorAllende);
				libroRepository.save(libro);
			}
			//46
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Distancia de rescate", "Primera edición", autorSchweblin, editorialPenguin).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Distancia de rescate");
				libro.setDescripcion("solo de sequías y herbicidas, quizás se trate del hilo vital y filoso que nos ata a nuestros hijos, y del veneno que echamos sobre ellos. Nada es un cliché cuando finalmente sucede.Distancia de rescate sigue esta vertiginosa fatalidad haciéndose siempre las mismas preguntas: ¿Hay acaso algún apocalipsis que no sea personal? ¿Cuál es el punto exacto en el que, sin saberlo, se da el paso en falso que finalmente nos condena?");
				libro.setPaginas(192);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(95);
				libro.setFechaPublicacion(LocalDate.of(2014, 1, 1));
				libro.setUrlFoto("https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/8e1f15d6-c7e3-4a9f-af22-99ec0fd91b94/9789877694130_6a6f31ec-5f5e-4b5f-ad0f-df6223517c2a.webp");
				libro.setPrecio(28.99);
				libro.setCategoria(Set.of(Categoria.suspenso, Categoria.psicologia, Categoria.terror));
				libro.setEditorial(editorialPenguin);
				libro.setAutor(autorSchweblin);
				libroRepository.save(libro);
			}
			//47
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Kentukis", "Primera edición", autorSchweblin, editorialPenguin).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Kentukis");
				libro.setDescripcion("Casi siempre comienza en los hogares. Ya se registran miles de casos en Vancouver, Hong Kong, Tel Aviv, Barcelona, ​​Oaxaca, y se está propagando rápidamente a todos los rincones del mundo. Los kentukis no son mascotas, ni fantasmas, ni robots. Son ciudadanos reales, y el problema —se dice en las noticias y se comparte en las redes— es que una persona que vive en Berlín no debería poder pasearse libremente por el living de alguien que vive en Sídney; ni alguien que vive en Bangkok desayunar junto a tus hijos en tu departamento de Buenos Aires. En especial, cuando esas personas que dejamos entrar a casa son completamente anónimas.");
				libro.setPaginas(240);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(92);
				libro.setFechaPublicacion(LocalDate.of(2018, 1, 1));
				libro.setUrlFoto("https://acdn-us.mitiendanube.com/stores/001/029/689/products/kentukis1-17e3f1f4b0d341ab9516013826188417-1024-1024.webp");
				libro.setPrecio(31.99);
				libro.setCategoria(Set.of(Categoria.fantastico, Categoria.suspenso));
				libro.setEditorial(editorialPenguin);
				libro.setAutor(autorSchweblin);
				libroRepository.save(libro);
			}
			//48
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Pájaros en la boca", "Primera edición", autorSchweblin, editorialPenguin).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Pájaros en la boca");
				libro.setDescripcion("La presente compilación incluye veinte relatos ya publicados en ediciones anteriores de sus libros de cuentos y en ediciones internacionales, así como un relato inédito publicado por la revista Granta. La, llevada a cabo por la propia autora, configura una antología de selección de su mejor prosa breve hasta la fecha, así como una pieza indispensable de la literatura argentina contemporánea.");
				libro.setPaginas(208);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(91);
				libro.setFechaPublicacion(LocalDate.of(2009, 1, 1));
				libro.setUrlFoto("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRoxPq4CGqgPQPoppnARfKhHgVVTb2nWkNaLA&s");
				libro.setPrecio(25.99);
				libro.setCategoria(Set.of(Categoria.fantastico, Categoria.terror, Categoria.suspenso));
				libro.setEditorial(editorialPenguin);
				libro.setAutor(autorSchweblin);
				libroRepository.save(libro);
			}
			//49
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"El asesinato de Roger Ackroyd", "Primera edición", autorChristie, editorialPlaneta).isEmpty()) {

				Libro libro = new Libro();
				libro.setTitulo("El asesinato de Roger Ackroyd");
				libro.setDescripcion("La señora Ferrari ha muerto víctima de una sobredosis de somníferos. Hace un año, su marido murió al parecer de una gastritis aguda. Carolina Sheppard, la hermana del médico del pueblo, sospecha que fue envenenada. Poco después, Roger Ackroyd, el terrateniente de la villa, aparece muerto con una daga tunecina clavada en la espalda. ¿Estarán las tres muertes relacionadas?");
				libro.setPaginas(320);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(95);
				libro.setFechaPublicacion(LocalDate.of(1926, 1, 1));
				libro.setUrlFoto("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT-Z1zl9Tbe9s6W4czrJXOfs0r2nAOBh9dHCg&s");
				libro.setPrecio(28.99);
				libro.setCategoria(Set.of(Categoria.policial, Categoria.suspenso));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorChristie);
				libroRepository.save(libro);
			}
			//50
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Testigo de cargo", "Primera edición", autorChristie, editorialPlaneta).isEmpty()) {

				Libro libro = new Libro();
				libro.setTitulo("Testigo de cargo");
				libro.setDescripcion("Desde el impactante relato que da título al libro, adaptado al teatro y posteriormente convertido en el clásico thriller cinematográfico de Billy Wilder, estas historias de crímenes desconcertantes y brillantes deducciones muestran a Christie en su máximo esplendor. Sin mencionar que Hercule Poirot se enfrenta a su mayor desafío cuando la víctima solicita sus servicios.");
				libro.setPaginas(224);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(91);
				libro.setFechaPublicacion(LocalDate.of(1953, 1, 1));
				libro.setUrlFoto("https://images.cdn1.buscalibre.com/fit-in/360x360/5d/32/5d323f46674a3f55d9a581b5ac774979.jpg");
				libro.setPrecio(25.99);
				libro.setCategoria(Set.of(Categoria.policial, Categoria.suspenso));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorChristie);
				libroRepository.save(libro);
			}
			//51
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Carrie", "Primera edición", autorKing, editorialPenguin).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Carrie");
				libro.setDescripcion("Un clásico moderno, Carrie introdujo una voz nueva y distintiva en la ficción estadounidense: Stephen King. La historia de Carrie White, una incomprendida estudiante de secundaria, sus extraordinarios poderes telequinéticos y su violenta venganza, sigue siendo una de las novelas más transgresoras e impactantes de todos los tiempos.");
				libro.setPaginas(199);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(92);
				libro.setFechaPublicacion(LocalDate.of(1974,1,1));
				libro.setUrlFoto("https://images.cdn2.buscalibre.com/fit-in/360x360/25/5c/255c5ee92100361059d05bc18876194f.jpg");
				libro.setPrecio(24.99);
				libro.setCategoria(Set.of(Categoria.terror, Categoria.suspenso));
				libro.setEditorial(editorialPenguin);
				libro.setAutor(autorKing);
				libroRepository.save(libro);
			}
			//52
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"El resplandor", "Primera edición", autorKing, editorialPlaza).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("El resplandor");
				libro.setDescripcion("REDRUM. Esa era la palabra que Danny había visto. Y aunque no sabía leer, entendió que era un mensaje de horror el que se había reflejado en aquel espejo. Danny tenía cinco años, ya los cinco años, pocos niños saben leer, pocos niños saben que los espejos invierten las imágenes y pocos niños, casi ninguno, saben diferenciar la realidad de sus fantasías. Aunque, claro, Danny tenía pruebas de que sus fantasías, aquellas fantasías relacionadas con \"el resplandor\", acababan cumpliéndose. REDRUM-ASESINATO. Y el palo ensangrentado.");
				libro.setPaginas(447);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(94);
				libro.setFechaPublicacion(LocalDate.of(1977,1,1));
				libro.setUrlFoto("https://images.cdn2.buscalibre.com/fit-in/360x360/2b/f4/2bf446b4fb582f86bb6616ee8bc279aa.jpg");
				libro.setPrecio(29.99);
				libro.setCategoria(Set.of(Categoria.terror, Categoria.suspenso));
				libro.setEditorial(editorialPlaza);
				libro.setAutor(autorKing);
				libroRepository.save(libro);
			}
			//53
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"La milla verde", "Primera edición", autorKing, editorialPenguin).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("La milla verde");
				libro.setDescripcion("Octubre de 1932, penitenciaría de Cold Mountain. Los condenados a muerte guardan el momento de ser conducidos a la silla eléctrica. Los crímenes abominables que han cometido les convierten en carnaza de un sistema legal que se alimenta de un círculo de locura, muerte y venganza. Y en esa antesala del infierno Stephen King traza una pavorosa radiografía del horror en estado puro.");
				libro.setPaginas(400);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(93);
				libro.setFechaPublicacion(LocalDate.of(1996,1,1));
				libro.setUrlFoto("https://images.cdn3.buscalibre.com/fit-in/360x360/19/4d/194dba67ee8af46ac3a55b060fd90f9f.jpg");
				libro.setPrecio(32.99);
				libro.setCategoria(Set.of(Categoria.terror, Categoria.suspenso));
				libro.setEditorial(editorialPenguin);
				libro.setAutor(autorKing);
				libroRepository.save(libro);
			}
			//54
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
        		"Cementerio de animales", "Primera edición", autorKing, editorialPenguin).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Cementerio de animales");
				libro.setDescripcion("Un hermoso día de agosto, el doctor Louis Creed llega con su esposa Rachel, sus hijos Eileen y Gage y el gato Church a su nuevo hogar, una gran casa situada en las afueras de Ludlow, Nueva Inglaterra. El lugar parece tranquilo y lo bastante alejado del ajetreo urbano. Detrás de la casa de los Creed hay incluso un campo de enterramientos, en donde los niños del lugar han sepultado a sus animales durante generaciones: el Cementerio de animales. Pero enseguida empieza la pesadilla... al menos para Louis. En su primer día de trabajo en el centro médico de la Universidad de Maine, le llevan el cuerpo horriblemente mutilado del estudiante Victor Pascow. El joven trata de anunciar al doctor Creed acerca del lugar situado detrás del Cementerio de animales, usado en tiempos antiguos por los indios MicMac e impregnado de un espíritu diabólico. El gato Church muere atropellado por un camión y Judd Crandall, un anciano vecino, persuade a Louis para que entierre al animalito en el cementerio de animales. Con ello, el doctor invoca fuerzas ocultas que es mejor no perturbar, y cuando la tragedia hiere a la familia Creed, Louis se embarca en una terrorífica aventura con consecuencias que van más allá de la comprensión humana... o de la cordura.");
				libro.setPaginas(374);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(92);
				libro.setFechaPublicacion(LocalDate.of(1983,1,1));
				libro.setUrlFoto("https://acdn-us.mitiendanube.com/stores/001/731/769/products/9789877255133-7ea7703ade2608762e16964226268090-640-0.jpg");
				libro.setPrecio(28.99);
				libro.setCategoria(Set.of(Categoria.terror, Categoria.suspenso));
				libro.setEditorial(editorialPenguin);
				libro.setAutor(autorKing);
				libroRepository.save(libro);
			}
			//55
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"El pasillo de la muerte", "Primera edición", autorKing, editorialPlaza).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("El pasillo de la muerte");
				libro.setDescripcion("En un lugar del sur de los Estados Unidos, en plena Depresión, Paul Edgecomb es un funcionario de prisiones encargado de vigilar la Milla Verde, un pasillo que separa las celdas de los reclusos condenados a la silla eléctrica. John Coffey, un gigantesco negro acusado de asesinar brutalmente a dos hermanas de nueve años, está esperando su inminente ejecución. Tras una personalidad ingenua e infantil, Coffey esconde un prodigioso don sobrenatural");
				libro.setPaginas(446);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(89);
				libro.setFechaPublicacion(LocalDate.of(1996,1,1));
				libro.setUrlFoto("https://0.academia-photos.com/attachment_thumbnails/64970363/mini_magick20201118-14474-1vl1u7.png?1605708442");
				libro.setPrecio(31.99);
				libro.setCategoria(Set.of(Categoria.terror, Categoria.suspenso));
				libro.setEditorial(editorialPlaza);
				libro.setAutor(autorKing);
				libroRepository.save(libro);
			}
			//56
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Emma", "Primera edición", autorAusten, editorialPenguin).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Emma");
				libro.setDescripcion("Emma Woodhouse es uno de los personajes más cautivadores y vívidos de Austen. Bella, consentida, vanidosa e ingeniosa, Emma organiza la vida de los habitantes de su tranquilo pueblecito y hace de casamentera con consecuencias devastadoras.");
				libro.setPaginas(512);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(96);
				libro.setFechaPublicacion(LocalDate.of(1815,12,23));
				libro.setUrlFoto("https://images.cdn1.buscalibre.com/fit-in/360x360/c5/9f/c59fa2cfcda0ceb5d1a64e0a199d2973.jpg");
				libro.setPrecio(28.50);
				libro.setCategoria(Set.of(Categoria.romance, Categoria.historico));
				libro.setEditorial(editorialPenguin);
				libro.setAutor(autorAusten);
				libroRepository.save(libro);
			}
			//57
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Persuasión", "Primera edición", autorAusten, editorialPenguin).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Persuasión");
				libro.setDescripcion("Jane Austen es heredera de los logros de la novela inglesa del siglo XVIII, pero al mismo tiempo crea un estilo nuevo de novela más breve, concentrada e intensa, y exhibe en sus dos últimas obras un dominio de recursos narrativos en la expresión de la interioridad que se adelanta a su época. El realismo económico de Jane Austen, la implicación de sus primeras obras en los debates de la última década del siglo XVIII sobre la naturaleza humana, la familia, las instituciones sociales o la educación de las mujeres, y el reflejo en sus novelas de madurez de las innovaciones y escándalos de la Inglaterra de la Regencia, demuestra que la inteligencia creadora, alimentada por las lecturas y la observación, es capaz de interpretar el mundo desde la mesa de un cuarto de estar. de Jane Austen de la que se puede decir que es basicamente una historia de amor. Todas sus obras cuentan siempre el enamoramiento de una o dos parejas y acaban con la boda de los protagonistas, pero Persuasion es la única en la que el interés narrativo se centra en los sentimientos y en la interioridad de la protagonista.");
				libro.setPaginas(304);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(95);
				libro.setFechaPublicacion(LocalDate.of(1818,12,20));
				libro.setUrlFoto("https://images.cdn3.buscalibre.com/fit-in/360x360/5a/35/5a3561284bd697820313e8b63517efde.jpg");
				libro.setPrecio(23.99);
				libro.setCategoria(Set.of(Categoria.romance, Categoria.historico));
				libro.setEditorial(editorialPenguin);
				libro.setAutor(autorAusten);
				libroRepository.save(libro);
			}
			//58
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Llévame a cualquier lugar", "Primera edición", autorKellen, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Llévame a cualquier lugar");
				libro.setDescripcion("Blake y Léane no son las dos piezas de un rompecabezas destinadas a encajar, ni siquiera se gustaban cuando el concurso anual de periodismo de la universidad los puso en el mismo punto de salida.Él valora sus sueños por encima de cualquier cosa y no dejaría el camino hacia la meta, por una aventura que sabe una ciencia cierta que no durará. Ella posee un acento francés con la capacidad de volverlo loco y su encanto natural parece reflejarse en los ojos de su mayor contrincante.Cuando el calor de la atracción entra en su punto álgido, el frío de la realidad les muestra que los caminos más largos, a veces deben realizarse con alguien que te sostenga de la mano.");
				libro.setPaginas(320);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(88);
				libro.setFechaPublicacion(LocalDate.of(2013,1,1));
				libro.setUrlFoto("https://images.cdn1.buscalibre.com/fit-in/360x360/ce/33/ce333adad212cd6e080dbe3ae7428d62.jpg");
				libro.setPrecio(24.99);
				libro.setCategoria(Set.of(Categoria.romance));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorKellen);
				libroRepository.save(libro);
			}			
			//59
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"33 razones para volver a verte", "Primera edición", autorKellen, editorialTitania).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("33 razones para volver a verte");
				libro.setDescripcion("Mike, Rachel, Luke y Jason han sido amigos inseparables desde pequeños. Pero sus caminos se alejaron cuando Rachel cometió el error de enamorarse del chico equivocado, Mike, que terminó traicionando a la única persona que siempre estuvo dispuesta a arriesgarlo todo por él. Cinco años después, el destino vuelve a unirlos; pero ahora Rachel ha cambiado, es tan arisca como su gato Mantequilla y ya no se permite confiar en nadie. Por eso, a pesar de estar a punto de ser desahuciada, lo último que desea es dejarse convencer para mudarse con ellos. ¿Cómo podría mantener su corazón intacto ya salvo viviendo bajo el mismo techo que Mike? Sabe que esconde secretos y que su mirada gris es capaz de despertar todos los recuerdos que ella lleva tanto tiempo intentando olvidar.");
				libro.setPaginas(300);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(90);
				libro.setFechaPublicacion(LocalDate.of(2015,1,1));
				libro.setUrlFoto("https://contentv2.tap-commerce.com/cover/large/9789874568670_1.jpg?id_com=717");
				libro.setPrecio(26.99);
				libro.setCategoria(Set.of(Categoria.romance));
				libro.setEditorial(editorialTitania);
				libro.setAutor(autorKellen);
				libroRepository.save(libro);
			}
			//60
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"El día que dejó de nevar en Alaska", "Primera edición", autorKellen, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("El día que dejó de nevar en Alaska");
				libro.setDescripcion("Heather cree que solo hay tres cosas que sabe hacer: atraer problemas, salir huyendo y correr. Así es como termina en Alaska, en un pequeño pueblo perdido, trabajando de camarera mientras intenta llevar una vida nueva y tranquila. Su único problema es que uno de los dueños del restaurante parece odiarla y que ella nunca antes ha conocido a nadie que despierte tanto su curiosidad. Nilak está reservado, frío y distante, pero Heather puede ver a través de todas las capas tras las que se esconde y sabe que en ocasiones hay recuerdos que pesan demasiado; como los de sus propios errores, esos que intenta dejar atrás. Pero, a veces, la vida te da una segunda oportunidad. La nieve empieza a retirarse. Y todo encaja.");
				libro.setPaginas(352);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(92);
				libro.setFechaPublicacion(LocalDate.of(2017,1,1));
				libro.setUrlFoto("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT7iC-ndZ70TK9-RqfbnK-CLBNYrjGOAsMWl8UlPFegvNG4hhdKPr2LPHoNYsd3lxzMQ6CEipYp6VSZnI9NkFCn2Tsie1nGOaqyilHW81w&s=10");
				libro.setPrecio(29.99);
				libro.setCategoria(Set.of(Categoria.romance));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorKellen);
				libroRepository.save(libro);
			}			
			//61
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Tú y yo, invencibles", "Primera edición", autorKellen, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Tú y yo, invencibles");
				libro.setDescripcion("Por la autora de Nosotros en la luna. Lucas es familiar, impulsivo y transparente. Juliette es fuerte, introspectiva y liberal. Él vive en Vallecas, trabaja en un taller de coches junto a su mejor amigo y por las tardes tocan en un grupo de música que marcará el curso de sus vidas para siempre. Ella ha crecido con su abuela en un barrio acomodado, pero sueña con ser independiente, volar alto y dejar huella en el corazón de alguien. Una noche de 1978, en pleno estallido de la movida madrileña, sus caminos se cruzan. Entonces surge la atracción, el deseo, el amor. Un amor radiactivo que lo arrolla todo a su paso mientras los dos se vuelven inseparables en un ambiente desenfrenado lleno de cambios, atrapados entre el éxito y el fracaso, la luz y la oscuridad, el perdón y el orgullo. Pero Lucas es imperfecto. Y Juliette guarda secretos. ¿Es eterna la pasión? ¿Se pueden olvidar la mentira y la traición sin que queden esquirlas? ¿Qué ocurre cuando dos meteoritos que prometieron ser invencibles colisionan?");
				libro.setPaginas(310);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(89);
				libro.setFechaPublicacion(LocalDate.of(2021,1,1));
				libro.setUrlFoto("https://m.media-amazon.com/images/S/compressed.photo.goodreads.com/books/1609247970i/56100788.jpg");
				libro.setPrecio(27.99);
				libro.setCategoria(Set.of(Categoria.romance));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorKellen);
				libroRepository.save(libro);
			}
			//62
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Nosotros en la luna", "Primera edición", autorKellen, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Nosotros en la luna");
				libro.setDescripcion("Cuando Rhys y Ginger se conocen en las calles de la ciudad de la luz, no imaginan que sus vidas se unirán para siempre, a pesar de la distancia y de que no puedan ser más diferentes. Ella vive en Londres ya veces se siente tan perdida que se ha olvidado hasta de sus propios sueños. Él es incapaz de quedarse quieto en ningún lugar y cree saber quién es. Y cada noche su amistad crece entre correos electrónicos llenos de confianzas, dudas e inquietudes. ¿Pero qué ocurre cuando el paso del tiempo pone a prueba su relación? ¿Es posible colgarse de la luna junto a otra persona sin poner en riesgo el corazón?");
				libro.setPaginas(300);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(91);
				libro.setFechaPublicacion(LocalDate.of(2020,1,1));
				libro.setUrlFoto("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS3A8s21c6hSfeDsyWwxajuohPg0Ihsmi6AWg&s");
				libro.setPrecio(28.50);
				libro.setCategoria(Set.of(Categoria.romance));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorKellen);
				libroRepository.save(libro);
			}
			//63
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"El mapa de los anhelos", "Primera edición", autorKellen, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("El mapa de los anhelos");
				libro.setDescripcion("Imagina que estás destinado a salvar a tu hermana, pero al final ella muere y la razón de tu existencia se desvanece. Eso es lo que le ocurre a Grace Peterson, la chica que siempre se ha sentido invisible, la que nunca ha salido de Nebraska, la que colecciona palabras y ve pasar los días refugiada en la monotonía. Hasta que llega a sus manos el juego de «El mapa de los anhelos» y, siguiendo las instrucciones, lo primero que debe hacer es encontrar a alguien llamado Will Tucker del que nunca ha oído hablar y que está a punto de embarcarse con ella en un viaje directo al corazón, lleno de vulnerabilidades y sueños olvidados, anhelos y afectos inesperados. Pero ¿es posible avanzar cuando los secretos comienzan a pesar demasiado? ¿Quién es quién en esta historia?");
				libro.setPaginas(336);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(93);
				libro.setFechaPublicacion(LocalDate.of(2022,1,1));
				libro.setUrlFoto("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSP1661j4SWA86CaxVTDT6m_MFL4HcrEIJZlg&s");
				libro.setPrecio(30.99);
				libro.setCategoria(Set.of(Categoria.romance));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorKellen);
				libroRepository.save(libro);
			}
			//64
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Donde todo brilla", "Primera edición", autorKellen, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Donde todo brilla");
				libro.setDescripcion("¿Y si lo único que necesitamos para ser felices es descubrir el brillo de las cosas intangibles? Nicki Aldrich y River Jackson han sido inseparables desde que llegaron al mundo con cuarenta y siete minutos de diferencia. Ella lo hizo envuelta en polvo de hadas. Él como si fuese un meteoro en llamas. El pequeño pueblo costero donde crecieron se convirtió en el escenario de sus paseos en bicicleta, las tardes en la casa del árbol y los primeros amores, secretos y dudas. Sin embargo, con el paso de los años, River sueña con escapar de aquel rincón perdido donde todo gira alrededor de la tradicional pesca de langosta y Nicki anhela encuentra su lugar en el mundo. Pero ¿qué ocurre cuando nada sale como lo habían planeado? ¿Es posible elegir dos caminos distintos y, pese a todo, encontrarse en el final del trayecto? Para lograrlo, River y Nicki tendrán que bucear en las profundidades del corazón, rescatar pedazos de lo que fueron y entender aquello que rompieron. Y quizás así, uniendo y encajando cada fragmento, logren descubrir quiénes son ahora y recordar el brillo de las cosas intangibles.");
				libro.setPaginas(336);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(90);
				libro.setFechaPublicacion(LocalDate.of(2023,1,1));
				libro.setUrlFoto("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTqN0p3Oma4jCRW0hpelL-isdvFSzy6V3D7ag&s");
				libro.setPrecio(31.99);
				libro.setCategoria(Set.of(Categoria.romance));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorKellen);
				libroRepository.save(libro);
			}
			//65
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Las alas de Sophie", "Primera edición", autorKellen, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Las alas de Sophie");
				libro.setDescripcion("Cuando Sophie se enamoró de Simon, supo que juntos tejerían una historia inolvidable llena de vivencias y canciones, pero todo acabó una noche de enero y sus sueños quedaron congelados en aquel invierno eterno, el más largo y frío que nunca pudo imaginar. Hasta que el hielo empieza a retirarse para que Ámsterdam se vista de primavera. Entonces, Sophie descubre que Koen estará a su lado cuando decida alzar el vuelo, que su familia y amigos son su brújula, que ganar requiere de ingenio y que el corazón sigue sus propias reglas.");
				libro.setPaginas(296);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(88);
				libro.setFechaPublicacion(LocalDate.of(2020,1,1));
				libro.setUrlFoto("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSPf37nhEsgBHTVhokVHOVej9Vn4qjgX475Kg&s");
				libro.setPrecio(26.99);
				libro.setCategoria(Set.of(Categoria.romance));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorKellen);
				libroRepository.save(libro);
			}
			//66
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Todo lo que nunca fuimos", "Primera edición", autorKellen, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Todo lo que nunca fuimos");
				libro.setDescripcion("Axel es el mejor amigo de su hermano mayor y, cuando accede a acogerla en su casa durante unos meses, quiere ayudarla a encontrar y unir los pedazos de la chica llena de color que un día fue. Pero no sabe que ella siempre ha estado enamorada de él, a pesar de que sean casi familia, ni de que toda su vida está a punto de cambiar. Porque ella está prohibida, pero le despierta la piel. Porque es el mar, noches estrelladas y vinilos de los Beatles. Porque a veces basta un «deja que ocurre» para tenerlo todo.");
				libro.setPaginas(310);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(92);
				libro.setFechaPublicacion(LocalDate.of(2019,1,1));
				libro.setUrlFoto("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQfXDD2-iezBK_NzCKaUUiu-9c3_8aUYFUeJA&s");
				libro.setPrecio(28.99);
				libro.setCategoria(Set.of(Categoria.romance));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorKellen);
				libroRepository.save(libro);
			}
			//67
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Sigue lloviendo", "Primera edición", autorKellen, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Sigue lloviendo");
				libro.setDescripcion("Víctor y Sara una vez creyeron tenerlo todo. Se amaron con una intensidad que pocas veces se repite en la vida, pero también se hizo daño. Su historia terminó en un divorcio que los dejó rotos, obligándolos a reconstruirse el uno sin el otro. Ahora  intento seguir adelante, recogiendo los pedazos de lo que fueron y adaptándose a un presente donde ya no son «nosotros», sino dos extraños con un pasado compartido. Pero, cuando sus caminos se cruzan de nuevo , los sentimientos enterrados vuelven a salir a la superficie. La nostalgia se mezcla con el dolor, el deseo choca contra el miedo y el amor se viste de resentimiento. ¿Se puede amar y odiar a alguien a la vez? ¿Puede el amor transformarse y sobrevivir al tiempo? ¿Existen las segundas oportunidades… o algunas historias simplemente están destinadas a terminar?");
				libro.setPaginas(300);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(87);
				libro.setFechaPublicacion(LocalDate.of(2015,1,1));
				libro.setUrlFoto("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTVLofgGGZgYTRDxxH39fTr4mZfBg7nok7PZg&s");
				libro.setPrecio(25.99);
				libro.setCategoria(Set.of(Categoria.romance));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorKellen);
				libroRepository.save(libro);
			}
			//68
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Todo lo que somos juntos", "Primera edición", autorKellen, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Todo lo que somos juntos");
				libro.setDescripcion("Han pasado tres años desde la última vez que se vio. Ahora, Leah está a punto de cumplir su sueño de exponer en una galería.Y, pese al pasado, Axel necesita formar parte de un momento como ese. Cuando sus caminos vuelven a cruzarse, Leah tiene que tomar decisiones que pueden cambiarlo todo, porque, a pesar de lo que ocurrió, los recuerdos de toda su vida siguen ahí; intactos, bonitos, únicos. Colándose en cada grieta que aún no ha cerrado. Porque él sigue siendo el chico que aún no ha olvidado. Porque es el mar, noches estrelladas y vinilos de los Beatles. Porque a veces basta un «deja que ocurre» para tenerlo todo");
				libro.setPaginas(352);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(91);
				libro.setFechaPublicacion(LocalDate.of(2019, 4, 23));
				libro.setUrlFoto("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT76pGTqU1eiURtazCLGd7Dxh5wnTcVwWG7yQ&s");
				libro.setPrecio(32.50);
				libro.setCategoria(Set.of(Categoria.romance));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorKellen);
				libroRepository.save(libro);
			}
			//69
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
        			"El chico que dibujaba constelaciones", "Primera edición", autorKellen, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("El chico que dibujaba constelaciones");
				libro.setDescripcion("Esta es una historia de amor, de sueños y de vida. La de Valentina. La chica que no sabía que tenía el mundo a sus pies, la que creció y empezó a pensar en imposibles. La que cazaba estrellas, la que anhelaba más, la que tropezó con él. Con Gabriel. El chico que dibujaba constelaciones, el valiente e idealista, el que confió en las palabras «para siempre», y creó los pilares que terminaron sosteniendo el pasado, el ahora, lo que fueron y los recuerdos que se convertirán en polvo.");
				libro.setPaginas(304);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(92);
				libro.setFechaPublicacion(LocalDate.of(2018, 1, 15));
				libro.setUrlFoto("https://www.planetadelibros.com.ar/usuaris/libros/fotos/402/original/portada_el-chico-que-dibujaba-constelaciones_alice-kellen_202406112346.jpg");
				libro.setPrecio(30.99);
				libro.setCategoria(Set.of(Categoria.romance));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorKellen);
				libroRepository.save(libro);
			}
			//70
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"La teoría de los archipiélagos", "Primera edición", autorKellen, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("La teoría de los archipiélagos");
				libro.setDescripcion("La teoría de los archipiélagos viene a decir que todos somos islas, llegamos solos a este mundo y nos vamos exactamente iguales, pero necesitamos tener otras islas alrededor para sentirnos felices en medio de ese mar que une tanto como separa. Yo siempre pensé que sería una isla pequeñita, de esas en las que hay tres palmeras, una playa, dos rocas y poco más; me he sentido invisible durante gran parte de mi vida. Pero entonces apareciste tú, que sin duda serías una isla volcánica llena de grutas y flores. Y es la primera vez que me pregunto si dos islas pueden tocarse en la profundidad del océano, aunque nadie sea capaz de verlo. Si eso existe, si entre los corales y sedimentos y lo que sea que nos ancla en medio del mar hay un punto de unión, sin duda somos tú y yo. Y si no es así, estamos tan cerca que estoy convencido de poder llegar nadando hasta ti.");
				libro.setPaginas(400);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(89);
				libro.setFechaPublicacion(LocalDate.of(2022, 1, 27));
				libro.setUrlFoto("https://www.planetadelibros.com.ar/usuaris/libros/fotos/363/original/362769_portada_la-teoria-de-los-archipielagos_alice-kellen_202209071314.jpg");
				libro.setPrecio(37.50);
				libro.setCategoria(Set.of(Categoria.romance));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorKellen);
				libroRepository.save(libro);
			}
			//71
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Quedará el amor", "Primera edición", autorKellen, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Quedará el amor");
				libro.setDescripcion("El sol baña los acantilados y las aguas turquesas del mar de Cornualles cuando Jane Bellamy y Cedric Stone se conocen en el verano de 1939. No están destinados a ser una ecuación perfecta, pero son jóvenes y el amor lo arrolla todo a su paso. Así que esta historia comienza como otras muchas: él y ella se enamoran. Hay primeras palabras, primeras miradas y primeros besos. Y luego nada. Solo oscuridad. Todo cambia. Años más tarde, en un hospital de Edimburgo, Margot Abbot sostiene en la mano un anillo que pertenece al paciente que duerme en la cama, Cedric Stone. Ella todavía no lo sabe, pero está a punto de abrir un baúl de recuerdos y de descubrir qué ocurrió tras aquellos luminosos días de estío que quedaron atrás.");
				libro.setPaginas(352);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(88);
				libro.setFechaPublicacion(LocalDate.of(2016, 3, 10));
				libro.setUrlFoto("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRw3w3H6P3n01j_KIbOD94NRz_J7G39L9ligg&s");
				libro.setPrecio(28.99);
				libro.setCategoria(Set.of(Categoria.romance));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorKellen);
				libroRepository.save(libro);
			}
			//72
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Cinder", "Primera edición", autorMeyer, editorialPenguin).isEmpty()) {

				Libro libro = new Libro();
				libro.setTitulo("Cinder");
				libro.setDescripcion("Humanos y androides pueblan las bulliciosas calles de Nueva Pekín. Una plaga mortal asola a la población. Desde el espacio, un despiadado pueblo lunar observa, esperando el momento oportuno para actuar. Nadie sabe que el destino de la Tierra depende de una chica… Cinder, una mecánica prodigiosa, es una cíborg. Es una ciudadana de segunda clase con un pasado misterioso, despreciada por su madrastra y culpada de la enfermedad de su hermanastra. Pero cuando su vida se entrelaza con la del apuesto príncipe Kai, se encuentra de repente en el centro de una lucha intergaláctica y una atracción prohibida. Atrapada entre el deber y la libertad, la lealtad y la traición, debe desentrañar los secretos de su pasado para proteger el futuro de su mundo.");
				libro.setPaginas(390);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(93);
				libro.setFechaPublicacion(LocalDate.of(2012, 1, 1));
				libro.setUrlFoto("https://vreditoras.com.ar//uploads/libros/ef169e24de3e8dc5c116548ec8cecda9Cinder-TAPA-BAJA.jpg");
				libro.setPrecio(34.99);
				libro.setCategoria(Set.of(Categoria.cienciaficcion, Categoria.fantastico));
				libro.setEditorial(editorialPenguin);
				libro.setAutor(autorMeyer);
				libroRepository.save(libro);
			}
			//73
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Scarlet", "Primera edición", autorMeyer, editorialPenguin).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Scarlet");
				libro.setDescripcion("Cinder ha regresado e intenta escapar de prisión, aunque si lo consigue se convertirá en la fugitiva más buscada de la Commonwealth, en esta segunda entrega de Marissa Meyer. Al otro lado del mundo, la abuela de Scarlet Benoit ha desaparecido. Resulta que Scarlet desconoce muchos detalles sobre su abuela y el grave peligro que la ha azotado durante toda su vida. Cuando Scarlet conoce a Wolf, un luchador callejero que podría tener información sobre el paradero de su abuela, se muestra reacia a confiar en este desconocido, pero se siente inexplicablemente atraída hacia él, y él hacia ella. Mientras Scarlet y Wolf resuelven un misterio, se topan con otro al conocer a Cinder. Ahora, todos deben mantenerse un paso por delante de la despiadada Reina Lunar Levana.");
				libro.setPaginas(450);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(91);
				libro.setFechaPublicacion(LocalDate.of(2013, 1, 1));
				libro.setUrlFoto("https://images.cdn3.buscalibre.com/fit-in/360x360/35/26/3526b91008218019506b8373b20cc1e1.jpg");
				libro.setPrecio(35.99);
				libro.setCategoria(Set.of(Categoria.cienciaficcion, Categoria.fantastico));
				libro.setEditorial(editorialPenguin);
				libro.setAutor(autorMeyer);
				libroRepository.save(libro);
			}
			//74
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Cress", "Primera edición", autorMeyer, editorialPenguin).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Cress");
				libro.setDescripcion("En este tercer libro de las Crónicas Lunares, Cinder y el Capitán Thorne son fugitivos, ahora acompañados por Scarlet y Wolf. Juntos, planean derrocar a la Reina Levana y su ejército. Su mayor esperanza reside en Cress, una joven prisionera en un satélite desde su infancia, cuya única compañía han sido sus pantallas. Todo ese tiempo frente a la pantalla la ha convertido en una excelente hacker. Desafortunadamente, acaba de recibir órdenes de Levana para dar con Cinder y su apuesto cómplice. Cuando un audaz rescate de Cress fracasa, el grupo se separa. Cress por fin es libre, pero a un precio muy alto. Mientras tanto, la Reina Levana no permitirá que nada impida su matrimonio con el Emperador Kai. Puede que Cress, Scarlet y Cinder no se hayan alistado para salvar el mundo, pero quizá sean la única esperanza que le queda.");
				libro.setPaginas(500);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(92);
				libro.setFechaPublicacion(LocalDate.of(2014, 1, 1));
				libro.setUrlFoto("https://vreditoras.com.ar//uploads/libros/2a4b7927c144fde26e1599ede03c1657CRESS-TAPA-BAJA.jpg");
				libro.setPrecio(36.99);
				libro.setCategoria(Set.of(Categoria.cienciaficcion, Categoria.fantastico));
				libro.setEditorial(editorialPenguin);
				libro.setAutor(autorMeyer);
				libroRepository.save(libro);
			}
			//75
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Winter", "Primera edición", autorMeyer, editorialPenguin).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Winter");
				libro.setDescripcion("La princesa Winter es admirada por su gracia, bondad y belleza, a pesar de las cicatrices en su rostro. Se dice que es incluso más hermosa que su madrastra, la reina Levana... Cuando Winter se enamora del apuesto guardia del palacio, Jacin, teme que la malvada reina arruine su romance antes de que siquiera comience. Pero la rebelión contra la reina se extiende por todo el reino. Junto con la mecánica ciborg, Cinder, y sus aliados, Winter podría incluso encontrar el poder para iniciar una revolución y ganar una guerra que se ha prolongado demasiado.");
				libro.setPaginas(700);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(94);
				libro.setFechaPublicacion(LocalDate.of(2015, 1, 1));
				libro.setUrlFoto("https://vreditoras.com.ar//uploads/libros/a0c572d77a5d8fb2f5602a032bcf56e8Winter-TAPA-BAJA.jpg");
				libro.setPrecio(39.99);
				libro.setCategoria(Set.of(Categoria.cienciaficcion, Categoria.fantastico));
				libro.setEditorial(editorialPenguin);
				libro.setAutor(autorMeyer);
				libroRepository.save(libro);
			}
			//76
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Heartless", "Primera edición", autorMeyer, editorialPenguin).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Heartless");
				libro.setDescripcion("Catherine es una de las jóvenes más deseadas del País de las Maravillas y la favorita del soltero Rey de Corazones, pero sus intereses son otros. Talentosa pastelera, su único deseo es abrir una pastelería con su mejor amiga. Sin embargo, según su madre, tal meta es impensable para la joven que podría ser la próxima reina. Entonces, Cath conoce a Jest, el apuesto y misterioso bufón de la corte. Por primera vez, siente la fuerza de la verdadera atracción. Arriesgándose a ofender al rey y enfurecer a sus padres, ella y Jest inician un intenso y secreto noviazgo. Cath está decidida a forjar su propio destino y a enamorarse a su manera. Pero en una tierra donde abundan la magia, la locura y los monstruos, el destino tiene otros planes.");
				libro.setPaginas(420);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(90);
				libro.setFechaPublicacion(LocalDate.of(2016, 1, 1));
				libro.setUrlFoto("https://images.cdn3.buscalibre.com/fit-in/360x360/c2/3c/c23c70613dc1a56084d04b3ce04cc890.jpg");
				libro.setPrecio(33.99);
				libro.setCategoria(Set.of(Categoria.fantastico, Categoria.suspenso));
				libro.setEditorial(editorialPenguin);
				libro.setAutor(autorMeyer);
				libroRepository.save(libro);
			}
			//77
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Bajo la misma estrella", "Primera edición", autorGreen, editorialNubeDeTinta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Bajo la misma estrella");
				libro.setDescripcion("A Hazel ya Gus les gustaría tener vidas más corrientes. Algunos dirían que no han nacido con estrella, que su mundo es injusto. Hazel y Gus son solo adolescentes, pero si algo les ha enseñado el cáncer que ambos padecen es que no hay tiempo para lamentos, porque, nos guste o no, solo existe el hoy y el ahora. Y por ello, con la intención de hacer realidad el mayor deseo de Hazel —conocer a su escritor favorito—, cruzarán juntos el Atlántico para vivir una aventura contrarreloj, tan catártica como desgarradora. Destino: Ámsterdam, el lugar donde reside un enigmático y malhumorado escritor, la única persona que tal vez pueda ayudars a ordenar las piezas del enorme rompecabezas del que forman parte...");
				libro.setPaginas(304);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(96);
				libro.setFechaPublicacion(LocalDate.of(2012, 1, 10));
				libro.setUrlFoto("https://images.cdn3.buscalibre.com/fit-in/520x520/f2/67/f267cdd7d83a8b08394786f07f950ea1.jpg");
				libro.setPrecio(34.99);
				libro.setCategoria(Set.of(Categoria.romance, Categoria.drama));
				libro.setEditorial(editorialNubeDeTinta);
				libro.setAutor(autorGreen);
				libroRepository.save(libro);
			}
			//78
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Ciudades de papel", "Primera edición", autorGreen, editorialNubeDeTinta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Ciudades de papel");
				libro.setDescripcion("Quentin está en su último año de instituto, a punto de graduarse, y tiene un vínculo especial con su vecina Margo, de la que siempre ha estado enamorado. A pesar de que cuando eran niños pasaban muchas horas jugando juntos, desde hace tiempo ella apenas le dirige la palabra. Pero todo cambia la noche en que Margo va a buscarlo a su casa y le pide que la acompañe en un «road trip» algo particular: Margo se ha enterado de que su novio la engaña con su mejor amiga y ha planeado una venganza en toda regla que acometerá antes de la salida del sol. Quentin acepta convertirse en su cómplice, con la esperanza de que esa noche vuelva a unirlos para siempre... Pero las cosas no resultan como él desea: al día siguiente, Margo ha desaparecido sin decir nada a nadie. El misterio de su desaparición parece no importar demasiado a los padres de Margo: no es la primera vez que se escapa y ya están hartos de las continuas excentricidades de su hija, pero Quentin pronto se da cuenta de que esta vez todo es distinto: Margo ha dejado una serie de pistas encadenadas y pensadas para que solo él pueda descifrarlas...");
				libro.setPaginas(368);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(88);
				libro.setFechaPublicacion(LocalDate.of(2008, 10, 16));
				libro.setUrlFoto("https://contentv2.tap-commerce.com/cover/large/9789871997039_1.jpg?id_com=717");
				libro.setPrecio(32.99);
				libro.setCategoria(Set.of(Categoria.romance, Categoria.aventura));
				libro.setEditorial(editorialNubeDeTinta);
				libro.setAutor(autorGreen);
				libroRepository.save(libro);
			}
			//79
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Buscando a Alaska", "Primera edición", autorGreen, editorialNubeDeTinta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Buscando a Alaska");
				libro.setDescripcion("Cansado de su aburrida existencia, Miles, de 16 años, se muda a un colegio internado para ir en busca de lo que el poeta Rabelais llamó el “Gran quizás”. Ahí, su recién descubierta libertad y una enigmática chica, Alaska, lo lanzan de lleno a la vida. Pero cuando Miles siente que está por alcanzar su objetivo, una tragedia inesperada amenaza con arrebatárselo.");
				libro.setPaginas(320);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(89);
				libro.setFechaPublicacion(LocalDate.of(2005, 3, 3));
				libro.setUrlFoto("https://images.cdn1.buscalibre.com/fit-in/360x360/2d/8d/2d8d30dc79e4819ee6861448cb93c50e.jpg");
				libro.setPrecio(33.50);
				libro.setCategoria(Set.of(Categoria.romance));
				libro.setEditorial(editorialNubeDeTinta);
				libro.setAutor(autorGreen);
				libroRepository.save(libro);
			}
			//80
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Mil veces hasta siempre", "Primera edición", autorGreen, editorialNubeDeTinta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Mil veces hasta siempre");
				libro.setDescripcion("Aza nunca tuvo intención de investigar el misterio del multimillonario fugitivo Russell Pickett. Pero hay una recompensa de cien mil dólares en juego y su mejor y más intrépida amiga, Daisy, no está dispuesta a dejarla escapar. Así, juntas, recorrerán la corta distancia y las enormes diferencias que les separan del hijo de Russell Pickett, Davis. Aza lo está intentando. Trata de ser una buena hija, una buena amiga, una buena estudiante y, tal vez, incluso una buena detective, mientras vive en la espiral cada vez más estrecha de sus propios pensamientos.");
				libro.setPaginas(304);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(90);
				libro.setFechaPublicacion(LocalDate.of(2017, 10, 10));
				libro.setUrlFoto("https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/84be5c92-e013-4d2b-8719-7bd2718a77de/9789871997305.jpg");
				libro.setPrecio(34.99);
				libro.setCategoria(Set.of(Categoria.romance));
				libro.setEditorial(editorialNubeDeTinta);
				libro.setAutor(autorGreen);
				libroRepository.save(libro);
			}
			//81
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Noches Blancas", "Primera edición", autorGreen, editorialNubeDeTinta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Noches Blancas");
				libro.setDescripcion("Todo puede cambiar en cuestión de segundos, solo se necesita un poco de nieve y de magia navideña. La vida de Jubilee no es perfecta: acaba de discutir con su novio Noah y tiene que viajar hacia Florida por un problema con sus padres. Pero una inesperada tormenta de nieve en Nochebuena lo cambiará todo cuando el tren en el que viaja se detiene en la pequeña localidad de Gracetown. Afortunadamente, conoce a Stuart, un joven del mismo tren que la invita a pasar las fiestas con su familia. Como con las piezas de un dominó, un beso lo cambiará todo, y dará lugar a otras dos historias de amor. Tu vida está a punto de cambiar, ¿estás dispuesto a perdértelo?");
				libro.setPaginas(368);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(80);
				libro.setFechaPublicacion(LocalDate.of(2008, 10, 2));
				libro.setUrlFoto("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTaoknDZ9Mp-vbDw0WhY1O91BmFix-VJVucow&s");
				libro.setPrecio(28.99);
				libro.setCategoria(Set.of(Categoria.romance));
				libro.setEditorial(editorialNubeDeTinta);
				libro.setAutor(autorGreen);
				libroRepository.save(libro);
			}
			//82
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial("Yo antes de ti", "Primera edición", autorMoyes, editorialPenguin).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Yo antes de ti");
				libro.setDescripcion("Una historia que necesitas experimentar. Una novela inolvidable. Louisa Clark sabe muchas cosas. Sabe cuántos pasos hay entre la parada del autobús y su casa. Sabe que le gusta trabajar en el café Buttered Bun y sabe que quizás no quiera a su novio Patrick. Lo que Lou no sabe es que está a punto de perder su trabajo o que son sus pequeñas rutinas las que la mantienen en su sano juicio. Will Traynor sabe que un accidente de moto se llevó sus ganas de vivir. Sabe que ahora todo le parece insignificante y triste y sabe exactamente cómo va a solucionarlo. Lo que Will no sabe es que Lou está a punto de irrumpir en su mundo con una explosión de color. Y ninguno de los dos sabe que va a cambiar al otro para siempre. Yo antes de ti reúne a dos personas que no podrían tener menos en común en una novela conmovedoramente romántica con una pregunta: ¿Qué decidirías cuando hacer feliz a la persona a la que amas significa también destrozarte el corazón?");
				libro.setPaginas(496);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(95);
				libro.setFechaPublicacion(LocalDate.of(2012, 1, 5));
				libro.setUrlFoto("https://images.cdn3.buscalibre.com/fit-in/360x360/d0/ac/d0ac4eadaa99b866d2e308b6500feaed.jpg");
				libro.setPrecio(34.99);
				libro.setCategoria(Set.of(Categoria.romance, Categoria.drama));
				libro.setEditorial(editorialPenguin);
				libro.setAutor(autorMoyes);
				libroRepository.save(libro);
			}
			//83
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial("Después de ti", "Primera edición", autorMoyes, editorialPenguin).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Después de ti");
				libro.setDescripcion("¿Cómo se sigue adelante después de perder a la persona amada? ¿Cómo se construye una vida que valga la pena vivir? Louisa Clark ya no es una chica común y corriente que vive una vida común y corriente. Después de los transformadores seis meses que pasó con Will Traynor, está luchando sin él. Cuando un accidente extraordinario obliga a Lou a regresar a casa con su familia, no puede evitar sentir que está justo donde empezó. Su cuerpo sana, pero Lou sabe que necesita un empujón para volver a la vida. Así es como termina en el sótano de una iglesia con los miembros del grupo de apoyo «Siguiendo Adelante», quienes comparten reflexiones, risas, frustraciones y galletas terribles. También la llevarán hasta el fuerte y capaz Sam Fielding, el paramédico cuyo trabajo es cuestión de vida o muerte, y el único hombre que tal vez pueda comprenderla. Entonces aparece una figura del pasado de Will y trastoca todos sus planes, impulsándola hacia un futuro muy diferente… Para Lou Clark, la vida después de Will Traynor significa aprender a enamorarse de nuevo, con todos los riesgos que ello conlleva. Pero aquí Jojo Moyes nos presenta a dos familias, tan reales como la nuestra, cuyas alegrías y tristezas te conmoverán profundamente, y donde les esperan cambios y sorpresas.");
				libro.setPaginas(432);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(88);
				libro.setFechaPublicacion(LocalDate.of(2015, 9, 29));
				libro.setUrlFoto("https://images.cdn2.buscalibre.com/fit-in/360x360/83/4f/834f3dfb00fdc2e6eed67c89055bf040.jpg");
				libro.setPrecio(31.50);
				libro.setCategoria(Set.of(Categoria.romance, Categoria.drama));
				libro.setEditorial(editorialPenguin);
				libro.setAutor(autorMoyes);
				libroRepository.save(libro);
			}
			//84
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial("Sigo siendo yo", "Primera edición", autorMoyes, editorialPenguin).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Sigo siendo yo");
				libro.setDescripcion("Lou Clark sabe demasiadas cosas... Sabe cuántos kilómetros hay entre su nuevo hogar en Nueva York y su nuevo novio, Sam, en Londres. Sabe que su jefe es un buen hombre y sabe que su mujer le está ocultando un secreto. Lo que Lou no sabe es que está a punto de conocer a alguien que va a poner toda su vida patas arriba. Porque Josh le recordará tanto a un hombre que conoció que hace que el corazón le duela. Lou no sabe lo que hará a continuación, lo que sí sabe es que lo que decidirá lo cambiará todo para siempre.");
				libro.setPaginas(480);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(90);
				libro.setFechaPublicacion(LocalDate.of(2018, 1, 30));
				libro.setUrlFoto("https://images.cdn1.buscalibre.com/fit-in/360x360/b5/00/b500fa7183e463df4e59fcdbb78c0f00.jpg");
				libro.setPrecio(33.00);
				libro.setCategoria(Set.of(Categoria.romance, Categoria.drama));
				libro.setEditorial(editorialPenguin);
				libro.setAutor(autorMoyes);
				libroRepository.save(libro);
			}
			//85
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial("La chica que dejaste atrás", "Primera edición", autorMoyes, editorialPenguin).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("La chica que dejaste atrás");
				libro.setDescripcion("En 1916 el artista francés Édouard Lefèvre ha de dejar a su mujer, Sophie, para luchar en el frente. Cuando su ciudad cae en manos de los alemanes, ella se ve forzada a acoger a los oficiales que cada noche llegan al hotel que regenta. Y desde el momento en que el nuevo comandante posa su mirada en el retrato que Édouard pintó de su esposa nace en él una oscura obsesión que obligará a Sophie a arriesgarlo todo y tomar una terrible decisión. Casi un siglo más tarde, el retrato de Sophie llega a manos de Liv Halston como regalo de boda de su marido poco antes de su arrepentida muerte. Su belleza le recuerda su corta historia de amor. Pero cuando un encuentro casual revela el verdadero valor de la obra, comienza la batalla por su turbulenta historia, una historia que está a punto de resurgir, arrastrando con ella la vida de Liv.");
				libro.setPaginas(544);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(92);
				libro.setFechaPublicacion(LocalDate.of(2012, 9, 27));
				libro.setUrlFoto("https://images.cdn1.buscalibre.com/fit-in/360x360/49/c5/49c55ae19e997461f22e62fa755a0cb6.jpg");
				libro.setPrecio(36.10);
				libro.setCategoria(Set.of(Categoria.romance, Categoria.historico));
				libro.setEditorial(editorialPenguin);
				libro.setAutor(autorMoyes);
				libroRepository.save(libro);
			}
			//86
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial("París para uno", "Primera edición", autorMoyes, editorialPenguin).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("París para uno");
				libro.setDescripcion("Nell tiene veintiséis años y nunca ha estado en París. Ni siquiera ha pasado nunca un fin de semana romántico en ninguna parte. Viajar al extranjero no es realmente lo suyo. Pero cuando su novio no se presenta a su miniescapada, Nell tendrá la oportunidad de demostrar a todos -incluso a sí misma- que se equivocan. Sola en París, descubre una versión de su personalidad que ni siquiera sabía que existía: independiente e intrépida. ¿Se convertirá este fin de semana en la mayor aventura de su vida?");
				libro.setPaginas(288);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(85);
				libro.setFechaPublicacion(LocalDate.of(2016, 10, 18));
				libro.setUrlFoto("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSQGqlYU57g1c9XScbPEN9vU2WvBLk4aflGKQ&s");
				libro.setPrecio(24.90);
				libro.setCategoria(Set.of(Categoria.romance));
				libro.setEditorial(editorialPenguin);
				libro.setAutor(autorMoyes);
				libroRepository.save(libro);
			}
			//87
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial("Cuatro", "Primera edición", autorRoth, editorialMolino).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Cuatro");
				libro.setDescripcion("Llega el esperado spin-off de la trilogía Divergente, contada desde el punto de vista de Cuatro, con 4 historias distintas en un único volumen. Las cuatro piezas incluidas en Cuatro (La transferencia, El iniciado, El hijo, El traidor y tres escenas adicionales) darán a los lectores de la serie superventas Divergente, la mirada del popular Tobías sobre distintos momentos únicos en la épica trilogía.");
				libro.setPaginas(304);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(85);
				libro.setFechaPublicacion(LocalDate.of(2014, 7, 8));
				libro.setUrlFoto("https://images.cdn3.buscalibre.com/fit-in/360x360/4e/79/4e7993c55b74ac71366f5f571e3b4cab.jpg");
				libro.setPrecio(29.99);
				libro.setCategoria(Set.of(Categoria.cienciaficcion, Categoria.fantastico));
				libro.setEditorial(editorialMolino);
				libro.setAutor(autorRoth);
				libroRepository.save(libro);
			}
			//88
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial("Leal", "Primera edición", autorRoth, editorialMolino).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Leal");
				libro.setDescripcion("Para Tris Prior, la sociedad basada en facciones que conocía y en la que creía fue destruida por la corrupción del poder, la codicia, la pérdida y la violencia. Así que cuando tiene la oportunidad de ver y experimentar el mundo más allá de las paredes de su distópica Chicago, está lista para eso. Tal vez, ella y Tobias encontrarán una vida que es mucho más fácil y simple, libre de dolores, mentiras y traiciones. Pero la nueva realidad de Tris es aún más alarmante que la que había dejado atrás. Antiguos descubrimientos se vuelven rápidamente sin sentido. Nuevas verdades cambian a aquellos a quienes ama. Y una vez más, Tris debe luchar para comprender las complejidades de la naturaleza humana -y de sí misma- mientras se enfrenta a decisiones imposibles sobre el coraje, la lealtad, el sacrificio y el amor.");
				libro.setPaginas(496);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(87);
				libro.setFechaPublicacion(LocalDate.of(2013, 10, 22));
				libro.setUrlFoto("https://images.cdn1.buscalibre.com/fit-in/360x360/95/3c/953c43cf0554d79c0d79d88b76f6c1cb.jpg");
				libro.setPrecio(35.50);
				libro.setCategoria(Set.of(Categoria.cienciaficcion, Categoria.aventura));
				libro.setEditorial(editorialMolino);
				libro.setAutor(autorRoth);
				libroRepository.save(libro);
			}
			//89
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial("Insurgente", "Primera edición", autorRoth, editorialMolino).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Insurgente");
				libro.setDescripcion("Una sola elección puede transformarte o destruirte. Pero toda elección tiene consecuencias, y mientras la agitación crece entre las facciones a su alrededor, Tris Prior debe seguir intentando salvar a quienes ama —y a sí misma— mientras lidia con las inquietantes preguntas del duelo y el perdón, la identidad y la lealtad, la política y el amor. El día de su iniciación debería haber sido una celebración de victoria con su facción elegida; en cambio, terminó con horrores indescriptibles. La guerra se cierne sobre ella a medida que el conflicto entre las facciones y sus ideologías se intensifica. Y en tiempos de guerra, hay que elegir bando, los secretos saldrán a la luz y las decisiones se volverán aún más irrevocables y poderosas. Transformada por sus propias decisiones, pero también por el dolor y la culpa que la atormentan, nuevos descubrimientos radicales y relaciones cambiantes, Tris debe aceptar plenamente su Divergencia, incluso si desconoce lo que puede perder al hacerlo.");
				libro.setPaginas(544);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(90);
				libro.setFechaPublicacion(LocalDate.of(2012, 5, 1));
				libro.setUrlFoto("https://images.cdn2.buscalibre.com/fit-in/360x360/ff/9e/ff9eba845e44185fc48b2a672d34db6a.jpg");
				libro.setPrecio(34.00);
				libro.setCategoria(Set.of(Categoria.cienciaficcion, Categoria.aventura));
				libro.setEditorial(editorialMolino);
				libro.setAutor(autorRoth);
				libroRepository.save(libro);
			}
			//90
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial("Divergente", "Primera edición", autorRoth, editorialMolino).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Divergente");
				libro.setDescripcion("En el Chicago distópico de Beatrice Prior, la sociedad está dividida en cinco facciones, cada una de ellas dedicada a cultivar una virtud concreta: Verdad (los sinceros), Abnegación (los altruistas), Osadía (los valientes), Cordialidad (los pacíficos) y Erudición (los inteligentes). En una ceremonia anual, todos los chicos de dieciséis años deben decidir a qué facción dedicarán el resto de sus vidas. Beatrice tiene que elegir entre quedarse con su familia... y ser quien realmente es, no puede tener ambas cosas. Así que tomará una decisión que sorprenderá a todo el mundo, incluida ella. Durante el competitivo proceso de iniciación posterior, Beatrice decide pasar a llamarse Tris e intenta averiguar quiénes son sus verdaderos amigos, y dónde encaja en su vida, enamorarse de un chico que unas veces resulta fascinante y otras veces la exaspera. Sin embargo, Tris también tiene un secreto, un secreto que no ha contado a nadie para no poner su vida en peligro. Cuando descubre un conflicto que amenaza con desbaratar, en apariencia, la perfecta sociedad en la que vive, también averigua que su secreto podría ser la clave para salvar a los que ama o... para acabar muerta.");
				libro.setPaginas(464);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(92);
				libro.setFechaPublicacion(LocalDate.of(2011, 4, 26));
				libro.setUrlFoto("https://images.cdn1.buscalibre.com/fit-in/360x360/d9/ac/d9ac44103b7a77e3efeccfb5eda7c693.jpg");
				libro.setPrecio(32.90);
				libro.setCategoria(Set.of(Categoria.cienciaficcion, Categoria.aventura, Categoria.fantastico));
				libro.setEditorial(editorialMolino);
				libro.setAutor(autorRoth);
				libroRepository.save(libro);
			}
			//91
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Los Juegos del Hambre", "Primera edición", autorCollins, editorialMolino).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Los Juegos del Hambre");
				libro.setDescripcion("Un pasado de guerras ha dejado los 12 distritos que dividen Panem bajo el poder tiránico del “Capitolio”. Sin libertad y en la pobreza, nadie puede salir de los límites de su distrito. Sólo una chica de 16 años, Katniss Everdeen, osa desafiar las normas para conseguir comida. Sus principios se pondrán a prueba con “Los juegos del hambre”, espectáculo televisado que el Capitolio organiza para humillar a la población. Cada año, 2 representantes de cada distrito serán obligados a subsistir en un medio hostil y luchar a muerte entre ellos hasta que quede un solo superviviente. Cuando su hermana pequeña es elegida para participar, Katniss sin duda en ocupar su lugar, decidió a demostrar con su actitud firme y decidida, que aún en las situaciones más desesperadas hay lugar para el amor y el respeto.");
				libro.setPaginas(400);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(95);
				libro.setFechaPublicacion(LocalDate.of(2008, 9, 14));
				libro.setUrlFoto("https://images.cdn2.buscalibre.com/fit-in/360x360/30/f8/30f8f558cdbedb0fe519306d6e0aae69.jpg");
				libro.setPrecio(32.99);
				libro.setCategoria(Set.of(Categoria.cienciaficcion, Categoria.aventura, Categoria.suspenso));
				libro.setEditorial(editorialMolino);
				libro.setAutor(autorCollins);
				libroRepository.save(libro);
			}
			//92
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"En llamas", "Primera edición", autorCollins, editorialMolino).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("En llamas");
				libro.setDescripcion("Contra todo pronóstico, Katniss ha ganado Los Juegos del Hambre. Es un milagro que ella y su compañero del Distrito 12, Peeta Mellark, sigan vivos. Katniss debería sentirse aliviada, incluso contenta, ya que, al fin y al cabo, ha regresado con su familia y su amigo de toda la vida, Gale. Sin embargo, nada es como a ella le gustaría. Gale guarda las distancias y Peeta le ha dado la espalda por completo. Además se rumorea que existe una rebelión contra el Capitolio...");
				libro.setPaginas(416);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(94);
				libro.setFechaPublicacion(LocalDate.of(2009, 9, 1));
				libro.setUrlFoto("https://images.cdn2.buscalibre.com/fit-in/360x360/a1/f1/a1f15a4f5577084cfda5178cd6f35d5d.jpg");
				libro.setPrecio(34.99);
				libro.setCategoria(Set.of(Categoria.cienciaficcion, Categoria.aventura, Categoria.suspenso));
				libro.setEditorial(editorialMolino);
				libro.setAutor(autorCollins);
				libroRepository.save(libro);
			}
			//93
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Sinsajo", "Primera edición", autorCollins, editorialMolino).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Sinsajo");
				libro.setDescripcion("Katnis Everdeen ha sobrevivido dos veces a Los Juegos del Hambre, pero no está a salvo. La revolución se extiende y, al parecer, todos han tenido algo que ver en el meticuloso plan, todos excepto Katniss. Aun así su papel en la batalla final es el más importante de todos. Katniss debe convertirse en el Sinsajo, en el símbolo de la rebelión... a cualquier precio.");
				libro.setPaginas(432);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(92);
				libro.setFechaPublicacion(LocalDate.of(2010, 8, 24));
				libro.setUrlFoto("https://acdn-us.mitiendanube.com/stores/001/731/769/products/71wrweswtrl1-2e1c95dc520b4eee8c16533242656151-480-0.webp");
				libro.setPrecio(35.99);
				libro.setCategoria(Set.of(Categoria.cienciaficcion, Categoria.aventura, Categoria.suspenso));
				libro.setEditorial(editorialMolino);
				libro.setAutor(autorCollins);
				libroRepository.save(libro);
			}
			//94
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Balada de pájaros cantores y serpientes", "Primera edición", autorCollins, editorialMolino).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Balada de pájaros cantores y serpientes");
				libro.setDescripcion("Es la mañana de la cosecha que dará comienzo a los décimos Juegos del Hambre. En el Capitolio, Coriolanus Snow, de dieciocho años de edad, se prepara para la oportunidad única de alcanzar la gloria como mentor de los Juegos. La casa de los Snow, antes tan influyente, atraviesa tiempos difíciles, por lo que su destino depende de que Coriolanus consiga superar a sus compañeros en encanto, ingenio y estrategia como mentor del tributo que le sea adjudicado. Todo está en su contra... Lo han humillado al asignarle a la tributo del Distrito 12 y ahora, sus destinos están irremediablemente unidos.");
				libro.setPaginas(608);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(88);
				libro.setFechaPublicacion(LocalDate.of(2020, 5, 19));
				libro.setUrlFoto("https://images.cdn1.buscalibre.com/fit-in/360x360/c1/92/c1926f2d2edb59366661da8c7c3b606f.jpg");
				libro.setPrecio(38.99);
				libro.setCategoria(Set.of(Categoria.cienciaficcion, Categoria.aventura, Categoria.suspenso));
				libro.setEditorial(editorialMolino);
				libro.setAutor(autorCollins);
				libroRepository.save(libro);
			}
			//95
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Toda la verdad de mis mentiras", "Primera edición", autorBenavent, editorialPenguin).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Toda la verdad de mis mentiras");
				libro.setDescripcion("¿Puedes mantener una amistad a pesar de las mentiras? Una despedida de soltera en autocaravana. Un grupo de amigos... ...y muchos secretos.");
				libro.setPaginas(408);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(90);
				libro.setFechaPublicacion(LocalDate.of(2019, 2, 21));
				libro.setUrlFoto("https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/a766033c-11fd-4b9c-b56c-fd752d5d674e/9789877392593_ecc1f64f-a9ab-4e03-8e47-76019a0e5a99.webp");
				libro.setPrecio(32.50);
				libro.setCategoria(Set.of(Categoria.romance));
				libro.setEditorial(editorialPenguin);
				libro.setAutor(autorBenavent);
				libroRepository.save(libro);
			}
			//96
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Un cuento perfecto", "Primera edición", autorBenavent, editorialPenguin).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Un cuento perfecto");
				libro.setDescripcion("¿Qué sucede cuando descubres que el final de tu cuento no es como soñabas? Érase una vez una mujer que lo tenía todo y un chico que no tenía nada. Érase una vez una historia de amor entre el éxito y la duda. Borra una vez un cuento perfecto.");
				libro.setPaginas(640);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(95);
				libro.setFechaPublicacion(LocalDate.of(2020, 2, 20));
				libro.setUrlFoto("https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/6323a56f-e68f-460e-82bd-54dc54454805/9789877391695.jpg");
				libro.setPrecio(34.99);
				libro.setCategoria(Set.of(Categoria.romance));
				libro.setEditorial(editorialPenguin);
				libro.setAutor(autorBenavent);
				libroRepository.save(libro);
			}
			//97
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Esnob", "Primera edición", autorBenavent, editorialPenguin).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Esnob");
				libro.setDescripcion("Imagina: ets un tauró de les Finances estil llop de Wall Street, vens d'una buena familia i siempre ho has tingut tot, per no parlar del fet que no hi ha dona que se't resisteixi. I quan estàs a punt de tocar el cim de l'èxit amb la punta dels dits, ho perds tot... per culpa teva. La teva única sortida és tornar a començar, i aquí et trobes, amb el teu vestit esnob en un polígon industrial, en el teu primer dia com a ventafocs. Pero tranquilo, Alejo, que aquest no és el conte de siempre, o potser sí?");
				libro.setPaginas(368);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(85);
				libro.setFechaPublicacion(LocalDate.of(2013, 1, 10));
				libro.setUrlFoto("https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/7795b6f8-2ea8-4062-a4fc-28175efe0722/9789877392333_c8363fa8-bffa-4f6d-912d-72b554bd62d1.jpg");
				libro.setPrecio(29.90);
				libro.setCategoria(Set.of(Categoria.romance));
				libro.setEditorial(editorialPenguin);
				libro.setAutor(autorBenavent);
				libroRepository.save(libro);
			}
			//98
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Alguien que no soy", "Primera edición", autorBenavent, editorialPenguin).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Alguien que no soy");
				libro.setDescripcion("Alba es periodista de vocación, es soltera y sobrevive en Lavapiés. Alba pierde su empleo, pero Gabi le consigue una entrevista. Alba ahora es secretaria y se siente frustrada. En su primer día de trabajo cruza la mirada con un hombre en el metro. Ese hombre es Hugo. Sexy, provocadora y horriblemente deseable. También conocerá a Nicolás, enigmático e inquietantemente atractivo. Y...todo se complica cuando Hugo y Nico le proponen un juego...");
				libro.setPaginas(424);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(87);
				libro.setFechaPublicacion(LocalDate.of(2015, 4, 2));
				libro.setUrlFoto("https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/51590a62-480a-4782-941d-139057e8b8e7/9789877392418.jpg");
				libro.setPrecio(30.99);
				libro.setCategoria(Set.of(Categoria.romance));
				libro.setEditorial(editorialPenguin);
				libro.setAutor(autorBenavent);
				libroRepository.save(libro);
			}
			//99
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Alguien como yo", "Primera edición", autorBenavent, editorialPenguin).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Alguien como yo");
				libro.setDescripcion("Alba ha recuperado a Nico y se ha alejado de Hugo, que se ha cansado de jugar. Alba intenta que su relación con Nico funcione. Alba echa de menos a Hugo y tiene que fingir normalidad porque trabajan juntos. Alba piensa en Nueva York y decide ser ella misma la noche de fin de año. Alba, Nico y Hugo no volverán a ser quienes eran.");
				libro.setPaginas(400);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(88);
				libro.setFechaPublicacion(LocalDate.of(2015, 7, 2));
				libro.setUrlFoto("https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/0614461c-2f9d-426a-bd78-5fc60d6da110/9789877392449.jpg");
				libro.setPrecio(31.50);
				libro.setCategoria(Set.of(Categoria.romance));
				libro.setEditorial(editorialPenguin);
				libro.setAutor(autorBenavent);
				libroRepository.save(libro);
			}
			//100
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Seremos recuerdos", "Primera edición", autorBenavent, editorialPenguin).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Seremos recuerdos");
				libro.setDescripcion("Segunda parte de la bilogía Sofía, una historia sobre el amor, la magia y las decisiones que cambian la vida.");
				libro.setPaginas(408);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(90);
				libro.setFechaPublicacion(LocalDate.of(2018, 4, 5));
				libro.setUrlFoto("https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/1fbd574b-0c99-4036-ad16-099a0cffce0b/9789877391152.jpg");
				libro.setPrecio(33.90);
				libro.setCategoria(Set.of(Categoria.romance));
				libro.setEditorial(editorialPenguin);
				libro.setAutor(autorBenavent);
				libroRepository.save(libro);
			}
			//101
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Alguien como tú", "Primera edición", autorBenavent, editorialPenguin).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Alguien como tú");
				libro.setDescripcion("Segunda parte de Mi elección, donde las emociones se intensifican y la tensión amorosa se vuelve irresistible.");
				libro.setPaginas(400);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(87);
				libro.setFechaPublicacion(LocalDate.of(2015, 5, 7));
				libro.setUrlFoto("https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/992af217-1b34-4c5d-aa41-39ff6e31f995/9789877392425.jpg");
				libro.setPrecio(30.99);
				libro.setCategoria(Set.of(Categoria.romance));
				libro.setEditorial(editorialPenguin);
				libro.setAutor(autorBenavent);
				libroRepository.save(libro);
			}
			//102
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"La magia de ser Sofía", "Primera edición", autorBenavent, editorialPenguin).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("La magia de ser Sofía");
				libro.setDescripcion("Una historia sobre conexiones inesperadas, incertidumbre emocional y la magia que surge en lo cotidiano.");
				libro.setPaginas(400);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(92);
				libro.setFechaPublicacion(LocalDate.of(2017, 3, 2));
				libro.setUrlFoto("https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/2892d74b-889a-4faf-9c8c-dde550a1fc3a/9789877390889.jpg");
				libro.setPrecio(32.20);
				libro.setCategoria(Set.of(Categoria.romance));
				libro.setEditorial(editorialPenguin);
				libro.setAutor(autorBenavent);
				libroRepository.save(libro);
			}
			//103
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Encontrando a Silvia", "Primera edición", autorBenavent, editorialPenguin).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Encontrando a Silvia");
				libro.setDescripcion("Silvia busca reconstruir su vida y el amor mientras enfrenta los secretos y las decisiones del pasado.");
				libro.setPaginas(400);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(88);
				libro.setFechaPublicacion(LocalDate.of(2016, 5, 10));
				libro.setUrlFoto("https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/c485df25-9ac9-4425-93c9-c55fcbc17fb0/9789877392289.jpg");
				libro.setPrecio(32.50);
				libro.setCategoria(Set.of(Categoria.romance));
				libro.setEditorial(editorialPenguin);
				libro.setAutor(autorBenavent);
				libroRepository.save(libro);
			}
			//104
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Como no escribí nuestra historia", "Primera edición", autorBenavent, editorialPenguin).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Como no escribí nuestra historia");
				libro.setDescripcion("Una nueva forma de leer el amor. Porque a veces la verdad (no) es solo aquello que queremos creer. Elsa Benavides es una escritora de éxito con una crisis creativa y una obsesión: matar al personaje que la catapultó al éxito. Pero la solución a sus problemas no pasa por electrocutar a Valentina con un móvil en la bañera. Es la punta del iceberg de una herida más profunda. Decidida a huir para volver a abrazar la escritura, se topa con Darío, un músico recién llegado de París que además es su vecino. Empieza así una nueva historia en la que Elsa es la protagonista. ¿Será capaz de contarlo todo?");
				libro.setPaginas(368);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(87);
				libro.setFechaPublicacion(LocalDate.of(2017, 4, 15));
				libro.setUrlFoto("https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/1c4aabee-9685-4147-b72b-ef98900b4022/1e90079e-9147-41c9-95af-9b46b232a0a6.jpg");
				libro.setPrecio(31.99);
				libro.setCategoria(Set.of(Categoria.romance));
				libro.setEditorial(editorialPenguin);
				libro.setAutor(autorBenavent);
				libroRepository.save(libro);
			}
			//105
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Todas esas cosas que te diré mañana", "Primera edición", autorBenavent, editorialPenguin).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Todas esas cosas que te diré mañana");
				libro.setDescripcion("Una historia sobre segundas oportunidades, la vida que elegimos y los secretos que guardamos hasta el último momento.");
				libro.setPaginas(432);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(89);
				libro.setFechaPublicacion(LocalDate.of(2018, 2, 12));
				libro.setUrlFoto("https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/f6400b16-f591-456b-b965-fb19e359b8b5/9789877391831.jpg");
				libro.setPrecio(33.50);
				libro.setCategoria(Set.of(Categoria.romance));
				libro.setEditorial(editorialPenguin);
				libro.setAutor(autorBenavent);
				libroRepository.save(libro);
			}
			//106
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Valeria al desnudo", "Primera edición", autorBenavent, editorialPenguin).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Valeria al desnudo");
				libro.setDescripcion("Valeria enfrenta la vida con valentía, amor y amistad, mientras busca entender su lugar en el mundo y en el corazón de quienes ama.");
				libro.setPaginas(368);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(90);
				libro.setFechaPublicacion(LocalDate.of(2013, 1, 15));
				libro.setUrlFoto("https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/2a343de0-2bc4-4f70-809a-71dc79d7bf03/9789877392500_f5c6ef53-39d1-4784-a305-a9b05e6b438d.jpg");
				libro.setPrecio(31.99);
				libro.setCategoria(Set.of(Categoria.romance));
				libro.setEditorial(editorialPenguin);
				libro.setAutor(autorBenavent);
				libroRepository.save(libro);
			}
			//107
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Valeria en blanco y negro", "Primera edición", autorBenavent, editorialPenguin).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Valeria en blanco y negro");
				libro.setDescripcion("Segunda parte de la saga Valeria, donde los desafíos del amor y la amistad se complican con nuevas emociones y secretos.");
				libro.setPaginas(384);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(88);
				libro.setFechaPublicacion(LocalDate.of(2014, 2, 12));
				libro.setUrlFoto("https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/3d1c1fa0-8e6a-4488-a15d-6dcfd738d11e/9789877392494_d3c3f023-f976-4f44-8499-293b2b01e801.jpg");
				libro.setPrecio(32.50);
				libro.setCategoria(Set.of(Categoria.romance));
				libro.setEditorial(editorialPenguin);
				libro.setAutor(autorBenavent);
				libroRepository.save(libro);
			}
			//108
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Valeria en el espejo", "Primera edición", autorBenavent, editorialPenguin).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Valeria en el espejo");
				libro.setDescripcion("Tercera parte de la saga Valeria, con nuevas decisiones, retos personales y la magia de las relaciones que cambian la vida.");
				libro.setPaginas(392);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(87);
				libro.setFechaPublicacion(LocalDate.of(2015, 3, 18));
				libro.setUrlFoto("https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/f4142c2b-6d82-4fcf-909b-4b84f87b9fdd/9789877392487_02d9255d-7e10-4f6e-8e41-680891222916.jpg");
				libro.setPrecio(33.00);
				libro.setCategoria(Set.of(Categoria.romance));
				libro.setEditorial(editorialPenguin);
				libro.setAutor(autorBenavent);
				libroRepository.save(libro);
			}
			//109
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"En los zapatos de Valeria", "Primera edición", autorBenavent, editorialPenguin).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("En los zapatos de Valeria");
				libro.setDescripcion("Cuarta parte de la saga Valeria, con la protagonista enfrentando decisiones cruciales en su vida amorosa y profesional.");
				libro.setPaginas(400);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(89);
				libro.setFechaPublicacion(LocalDate.of(2016, 4, 10));
				libro.setUrlFoto("https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/5829423f-724a-4f08-a2ec-f3c5eb97f75b/9789877392470_ffc2f0cb-7064-41a1-97fe-34aa969e5b99.webp");
				libro.setPrecio(34.00);
				libro.setCategoria(Set.of(Categoria.romance));
				libro.setEditorial(editorialPenguin);
				libro.setAutor(autorBenavent);
				libroRepository.save(libro);
			}
			//110
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Fuimos canciones", "Primera edición", autorBenavent, editorialPenguin).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Fuimos canciones");
				libro.setDescripcion("La primera parte de la trilogía Canciones y recuerdos, con historias de amor, pasión y autodescubrimiento.");
				libro.setPaginas(416);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(91);
				libro.setFechaPublicacion(LocalDate.of(2018, 3, 8));
				libro.setUrlFoto("https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/2ba875f6-5a9a-4e65-a42d-18b2028bd5b9/9789877391138.jpg");
				libro.setPrecio(33.50);
				libro.setCategoria(Set.of(Categoria.romance));
				libro.setEditorial(editorialPenguin);
				libro.setAutor(autorBenavent);
				libroRepository.save(libro);
			}
			//111
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Martina en tierra firme", "Primera edición", autorBenavent, editorialPenguin).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Martina en tierra firme");
				libro.setDescripcion("Martina inicia un viaje de autodescubrimiento y amor en esta novela llena de emociones y decisiones vitales.");
				libro.setPaginas(384);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(88);
				libro.setFechaPublicacion(LocalDate.of(2019, 5, 7));
				libro.setUrlFoto("https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/1b2c2606-1b56-4acd-b9dd-c8f82c1edb96/9789877390766.jpg");
				libro.setPrecio(32.50);
				libro.setCategoria(Set.of(Categoria.romance));
				libro.setEditorial(editorialPenguin);
				libro.setAutor(autorBenavent);
				libroRepository.save(libro);
			}
			//112
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Martina con vistas al mar", "Primera edición", autorBenavent, editorialPenguin).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Martina con vistas al mar");
				libro.setDescripcion("Segunda parte de la historia de Martina, donde amor, decisiones y secretos se entrelazan en un viaje emocionante.");
				libro.setPaginas(392);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(89);
				libro.setFechaPublicacion(LocalDate.of(2020, 4, 10));
				libro.setUrlFoto("https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/85846a24-4392-4c5b-ad5f-eac11805a3de/9789877390759.jpg");
				libro.setPrecio(33.50);
				libro.setCategoria(Set.of(Categoria.romance));
				libro.setEditorial(editorialPenguin);
				libro.setAutor(autorBenavent);
				libroRepository.save(libro);
			}
			//113
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"El arte de engañar al karma", "Primera edición", autorBenavent, editorialPenguin).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("El arte de engañar al karma");
				libro.setDescripcion("Una novela sobre cómo nuestras decisiones, errores y segundas oportunidades nos moldean y nos enseñan a amar.");
				libro.setPaginas(416);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(90);
				libro.setFechaPublicacion(LocalDate.of(2018, 2, 12));
				libro.setUrlFoto("https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/da13c1a0-c5ab-4298-9a9e-a8c8ce037d1e/9789877391756.jpg");
				libro.setPrecio(33.50);
				libro.setCategoria(Set.of(Categoria.romance));
				libro.setEditorial(editorialPenguin);
				libro.setAutor(autorBenavent);
				libroRepository.save(libro);
			}
			//114
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Blackwater: La riada", "Primera edición", autorMcdowell, editorialTitania).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Blackwater: La riada");
				libro.setDescripcion("Las gélidas y oscuras aguas del río Blackwater inundan Perdido, un pequeño pueblo al sur de Alabama. Allí, los Caskey, un gran clan de ricos terratenientes, intentan hacer frente a los daños causados por la riada. Liderados por Mary-Love, la incontestable matriarca, y Óscar, su obediente hijo, los Caskey trabajan por recomponerse y salvar su fortuna. Pero no cuentan con la aparición de la misteriosa Elinor Dammert. Una joven hermosa pero parca en palabras con una única lente: acercarse a los Caskey cueste lo que cueste.");
				libro.setPaginas(320);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(90);
				libro.setFechaPublicacion(LocalDate.of(2014, 5, 20));
				libro.setUrlFoto("https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/b77b4b89-900a-4cdc-a9da-049129fb633d/9789878453507_a42918b3-fcc2-449a-bb30-11b5607d56bf.jpg");
				libro.setPrecio(29.99);
				libro.setCategoria(Set.of(Categoria.terror));
				libro.setEditorial(editorialTitania);
				libro.setAutor(autorMcdowell);
				libroRepository.save(libro);
			}
			//115
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Blackwater: La casa", "Primera edición", autorMcdowell, editorialTitania).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Blackwater: La casa");
				libro.setDescripcion("Perdido, 1928. El clan Caskey se desmorona con la cruenta guerra personal entre Mary-Love y Elinor. En los recovecos del caserón donde viven Elinor y Oscar se esconden crisis conyugales y existenciales con repercusiones que desafían la imaginación, mientras los peores recuerdos, aquellos que uno se esfuerza por mantener ocultos, acechan para tejer sus mortíferas redes y salir a flote.");
				libro.setPaginas(352);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(89);
				libro.setFechaPublicacion(LocalDate.of(2016, 6, 12));
				libro.setUrlFoto("https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/b4a4a28a-089b-4daa-b628-fdb73241bb4b/9789878453538_b55c3c37-15e0-4cc6-91f4-134a5117eada.jpg");
				libro.setPrecio(31.00);
				libro.setCategoria(Set.of(Categoria.terror));
				libro.setEditorial(editorialTitania);
				libro.setAutor(autorMcdowell);
				libroRepository.save(libro);
			}
			//116
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Blackwater: La fortuna", "Primera edición", autorMcdowell, editorialTitania).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Blackwater: La fortuna");
				libro.setDescripcion("Como un organismo vivo, el clan Caskey crece y se transforma. Unos tendrá que enfrentarse a la muerte, otros serán bendecidos con la vida. A través de acercamientos inesperados cambiarán las relaciones y el odio, finalmente, quedará enterrado. Miriam dirige ahora el aserradero y saca buen rédito de sus gestiones. Todo Perdido celebrará un sorprendente y milagroso descubrimiento. Pero, ¿servirá de algo la repentina fortuna cuando la naturaleza empiece a reclamar lo que es suyo?");
				libro.setPaginas(368);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(90);
				libro.setFechaPublicacion(LocalDate.of(2017, 3, 22));
				libro.setUrlFoto("https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/71df6986-afae-46df-bf5b-11ddb96083a8/9789878453552_e3561b70-bdec-42b2-a0f3-8d3f1f3818a3.jpg");
				libro.setPrecio(32.00);
				libro.setCategoria(Set.of(Categoria.terror));
				libro.setEditorial(editorialTitania);
				libro.setAutor(autorMcdowell);
				libroRepository.save(libro);
			}
			//117
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Blackwater: Lluvia", "Primera edición", autorMcdowell, editorialTitania).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Blackwater: Lluvia");
				libro.setDescripcion("1958. Pasan los años entre suntuosas fiestas, uniones insólitas y estremecedoras revelaciones, pero nada traerá paz a la familia Caskey. Saben que, tras la calma, siempre aguarda una nueva tormenta. Algo terrible se cierne sobre Perdido, sus habitantes y su río. Ha llegado el momento de la profecía.");
				libro.setPaginas(384);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(91);
				libro.setFechaPublicacion(LocalDate.of(2018, 5, 15));
				libro.setUrlFoto("https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/ea1014dd-dd5d-4413-b852-8f7a274a5668/9789878453569_4f9a07b0-8181-4b3a-b7a4-637b8c77647e.jpg");
				libro.setPrecio(33.00);
				libro.setCategoria(Set.of(Categoria.terror));
				libro.setEditorial(editorialTitania);
				libro.setAutor(autorMcdowell);
				libroRepository.save(libro);
			}
			//118
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Blackwater: La guerra", "Primera edición", autorMcdowell, editorialTitania).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Blackwater: La guerra");
				libro.setDescripcion("Comienza una nueva era para el clan Caskey: la persistencia y el trabajo duro de Elinor en Perdido por fin parecen dar sus frutos. Su control arraiga en los hogares de un pueblo que en el pasado desconfió de sus intenciones. Sus enemigos, poderosos antaño, decrecen en número y pierden fuerza. El conflicto armado en Europa trae sangre nueva a Perdido. En las tierras de los Caskey, los hombres van y vienen como marionetas. No saben que sus vidas penden de un hilo.");
				libro.setPaginas(400);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(92);
				libro.setFechaPublicacion(LocalDate.of(2019, 4, 10));
				libro.setUrlFoto("https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/245b43b7-2f5c-48a6-aec6-8ebe02f88b05/9789878453545_fa496e05-2f48-48f4-bc65-e99aca70c78a.jpg");
				libro.setPrecio(34.50);
				libro.setCategoria(Set.of(Categoria.terror));
				libro.setEditorial(editorialTitania);
				libro.setAutor(autorMcdowell);
				libroRepository.save(libro);
			}
			//119
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Calliope", "Primera edición", autorMcdowell, editorialTitania).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Calliope");
				libro.setDescripcion("Calliope “Calley” Dakin tiene siete años y es el ojito derecho de su padre. Pero su mundo infantil se desmorona cuando, durante un viaje al bullicioso Nueva Orleans, su adorado padre es secuestrado, asesinado y descuartizado por dos mujeres sin ningún motivo aparente. Después de esto Calley y su madre se ven atrapadas en una serie de extraños sucesos que las llevan a Pensacola, donde, en una casa que resulta ser idéntica a la de su difunta bisabuela, una enigmática mujer aguarda su llegada.");
				libro.setPaginas(360);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(89);
				libro.setFechaPublicacion(LocalDate.of(2013, 7, 10));
				libro.setUrlFoto("https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/afdf244c-321c-48a3-afac-fbf3e8c83d62/9789505473113_8669b2b8-0bf3-4d19-a2e5-7c834c1d93fc.webp");
				libro.setPrecio(30.50);
				libro.setCategoria(Set.of(Categoria.terror));
				libro.setEditorial(editorialTitania);
				libro.setAutor(autorMcdowell);
				libroRepository.save(libro);
			}
			//120
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Blackwater: El dique", "Primera edición", autorMcdowell, editorialTitania).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Blackwater: El dique");
				libro.setDescripcion("Mientras Perdido se recupera de la inundación, propone la construcción de un dique que impida una nueva catástrofe. Sin embargo, con las obras comenzarán las corrientes impredecibles y las desapariciones. Mientras tanto, en el clan Caskey, la matriarca Mary-Love ve cómo sus intereses chocan con los de Elinor, su misteriosa nuera. Las tensiones entre ambas amenazan con destruir el pueblo entero. En Perdido se avecinan grandes cambios, y las consecuencias serán devastadoras.");
				libro.setPaginas(336);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(88);
				libro.setFechaPublicacion(LocalDate.of(2015, 4, 18));
				libro.setUrlFoto("https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/90a53131-1337-463b-ba15-1ed78383afa6/9789878453521_da7a4afe-e09b-470f-960c-ad67b93dc144.jpg");
				libro.setPrecio(30.50);
				libro.setCategoria(Set.of(Categoria.terror));
				libro.setEditorial(editorialTitania);
				libro.setAutor(autorMcdowell);
				libroRepository.save(libro);
			}
			//121
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"No tengas miedo", "Primera edición", autorKing, editorialPlaza).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("No tengas miedo");
				libro.setDescripcion("Cuando el Departamento de Policía de Buckeye City recibe una carta de alguien que pretende «matar a trece inocentes y a un culpable» para expiar una muerte innecesaria, la detective Izzy Jaynes no sabe qué pensar. ¿Están a punto de asesinar a catorce personas por venganza? Preocupada, decide acudir a Holly Gibney para que la ayude. Mientras tanto, la activista por los derechos de la mujer Kate McKay se embarca en una gira de conferencias, atrayendo a tantos seguidores como detractores. Alguien que se opone vehementemente a su mensaje ataca sus eventos y, aunque al principio nadie resulta herido, el acosador se vuelve cada vez más atrevido, y contactan a Holly Gibney para proteger a Kate. Con un fascinante elenco de personajes conocidos y nuevos, estos dos hilos narrativos se unen en un tapiz escalofriante y espectacular.");
				libro.setPaginas(350);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(88);
				libro.setFechaPublicacion(LocalDate.of(1980, 6, 15));
				libro.setUrlFoto("https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/7ce63875-e73b-46e3-8ba5-f8f2d44cded0/9789506447557.jpg");
				libro.setPrecio(29.99);
				libro.setCategoria(Set.of(Categoria.terror, Categoria.suspenso));
				libro.setEditorial(editorialPlaza);
				libro.setAutor(autorKing);
				libroRepository.save(libro);
			}
			//122
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"La cúpula", "Primera edición", autorKing, editorialPenguin).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("La cúpula");
				libro.setDescripcion("Un tranquilo día de otoño la ciudad de Chester's Mill queda inexplicable y repentinamente aislada del resto del mundo por un campo de fuerza invisible. Los aviones se estrellan y caen del cielo consumiéndose entre llamas, la gente corre sin rumbo por el pueblo vecino al verso separado de sus familias y los coches estallan al impactar contra el muro invisible. Nadie logra comprender cuál es la naturaleza de la barrera, ni su procedencia, ni cómo ha llegado hasta allí, ni si algún día desaparecerá. Dale Barbara, un veterano desilusionado de la guerra del Golfo reconvertido en un mediocre cocinero; Julia Shumway, directora del periódico local; y un grupo de skaters adolescentes lucharán para descubrir el misterio de la cúpula. Sin embargo, en el otro bando se encuentran el gran Jim Rennie, alcalde de Chester's Mill, un hombre corrupto y sin escrúpulos dispuesto a todo para tomar las riendas del poder, y su hijo, que oculta un terrible secreto en una oscura despensa. Pero su adversario principal es la propia cúpula. Porque el tiempo no es infinito. El tiempo corre e irremediablemente se acaba...");
				libro.setPaginas(1072);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(92);
				libro.setFechaPublicacion(LocalDate.of(2009, 11, 25));
				libro.setUrlFoto("https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/f6e22475-224c-4db9-a5bb-fafd4932cd86/9789877257014_33eaf902-84af-4212-b715-686942bca64a.jpg");
				libro.setPrecio(39.99);
				libro.setCategoria(Set.of(Categoria.terror, Categoria.suspenso));
				libro.setEditorial(editorialPenguin);
				libro.setAutor(autorKing);
				libroRepository.save(libro);
			}
			//123
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Cujo", "Primera edición", autorKing, editorialPenguin).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Cujo");
				libro.setDescripcion("Durante toda su vida Cujo fue un buen perro, un San Bernardo grandote, pacífico, juguetón y amante de los niños. Realmente se trataba de un perro bueno y feliz. Feliz hasta que le sucedió algo, y el cerebro de perro de Cujo se cubrió de una de esas oscuridades que se alimentan de sangre. Ahora, se ha convertido en un perro asesino; doblemente cruel por cuanto la gente no conoce su mutación y aún le ve en su anterior bondad. Heraldo de un pequeño Apocalipsis, Cujo desencadenará sobre un pueblo modélico un huracán de pánico y de muerte.");
				libro.setPaginas(320);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(90);
				libro.setFechaPublicacion(LocalDate.of(1981, 5, 12));
				libro.setUrlFoto("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTFbZnJd85VeLPmhH_72AJ7xFnQPjy0ymAlDQ&s");
				libro.setPrecio(28.50);
				libro.setCategoria(Set.of(Categoria.terror, Categoria.suspenso));
				libro.setEditorial(editorialPenguin);
				libro.setAutor(autorKing);
				libroRepository.save(libro);
			}
			//124
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Billy Summers", "Primera edición", autorKing, editorialPlaza).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Billy Summers");
				libro.setDescripcion("Billy Summers es un hombre armado y encerrado en una habitación. Es un asesino a sueldo, el mejor en su campo. Pero solo acepta trabajos si el objetivo es un verdadero criminal. Y ahora Billy quiere retirarse. Pero antes, un último encargo. Billy es uno de los mejores francotiradores del mundo, un veterano condecorado de la guerra de Irak y un Houdini a la hora de desaparecer tras cumplir su misión. ¿Qué podría salir mal?");
				libro.setPaginas(512);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(91);
				libro.setFechaPublicacion(LocalDate.of(2021, 8, 3));
				libro.setUrlFoto("https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/afa244ed-798e-4467-871c-db73c1bcfc05/70a370eb-4a9c-4c5c-b638-ebaccd597268.jpg");
				libro.setPrecio(35.00);
				libro.setCategoria(Set.of(Categoria.suspenso, Categoria.terror));
				libro.setEditorial(editorialPlaza);
				libro.setAutor(autorKing);
				libroRepository.save(libro);
			}
			//125
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Bellas durmientes", "Primera edición", autorKing, editorialPenguin).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Bellas durmientes");
				libro.setDescripcion("En esta espectacular colaboración entre padre e hijo, Stephen King y Owen King nos ofrecen la historia más arriesgada de cuantas han contado hasta ahora: ¿qué pasaría si las mujeres abandonaran este mundo? En un futuro tan real y cercano que podría ser hoy, cuando las mujeres se duermen, brota de su cuerpo una especie de capullo que las aísla del exterior. Si las despiertan, las molestan o tocan el capullo que las envuelve, reaccionan con una violencia extrema. Y durante el sueño se evaden a otro mundo. Los hombres, por su parte, quedan abandonados a sus instintos primarios. La misteriosa Evie, sin embargo, es inmune a esta bendición o castigo del trastorno del sueño. ¿Se trata de una anomalía médica que hay que estudiar? ¿O es un demonio al que hay que liquidar? Una fábula del siglo XXI sobre la posibilidad de un mundo exclusivamente femenino más pacífico y más justo que resulta especialmente relevante hoy en día.");
				libro.setPaginas(768);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(90);
				libro.setFechaPublicacion(LocalDate.of(2017, 9, 5));
				libro.setUrlFoto("https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/7c0ba66a-9f5e-42c7-9e43-0dc68909674f/9789877257489_8c7468c0-2b5a-4bae-960d-4a4cc09bb964.jpg");
				libro.setPrecio(36.00);
				libro.setCategoria(Set.of(Categoria.terror, Categoria.suspenso));
				libro.setEditorial(editorialPenguin);
				libro.setAutor(autorKing);
				libroRepository.save(libro);
			}
			//126
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Casa negra", "Primera edición", autorKing, editorialPenguin).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Casa negra");
				libro.setDescripcion("Hace veinte años, un chico llamado Jack Sawyer viajó a los Territorios, un mundo paralelo, para salvar a su madre. Ahora Jack, ex detective de homicidios, decide comprarse una casa en un pueblo tranquilo de Wisconsin. No conserva recuerdo alguno de sus aventuras en los Territorios, pero se vio obligado a dejar a la policía de Los Ángeles cuando un suceso casual le despertó un inexplicable malestar. Cuando se produce una serie de horripilantes asesinatos en Wisconsin, el jefe de la policía local pide a Jack que lo ayude en su investigación. Pero ¿son esos asesinatos simplemente obra de un perturbado, o se ha desatado una fuerza misteriosa y maligna? ¿Cuál es la causa de las extrañas visiones que tiene Jack? ¿Acaso alguien trata de comunicarle algo? Jack se ve arrastrado de nuevo a los Territorios y hacia su propio pasado. Solamente allí podrás encontrar la fuerza necesaria para entrar en la Casa Negra y enfrentarse a los espantosos y viles seres del mal que esta cobija. En Casa Negra Stephen King y Peter Straub vuelven a contar otra historia de Jack Sawyer, protagonista de El talismán , el primer libro que escribieron juntos.");
				libro.setPaginas(400);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(89);
				libro.setFechaPublicacion(LocalDate.of(1981, 7, 12));
				libro.setUrlFoto("https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/75d004f5-8397-4c00-9839-3b50b15784c7/9789877256314_e87783ae-a3d0-4acb-8d95-befa8c6426d5.jpg");
				libro.setPrecio(30.00);
				libro.setCategoria(Set.of(Categoria.terror, Categoria.suspenso));
				libro.setEditorial(editorialPenguin);
				libro.setAutor(autorKing);
				libroRepository.save(libro);
			}
			//127
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Dolores Claiborne", "Primera edición", autorKing, editorialPenguin).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Dolores Claiborne");
				libro.setDescripcion("Sospechosa del asesinato de Vera Donovan, su adinerada empleadora, Dolores Claiborne relata a la policía la historia de su vida, haciendo referencia a su matrimonio en crisis y a la sospechosa muerte de su violento esposo, Joe St. George, treinta años atrás. Dolores también describe el deterioro físico y mental de Vera y su lealtad hacia una empleadora que se ha vuelto emocionalmente exigente en los últimos años.");
				libro.setPaginas(320);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(90);
				libro.setFechaPublicacion(LocalDate.of(1992, 5, 15));
				libro.setUrlFoto("https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/d6ad6215-b66d-4d3e-93bb-1abac67f8f33/9789877256994_1c23e6b1-a613-44f2-a0e8-970517932b63.jpg");
				libro.setPrecio(29.50);
				libro.setCategoria(Set.of(Categoria.terror, Categoria.suspenso));
				libro.setEditorial(editorialPenguin);
				libro.setAutor(autorKing);
				libroRepository.save(libro);
			}
			//128
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Quien pierde paga", "Primera edición", autorKing, editorialPlaza).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Quien pierde paga");
				libro.setDescripcion("Así comienza la fascinante nueva novela de Stephen King sobre un lector fanático. El genio es John Rothstein, un autor de culto, creador del personaje de Jimmy Gold. Morris Bellamy está fuera de sí, no solo porque Rothstein haya dejado de escribir, sino también porque considera que el inconformista Jimmy Gold se ha vendido para dedicarse a la publicidad. Morris decide matar a Rothstein y vacía su caja fuerte para llevarse no solo todo el dinero sino además el verdadero tesoro: los cuadernos de notas de otra novela protagonizada por Jimmy Gold. Morris lo esconde todo y al día siguiente acaba en la cárcel por otro crimen terrorista. Décadas más tarde un chico llamado Pete Saubers encuentra el tesoro y ahora son él y su familia a quienes han de salvar a Bill Hodges, Holly Gibney y Jerome Robinson del vengativo y trastornado Morris cuando sale de la cárcel tras treinta y cinco años encerrado.");
				libro.setPaginas(384);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(88);
				libro.setFechaPublicacion(LocalDate.of(2002, 10, 10));
				libro.setUrlFoto("https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/403c8070-2a79-4029-bd39-5ccaaba15c89/3dd3db42-c46e-4f6a-a60c-9879adc37fce.jpg");
				libro.setPrecio(30.50);
				libro.setCategoria(Set.of(Categoria.terror, Categoria.suspenso));
				libro.setEditorial(editorialPlaza);
				libro.setAutor(autorKing);
				libroRepository.save(libro);
			}
			//129
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Doctor Sueño", "Primera edición", autorKing, editorialPenguin).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Doctor Sueño");
				libro.setDescripcion("Ahora Danny Torrance, aquel niño aterrorizado del Hotel Overlook, es un adulto alcohólico atormentado por los fantasmas de su infancia. Un día se siente atraído por una ciudad de New Hampshire, donde encontrará trabajo en una residencia de ancianos y donde se apuntará a las reuniones de Alcohólicos Anónimos. En ese lugar le llega la visión de Abra Stone, una niña que necesita su ayuda. La persigue una tribu de seres paranormales que vive del resplandor de los niños especiales. Parecen personas mayores y totalmente normales que viajan por el país en sus autocaravanas, pero su misión es capturar, torturar y consumir a estos niños. Se alimentan de ellos para vivir y el resplandor de Abra tiene tanta fuerza que les podría mantener vivos durante mucho tiempo. Danny sabe que sin su ayuda Abra nunca conseguiría escaparse de ellos; Juntos emprenderán una lucha épica, una batalla sangrienta entre el Bien y el Mal, para intentar salvarla a ella y a los demás niños que sacrifican.");
				libro.setPaginas(544);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(92);
				libro.setFechaPublicacion(LocalDate.of(2013, 9, 24));
				libro.setUrlFoto("https://images.cdn1.buscalibre.com/fit-in/360x360/b9/a6/b9a660eb339f2e903c5f45a2e919b79a.jpg");
				libro.setPrecio(35.00);
				libro.setCategoria(Set.of(Categoria.terror, Categoria.suspenso));
				libro.setEditorial(editorialPenguin);
				libro.setAutor(autorKing);
				libroRepository.save(libro);
			}
			//130
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Cuento de hadas", "Primera edición", autorKing, editorialPlaza).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Cuento de hadas");
				libro.setDescripcion("Charlie Reade parece un estudiante de instituto normal y corriente, pero cargado con un gran peso sobre los hombros. Cuando él solo tenía diez años, su madre fue víctima de un atropello y la pena empujó a su padre a la bebida. Aunque era demasiado joven, Charlie tuvo que aprender a cuidarse solo... y también a ocuparse de su padre. Ahora, con diecisiete años, Charlie encuentra dos amigos inesperados: una perra llamada Radar y Howard Bowditch, su anciano dueño. El señor Bowditch es un ermitaño que vive en una colina enorme, en una casa enorme que tiene un cobertizo cerrado a cal y canto en el patio trasero. A veces, surgen sonidos extraños de él.Mientras Charlie se encarga de hacer recados para el señor Bowditch, Radar y él se hacen inseparables. Cuando el anciano falla, le deja al chico una cinta de casete que contiene una historia increíble y el gran secreto que Bowditch ha guardado durante toda su vida: dentro de su cobertizo existe un portal que conduce a otro mundo.");
				libro.setPaginas(672);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(90);
				libro.setFechaPublicacion(LocalDate.of(2022, 9, 6));
				libro.setUrlFoto("https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/eb5654a8-8376-49f7-a647-92acebb9ea01/9789877256857_8b31c6be-f1a9-4dbf-b45f-e9c609f9e3b7.jpg");
				libro.setPrecio(36.50);
				libro.setCategoria(Set.of(Categoria.terror, Categoria.suspenso));
				libro.setEditorial(editorialPlaza);
				libro.setAutor(autorKing);
				libroRepository.save(libro);
			}
			//131
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"La mitad oscura", "Primera edición", autorKing, editorialPlaza).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("La mitad oscura");
				libro.setDescripcion("¿Quién es el autor del macabro asesinato en la pequeña localidad de Castle Rock? Las pruebas inculpan a un padre de familia, pero cuando están a punto de arrestarlo, un dato cuestiona las evidencias. Este hombre ha escrito novelas con seudónimo, un alter ego ahora innecesario. ¿Puede un ser imaginario cobrar vida propia y obrar por su cuenta?");
				libro.setPaginas(384);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(89);
				libro.setFechaPublicacion(LocalDate.of(1989, 11, 15));
				libro.setUrlFoto("https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/99404698-425f-4f99-a8d0-f752564128ce/c53c0302-5002-4434-8165-bcd97d1e4938.jpg");
				libro.setPrecio(31.50);
				libro.setCategoria(Set.of(Categoria.terror, Categoria.suspenso));
				libro.setEditorial(editorialPlaza);
				libro.setAutor(autorKing);
				libroRepository.save(libro);
			}
			//132
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"La zona muerta", "Primera edición", autorKing, editorialPlaza).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("La zona muerta");
				libro.setDescripcion("Le había bastado con tocar al médico para saberlo: había pasado más de cuatro años en coma. Y se horrorizó. Se horrorizó por los cuatro años perdidos, pero sobre todo por saberlo. Porque una simple presión de manos era suficiente. Sabía. Sabía a distancia y por anticipado. Supo que ardería el restaurante. Supo quién era el asesino escurridizo. Y sabía tantas cosas... ¡No era justo! ¡No lo era! La jaqueca le martirizaba y parecía que la cabeza le fuera a estallar. Además, quienes querían saber luego le rehuían como si fuera un monstruo. Y la tortura de saber seguía implacable, y el rechazo, y la publicidad, y el horror de tomar una decisión, y sólo con pensarlo la cabeza le dolía atrozmente. Aquel hombre no sólo era inicuo, sino que iba a convertirse en presidente de los Estados Unidos e iba a hacer saltar el planeta en pedazos. Y él lo sabía. LO SABÍA. Tenía que matarlo. ¿Tenías que hacerlo? ¿Por qué? ¿Por qué el horror de saber? Pero los datos estaban echados: no podía llevar su conocimiento a la zona muerta para convertirse en un ciudadano vulgar, tan vulgar como su nombre, John Smith.");
				libro.setPaginas(400);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(90);
				libro.setFechaPublicacion(LocalDate.of(1979, 6, 1));
				libro.setUrlFoto("https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/7b0603fd-4e8f-428a-97eb-3081de091ec4/9789877255218.jpg");
				libro.setPrecio(30.00);
				libro.setCategoria(Set.of(Categoria.terror, Categoria.suspenso));
				libro.setEditorial(editorialPlaza);
				libro.setAutor(autorKing);
				libroRepository.save(libro);
			}
			//133
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"El instituto", "Primera edición", autorKing, editorialPenguin).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("El instituto");
				libro.setDescripcion("En mitad de la noche en un tranquilo barrio de Minneapolis raptan a Luke Ellis, de doce años, tras haber asesinado a sus padres. Una operación que dura menos de dos minutos. Luke se despierta en la siniestra institución conocida como El Instituto, en un cuarto que se asemeja al suyo pero sin ventanas. En habitaciones parecidas hay más niños: Kalisha, Nick, George, Iris y Avery Dixon, entre otros, que comparten capacidades especiales como telequinesia o la telepatía. Todos ellos se alojan en la Mitada Delantera de la institución. Los mayores, en cambio, se encuentran en la Mitad Trasera. Como dice Kalisha: \"El que entra no sale. La señora Sigsby, la directora y el resto del personal se dedican a aprovecharse sin compasión del talento paranormal de los chicos. Si te portas bien te premian. Si no, el castigo es brutal. Luke se da cuenta de que las víctimas van desapareciendo y son trasladadas a la Mitad Trasera, así que se obsesiona con escapar y pedir ayuda. Pero nunca nadie se ha escapado de El Instituto...");
				libro.setPaginas(576);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(91);
				libro.setFechaPublicacion(LocalDate.of(2019, 9, 10));
				libro.setUrlFoto("https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/c555274b-0e5c-4819-b874-db403547dd53/9789877254235.jpg");
				libro.setPrecio(35.50);
				libro.setCategoria(Set.of(Categoria.terror, Categoria.suspenso));
				libro.setEditorial(editorialPenguin);
				libro.setAutor(autorKing);
				libroRepository.save(libro);
			}
			//134
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Romper el círculo", "Primera edición", autorHoover, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Romper el círculo");
				libro.setDescripcion("Lily no siempre lo ha tenido fácil, pero eso nunca le ha impedido luchar por la vida que quiere y ha recorrido un largo camino para llegar donde está ahora. Su vida comienza a cambiar el día en que Ryle Kincaid, un extraordinario neurocirujano, se fija en ella. Ryle es asertivo, terco, tal vez incluso un poco arrogante, pero también es sensato, tremendamente atractivo, brillante y tiene una debilidad total por ella. Todo en él es perfecto salvo su completa aversión a las relaciones, así que cuando Lily se de cuenta de que ella es la excepción a su regla de «no tener citas», no puede evitar preguntarse por qué ha tomado esa decisión. A medida que las preguntas sobre su nueva relación la asaltan, también lo hacen los pensamientos sobre Atlas Corrigan, su primer amor y un vínculo con el pasado que dejó atrás. Él era su alma gemela, su protector. Cuando Atlas reaparece repentinamente y Ryle comienza a mostrar su verdadera cara, todo lo que Lily ha construido con él se ve amenazado.");
				libro.setPaginas(320);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(90);
				libro.setFechaPublicacion(LocalDate.of(2016, 2, 15));
				libro.setUrlFoto("https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/7f216c5a-0ef9-4cce-91e9-c60fa75b6620/9789504988052_4429ad1d-996b-43af-a7f4-831147992083.jpg");
				libro.setPrecio(29.99);
				libro.setCategoria(Set.of(Categoria.romance));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorHoover);
				libroRepository.save(libro);
			}
			//135
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Volver a empezar", "Primera edición", autorHoover, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Volver a empezar");
				libro.setDescripcion("Lily y su exmarido, Ryle, acaban de pactar la custodia compartida de su niña cuando Lily se encuentra de nuevo con su primer amor, Atlas. Después de casi dos años separados, está entusiasmada porque, por una vez, el tiempo está de su lado, e inmediatamente dice que sí cuando Atlas le pide una cita. Pero su alegría se desvanece cuando piensa que, aunque ya no están casados, Ryle sigue teniendo un papel en la familia, y no consentirá que Atlas Corrigan esté presente en su vida y en la de su hija. Volver a empezar alterna entre las perspectivas de Lily y Atlas y continúa justo donde nos dejó Romper el círculo. Descubriremos más sobre el pasado de Atlas y seguiremos a Lily en busca de una segunda oportunidad de encontrar el amor verdadero mientras tiene que lidiar con un exmarido celoso.");
				libro.setPaginas(336);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(89);
				libro.setFechaPublicacion(LocalDate.of(2017, 4, 10));
				libro.setUrlFoto("https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/6a004474-fd59-4f2f-8b75-a88e7896015e/370103e1-3e5c-43a4-a137-f7bc0bb26b28.jpg");
				libro.setPrecio(30.50);
				libro.setCategoria(Set.of(Categoria.romance));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorHoover);
				libroRepository.save(libro);
			}
			//136
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"A pesar de ti", "Primera edición", autorHoover, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("A pesar de ti");
				libro.setDescripcion("A Morgan Grant y su hija de dieciséis años, Clara, nada les gustaría más que no parecerse. Morgan está decidida a evitar que su hija cometa los mismos errores que ella, pues al quedarse embarazada y casarse demasiado joven, tuvo que dejar en el aire sus propios sueños. Por suerte, Clara no quiere seguir sus pasos: su predecible madre no tiene un hueso de espontaneidad en el cuerpo. Con personalidades tan contrarias les resulta cada vez más difícil coexistir. La única persona que puede traer paz al hogar es Chris, marido, padre y el ancla de la familia. Pero esa paz se rompe cuando se ve envuelto en un trágico y extraño accidente con desgarradoras consecuencias para ellas. Mientras lucha por reconstruir todo lo que se derrumbó, Morgan encuentra a consuelo en la última persona que esperaba y Clara se vuelve hacia el único chico que le han prohibido ver. Pero con cada nuevo secreto y malentendido madre e hija se separan cada vez más, lo último que imaginan es que para volver a enamorarse se necesita la una a la otra.");
				libro.setPaginas(304);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(88);
				libro.setFechaPublicacion(LocalDate.of(2015, 7, 20));
				libro.setUrlFoto("https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/3e252f59-d034-4d44-b8f5-d26b86909907/e3632695-7761-4188-8841-dd098d079dba.jpg");
				libro.setPrecio(28.99);
				libro.setCategoria(Set.of(Categoria.romance));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorHoover);
				libroRepository.save(libro);
			}
			//137
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"9 de noviembre", "Primera edición", autorHoover, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("9 de noviembre");
				libro.setDescripcion("Fallon y Ben se encuentran por casualidad cuando sus vidas están cambiando. Ella está a punto de instalarse en Nueva York con la esperanza de cumplir su sueño y convertirse en actriz de teatro, y Ben quiere ser escritor. Se cruzan como dos estrellas fugaces pero la intensidad de lo que comparten les lleva a fijar una cita anual, el 9 de noviembre, para no olvidarse. Fallon se convierte entonces en la inspiración de Ben, en su musa. En cada encuentro anual obtiene material para continuar escribiendo, y los dos se explican sus vidas. Hasta que en una de las citas Fallon empieza a dudar de lo que Ben le cuenta, ¿es posible que se haya inventado una vida de novela? ¿Y por qué haría algo así?");
				libro.setPaginas(288);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(90);
				libro.setFechaPublicacion(LocalDate.of(2015, 11, 9));
				libro.setUrlFoto("https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/3b9cd0ed-2f98-4eb6-8229-1dab0c82b482/9789504986799_c740da58-2b66-4dbb-b5db-6854ef6aa891.jpg");
				libro.setPrecio(27.50);
				libro.setCategoria(Set.of(Categoria.romance));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorHoover);
				libroRepository.save(libro);
			}
			//138
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Corazón roto", "Primera edición", autorHoover, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Corazón roto");
				libro.setDescripcion( "Los Voss no son una familia para empezar, viven en una iglesia reutilizada. La madre, que años atrás tuvo cáncer, vive en el sótano; el padre está casado con la antigua enfermera de la madre; el pequeño medio hermano no tiene permitido hacer o comer nada divertido; y los hermanos mayores son irritantemente perfectos. Y luego está Mérito. Merit Voss colecciona trofeos que no ha ganado y secretos que su familia la obliga a guardar. Mientras navega por la tienda de antigüedades local en busca de su próximo trofeo, se encuentra a Sagan. Su conexión es inmediata hasta que descubre que él está completamente fuera de su alcance. Merit se encierra profundamente en sí misma, observando a su familia desde la distancia, cuando descubre un secreto que ningún trofeo en el mundo puede arreglar. ");
				libro.setPaginas(312);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(89);
				libro.setFechaPublicacion(LocalDate.of(2018, 3, 15));
				libro.setUrlFoto("https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/5d256735-57ea-476a-bed1-b642bc28582c/9789504990161.jpg");
				libro.setPrecio(29.00);
				libro.setCategoria(Set.of(Categoria.romance));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorHoover);
				libroRepository.save(libro);
			}
			//139
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"No te olvidaré", "Primera edición", autorHoover, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("No te olvidaré");
				libro.setDescripcion("Después de pasar cinco años en la cárcel por el trágico error que costó la vida de su gran amor Scott, Kenna Rowan regresa a casa con un único deseo: abrazar a su hija Diem, de cuatro años, que vive con los padres de Scott ya la que no ha visto desde que nació. La única persona que no le ha cerrado la puerta por completo es Ledger Ward, dueño del bar local y uno de los pocos vínculos que le quedan con Diem. Pero ella sabe que si alguien descubre que Ledger se está convirtiendo lentamente en una parte importante de su vida, crece el riesgo de no recuperar a su hija. Kenna deberá encontrar una manera de reparar los errores de su pasado si quiere construir un nuevo futuro.");
				libro.setPaginas(296);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(88);
				libro.setFechaPublicacion(LocalDate.of(2016, 9, 5));
				libro.setUrlFoto("https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/74ee213c-4b5a-458b-88a9-b154cf63110e/9789504983583_09d99cfc-824e-4afd-b431-3ff4c428e844.jpg");
				libro.setPrecio(28.50);
				libro.setCategoria(Set.of(Categoria.romance));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorHoover);
				libroRepository.save(libro);
			}
			//140
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Tal vez nunca", "Primera edición", autorHoover, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Tal vez nunca");
				libro.setDescripcion("El sp in-off de Tal vez mañana. La historia del carismático Warren. Cuando a Warren le ofrecen la oportunidad de tener una compañera en un piso donde solo vivían chicos, acepta inmediatamente, ya que cree que puede ser interesante. Las dudas nacen cuando su nueva compañera de piso resulta ser Bridgette, una chica aparentemente fría y calculadora. ¿Podrá Warren descongelar el corazón de Bridgette? ¿Será ella capaz de aprender a amar? Tal vez algún día. Tal vez nunca.");
				libro.setPaginas(280);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(87);
				libro.setFechaPublicacion(LocalDate.of(2017, 5, 10));
				libro.setUrlFoto("https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/6cf4e12e-b0e7-4b41-91c2-8158796580b8/9789878220482_6b0d55bc-c5e9-4489-9696-643e2fd3f705.jpg");
				libro.setPrecio(27.99);
				libro.setCategoria(Set.of(Categoria.romance));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorHoover);
				libroRepository.save(libro);
			}
			//141
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Primer amor", "Primera edición", autorHoover, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Primer amor");
				libro.setDescripcion("Después de una dura infancia, Beyah Grim consigue una beca para abandonar para siempre el lugar en el que nunca ha sido feliz. Pero dos meses antes de marcharse una muerte inesperada la deja sin hogar y se ve obligado a pasar el resto del verano en Texas con un padre al que apenas conoce. Rota y ansiosa por que el verano pase rápido, Beyah no tiene tiempo ni paciencia para Samson, el rico y melancólico vecino de al lado. Sin embargo, la conexión entre ellos es demasiado intensa como para ignorarla. Tienen claro que sus planes de futuro les van a separar así que deciden mantener una relación casual de verano. Lo que no saben todavía es que una corriente inesperada está a punto de arrastrar sus corazones al mar.");
				libro.setPaginas(300);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(89);
				libro.setFechaPublicacion(LocalDate.of(2015, 6, 15));
				libro.setUrlFoto("https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/7a2e0f22-8123-4ef5-8a06-6e553599f8bd/9789504990963.jpg");
				libro.setPrecio(28.99);
				libro.setCategoria(Set.of(Categoria.romance));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorHoover);
				libroRepository.save(libro);
			}
			//142
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Tal vez mañana", "Primera edición", autorHoover, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Tal vez mañana");
				libro.setDescripcion("A los veintidós años, Sydney lo tiene todo: el novio perfecto, un futuro brillante y un bonito apartamento que comparte con su mejor amiga. Pero todo cambia el día en que Ridge, su misterioso y atractivo vecino músico, le advierte que su novio la engaña con su mejor amiga y Sydney debe decidir qué hacer con su vida. Sólo con lo puesto y sin recursos, Ridge la acoge en su casa y no deja de sorprenderla. Sydney vibra cuando él toca sus hermosas melodías y, aunque el corazón de Ridge está ocupado, él no puede ignorar que ha encontrado a su musa. Cuando finalmente se den cuenta de lo que se necesita, entenderán que los sentimientos no pueden traicionar al corazón.");
				libro.setPaginas(368);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(91);
				libro.setFechaPublicacion(LocalDate.of(2014, 5, 15));
				libro.setUrlFoto("https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/4c99cf40-92ca-4f98-b41f-dabf616ee0ba/9789878220529_45bcfaba-a800-490e-9e8c-0cd9fac0a99f.jpg");
				libro.setPrecio(30.00);
				libro.setCategoria(Set.of(Categoria.romance));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorHoover);
				libroRepository.save(libro);
			}
			//143
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Tal vez ahora", "Primera edición", autorHoover, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Tal vez ahora");
				libro.setDescripcion("Por la autora de Romper el círculo, número uno en ventas de The New York Times y un auténtico fenómeno en TikTok. ¿Qué es más importante, la amistad, la lealtad o el amor? Ridge y Sydney no pueden creer que al fin puedan estar juntos. La relación entre Warren y Bridgette sigue tan tumultuosa como siempre y Maggie va trampeando con su enfermedad. Convencida de sacar el máximo partido a su vida decide saltar de un avión en paracaídas cuando conoce a Jake. Al prepararse para su cita con él, encuentra una vieja lista de deseos y decide que tal vez ahora ha llegado el momento de cumplirlos. Mientras Maggie pone al día de sus aventuras en Ridge, a Sydney le cuesta no sentirse celosa por la amistad que aún existe entre ellos. Pero si quiere que su relación funcione, va a tener que asumirlo o alejarse de él para siempre. «Con una habilidad especial para la novela de emociones de alto voltaje, Hoover ha vendido más de 20 millones de libros. Y lo ha hecho a su manera». Los New York Times");
				libro.setPaginas(352);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(90);
				libro.setFechaPublicacion(LocalDate.of(2016, 3, 10));
				libro.setUrlFoto("https://cdn.livriz.com/media/mediaspace/F9AFB48D-741D-4834-B760-F59344EEFF34/45/e1b21086-5002-422d-b7ba-45f12ac8df24/9789878220499.jpg");
				libro.setPrecio(29.50);
				libro.setCategoria(Set.of(Categoria.romance));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorHoover);
				libroRepository.save(libro);
			}
			//144
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Princesa de los hielos", "Primera edición", autorDami, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Princesa de los hielos");
				libro.setDescripcion("En las lindas del Reino de la Fantasía, existe un Reino… una tierra fría e inhóspita, donde una joven princesa guarda un importante secreto. Ahora, alguien desea volver al tiempo de la antigua magia, y sólo Gunnar, el gran lobo blanco, puede defender a la princesa Nieves, mostrando así su verdadera naturaleza.");
				libro.setPaginas(288);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(90);
				libro.setFechaPublicacion(LocalDate.of(2012, 3, 1));
				libro.setUrlFoto("https://www.clubgeronimostilton.es/ficheros/libros/2_1_Princesadelos.jpg");
				libro.setPrecio(25.99);
				libro.setCategoria(Set.of(Categoria.fantastico));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorDami);
				libroRepository.save(libro);
			}
			//145
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Princesas del alba: Astrid", "Primera edición", autorDami, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Princesas del alba: Astrid");
				libro.setDescripcion("Después del tremendo y devastador ataque de un mago malvado, la decadencia del Gran Reino fue imparable. El hielo de Arcándida se derritió, la arena de Rocadocre se levantó y cubrió bosques y llanuras. El egoísmo y la discordia irrumpieron entre los habitantes. Año tras año, las familias reales iban desapareciendo junto a las últimas esperanzas de reinstaurar la paz y la armonía. Un día, la joven Astrid se encuentra cara a cara con un misterioso lobo plateado. Al principio, duda de que sea posible un mundo distinto, pero, al final, será ella, contra todo y contra todos, quien encuentre a las otras descendientes de las antiguas princesas. Las herederas que, según una antigua profecía, un día serán capaces de luchar codo con codo para derrotar al mago malvado.");
				libro.setPaginas(280);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(89);
				libro.setFechaPublicacion(LocalDate.of(2013, 4, 10));
				libro.setUrlFoto("https://www.clubgeronimostilton.es/ficheros/libros/las_princesas_del_alba_astrid.jpg");
				libro.setPrecio(25.50);
				libro.setCategoria(Set.of(Categoria.fantastico));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorDami);
				libroRepository.save(libro);
			}
			//146
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Reina de los corales", "Primera edición", autorDami, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Reina de los corales");
				libro.setDescripcion("Una aventura submarina donde la reina del mar deberá restaurar la armonía entre las criaturas de los océanos.");
				libro.setPaginas(270);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(87);
				libro.setFechaPublicacion(LocalDate.of(2014, 5, 12));
				libro.setUrlFoto("https://www.clubgeronimostilton.es/ficheros/libros/2princesa_de_los_corales_9788408100119.jpg");
				libro.setPrecio(26.00);
				libro.setCategoria(Set.of(Categoria.fantastico));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorDami);
				libroRepository.save(libro);
			}
			//147
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Princesas del alba: Nemis", "Primera edición", autorDami, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Princesas del alba: Nemis");
				libro.setDescripcion("De camino a la oscura morada del Señor de la Discordia, alguien a quien consideraban su amigo separa a las Princesas del Alba. Mientras Astrid se aleja en el horizonte a bordo de un enorme velero, la duda se insinúa en sus corazones. Y, en el momento más difícil, del mar nacerá una nueva esperanza. El viaje hacia el Reino de las Sombras acaba de empezar…");
				libro.setPaginas(280);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(88);
				libro.setFechaPublicacion(LocalDate.of(2013, 6, 15));
				libro.setUrlFoto("https://www.clubgeronimostilton.es/ficheros/libros/princesas_del_alba_nemis.jpg");
				libro.setPrecio(25.50);
				libro.setCategoria(Set.of(Categoria.fantastico));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorDami);
				libroRepository.save(libro);
			}
			//148
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Princesa del desierto", "Primera edición", autorDami, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Princesa del desierto");
				libro.setDescripcion("La ciudad de Rocadocre está en plena ebullición: ¡va a empezar el Mercado de las Arenas! Pero, de noche, las estrellas se ocultan en el cielo, y un viento inquieto agita el Desierto de los Susurros. Cuando la prima de la princesa Samah desaparece en la nada, los presagios se convierten en realidad: una terrible amenaza se cierne sobre el reino… ");
				libro.setPaginas(260);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(86);
				libro.setFechaPublicacion(LocalDate.of(2012, 7, 1));
				libro.setUrlFoto("https://www.clubgeronimostilton.es/ficheros/libros/2princesa_del_desierto_9788408102304.jpg");
				libro.setPrecio(24.99);
				libro.setCategoria(Set.of(Categoria.fantastico));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorDami);
				libroRepository.save(libro);
			}
			//149
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Princesas del alba: Sybil", "Primera edición", autorDami, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Princesas del alba: Sybil");
				libro.setDescripcion("En el Reino de las Sombras, las princesas del Alba caen víctimas de un terrible hechizo que las lleva a enfrentarse las unas con las otras. El vínculo que las une parece que se ha roto y sus caminos se separan a las puertas del enfrentamiento final. ¿Quién detendrá los malvados planes del señor de la Discordia?");
				libro.setPaginas(280);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(88);
				libro.setFechaPublicacion(LocalDate.of(2013, 8, 5));
				libro.setUrlFoto("https://www.clubgeronimostilton.es/ficheros/libros/princesas_del_alba_sybil.jpg");
				libro.setPrecio(25.50);
				libro.setCategoria(Set.of(Categoria.fantastico));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorDami);
				libroRepository.save(libro);
			}
			//150
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Princesa de los bosques", "Primera edición", autorDami, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Princesa de los bosques");
				libro.setDescripcion("Tambores de guerra rompen el silencio del Bosque Viviente. Los fieros Nai-Lai se han rebelado contra la princesa Yara. Así comienza una asombrosa aventura en la que Yara va a necesitar todo su arrojo para defender el reino de un terrible enemigo.");
				libro.setPaginas(290);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(87);
				libro.setFechaPublicacion(LocalDate.of(2012, 9, 10));
				libro.setUrlFoto("https://www.clubgeronimostilton.es/ficheros/libros/2princesa_de_los_bosques_9788408111504.jpg");
				libro.setPrecio(26.50);
				libro.setCategoria(Set.of(Categoria.fantastico));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorDami);
				libroRepository.save(libro);
			}
			//151
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Princesa de la oscuridad", "Primera edición", autorDami, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Princesa de la oscuridad");
				libro.setDescripcion("En el corazón del Reino de la Oscuridad, en un laberinto de grutas excavadas en la roca negra, la vida de la princesa Diamante transcurre aparentemente tranquila. Pero la calma que envuelve el palacio de Tierranegra se ve amenazada por los inquietantes planes de quien desea acabar con la paz de los Cinco Reinos.");
				libro.setPaginas(300);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(89);
				libro.setFechaPublicacion(LocalDate.of(2013, 2, 20));
				libro.setUrlFoto("https://www.clubgeronimostilton.es/ficheros/libros/2princesa_de_la_oscuridad_9788408013587.jpg");
				libro.setPrecio(26.99);
				libro.setCategoria(Set.of(Categoria.fantastico));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorDami);
				libroRepository.save(libro);
			}
			//152
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"La reina del sueño", "Primera edición", autorDami, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("La reina del sueño");
				libro.setDescripcion("Ahora que las cinco estrofas de la Canción del Sueño han caído en manos del Príncipe Sin Nombre, el Reino de la Fantasía corre más peligro que nunca. Las princesas, al fin juntas, tendrán que apelar a todo su valor para llegar a la enigmática Roca del Sueño y obligar a su enemigo a enfrentarse a ellas a cara descubierta. Cuando el gran momento se acerca, una verdad oculta durante años aflora desde las profundidades del tiempo, cambiando inesperadamente el curso de los acontecimientos…");
				libro.setPaginas(310);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(90);
				libro.setFechaPublicacion(LocalDate.of(2014, 3, 15));
				libro.setUrlFoto("https://www.clubgeronimostilton.es/ficheros/libros/2la_reina_del_sueno_9788408112532.jpg");
				libro.setPrecio(27.99);
				libro.setCategoria(Set.of(Categoria.fantastico));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorDami);
				libroRepository.save(libro);
			}
			//153
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Bruja de las mareas", "Primera edición", autorDami, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Bruja de las mareas");
				libro.setDescripcion("Ahora que el Rey Malvado y el Príncipe Sin Nombre duermen en la Roca del Sueño, parece que va a empezar una época de prosperidad en el Gran Reino. Pero, de repente, se vislumbra una sombra en el horizonte: las Brujas Grises han vuelto. Estas criaturas sin tiempo, viejas aliadas del Rey Malvado, se proponen luchar contra las Princesas con las armas de la Magia Sin Color. Durante un enfrentamiento sin precedentes, las cinco hijas del Rey Sabio comprenden que hay algo en el pasado de las brujas, un secreto oculto entre los pliegues del tiempo. Y tendrán que descubrirlo si quieren derrotarlas.");
				libro.setPaginas(288);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(88);
				libro.setFechaPublicacion(LocalDate.of(2018, 3, 1));
				libro.setUrlFoto("https://www.clubgeronimostilton.es/ficheros/libros/2la_bruja_de_las_mareas_9788408120568.jpg");
				libro.setPrecio(22.99);
				libro.setCategoria(Set.of(Categoria.fantastico));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorDami);
				libroRepository.save(libro);
			}
			//154
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Bruja de las llamas", "Primera edición", autorDami, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Bruja de las llamas");
				libro.setDescripcion("Un incendio amenaza el Bosque Viviente. El Gran Reino está de nuevo en peligro. Tras el fuego está la mano de Pirea, bruja de las Llamas y señora de las Centellas. Yara, la más joven de las Princesas, tendrá que enfrentarse al enemigo y luchar hasta… ¡la última llama!");
				libro.setPaginas(296);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(89);
				libro.setFechaPublicacion(LocalDate.of(2018, 7, 15));
				libro.setUrlFoto("https://www.clubgeronimostilton.es/ficheros/libros/2bruja_de_las_llamas_9788408125440.jpg");
				libro.setPrecio(22.99);
				libro.setCategoria(Set.of(Categoria.fantastico));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorDami);
				libroRepository.save(libro);
			}
			//155
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Bruja de las tormentas", "Primera edición", autorDami, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Bruja de las tormentas");
				libro.setDescripcion("El cielo está cada vez más oscuro, el viento empuja las nubes. El hechizo de la Tormenta Final está a punto de comenzar. Ha llegado el momento de que las princesas se enfrenten a la terrible bruja Etheria, señora de las Tormentas y el Rayo…");
				libro.setPaginas(304);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(90);
				libro.setFechaPublicacion(LocalDate.of(2019, 7, 1));
				libro.setUrlFoto("https://www.clubgeronimostilton.es/ficheros/libros/2portada_bruja_de_las_tormentas_tea_stilton_201503261640.jpg");
				libro.setPrecio(23.50);
				libro.setCategoria(Set.of(Categoria.fantastico));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorDami);
				libroRepository.save(libro);
			}
			//156
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Bruja de las cenizas", "Primera edición", autorDami, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Bruja de las cenizas");
				libro.setDescripcion("Una expedición cruza las Tierras de la Nada en busca de la Fuente de la Verdad. Cuando están muy cerca de la meta, aparece la cruel Bruja de las Cenizas, dispuesta a atacar a las princesas y a adueñarse del Gran Reino…");
				libro.setPaginas(290);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(88);
				libro.setFechaPublicacion(LocalDate.of(2020, 3, 12));
				libro.setUrlFoto("https://www.clubgeronimostilton.es/ficheros/libros/2portada_bruja_de_las_cenizas_tea_stilton_201507300854.jpg");
				libro.setPrecio(23.99);
				libro.setCategoria(Set.of(Categoria.fantastico));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorDami);
				libroRepository.save(libro);
			}
			//157
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Bruja del aire", "Primera edición", autorDami, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Bruja del aire");
				libro.setDescripcion("Una nube avanza en el horizonte, y engulle todo cuanto se cruza en su camino. Sulfúrea, bruja del Aire y señora de las Esencias, está lista para atacar y enfrentarse a las princesas en un reto extremo hasta el último hechizo.");
				libro.setPaginas(276);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(86);
				libro.setFechaPublicacion(LocalDate.of(2020, 11, 6));
				libro.setUrlFoto("https://www.clubgeronimostilton.es/ficheros/libros/2portada_bruja_del_aire_tea_stilton_201601251118.jpg");
				libro.setPrecio(23.99);
				libro.setCategoria(Set.of(Categoria.fantastico));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorDami);
				libroRepository.save(libro);
			}
			//158
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Bruja de las brujas", "Primera edición", autorDami, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Bruja de las brujas");
				libro.setDescripcion("Las princesas están listas para enfrentarse a Jamás Nombrada, bruja de las Brujas y despiadada señora de la Magia Sin Color. En una lucha hasta el último hechizo, las cinco hijas del Rey Sabio tendrán que reunir toda la fuerza de sus corazones para salvar la Fantasía gracias al poder del Amor.");
				libro.setPaginas(320);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(91);
				libro.setFechaPublicacion(LocalDate.of(2021, 4, 10));
				libro.setUrlFoto("https://www.clubgeronimostilton.es/ficheros/libros/bruja_de_las_brujas2.jpg");
				libro.setPrecio(24.99);
				libro.setCategoria(Set.of(Categoria.fantastico));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorDami);
				libroRepository.save(libro);
			}
			//159
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"El código del dragón", "Primera edición", autorDami, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("El código del dragón");
				libro.setDescripcion("¿Qué ocultan los sótanos de la Universidad de Ratford? ¿Quién ha raptado a Hans Ratonilo? ¡Un misterio que sólo podía resolver el Club de Tea! Ésta es su primera e increíble aventura.");
				libro.setPaginas(224);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(89);
				libro.setFechaPublicacion(LocalDate.of(2010, 3, 5));
				libro.setUrlFoto("https://www.clubgeronimostilton.es/ficheros/libros/2portada_el_codigo_del_dragon_tea_stilton_201601050949.jpg");
				libro.setPrecio(19.99);
				libro.setCategoria(Set.of(Categoria.aventura, Categoria.fantastico));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorDami);
				libroRepository.save(libro);
			}
			//160
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"La montaña parlante", "Primera edición", autorDami, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("La montaña parlante");
				libro.setDescripcion("Una misteriosa amenaza planea sobre el rancho de Nicky, en Australia..., pero el Club de Tea está listo para enfrentarse a ella viajando hasta el corazón del país ¡y viviendo una superratónica aventura!");
				libro.setPaginas(192);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(87);
				libro.setFechaPublicacion(LocalDate.of(2011, 2, 11));
				libro.setUrlFoto("https://www.clubgeronimostilton.es/ficheros/libros/2portada___201511261604.jpg");
				libro.setPrecio(18.99);
				libro.setCategoria(Set.of(Categoria.aventura));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorDami);
				libroRepository.save(libro);
			}
			//161
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"La ciudad secreta", "Primera edición", autorDami, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("La ciudad secreta");
				libro.setDescripcion("El Club de Tea se enfrenta a un puente colgante, un cóndor gigantesco y trampas milenarias… Una trepidante aventura para descubrir la Ciudad Secreta de los incas.");
				libro.setPaginas(256);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(90);
				libro.setFechaPublicacion(LocalDate.of(2012, 5, 10));
				libro.setUrlFoto("https://www.clubgeronimostilton.es/ficheros/libros/2portada___201511261613.jpg");
				libro.setPrecio(21.99);
				libro.setCategoria(Set.of(Categoria.aventura, Categoria.fantastico));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorDami);
				libroRepository.save(libro);
			}
			//162
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Misterio en París", "Primera edición", autorDami, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Misterio en París");
				libro.setDescripcion("Las chicas del Club de Tea van a París de vacaciones, pero allí tendrán que enfrentarse a un nuevo e inquietante misterio. Para resolverlo, nuestras amigas darán caza al ladrón bajo la sombra...");
				libro.setPaginas(224);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(88);
				libro.setFechaPublicacion(LocalDate.of(2009, 9, 3));
				libro.setUrlFoto("https://www.clubgeronimostilton.es/ficheros/libros/2portada_misterio_en_paris_geronimo_stilton_201601251122.jpg");
				libro.setPrecio(19.99);
				libro.setCategoria(Set.of(Categoria.policial, Categoria.aventura));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorDami);
				libroRepository.save(libro);
			}
			//163
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"El barco fantasma", "Primera edición", autorDami, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("El barco fantasma");
				libro.setDescripcion("El Corazón de Yasmina, un valiosísimo diamante oculto en las profundidades del mar, espera volver a brillar. Sumérjanse en esta aventura. Y después… ¡No se pierdan el fantástico viaje a la Gran Muralla China!");
				libro.setPaginas(208);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(86);
				libro.setFechaPublicacion(LocalDate.of(2013, 6, 18));
				libro.setUrlFoto("https://www.clubgeronimostilton.es/ficheros/libros/2portada___201602241128.jpg");
				libro.setPrecio(20.50);
				libro.setCategoria(Set.of(Categoria.suspenso, Categoria.aventura));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorDami);
				libroRepository.save(libro);
			}
			//164
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Aventura en Nueva York", "Primera edición", autorDami, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Aventura en Nueva York");
				libro.setDescripcion("El Club de Tea vuela a Nueva York, donde hacen nuevos amigos, Nicky participa en la maratón y, además, deben resolver un nuevo caso: ¿quién es el misterioso Fénix que amenaza a la familia de Pamela?");
				libro.setPaginas(220);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(89);
				libro.setFechaPublicacion(LocalDate.of(2010, 8, 12));
				libro.setUrlFoto("https://www.clubgeronimostilton.es/ficheros/libros/2portada___201511261623.jpg");
				libro.setPrecio(20.99);
				libro.setCategoria(Set.of(Categoria.policial, Categoria.aventura));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorDami);
				libroRepository.save(libro);
			}
			//165
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"El tesoro de hielo", "Primera edición", autorDami, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("El tesoro de hielo");
				libro.setDescripcion("Las chicas del Club de Tea y los Ratones Azules viven una increíble aventura en Alaska, con carreras en motos de nieve, danzas inuits y misteriosas explosiones en el hielo.");
				libro.setPaginas(240);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(90);
				libro.setFechaPublicacion(LocalDate.of(2014, 1, 10));
				libro.setUrlFoto("https://www.clubgeronimostilton.es/ficheros/libros/2portada_el_tesoro_de_hielo_tea_stilton_201601251126.jpg");
				libro.setPrecio(21.99);
				libro.setCategoria(Set.of(Categoria.aventura));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorDami);
				libroRepository.save(libro);
			}
			//166
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Náufragos de las estrellas", "Primera edición", autorDami, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Náufragos de las estrellas");
				libro.setDescripcion("Las chicas emprenden un viaje que las llevará al espacio, entre astronautas perdidos y robots rebeldes. Acompáñalas hasta su  destino más superratónico… ¡La Luna!");
				libro.setPaginas(256);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(87);
				libro.setFechaPublicacion(LocalDate.of(2015, 6, 12));
				libro.setUrlFoto("https://www.clubgeronimostilton.es/ficheros/libros/2portada___201511261624.jpg");
				libro.setPrecio(22.50);
				libro.setCategoria(Set.of(Categoria.cienciaficcion, Categoria.aventura));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorDami);
				libroRepository.save(libro);
			}
			//167
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"El secreto del castillo escocés", "Primera edición", autorDami, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("El secreto del castillo escocés");
				libro.setDescripcion("El secreto del castillo escocés. Un trepidante viaje por Escocia… ¡en moto! El Club de Tea se dirige a un antiguo castillo y allí descubrirán un misterioso secreto para salvar el edificio de la destrucción.");
				libro.setPaginas(224);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(90);
				libro.setFechaPublicacion(LocalDate.of(2011, 10, 7));
				libro.setUrlFoto("https://www.clubgeronimostilton.es/ficheros/libros/2portada___201511261625.jpg");
				libro.setPrecio(20.99);
				libro.setCategoria(Set.of(Categoria.suspenso, Categoria.aventura));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorDami);
				libroRepository.save(libro);
			}
			//168
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"La esmeralda del príncipe indio", "Primera edición", autorDami, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("La esmeralda del príncipe indio");
				libro.setDescripcion("¿Qué ocurre en Chennai, una ciudad de la misteriosa India? El oasis donde la asociación Ratones Azules protege a los monos está en peligro. Entre ricas telas, especias aromáticas y monos traviesos, las chicas del Club de Tea buscan desesperadamente la esmeralda del príncipe.");
				libro.setPaginas(240);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(89);
				libro.setFechaPublicacion(LocalDate.of(2012, 3, 9));
				libro.setUrlFoto("https://www.clubgeronimostilton.es/ficheros/libros/2portada___201602241138.jpg");
				libro.setPrecio(21.50);
				libro.setCategoria(Set.of(Categoria.aventura, Categoria.suspenso));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorDami);
				libroRepository.save(libro);
			}
			//169
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Misterio en el Orient Express", "Primera edición", autorDami, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Misterio en el Orient Express");
				libro.setDescripcion("Una famosa bailarina, un pintor malhumorado, una heredera arruinada y la cantante pop del momento… Entre ellos se oculta un ladrón: ¡el Velo de Luz, un valiosísimo traje de novia, está en peligro!");
				libro.setPaginas(228);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(92);
				libro.setFechaPublicacion(LocalDate.of(2013, 9, 20));
				libro.setUrlFoto("https://www.clubgeronimostilton.es/ficheros/libros/2portada___201602241140.jpg");
				libro.setPrecio(22.99);
				libro.setCategoria(Set.of(Categoria.policial, Categoria.aventura));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorDami);
				libroRepository.save(libro);
			}
			//170
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Misterio entre bambalinas", "Primera edición", autorDami, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Misterio entre bambalinas");
				libro.setDescripcion("Las chicas del Club de Tea se presentan a un concurso de ballet clásico y, como todas las bailarinas, sueñan con actuar en el prestigioso escenario del teatro La Scala de Milán. Pero algunos conspiran en la sombra…");
				libro.setPaginas(216);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(85);
				libro.setFechaPublicacion(LocalDate.of(2014, 6, 15));
				libro.setUrlFoto("https://www.clubgeronimostilton.es/ficheros/libros/2portada_misterio_entre_bambalinas_tea_stilton_201602110948.jpg");
				libro.setPrecio(20.50);
				libro.setCategoria(Set.of(Categoria.policial));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorDami);
				libroRepository.save(libro);
			}
			//171
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"La leyenda de las flores de fuego", "Primera edición", autorDami, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("La leyenda de las flores de fuego");
				libro.setDescripcion("Existe un archipiélago repleto de maravillas: las islas Hawái. Allí el Club de Tea se ve envuelto en una intriga… ¡explosiva! ¿Resolverán el misterioantes de que el volcán Mauna Loa entre en erupción?");
				libro.setPaginas(240);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(91);
				libro.setFechaPublicacion(LocalDate.of(2016, 2, 4));
				libro.setUrlFoto("https://www.clubgeronimostilton.es/ficheros/libros/2portada_la_leyenda_de_las_flores_de_fuego_tea_stilton_201602111011.jpg");
				libro.setPrecio(22.99);
				libro.setCategoria(Set.of(Categoria.fantastico));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorDami);
				libroRepository.save(libro);
			}
			//172
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Misión flamenco", "Primera edición", autorDami, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Misión flamenco");
				libro.setDescripcion("Las chicas del Club de Tea vuelan a España, donde un misterioso robo transformará su viaje en una aventura superratónica, tras la pista de un tesoro secreto…");
				libro.setPaginas(208);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(84);
				libro.setFechaPublicacion(LocalDate.of(2015, 11, 10));
				libro.setUrlFoto("https://www.clubgeronimostilton.es/ficheros/libros/2portada_mision_flamenco_tea_stilton_201602251054.jpg");
				libro.setPrecio(19.99);
				libro.setCategoria(Set.of(Categoria.policial, Categoria.aventura));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorDami);
				libroRepository.save(libro);
			}
			//173
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Los secretos del Olimpo", "Primera edición", autorDami, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Los secretos del Olimpo");
				libro.setDescripcion("Durante sus vacaciones en Grecia, las chicas del Club de Tea participan en los ensayos de una obra de teatro muy antigua. Poco antes del debut, el actor protagonista desaparece misteriosamente. ¿Qué le habrá ocurrido? ¿Cómo estrenará la obra la compañía sin su actor más importante? Las cinco amigas investigarán para resolver el enigma.");
				libro.setPaginas(256);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(90);
				libro.setFechaPublicacion(LocalDate.of(2017, 3, 22));
				libro.setUrlFoto("https://www.clubgeronimostilton.es/ficheros/libros/los_secretos_del_olimpo.jpg");
				libro.setPrecio(22.99);
				libro.setCategoria(Set.of(Categoria.fantastico, Categoria.aventura));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorDami);
				libroRepository.save(libro);
			}
			//174
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Misterio en Hollywood", "Primera edición", autorDami, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Misterio en Hollywood");
				libro.setDescripcion("¡Qué ilusión! Estamos grabando una película en Hollywood, rodeadas de famosos actores y directores de cine. Pero alguien busca sabotear la película con un misterioso robo…");
				libro.setPaginas(230);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(88);
				libro.setFechaPublicacion(LocalDate.of(2012, 8, 14));
				libro.setUrlFoto("https://www.clubgeronimostilton.es/ficheros/libros/2portada_misterio_en_hollywood_tea_stilton_201602221230.jpg");
				libro.setPrecio(20.99);
				libro.setCategoria(Set.of(Categoria.policial));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorDami);
				libroRepository.save(libro);
			}
			//175
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"El tesoro perdido", "Primera edición", autorDami, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("El tesoro perdido");
				libro.setDescripcion("Las chicas del Club de Tea viajan a Turquía para visitar unas excavaciones arqueológicas. Allí, entre ruinas antiguas de gran valor, descubren la importancia de la amistad y el trabajo en equipo.");
				libro.setPaginas(228);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(86);
				libro.setFechaPublicacion(LocalDate.of(2011, 4, 1));
				libro.setUrlFoto("https://www.clubgeronimostilton.es/ficheros/libros/el_tesoro_perdido.jpg");
				libro.setPrecio(19.99);
				libro.setCategoria(Set.of(Categoria.aventura));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorDami);
				libroRepository.save(libro);
			}
			//176
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Princesas en Viena", "Primera edición", autorDami, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Princesas en Viena");
				libro.setDescripcion("Nos espera una nueva aventura en la ciudad del vals. Entre un concurso de pastelería y un baile en palacio, tendremos que desenmascarar a... ¡un ladrón de recetas!");
				libro.setPaginas(232);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(87);
				libro.setFechaPublicacion(LocalDate.of(2018, 5, 17));
				libro.setUrlFoto("https://www.clubgeronimostilton.es/ficheros/libros/princesas_en_viena.jpg");
				libro.setPrecio(21.50);
				libro.setCategoria(Set.of(Categoria.aventura, Categoria.fantastico));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorDami);
				libroRepository.save(libro);
			}
			//177
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"La leyenda del jardín chino", "Primera edición", autorDami, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("La leyenda del jardín chino");
				libro.setDescripcion("Hay un tesoro escondido entre las flores y las plantas legendarias de un antiguo jardín tradicional, situado en el corazón de China. ¿Nos ayudas a encontrarlo?");
				libro.setPaginas(224);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(90);
				libro.setFechaPublicacion(LocalDate.of(2019, 2, 8));
				libro.setUrlFoto("https://www.clubgeronimostilton.es/ficheros/libros/la_leyenda_del_jardin_chino.jpg");
				libro.setPrecio(22.50);
				libro.setCategoria(Set.of(Categoria.aventura));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorDami);
				libroRepository.save(libro);
			}
			//178
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"El secreto de Florencia", "Primera edición", autorDami, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("El secreto de Florencia");
				libro.setDescripcion("La maravillosa ciudad de Florencia nos espera. Acompañadnos en una aventura superratónica y ayudadnos a desenmascarar a un misterioso ladrón de obras de arte.");
				libro.setPaginas(240);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(88);
				libro.setFechaPublicacion(LocalDate.of(2014, 10, 30));
				libro.setUrlFoto("https://www.clubgeronimostilton.es/ficheros/libros/el_secreto_de_florencia.jpg");
				libro.setPrecio(21.99);
				libro.setCategoria(Set.of(Categoria.policial));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorDami);
				libroRepository.save(libro);
			}
			//179
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Viaje a México", "Primera edición", autorDami, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Viaje a México");
				libro.setDescripcion("En México nos esperan colores maravillosos, delicadas mariposas, música, alegría y… un gran misterio. Para resolverlo, tendremos que seguir los pasos de la famosa pintora Frida Kahlo.");
				libro.setPaginas(230);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(85);
				libro.setFechaPublicacion(LocalDate.of(2013, 6, 21));
				libro.setUrlFoto("https://www.clubgeronimostilton.es/ficheros/libros/viaje_a_mexico.jpg");
				libro.setPrecio(20.50);
				libro.setCategoria(Set.of(Categoria.aventura));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorDami);
				libroRepository.save(libro);
			}
			//180
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"En Barcelona con un gran chef", "Primera edición", autorDami, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("En Barcelona con un gran chef");
				libro.setDescripcion("Si tomáis la cocina de un famoso restaurante español, añadís un caso por resolver y lo aderezáis con una pizca de misterio… ¡la aventura está servida!");
				libro.setPaginas(200);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(84);
				libro.setFechaPublicacion(LocalDate.of(2012, 2, 18));
				libro.setUrlFoto("https://www.clubgeronimostilton.es/ficheros/libros/en_barcelona_con_un_gran_chef.jpg");
				libro.setPrecio(19.99);
				libro.setCategoria(Set.of(Categoria.policial));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorDami);
				libroRepository.save(libro);
			}
			//181
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"El sol de medianoche", "Primera edición", autorDami, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("El sol de medianoche");
				libro.setDescripcion("Salimos de viaje para resolver un misterio relacionado con un paquete perdido y una amistad muy especial… Esta vez con destino Noruega. ¿Listas para acompañarnos?");
				libro.setPaginas(248);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(89);
				libro.setFechaPublicacion(LocalDate.of(2016, 1, 22));
				libro.setUrlFoto("https://www.clubgeronimostilton.es/ficheros/libros/el_sol_de_medianoche.jpg");
				libro.setPrecio(22.50);
				libro.setCategoria(Set.of(Categoria.aventura));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorDami);
				libroRepository.save(libro);
			}
			//182
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Persecución en Argentina", "Primera edición", autorDami, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Persecución en Argentina");
				libro.setDescripcion("¡Todas a montar! Nos espera un largo recorrido a caballo por la pampa argentina en busca de un antiguo tesoro familiar.");
				libro.setPaginas(240);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(90);
				libro.setFechaPublicacion(LocalDate.of(2019, 9, 6));
				libro.setUrlFoto("https://www.clubgeronimostilton.es/ficheros/libros/persecucion_en_argentina.jpg");
				libro.setPrecio(22.99);
				libro.setCategoria(Set.of(Categoria.aventura, Categoria.policial));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorDami);
				libroRepository.save(libro);
			}
			//183
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"El robo del diamante rosa", "Primera edición", autorDami, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("El robo del diamante rosa");
				libro.setDescripcion("Vamos a Sudáfrica a hacer un crucero y… ¡hay un imprevisto! Ayudadnos a investigar el misterioso robo de un diamante que ha desaparecido como por arte de magia.");
				libro.setPaginas(220);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(88);
				libro.setFechaPublicacion(LocalDate.of(2015, 4, 9));
				libro.setUrlFoto("https://www.clubgeronimostilton.es/ficheros/libros/ElRoboDelDiamanteRosa.png");
				libro.setPrecio(20.99);
				libro.setCategoria(Set.of(Categoria.policial));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorDami);
				libroRepository.save(libro);
			}
			//184
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Cita en París", "Primera edición", autorDami, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Cita en París");
				libro.setDescripcion("En esta nueva aventura, las Tea Sisters viajan a la capital francesa para ayudar a su amiga Martine con un emocionante proyecto artístico. Pero lo que comienza como una exploración de la ciudad y sus tesoros escondidos, pronto se convierte en una investigación llena de misterios: un cuadro antiguo, una dedicatoria enigmática y un secreto oculto en las calles de Montmartre.");
				libro.setPaginas(212);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(85);
				libro.setFechaPublicacion(LocalDate.of(2018, 12, 5));
				libro.setUrlFoto("https://www.clubgeronimostilton.es/ficheros/libros/Cita_en_Paris.png");
				libro.setPrecio(19.99);
				libro.setCategoria(Set.of(Categoria.aventura));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorDami);
				libroRepository.save(libro);
			}
			//185
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Canciones para Paula", "Primera edición", autorJeans, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Canciones para Paula");
				libro.setDescripcion("Paula es una adolescente de casi 17 años que encuentra el amor por primera vez en Internet. Después de estar dos meses hablando con Ángel, un joven periodista que trabaja en una revista de música, decide quedar con él y comprobar si lo que siente a través de la pantalla también lo experimenta en el cara a cara. Pero el chico llega tarde y, mientras espera, Paula conoce a Álex, un aspirante a escritor con una sonrisa maravillosa. A partir de ese momento comienza una historia de amores y desamores, de la que serán testigo \"la Sugus\", el grupo de amigas de Paula. Una chicas desenfadadas, alegres y, a veces, difíciles de tragar (como los caramelos Sugus), que ayudarán a la protagonista a tomar decisiones importantes en aquellos días de marzo en un lugar de la ciudad.");
				libro.setPaginas(680);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(90);
				libro.setFechaPublicacion(LocalDate.of(2010, 2, 1));
				libro.setUrlFoto("https://pdlibrosarg.cdnstatics2.com/usuaris/libros/thumbs/7a90527d-5339-400e-a331-f0176440b562/d_295_510/portada_canciones-para-paula_blue-jeans_201610282211.webp");
				libro.setPrecio(19.90);
				libro.setCategoria(Set.of(Categoria.romance));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorJeans);
				libroRepository.save(libro);
			}
			//186
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Buenos días, princesa", "Primera edición", autorJeans, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Buenos días, princesa");
				libro.setDescripcion("Han pasado algo más de dos años en la vida de los chicos que forman “el club de los incomprendidos”. Las cosas han cambiado desde que uno tras otro se fueron encontrando en el camino. Nuevos problemas, secretos, amores, celos... Sin embargo, hasta el momento, su amistad ha podido con todo y con todos. Raúl, se ha convertido en un atractivo joven y en un líder nato; Valeria, derrocha simpatía por donde pisa, aunque no ha vencido del todo a su timidez; Eli, es la que más se ha transformado de todos y se los lleva de calle; María, vigila y sueña tras sus gafas de pasta de color azul; Bruno, no consigue olvidar lo que siente y en lo más profundo de su corazón espera ser correspondido; y Ester, es la nuera que toda madre querría tener aunque no es tan inocente como todos piensan. Son seis chicos que sienten, sufren, aman, creen, ríen, evolucionan... como otros chicos de su edad. Pero los seis son especiales. Al menos, para el resto del grupo. ¿Conseguirán superar  todas las pruebas que se le van a presentar?");
				libro.setPaginas(544);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(92);
				libro.setFechaPublicacion(LocalDate.of(2012, 5, 8));
				libro.setUrlFoto("https://pdlibrosarg.cdnstatics2.com/usuaris/libros/thumbs/70e3125c-aa9c-4f9d-874f-eab37592b47b/d_295_510/portada_buenos-dias-princesa_blue-jeans_201505051651.webp");
				libro.setPrecio(21.90);
				libro.setCategoria(Set.of(Categoria.romance));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorJeans);
				libroRepository.save(libro);
			}
			//187
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"No sonrías que me enamoro", "Primera edición", autorJeans, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("No sonrías que me enamoro");
				libro.setDescripcion("Hasta hace unos meses, Eli, Valeria, Bruno, Raúl, María y Ester formaban El Club de los Incomprendidos. Cada uno con su personalidad y su carácter, eran los mejores amigos del mundo. Se conocieron dos años atrás en el instituto, y el haber pasado por similares y dolorosas circunstancias les acercó. Pero ahora, superados sus caminos: celos, dudas, amores secretos, relaciones complicadas con los padres… y el club no pasa por su mejor momento. Además, aparecerán otras personas en el camino que influirán en sus decisiones.");
				libro.setPaginas(432);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(88);
				libro.setFechaPublicacion(LocalDate.of(2013, 2, 5));
				libro.setUrlFoto("https://pdlibrosarg.cdnstatics2.com/usuaris/libros/thumbs/944596df-6a46-4c1c-a33c-e9708a399326/d_295_510/portada_no-sonrias-que-me-enamoro_blue-jeans_201506302011.webp");
				libro.setPrecio(21.90);
				libro.setCategoria(Set.of(Categoria.romance));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorJeans);
				libroRepository.save(libro);
			}
			//188
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Cállame con un beso", "Primera edición", autorJeans, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Cállame con un beso");
				libro.setDescripcion("El final de la trilogía no deja indiferente a nadie. Paula se marcha a Londres a estudiar y afronta con muchas dudas una relación a distancia. No será fácil elegir que camino tomar con el que cree que es el chico de su vida: Álex. El escritor ha abierto un biblio-café y tiene en una de sus clientas a una de sus mayores admiradoras. Las Sugus, por su parte, se han separado y entre ellas las cosas ya no son lo que eran. Miriam se ve inmersa en una relación tóxica, Cristina ha encontrado el amor y Diana, sigue siendo Diana, aunque ha madurado con Mario a su lado. Fue el desenlace más esperado de la primera historia que pasó de las Redes Sociales al papel.");
				libro.setPaginas(416);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(89);
				libro.setFechaPublicacion(LocalDate.of(2013, 11, 12));
				libro.setUrlFoto("https://pdlibrosarg.cdnstatics2.com/usuaris/libros/thumbs/e2e479f4-0a50-4332-bed2-85191f4cfe57/d_295_510/portada_callame-con-un-beso_blue-jeans_201612281630.webp");
				libro.setPrecio(21.90);
				libro.setCategoria(Set.of(Categoria.romance));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorJeans);
				libroRepository.save(libro);
			}
			//189
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Tengo un secreto: el diario de Meri", "Primera edición", autorJeans, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Tengo un secreto: el diario de Meri");
				libro.setDescripcion("Tengo un secreto: el diario de Meri es la novela basada en el blog personal que escribe la intrigante Incomprendida en la película El Club de los Incomprendidos. Basada en el besteller de Blue Jeans ¡Buenos días, princesa!, la cinta, que se estrenará el próximo 25 de diciembre, está producida por Bambú y Atresmedia y ya ha despertado una gran expectación en las redes sociales. En la nueva novela, que arrasará entre todos sus fans, veremos cómo y por qué empezó todo, seremos cómplices de las dudas, miedos e inseguridades de todos los Incomprendidos y, por fin, sabremos cómo siguen sus vidas después del sorprendente final de ¿Puedo soñar contigo? Una lectura imprescindible para comprender todo el universo de El Club de los Incomprendidos.");
				libro.setPaginas(368);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(87);
				libro.setFechaPublicacion(LocalDate.of(2014, 5, 6));
				libro.setUrlFoto("https://pdlibrosarg.cdnstatics2.com/usuaris/libros/thumbs/52b5403f-26a3-4654-a1eb-92c73af81168/d_295_510/portada_tengo-un-secreto-el-diario-de-mery_blue-jeans_201502032139.webp");
				libro.setPrecio(19.90);
				libro.setCategoria(Set.of(Categoria.romance));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorJeans);
				libroRepository.save(libro);
			}
			//190
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"La chica invisible", "Primera edición", autorJeans, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("La chica invisible");
				libro.setDescripcion("Aurora Ríos es invisible para casi todos. Los acontecimientos del pasado han hecho que se aísle del mundo y que apenas se relacione. A sus diecisiete años, no tiene amigos y está harta de que los habitantes de aquel pueblo hablen a su espalda. Una noche de mayo, su madre no la encuentra en casa cuando regresa del trabajo. No es lo habitual. Aurora aparece muerta a la mañana siguiente en el vestuario de su instituto, el Rubén Darío. Tiene un golpe en la cabeza y han dejado una brújula junto a su cuerpo. ¿Quién es el responsable de aquel terrible suceso? Julia Plaza, compañera de clase de la chica invisible, está obsesionada con encontrar la respuesta. Su gran inteligencia y su memoria prodigiosa le sirven para realizar el cubo de Rubik en cincuenta segundos o ser invencible jugando al ajedrez. Pero ¿podrá ayudar a sus padres en la resolución de aquel enigma? Su madre, Aitana, es la forense del caso y su padre, Miguel Ángel, el sargento de la Policía Judicial de la Guardia Civil encargado de la investigación. Julia, junto a su inseparable amigo Emilio, un chico muy particular con una mirada inquietante, tratará de hacer todo lo que esté en su mano para que el asesinato de Aurora Ríos no quede impune.");
				libro.setPaginas(512);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(94);
				libro.setFechaPublicacion(LocalDate.of(2018, 4, 5));
				libro.setUrlFoto("https://pdlibrosarg.cdnstatics2.com/usuaris/libros/thumbs/586e81ea-e8b7-4ceb-848e-af5eda82c3b3/d_295_510/portada_la-chica-invisible_blue-jeans_201803081557.webp");
				libro.setPrecio(22.90);
				libro.setCategoria(Set.of(Categoria.suspenso));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorJeans);
				libroRepository.save(libro);
			}
			//191
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"El puzle de cristal", "Primera edición", autorJeans, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("El puzle de cristal");
				libro.setDescripcion("Tras la explosión en la estación de metro, Julia no es la misma. Se ha convertido en una chica insegura, a veces insolente, y a la que le cuesta encontrar motivación para disfrutar de la vida como lo hacía antes. También las cosas han cambiado para Emilio. El joven del pelo azul se encuentra repleto de dudas respecto a su futuro inmediato. Además, conoce a alguien muy especial, que le hará replantearse su situación. Vanesa, por su parte, fue la más perjudicada del grupo por la explosión del artefacto. ¿Eso le está influyendo en su relación con Ingrid? El primer martes de enero del nuevo año, Julia recibe una inquietante e inesperada llamada. Hugo Velero, uno de los compañeros de piso de Iván Pardo, le asegura que el chico del piercing en la ceja ha desaparecido. Iván le ha hablado mucho a su amigo de su inteligencia y su capacidad deductiva, por lo que le pide ayuda a Julia para encontrarlo. La joven, en principio, piensa que es una broma y no acepta. Pero, casualmente, su abuela Pilar, una entrañable y curiosa septuagenaria, con las mismas capacidades mentales que su nieta, vive cerca del edificio en el que ahora reside el joven del que estuvo enamorada y del que no sabe nada desde hace unos meses. Julia decide pasar unos días con su abuela en la ciudad para encontrarse a sí misma. Sin embargo, no será una visita tranquila. Y es que la muerte aparecerá de nuevo en su vida.");
				libro.setPaginas(528);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(90);
				libro.setFechaPublicacion(LocalDate.of(2019, 4, 4));
				libro.setUrlFoto("https://pdlibrosarg.cdnstatics2.com/usuaris/libros/thumbs/52f91c06-5358-415e-9edf-a79dc23f0e42/d_295_510/293906_portada_el-puzle-de-cristal_blue-jeans_201902011856.webp");
				libro.setPrecio(22.90);
				libro.setCategoria(Set.of(Categoria.suspenso));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorJeans);
				libroRepository.save(libro);
			}
			//192
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"La promesa de Julia", "Primera edición", autorJeans, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("La promesa de Julia");
				libro.setDescripcion("Cuando Julia comienza a estudiar Criminología en la universidad, uno de sus profesores percibe enseguida que la inteligencia de la joven destaca por encima de la de los demás y decide plantearle un controvertido ejercicio: analizar el caso de Pedro Juncosa, un psicólogo que murió ahorcado cinco años atrás. Todo parece indicar que aquel hombre se quitó la vida, pero la opinión del profesor y las posteriores investigaciones que hace le generan muchas dudas a Julia. ¿Realmente fue un suicidio? ¿Qué queda de aquel crimen si no fue una muerte voluntaria? Este nuevo caso altera, sin desearlo, todo lo que la chica tiene a su alrededor, incluida su historia de amor. Además, su inseparable amigo Emilio conoce en la universidad a una extraña joven que le recuerda a Aurora, la chica invisible, quien esconde un complicado pasado. Y Vanesa, que se ha recuperado completamente y trabaja ahora en el hotel de sus padres, recibe una visita inesperada que le complicará la existenci. La muerte vuelve a sacudir la vida de la chica de la mente maravillosa. Siete sospechosos. Amistades peligrosas. Amores. Desengaños. Mentiras. Y giros impredecibles. La historia más Blue Jeans de todas, en la que se mezcla la esencia de sus doce novelas anteriores. El juego ha comenzado.");
				libro.setPaginas(544);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(93);
				libro.setFechaPublicacion(LocalDate.of(2020, 3, 5));
				libro.setUrlFoto("https://pdlibrosarg.cdnstatics2.com/usuaris/libros/thumbs/3a7cf3b2-8eb1-4843-b6d5-96ffba76b6f4/d_295_510/portada_la-promesa-de-julia_blue-jeans_202003051651.webp");
				libro.setPrecio(22.90);
				libro.setCategoria(Set.of(Categoria.suspenso));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorJeans);
				libroRepository.save(libro);
			}
			//193
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"La última vez que pienso en ti", "Primera edición", autorJeans, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("La última vez que pienso en ti");
				libro.setDescripcion("Barcelona, unos días antes de Sant Jordi. La ciudad se prepara para celebrar uno de los eventos más importantes del año, pero lo que prometía ser una gran fiesta literaria se ve empañada por la misteriosa desaparición de la joven escritora Ángela Fletcher. Los sueños de Ángela se estaban haciendo realidad: había publicado su primera novela y la habían invitado a un festival de literatura juvenil para firmar su libro. Allí conoció a Arán, un chico muy interesante y peculiar. Su encuentro en la fiesta posterior fue de película. Entonces, ¿qué ha podido ocurrir para que desaparezca sin dejar rastro? Los días pasan sin tener noticias de la joven. La policía se vuelca en una frenética búsqueda, pero lo que encuentran es el cadáver de otra de las escritoras de literatura juvenil más emergentes. La gente en Barcelona se queda conmocionada y no da crédito a lo que está sucediendo.");
				libro.setPaginas(544);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(92);
				libro.setFechaPublicacion(LocalDate.of(2023, 3, 1));
				libro.setUrlFoto("https://pdlibrosarg.cdnstatics2.com/usuaris/libros/thumbs/189c2fa8-4d97-4c2d-91bb-10a2168b159c/d_295_510/portada_la-ultima-vez-que-pienso-en-ti_blue-jeans_202505142111.webp");
				libro.setPrecio(23.90);
				libro.setCategoria(Set.of(Categoria.romance));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorJeans);
				libroRepository.save(libro);
			}
			//194
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"El campamento", "Primera edición", autorJeans, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("El campamento");
				libro.setDescripcion("Diez de los chicos más prometedores del país, menores de 23 años, han sido invitados a un campamento muy especial en los Pirineos. El precursor de esta idea es Fernando Godoy, uno de los hombres más ricos de España, que busca a alguien joven que le ayude a dar una nueva imagen a su imperio y que en el futuro ocupe su lugar. En aquel idílico paraje, recibirán formación y serán preparados para convertirse en la mano derecha del millonario. Pero solo uno podrá conseguirlo.");
				libro.setPaginas(544);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(90);
				libro.setFechaPublicacion(LocalDate.of(2021, 4, 7));
				libro.setUrlFoto("https://pdlibrosarg.cdnstatics2.com/usuaris/libros/thumbs/e0f0e6eb-90a1-446b-9106-cc9e30235e8b/d_295_510/330088_portada_el-campamento_blue-jeans_202102011738.webp");
				libro.setPrecio(23.90);
				libro.setCategoria(Set.of(Categoria.suspenso));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorJeans);
				libroRepository.save(libro);
			}
			//195
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Los crímenes de Chopin", "Primera edición", autorJeans, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Los crímenes de Chopin");
				libro.setDescripcion("En varias casas de Sevilla se han producido una serie de robos que preocupan a toda la ciudad. El ladrón, al que apodan «Chopin» porque siempre deja una partitura del famoso compositor para firmar el robo, se lleva dinero, joyas y diferentes artículos de valor. Una noche aparece un cadáver en el salón de una de esas viviendas y la tensión aumenta. Nikolai Olejnik es un joven polaco que llegó a España con su abuelo hace varios años. Desde que este murió, está solo y sobrevive a base de delinquir. Fue un niño prodigio en su país y su mayor pasión es tocar el piano. De repente, todo se complica y se convierte en el principal sospechoso de un asesinato. Niko acude al despacho de Celia Mayo, detective privado, a pedirle ayuda y allí conoce a Triana, la hija de Celia. La joven enseguida llama su atención, aunque no es el mejor momento para enamorarse. Blanca Sanz apenas lleva cinco meses trabajando en el periódico El Guadalquivir cuando recibe una extraña llamada en la que le filtran datos sobre el caso Chopin, que nadie más conoce. Desde ese momento se obsesiona con todo lo relacionado con la investigación e intenta averiguar quién está detrás de aquellos robos.");
				libro.setPaginas(480);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(92);
				libro.setFechaPublicacion(LocalDate.of(2023, 4, 12));
				libro.setUrlFoto("https://pdlibrosarg.cdnstatics2.com/usuaris/libros/thumbs/d73f729d-4748-45ae-a27f-4de8f716527a/d_295_510/354322_portada_los-crimenes-de-chopin_blue-jeans_202203311106.webp");
				libro.setPrecio(24.90);
				libro.setCategoria(Set.of(Categoria.suspenso));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorJeans);
				libroRepository.save(libro);
			}
			//196
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Una influencer muerta en París", "Primera edición", autorJeans, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Una influencer muerta en París");
				libro.setDescripcion("PARÍS, 2023. Una famosa marca francesa de perfumes y cosméticos convoca el Premio al Mejor Influencer del Momento de habla hispana para así hacerse un hueco en el mercado español. El galardón se entregará en la capital francesa, pero esta fiesta repleta de lujo, INFLUENCERS y lentejuelas acabará de una forma trágica: Henar Berasategui, una de las candidatas al premio y la instagrammer más popular de los últimos tiempos, aparece MUERTA en uno de los baños del teatro donde se celebra la gala. Junto al cadáver encuentran, con las manos llenas de sangre, a Ana Leyton (Ley), una tiktoker de diecinueve años que está arrasando y que es la mayor rival de Henar. El mundo de los influencers, sus representantes, las marcas, la rivalidad entre creadores de contenido, la juventud con la que adquieren la fama, los haters, la presión que soportan, las cuestiones relacionadas con la salud mental, los fans que se obsesionan con sus ídolos, los intereses y el dinero que mueven serán las claves de esta nueva novela de Blue Jeans, vertiginosa, intrigante y de rabiosa actualidad, en la que el amor, la incomprensión y la muerte también estarán muy presentes.");
				libro.setPaginas(496);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(93);
				libro.setFechaPublicacion(LocalDate.of(2024, 3, 6));
				libro.setUrlFoto("https://pdlibrosarg.cdnstatics2.com/usuaris/libros/thumbs/bbf1368e-d3b9-4104-aaca-32625272c2e5/d_360_620/398632_portada_una-influencer-muerta-en-paris_blue-jeans_202402290846.webp");
				libro.setPrecio(25.90);
				libro.setCategoria(Set.of(Categoria.suspenso));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorJeans);
				libroRepository.save(libro);
			}
			//197
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"¿Puedo soñar contigo?", "Primera edición", autorJeans, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("¿Puedo soñar contigo?");
				libro.setDescripcion("Atrás quedaron los malos momentos que hicieron peligrar el futuro de EL CLUB DE LOS INCOMPRENDIDOS. Valeria, Raúl, María, Bruno y Ester vuelven a estar muy unidos, gracias sobre todo al empeño de Alba, quien se está ganando con creces formar parte del Club. Pero después de la calma, la tormenta: malentendidos, envidias, reencuentros inesperados, historias que renacen, nuevos personajes y la reaparición de alguien muy especial para todos ellos volverá a poner su amistad en peligro.");
				libro.setPaginas(416);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(86);
				libro.setFechaPublicacion(LocalDate.of(2014, 2, 4));
				libro.setUrlFoto("https://pdlibrosarg.cdnstatics2.com/usuaris/libros/thumbs/6032c529-60cd-4231-a572-a4f367040d42/d_360_620/portada_puedo-sonar-contigo_blue-jeans_201601251823.webp");
				libro.setPrecio(19.90);
				libro.setCategoria(Set.of(Categoria.romance));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorJeans);
				libroRepository.save(libro);
			}
			//198
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Sabes que te quiero", "Primera edición", autorJeans, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Sabes que te quiero");
				libro.setDescripcion("La adolescencia es una época en la que todo se vive con mucha intensidad. Tanto para lo bueno, como para lo malo. Paula se fue de vacaciones con su familia a París e intentó olvidar, sin éxito, lo acontecido en las últimas semanas. Pero allí conoce a alguien que parece que quiere hacerle la vida imposible. Llega el verano y las Sugus celebran el deseado final de los exámenes. Pero no todos serán alegrías. Cuando la persona que te traiciona es tu mejor amiga, duele más. Una de las chicas, además, se enfrenta a un problema que es incapaz de controlar. Nuevos amores, desengaños, verdades y mentiras que convertirán esta segunda parte en una montaña rusa de sentimientos y sensaciones.");
				libro.setPaginas(624);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(88);
				libro.setFechaPublicacion(LocalDate.of(2010, 9, 1));
				libro.setUrlFoto("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSE9Qb_70r_Xf1QOpE_PbRyQtoltMlfP6Eqig&s");
				libro.setPrecio(19.90);
				libro.setCategoria(Set.of(Categoria.romance));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorJeans);
				libroRepository.save(libro);
			}
			//199
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Algo tan sencillo como tuitear te quiero", "Primera edición", autorJeans, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Algo tan sencillo como tuitear te quiero");
				libro.setDescripcion("El primer año en la universidad marca la vida de muchas personas. Te enfrentas a nuevos retos, nuevas ilusiones y a numerosos cambios que, por mucho que tengas previstos no dejan de sorprenderte. Todo esto se multiplica si, además, ese primer año lo pasas en una residencia de estudiantes. Vives veinticuatro horas, los siete días de la semana, con los que terminan convirtiéndose en tus mejores amigos. Abres los ojos de par en par y surge el amor, llegan las decepciones, descubres la pasión, te persiguen las tentaciones, conoces a fondo tus miedos... todo intensificado y a un ritmo que da vértigo. Los chicos de la Benjamin Franklin afrontan esa época repletos de sueños y también de dudas. Las cosas no siempre son lo que parecen ni salen como uno desea. Pero tienes que lanzar la moneda para saber si sale cruz o cara», Blue Jeans.");
				libro.setPaginas(544);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(89);
				libro.setFechaPublicacion(LocalDate.of(2015, 5, 26));
				libro.setUrlFoto("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS5Z1a1SWLYmePvlUVMZ4PteLgAx4GdSoXRww&s");
				libro.setPrecio(21.90);
				libro.setCategoria(Set.of(Categoria.romance));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorJeans);
				libroRepository.save(libro);
			}
			//200
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Algo tan sencillo como darte un beso", "Primera edición", autorJeans, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Algo tan sencillo como darte un beso");
				libro.setDescripcion("David, Elena, Óscar, Iria, Julen, Manu, Ainhoa y Toni, los chicos del pasillo 1 b vuelven a la residencia Benjamín Franklin después de las vacaciones de Navidad. Con ellos, también las nuevas parejas de David y Elena, Marta y Martín, que siguen adelante con su relación. Pero no están todos, falta Nicole, que tuvo que marcharse a Valencia, con su familia, tras sufrir una agresión xenófoba en el Starbucks donde trabajaba. Los chicos la echan mucho de menos, y ella está deseando volver, pero su familia no quiere ni oír hablar de ello; aún no se les ha quitado el susto del cuerpo. Aunque aparentemente todo sigue igual, los chicos se enfrentarán en este nuevo trimestre a las más variopintas situaciones: fenómenos semiparanormales, cambios de rumbo inesperados y la aparición en sus vidas de alguien que no debería estar, nuevos amores sorprendentes y otras rupturas previsibles.");
				libro.setPaginas(480);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(88);
				libro.setFechaPublicacion(LocalDate.of(2016, 5, 24));
				libro.setUrlFoto("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQpVRa13LCZNh8odmuUAil0m3YQVkesfEnoSg&s");
				libro.setPrecio(21.90);
				libro.setCategoria(Set.of(Categoria.romance));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorJeans);
				libroRepository.save(libro);
			}
			//201
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Algo tan sencillo como estar contigo", "Primera edición", autorJeans, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Algo tan sencillo como estar contigo");
				libro.setDescripcion("Los chicos del pasillo 1B acaban de regresar de las vacaciones de Semana Santa para afrontar el final de su primer año universitario. No estántodos los que empezaron, ya que Manu lleva más de dos meses sin aparecer por la residencia Benjamin Franklin. El malagueño le ha dicho a Iria que volvería, pero no ha cumplido con su palabra.Esos últimos meses de curso prometen ser muy agitados. Óscar y Ainhoa parecen ser de nuevo amigos, aunque uno de ellos necesite más; Julen ha encontrado el amor, como Toni, a quien Isa come Pizza le plantea un reto imposible para ser su novia. Además, la habitación 1155 tiene nueva inquilina. La extremeña Silvia se pasa las horas entregada a su carrera, Arquitectura, pero esconde un secreto, que termina contando a David. ¿Surgirá algo entre ellos? A Elena, quizás, no le haga demasiada gracia, porque después de que su hermana cortara con el sevillano, se replantea sus sentimientos hacia él, día tras día.");
				libro.setPaginas(480);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(90);
				libro.setFechaPublicacion(LocalDate.of(2017, 5, 23));
				libro.setUrlFoto("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTeqNob8gl8SJRHDaa7BHRi-_7h3u_tMuYomQ&s");
				libro.setPrecio(21.90);
				libro.setCategoria(Set.of(Categoria.romance));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorJeans);
				libroRepository.save(libro);
			}
			//202
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Natacha", "Primera edición", autorPescetti, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Natacha");
				libro.setDescripcion("Natacha tiene una mamá que inventa cuentos de monstruos, una amiga, Pati, con quien forma \"Las Chicas Perla\" y un perro, Rafles, un poco destrozón. Natacha es una chica divertida y preguntona. Juntos protagonizan los episodios que han consagrado a este querido personaje de la literatura infantil. En este libro se relatan algunas de sus más curiosas anécdotas.");
				libro.setPaginas(144);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(89);
				libro.setFechaPublicacion(LocalDate.of(1999, 8, 1));
				libro.setUrlFoto("https://www.loqueleo.com/uy/uploads/2017/04/resized/360_natacha.jpg");
				libro.setPrecio(12.99);
				libro.setCategoria(Set.of(Categoria.infantil));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorPescetti);
				libroRepository.save(libro);
			}
			//203
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Frin", "Primera edición", autorPescetti, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Frin");
				libro.setDescripcion("Frin es un chico que odia los deportes, tiene un particular sentido del humor, le gusta leer y andar en bicicleta. En este libro, el protagonista descubre con su grupo la amistad, vive insólitas situaciones en el colegio, realiza un sorprendente viaje y, a través de la poesía, encuentra el amor. Una novela con varios secretos, contada desde la óptica de los chicos, con realismo y humor, que encantará a sus lectores.");
				libro.setPaginas(208);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(92);
				libro.setFechaPublicacion(LocalDate.of(2003, 5, 10));
				libro.setUrlFoto("https://www.loqueleo.com/ar/uploads/2015/11/resized/800_9789504643029.jpg");
				libro.setPrecio(15.99);
				libro.setCategoria(Set.of(Categoria.infantil));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorPescetti);
				libroRepository.save(libro);
			}
			//204
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Buenísimo, Natacha", "Primera edición", autorPescetti, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Buenísimo, Natacha");
				libro.setDescripcion("Textos breves, diálogos y cartas donde Natacha, junto a su amiga Pati, el perro Rafles y sus compañeros de colegio, despliega su curiosa y divertida personalidad.");
				libro.setPaginas(160);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(88);
				libro.setFechaPublicacion(LocalDate.of(2001, 9, 1));
				libro.setUrlFoto("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQWH3qVuO1qdr1PjhwfP1FBPn3j5GVreMsh1A&s");
				libro.setPrecio(13.49);
				libro.setCategoria(Set.of(Categoria.infantil));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorPescetti);
				libroRepository.save(libro);
			}
			//205
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Nadie te creería", "Primera edición", autorPescetti, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Nadie te creería");
				libro.setDescripcion("Al igual que en El pulpo está crudo, Luis Pescetti sorprende con una serie de diálogos absurdos, que contienen juegos constantes con el lenguaje, algunas cartas insólitas y juegos ortográficos que llevan tanto a la risa como a la reflexión en torno a la propia lengua. Relatos muy divertidos, al estilo Pescetti, que juegan con la ortografía y permiten que los chicos reflexionen sobre el por qué de la ortografía y otros aspectos de la lengua.");
				libro.setPaginas(128);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(86);
				libro.setFechaPublicacion(LocalDate.of(2004, 3, 10));
				libro.setUrlFoto("https://www.loqueleo.com/ar/uploads/2015/11/resized/360_9789504643562.jpg");
				libro.setPrecio(11.99);
				libro.setCategoria(Set.of(Categoria.infantil));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorPescetti);
				libroRepository.save(libro);
			}
			//206
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"El pulpo está crudo", "Primera edición", autorPescetti, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("El pulpo está crudo");
				libro.setDescripcion("El pulpo está crudo contiene doce relatos breves impregnados de imaginación y humor, con diálogos descabellados. Historias disparatadas que divierten hasta provocar la risa, como la de un chico que comía flores o la de una pelea entre dos delirantes archisúperenemigos.");
				libro.setPaginas(112);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(85);
				libro.setFechaPublicacion(LocalDate.of(2002, 7, 15));
				libro.setUrlFoto("https://www.loqueleo.com/ar/uploads/2015/11/resized/360_9789504643319.jpg");
				libro.setPrecio(10.99);
				libro.setCategoria(Set.of(Categoria.humor, Categoria.infantil));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorPescetti);
				libroRepository.save(libro);
			}
			//207
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Lejos de Frin", "Primera edición", autorPescetti, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Lejos de Frin");
				libro.setDescripcion("Con esta nueva novela de Luis María Pescetti regresan los entrañables personajes de Frin y sus amigos -Alma, Linko, Arno y Vera-, quienes vivirán inesperadas situaciones donde no faltarán viajes, romance y aventuras de principio a fin. Con la calidez y el humor de esta historia, los chicos disfrutarán de uno de los personajes más queridos de la literatura juvenil de estos tiempos.");
				libro.setPaginas(200);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(90);
				libro.setFechaPublicacion(LocalDate.of(2010, 4, 1));
				libro.setUrlFoto("https://www.loqueleo.com/ar/uploads/2015/11/resized/360_9789504634300.jpg");
				libro.setPrecio(15.49);
				libro.setCategoria(Set.of(Categoria.infantil));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorPescetti);
				libroRepository.save(libro);
			}
			//208
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Alma y Frin", "Primera edición", autorPescetti, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Alma y Frin");
				libro.setDescripcion("Un nuevo episodio en las vidas del querido Frin y su grupo de amigos. Comienzan las vacaciones y Frin visita, resignado, el pequeño pueblo de su tío, en el campo. Mientras, Alma está en la playa con su mamá y Lynko conoce Alemania, el lugar adonde podría llegar a mudarse. Sin mucha comunicación con los demás, cada uno vive ese verano como un gran cambio.");
				libro.setPaginas(192);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(91);
				libro.setFechaPublicacion(LocalDate.of(2013, 8, 20));
				libro.setUrlFoto("https://www.loqueleo.com/ar/uploads/2015/11/resized/360_9789504637875.jpg");
				libro.setPrecio(15.99);
				libro.setCategoria(Set.of(Categoria.infantil));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorPescetti);
				libroRepository.save(libro);
			}
			//209
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Botiquín emocional", "Primera edición", autorPescetti, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Botiquín emocional");
				libro.setDescripcion("El derecho a sentir. A guardarte eso que sentís y a tener alguien de confianza para contarle. A ser consciente de lo que sentís (con palabras o sin ellas), a no tener una respuesta, a no sentir lo mismo que todos, a tener sentimientos encontrados, a estar en crisis. El derecho a la pausa (no actuar por impulso), a saber que no somos lo que sentimos, a que no te obligue la emoción de otro. El derecho a grandes emociones.");
				libro.setPaginas(128);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(87);
				libro.setFechaPublicacion(LocalDate.of(2016, 9, 12));
				libro.setUrlFoto("https://www.loqueleo.com/ar/uploads/2021/12/resized/360_tapa-botiquin-emocional.jpeg");
				libro.setPrecio(14.99);
				libro.setCategoria(Set.of(Categoria.infantil));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorPescetti);
				libroRepository.save(libro);
			}
			//210
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Te amo, lectura", "Primera edición", autorPescetti, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Te amo, lectura");
				libro.setDescripcion("La maestra le propone al grado la lectura de Tom Sawyer y El Principito, entre otras obras. Los alumnos interpretan mal la consigna y esto da lugar a una pelea de bandas entre chicos y chicas, según el título elegido, donde cada uno defiende su libro. Pero todo lleva a malentendidos, líos, diversión y aventuras. Junto a Rafles, su perro, y Pati, su mejor amiga, las Chicas Perla, las Chicas Coral y los varones del grado, Natacha protagonizará otra desopilante aventura.");
				libro.setPaginas(96);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(84);
				libro.setFechaPublicacion(LocalDate.of(2018, 3, 5));
				libro.setUrlFoto("https://www.loqueleo.com/ar/uploads/2018/02/resized/360_tapa-te-amo-lectura-natacha.jpg");
				libro.setPrecio(11.49);
				libro.setCategoria(Set.of(Categoria.infantil));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorPescetti);
				libroRepository.save(libro);
			}
			//211
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Chat Natacha chat", "Primera edición", autorPescetti, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Chat Natacha chat");
				libro.setDescripcion("Natacha y su inseparable amiga Pati logran convertir ciertas situaciones en verdaderas locuras.Natacha no puede con su genio y sigue poniendo su toque personal en lo que hace y, sobre todo, en lo que pregunta: ¿cómo se conocieron y conquistaron sus papás?, ¿cómo era su vida cuando ella no existía? ¿Se puede quemar un incendio? ¿Pueden usar aritos los perros varones?... Aprender danza árabe, resolver problemas con la computadora, una redacción sobre el cuerpo humano, \"el del hombre y el de la mujer\", y una visita a una granja en la que los animales del campo conocen a \"los animales de la ciudad\" son algunas de las situaciones que Natacha y su inseparable amiga Pati logran convertir en verdaderas locuras.");
				libro.setPaginas(140);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(88);
				libro.setFechaPublicacion(LocalDate.of(2011, 10, 1));
				libro.setUrlFoto("https://www.loqueleo.com/ar/uploads/2018/02/tapa-chat-natacha-chat.jpg");
				libro.setPrecio(13.99);
				libro.setCategoria(Set.of(Categoria.infantil));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorPescetti);
				libroRepository.save(libro);
			}
			//212
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Nuestro planeta Natacha", "Primera edición", autorPescetti, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Nuestro planeta Natacha");
				libro.setDescripcion("La seño Greichu les propone a los chicos un proyecto para todo el año: el planeta y la supervivencia de la humanidad. ¡Y encima quiere que lo hagan con chicos de otra escuela! Natacha, Pati y sus amigos se embarcan en una aventura hacia un mundo desconocido: los otros, extraños y extranjeros de la escuela vecina. Visitas, cartas y trabajos se suceden mientras ellos reflexionan sobre cómo cuidar nuestro planeta y a la especie humana, al tiempo que se hacen nuevos amigos y se producen trabajos en equipo, rivalidades y deslumbramientos con chicos y chicas que no conocían.");
				libro.setPaginas(152);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(87);
				libro.setFechaPublicacion(LocalDate.of(2020, 6, 20));
				libro.setUrlFoto("https://www.loqueleo.com/ar/uploads/2018/02/resized/800_tapa-nuestro-planeta-natacha.jpg");
				libro.setPrecio(14.49);
				libro.setCategoria(Set.of(Categoria.infantil));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorPescetti);
				libroRepository.save(libro);
			}
			//213
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Unidos contra Drácula", "Primera edición", autorPescetti, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Unidos contra Drácula");
				libro.setDescripcion("Este libro es reúne poesías, diálogos y textos de humor que recorren las emociones más íntimas, el absurdo, la infancia, el juego con el lenguaje y sus sonidos. Un libro único e irrepetible, que propone una mirada poética de la modernidad y atraviesa todas las etapas de la vida.");
				libro.setPaginas(120);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(85);
				libro.setFechaPublicacion(LocalDate.of(2005, 8, 10));
				libro.setUrlFoto("https://www.loqueleo.com/ar/uploads/2015/11/9789504633549.jpg");
				libro.setPrecio(12.49);
				libro.setCategoria(Set.of(Categoria.infantil));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorPescetti);
				libroRepository.save(libro);
			}
			//214
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Querido diario, Natacha", "Primera edición", autorPescetti, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Querido diario, Natacha");
				libro.setDescripcion("En Querido diario (Natacha), Luis María Pescetti nos recuerda todo lo que sucede cuando parece no pasar nada, y sin embargo laten y se construyen la intimidad y la amistad. Una tarde de sábado, Natacha y Pati se quedan en casa porque deciden empezar a escribir sus diarios. Todo comienza como una jornada tranquila, en la que discurren sobre la vida, cómo les quedan sus peinados, dónde pegar un brillito, el chico que les gusta, o un plan para salvar a la humanidad en extinción.");
				libro.setPaginas(136);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(89);
				libro.setFechaPublicacion(LocalDate.of(2014, 2, 1));
				libro.setUrlFoto("https://www.loqueleo.com/uy/uploads/2017/04/resized/800_tapa_2.jpg");
				libro.setPrecio(13.49);
				libro.setCategoria(Set.of(Categoria.infantil));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorPescetti);
				libroRepository.save(libro);
			}
			//215
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"La enciclopedia de las chicas Perla", "Primera edición", autorPescetti, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("La enciclopedia de las chicas Perla");
				libro.setDescripcion("Natacha y Pati están de vacaciones, y un día las cuida la Abu Marta (que no las deja chatear porque ella no sabe de computadoras), y otro día la tía de Pati (que llora por un novio). Además no pueden hacer ruido, y las telenovelas que mira la Abu son aburridísimas. Entonces, mientras esperan que empiece la bendita colonia de vacaciones (a-la-que-no-quieren-ir), mejor deciden ayudar a la humanidad escribiendo “La Enciclopedia de Las Chicas Perla”.");
				libro.setPaginas(180);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(84);
				libro.setFechaPublicacion(LocalDate.of(2007, 5, 1));
				libro.setUrlFoto("https://www.loqueleo.com/ar/uploads/2018/02/tapa-la-enciclopedia-de-las-chicas-perla.jpg");
				libro.setPrecio(14.49);
				libro.setCategoria(Set.of(Categoria.humor));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorPescetti);
				libroRepository.save(libro);
			}
			//216
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Historias de los señores Moc y Poc", "Primera edición", autorPescetti, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Historias de los señores Moc y Poc");
				libro.setDescripcion("Los señores Moc y Poc se hacen muchas preguntas, escriben cartas absurdas y entablan largos y desopilantes diálogos. Juegos con el lenguaje, poesía y humor se conjugan en un libro difícil de clasificar por la mixtura de géneros y temas. Un texto divertido, con el sello de Pescetti y los dibujos del gran O'Kif.");
				libro.setPaginas(104);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(83);
				libro.setFechaPublicacion(LocalDate.of(1998, 4, 15));
				libro.setUrlFoto("https://www.loqueleo.com/ar/uploads/2015/11/resized/360_9789504640585.jpg");
				libro.setPrecio(10.99);
				libro.setCategoria(Set.of(Categoria.humor, Categoria.infantil));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorPescetti);
				libroRepository.save(libro);
			}
			//217
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Cinco amigas y un león", "Primera edición", autorDami, editorialPlaneta).isEmpty()) {

				Libro libro = new Libro();
				libro.setTitulo("Cinco amigas y un león");
				libro.setDescripcion("Las chicas del Club de Tea se van a un safari fotográfico… ¡superratónico! Pero, de repente, Mosi, un cachorro de león recién nacido, desaparece. ¿Lograrán salvar al pequeño león?");
				libro.setPaginas(128);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(85);
				libro.setFechaPublicacion(LocalDate.of(2022, 3, 15));
				libro.setUrlFoto("https://pdlibrosarg.cdnstatics2.com/usuaris/libros/thumbs/4d17e2f9-ceff-427a-ba31-241838543da1/d_360_620/296063_portada___201603161520.webp");
				libro.setPrecio(19.99);
				libro.setCategoria(Set.of(Categoria.infantil, Categoria.aventura));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorDami);
				libroRepository.save(libro);
			}
			//218
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Skate para dos", "Primera edición", autorDami, editorialPlaneta).isEmpty()) {

				Libro libro = new Libro();
				libro.setTitulo("Skate para dos");
				libro.setDescripcion("Skate para dos… ¡Pero el miedo es solo mío! ¿Os habéis lanzado alguna vez en un monopatín sin tener el menor sentido del equilibrio? ¿Y habéis participado alguna vez en un campeonato de velocidad por parejas? Yo sí, ¡y os aseguro que no fue ningún paseo! Pero ¿sabéis una cosa? ¡Fue superratónico!");
				libro.setPaginas(144);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(83);
				libro.setFechaPublicacion(LocalDate.of(2021, 8, 22));
				libro.setUrlFoto("https://pdlibrosarg.cdnstatics2.com/usuaris/libros/thumbs/f3a4f432-82aa-4a3c-b8f1-3eb2d973d92f/d_360_620/425393_portada_skate-para-dos_geronimo-stilton_202507141518.webp");
				libro.setPrecio(18.50);
				libro.setCategoria(Set.of(Categoria.infantil, Categoria.aventura));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorDami);
				libroRepository.save(libro);
			}
			//219
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Es Navidad, Stilton", "Primera edición", autorDami, editorialPlaneta).isEmpty()) {

				Libro libro = new Libro();
				libro.setTitulo("Es Navidad, Stilton");
				libro.setDescripcion("Todo parecía listo para la cena navideña con mi familia. En cambio, pobre de mí, ¡qué Nochebuena tan increíble me esperaba! Recogí cientos de miles de quesitos al chocolate, un tráiler me aplastó la cola, y mi casa prendió fuego. Pero ¡cuántos roedores me ayudaron! Sí, en Navidad todos nos sentimos más buenos. ¡Qué bonito sería que ocurriera lo mismo todo el año!");
				libro.setPaginas(128);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(90);
				libro.setFechaPublicacion(LocalDate.of(2019, 12, 1));
				libro.setUrlFoto("https://pdlibrosarg.cdnstatics2.com/usuaris/libros/thumbs/d53e9c48-86b5-4853-a172-d7e995e9f8c5/d_360_620/portada_stilton-30-es-navidad-stilton_geronimo-stilton_201505261102.webp");
				libro.setPrecio(21.99);
				libro.setCategoria(Set.of(Categoria.infantil, Categoria.aventura, Categoria.fantastico));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorDami);
				libroRepository.save(libro);
			}
			//220
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"En busca de la maravilla perdida", "Primera edición", autorDami, editorialPlaneta).isEmpty()) {

				Libro libro = new Libro();
				libro.setTitulo("En busca de la maravilla perdida");
				libro.setDescripcion("Todo empezó con un café con leche… ¿Un café con leche? Pero ¿qué tiene que ver el café con leche con toda esta historia? Sí, así empezó todo: mi loca pasión por Provoleta, la expedición a la Isla Mariposa en busca de la Octava Maravilla…");
				libro.setPaginas(224);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(92);
				libro.setFechaPublicacion(LocalDate.of(2020, 5, 10));
				libro.setUrlFoto("https://pdlibrosarg.cdnstatics2.com/usuaris/libros/thumbs/a633e610-5c4d-4f03-9341-bc6776dacf9a/d_360_620/portada_stilton-2-en-busca-de-la-maravilla-perdida_geronimo-stilton_201607271258.webp");
				libro.setPrecio(26.00);
				libro.setCategoria(Set.of(Categoria.infantil, Categoria.aventura, Categoria.fantastico));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorDami);
				libroRepository.save(libro);
			}
			//221
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"El castillo de Roca tacaña", "Primera edición", autorDami, editorialPlaneta).isEmpty()) {

				Libro libro = new Libro();
				libro.setTitulo("El castillo de Roca tacaña");
				libro.setDescripcion("Tío Milordo, el ratón más tacaño de Ratonia, me había invitado a su castillo para asistir a la boda de su hijo Virgilio con Cloaquita Pestoseta- Tufarada. El castillo estaba rodeado de un foso lleno de agua pestilente...");
				libro.setPaginas(160);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(88);
				libro.setFechaPublicacion(LocalDate.of(2018, 10, 4));
				libro.setUrlFoto("https://proassetspdlcom.cdnstatics2.com/usuaris/libros/thumbs/eb1a0c65-f7b8-4b5c-b76d-c1a1b1fdad7b/d_360_620/portada___201602221244.webp");
				libro.setPrecio(20.99);
				libro.setCategoria(Set.of(Categoria.infantil, Categoria.aventura));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorDami);
				libroRepository.save(libro);
			}
			//222
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Un disparatado viaje a Ratikistán", "Primera edición", autorDami, editorialPlaneta).isEmpty()) {

				Libro libro = new Libro();
				libro.setTitulo("Un disparatado viaje a Ratikistán");
				libro.setDescripcion("Odio viajar. ¿Qué hago aquí, en Ratikistan, a cuarenta grados bajo cero, con toda mi familia en una autocaravana de queso? Todo ha sido por culpa de mi abuelo, el fundador de la editorial…");
				libro.setPaginas(176);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(84);
				libro.setFechaPublicacion(LocalDate.of(2022, 6, 11));
				libro.setUrlFoto("https://proassetspdlcom.cdnstatics2.com/usuaris/libros/thumbs/8b6ec649-7b59-40fd-9e1d-88377d5e60f2/d_360_620/portada___201604011251.webp");
				libro.setPrecio(22.50);
				libro.setCategoria(Set.of(Categoria.infantil, Categoria.aventura));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorDami);
				libroRepository.save(libro);
			}
			//223
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"El fantasma del metro", "Primera edición", autorDami, editorialPlaneta).isEmpty()) {

				Libro libro = new Libro();
				libro.setTitulo("El fantasma del metro");
				libro.setDescripcion("¿Qué sucede en el metro de Ratonia? ¿Qué misterios esconde el gato gigante que vaga por las oscuras galerías subterráneas, aterrorizando a todos los roedores de la ciudad? Brrr, quizá sea un fantasma...");
				libro.setPaginas(128);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(93);
				libro.setFechaPublicacion(LocalDate.of(2005, 3, 12));
				libro.setUrlFoto("https://proassetspdlcom.cdnstatics2.com/usuaris/libros/thumbs/31432a3e-654d-4d9f-8fca-7d1f0af6d715/d_360_620/portada_el-fantasma-del-metro_geronimo-stilton_201610311321.webp");
				libro.setPrecio(17.99);
				libro.setCategoria(Set.of(Categoria.infantil, Categoria.aventura));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorDami);
				libroRepository.save(libro);
			}
			//224
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Tras la pista del Yeti", "Primera edición", autorDami, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Tras la pista del Yeti");
				libro.setDescripcion("Para mí la amistad es una cosa importante: por eso me voy a buscar al profesor Voltio, desaparecido en el Himalaya siguiendo el rastro del misterioso yeti. ¿Lo encontraré entre enormes huellas y aludes? ¡Más historias morrocotudas directamente desde la Isla de los Ratones!");
				libro.setPaginas(208);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(91);
				libro.setFechaPublicacion(LocalDate.of(2009, 11, 9));
				libro.setUrlFoto("https://proassetspdlcom.cdnstatics2.com/usuaris/libros/thumbs/ac6d1ac4-0d3a-470d-8a08-7374bf7c807e/d_360_620/portada_gs-16ntras-la-pista-yeti_geronimo-stilton_201703291028.webp");
				libro.setPrecio(23.99);
				libro.setCategoria(Set.of(Categoria.infantil, Categoria.aventura));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorDami);
				libroRepository.save(libro);
			}
			//225
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Percy Jackson y el ladrón del rayo", "Primera edición", autorRiordan, editorialPenguin).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Percy Jackson y el ladrón del rayo");
				libro.setDescripcion("¿Qué pasaría si un día descubrieras que, en realidad, eres hijo de un dios griego que debe cumplir una misión secreta? Pues eso es lo que le sucede a Percy Jackson, que a partir de ese momento se dispone a vivir los acontecimientos más emocionantes de su vida. Expulsado de seis colegios, Percy padece dislexia y dificultades para concentrarse, o al menos ésa es la versión oficial. Objeto de burlas por inventarse historias fantásticas, ni siquiera él mismo acaba de creérselas hasta el día que los dioses del Olimpo le revelan la verdad: Percy es nada menos que un semidiós, es decir, el hijo de un dios y una mortal. Y como tal ha de descubrir quién ha robado el rayo de Zeus y así evitar que estalle una guerra entre los dioses. Para cumplir la misión contará con la ayuda de sus amigos Grover, un joven sátiro, y Annabeth, hija de Atenea.");
				libro.setPaginas(288);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(94);
				libro.setFechaPublicacion(LocalDate.of(2005, 6, 28));
				libro.setUrlFoto("https://www.penguinlibros.com/ar/4245550-large_default/el-ladron-del-rayo-percy-jackson-y-los-dioses-del-olimpo-1.webp");
				libro.setPrecio(22.99);
				libro.setCategoria(Set.of(Categoria.fantastico, Categoria.aventura, Categoria.infantil));
				libro.setEditorial(editorialPenguin);
				libro.setAutor(autorRiordan);
				libroRepository.save(libro);
			}
			//226
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"El mar de los monstruos", "Primera edición", autorRiordan, editorialPenguin).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("El mar de los monstruos");
				libro.setDescripcion("Desde que sabe que es hijo de un dios y una mortal, Percy Jackson espera que el destino le depare continuas aventuras. Y su expectativa se cumplirá con creces. Aunque el nuevo curso en la Escuela Meriwether transcurre con inusual normalidad, un simple partido de balón prisionero acaba en batalla campal contra una banda de feroces gigantes. A partir de ahí, las cosas se precipitan: el perímetro mágico que protege el Campamento Mestizo es destruido por un misterioso enemigo y la única seguridad con que contaban los semidioses desaparece. Así, para impedir este daño irreparable, Percy y sus amigos inician la travesía del temible Mar de los Monstruos en busca de lo único que puede salvar el campamento: el Vellocino de Oro.");
				libro.setPaginas(304);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(92);
				libro.setFechaPublicacion(LocalDate.of(2006, 4, 1));
				libro.setUrlFoto("https://www.penguinlibros.com/ar/3005384-large_default/el-mar-de-los-monstruos-percy-jackson-y-los-dioses-del-olimpo-2.webp");
				libro.setPrecio(23.99);
				libro.setCategoria(Set.of(Categoria.fantastico, Categoria.aventura));
				libro.setEditorial(editorialPenguin);
				libro.setAutor(autorRiordan);
				libroRepository.save(libro);
			}
			//227
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"La maldición del titán", "Primera edición", autorRiordan, editorialPenguin).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("La maldición del titán");
				libro.setDescripcion("Ante la llamada de socorro de su amigo el sátiro Grover, Percy acude inmediatamente en su auxilio. Y aunque va acompañado de Annabeth y Thalia, las dos semidiosas que son sus aliadas, ninguno imagina la sorpresa que los aguarda: una terrible mantícora pretende secuestrarlos y llevarlos ante el general enviado por Cronos, el diabólico señor de los titanes. Sin embargo, gracias a la ayuda de las cazadoras de Artemisa, Percy y sus aliadas logran escapar y volver al campamento mestizo. Una vez allí, emprenderán la búsqueda del monstruo que puede provocar la destrucción del Olimpo, a pesar de que, según la profecía del Oráculo, sólo uno de ellos logrará resistir la maldición del titán.");
				libro.setPaginas(320);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(93);
				libro.setFechaPublicacion(LocalDate.of(2007, 5, 1));
				libro.setUrlFoto("https://www.penguinlibros.com/ar/3005341-large_default/la-maldicion-del-titan-percy-jackson-y-los-dioses-del-olimpo-3.webp");
				libro.setPrecio(24.99);
				libro.setCategoria(Set.of(Categoria.fantastico, Categoria.aventura));
				libro.setEditorial(editorialPenguin);
				libro.setAutor(autorRiordan);
				libroRepository.save(libro);
			}
			//228
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"La batalla del laberinto", "Primera edición", autorRiordan, editorialPenguin).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("La batalla del laberinto");
				libro.setDescripcion("A punto de comenzar primero de secundaria, Percy Jackson no espera emociones fuertes, sino más bien un aburrimiento soporífero. Pero cuando en la nueva escuela se presenta una vieja amiga, seguida de un par de animadoras diabólicas, los acontecimientos se precipitan y todo empieza a ir de mal en peor. Cronos, el malvado señor de los titanes, amenaza con destruir el Campamento Mestizo, donde se refugian los jóvenes semidioses. Para evitarlo, Percy y sus amigos deben emprender una arriesgada búsqueda a través del laberinto,un mundo subterráneo plagado de trampas temibles y criaturas perversas, concebido para acabar con quienes se atrevan a profanarlo.");
				libro.setPaginas(352);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(94);
				libro.setFechaPublicacion(LocalDate.of(2008, 5, 6));
				libro.setUrlFoto("https://www.penguinlibros.com/ar/3005350-large_default/la-batalla-del-laberinto-percy-jackson-y-los-dioses-del-olimpo-4.webp");
				libro.setPrecio(25.99);
				libro.setCategoria(Set.of(Categoria.fantastico, Categoria.aventura));
				libro.setEditorial(editorialPenguin);
				libro.setAutor(autorRiordan);
				libroRepository.save(libro);
			}
			//229
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"El último héroe del Olimpo", "Primera edición", autorRiordan, editorialPenguin).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("El último héroe del Olimpo");
				libro.setDescripcion("Los mestizos han dedicado mucho tiempo a prepararse para la batalla decisiva contra los titanes, aunque saben que sus posibilidadesde obtener la victoria son mínimas. El ejército de Cronos es ahora más formidable que nunca y, con cada dios y cada mestizo que logra reclutar, aumentan los poderes del maligno titán. Tras fracasar en un primer intento de detener en alta mar las arrolladoras tropas de Cronos, Percy Jackson y los olímpicos se esfuerzan por mantener a raya la furia desatada del monstruo Tifón. Y cuando Cronos ordena el avance definitivo hacia Nueva York, donde el monte Olimpo, en lo alto del Empire State, se encuentra prácticamente indefenso, pararle los pies al implacable Señor del Tiempo dependerá exclusivamente de Percy y su hueste de jóvenes semidioses.");
				libro.setPaginas(432);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(96);
				libro.setFechaPublicacion(LocalDate.of(2009, 5, 5));
				libro.setUrlFoto("https://www.penguinlibros.com/ar/3005377-home_default/el-ultimo-heroe-del-olimpo-percy-jackson-y-los-dioses-del-olimpo-5.jpg");
				libro.setPrecio(26.99);
				libro.setCategoria(Set.of(Categoria.fantastico, Categoria.aventura));
				libro.setEditorial(editorialPenguin);
				libro.setAutor(autorRiordan);
				libroRepository.save(libro);
			}
			//230
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"La Comunidad del Anillo", "Primera edición", autorTolkien, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("La Comunidad del Anillo");
				libro.setDescripcion("«Este libro es como un relámpago en un cielo claro. Decir que la novela heroica, espléndida, elocuente y desinhibida, ha retornado de pronto en una época de un antirromanticismo casi patológico, sería inadecuado. Para quienes vivimos en esa extraña época, el retorno —y el alivio que nos trae— es sin duda lo más importante. Pero para la historia misma de la novela —una historia que se remonta a la Odisea y a antes de la Odisea— no es un retorno, sino un paso adelante o una revolución: la conquista de un territorio nuevo.» —C.S. Lewis, Time & Tide, 1954");
				libro.setPaginas(576);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(97);
				libro.setFechaPublicacion(LocalDate.of(1954, 7, 29));
				libro.setUrlFoto("https://pdlibrosarg.cdnstatics2.com/usuaris/libros/thumbs/62c2f9e8-0ea1-403c-aa21-31c101eeaf40/d_360_620/384505_portada_el-senor-de-los-anillos-n-0103-la-comunidad-del-anillo-edicion-revisada_j-r-r-tolkien_202310231103.webp");
				libro.setPrecio(32.99);
				libro.setCategoria(Set.of(Categoria.fantastico));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorTolkien);
				libroRepository.save(libro);
			}
			//231
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Las Dos Torres", "Primera edición", autorTolkien, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Las Dos Torres");
				libro.setDescripcion("La Compañía se ha disuelto y sus integrantes emprenden caminos separados. Frodo y Sam avanzan solos en su viaje a lo largo del río Anduin, perseguidos por la sombra misteriosa de un ser extraño que también ambiciona la posesión del Anillo. Mientras, hombres, elfos y enanos se preparan para la batalla final contra las fuerzas del Señor del Mal.");
				libro.setPaginas(464);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(96);
				libro.setFechaPublicacion(LocalDate.of(1954, 11, 11));
				libro.setUrlFoto("https://pdlibrosarg.cdnstatics2.com/usuaris/libros/thumbs/5811aca2-de3e-43be-9f70-d92cff5a8b44/d_360_620/387586_portada_el-senor-de-los-anillos-n-0203-las-dos-torres-edicion-revisada_j-r-r-tolkien_202309191635.webp");
				libro.setPrecio(32.99);
				libro.setCategoria(Set.of(Categoria.fantastico));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorTolkien);
				libroRepository.save(libro);
			}
			//232
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"El Retorno del Rey", "Primera edición", autorTolkien, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("El Retorno del Rey");
				libro.setDescripcion("Los ejércitos del Señor Oscuro van extendiendo cada vez más su maléfica sombra por la Tierra Media. Hombres, elfos y enanos unen sus fuerzas para presentar batalla a Sauron y sus huestes. Ajenos a estos preparativos, Frodo y Sam siguen adentrándose en el país de Mordor en su heroico viaje para destruir el Anillo de Poder en las Grietas del Destino.");
				libro.setPaginas(512);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(98);
				libro.setFechaPublicacion(LocalDate.of(1955, 10, 20));
				libro.setUrlFoto("https://pdlibrosarg.cdnstatics2.com/usuaris/libros/thumbs/fbacf17d-96c3-45bf-a12b-8e0bb129ea37/d_360_620/400210_portada_el-senor-de-los-anillos-n-0303-el-retorno-del-rey-edicion-revisada_j-r-r-tolkien_202403121720.webp");
				libro.setPrecio(34.99);
				libro.setCategoria(Set.of(Categoria.fantastico));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorTolkien);
				libroRepository.save(libro);
			}
			//233
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"El Hobbit", "Primera edición", autorTolkien, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("El Hobbit");
				libro.setDescripcion("El Hobbit es uno de los principales clásicos modernos y el preludio de El Señor de los Anillos. Bilbo Bolsón es como cualquier hobbit: no mide más de metro y medio, vive pacíficamente en la Comarca, y su máxima aspiración es disfrutar de los placeres sencillos de la vida: comer bien, pasear y charlar con los amigos. Pero su tranquilidad se ve interrumpida cuando el mago Gandalf y un grupo de trece enanos se presentan un día en su casa para involucrarlo en una aventura. Con la ayuda de un mapa misterioso, partirán hacia la Montaña Solitaria con el fin de rescatar el valioso tesoro custodiado por Smaug el Dorado, un terrible y enorme dragón. A pesar de las reticencias de Bilbo a participar en esta búsqueda, pronto descubrirá una temeridad y una habilidad como ladrón que jamás hubiera sospechado poseer.");
				libro.setPaginas(310);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(96);
				libro.setFechaPublicacion(LocalDate.of(1937, 9, 21));
				libro.setUrlFoto("https://pdlibrosarg.cdnstatics2.com/usuaris/libros/thumbs/772d1279-adde-4a29-ba82-11071178969c/d_360_620/380957_portada_el-hobbit-edicion-revisada_j-r-r-tolkien_202306071037.webp");
				libro.setPrecio(27.99);
				libro.setCategoria(Set.of(Categoria.fantastico));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorTolkien);
				libroRepository.save(libro);
			}
			//234
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Los Hijos de Húrin", "Primera edición", autorTolkien, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Los Hijos de Húrin");
				libro.setDescripcion("Los hijos de Húrin es uno de los grandes relatos que fundamentan la historia de la Tierra Media y se sitúa en la Primera Edad, cuando elfos, hombres y enanos llevaban unos pocos siglos sobre la tierra. Junto con las historia de Beren y Lúthien, es la historia más mencionada en El Señor de los Anillos y en El Silmarillion como referente del heroísmo y la tragedia en la lucha contra el Mal, en la Primera Edad encarnado en la figura de Morgoth. Una historia trágica de amores imposibles, pasiones incomprendidas y guerras sin cuartel entre el Bien y el Mal. Con hombres, elfos, enanos, orcos y dragones.");
				libro.setPaginas(336);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(92);
				libro.setFechaPublicacion(LocalDate.of(2007, 4, 17));
				libro.setUrlFoto("https://m.media-amazon.com/images/I/71-LT5Brh3L._AC_UF894,1000_QL80_.jpg");
				libro.setPrecio(33.99);
				libro.setCategoria(Set.of(Categoria.fantastico));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorTolkien);
				libroRepository.save(libro);
			}
			//235
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Cuentos inconclusos de Númenor y la Tierra Media", "Primera edición", autorTolkien, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Cuentos inconclusos de Númenor y la Tierra Media");
				libro.setDescripcion("Cuentos Inconclusos es una colección de relatos sobre la Historia de la Tierra Media desde los Primeros Días hasta el fin de la Guerra del Anillo, pasando por la Segunda Edad y el levantamiento de Sauron, en los que se refieren sucesos que más adelante se narrarán en El Silmarillion y en El Señor de los Anillos. El libro se concentra en el territorio de la Tierra Media,y comprende elementos tales como el animado discurso en que Gandalf explica cómo llegó a enviar a los Enanos a la famosa reunión en Bolsón Cerrado; la emergencia de Ulmo, el dios del mar, ante los ojos de Tuor en la costa de Beleriand; una descripción detallada de la organización militar de los Jinetes de Rohan; y el viaje de los Jinetes Negros durante la búsqueda del Anillo. Cuentos Inconclusos también contiene el único relato que se conserva sobre el extenso pasado, anterior a su caída, de Númenor, y todo lo que se sabe sobre los Cinco Magos, las Palantíri y la leyenda de Amroth.");
				libro.setPaginas(480);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(91);
				libro.setFechaPublicacion(LocalDate.of(1980, 11, 2));
				libro.setUrlFoto("https://pdlibrosarg.cdnstatics2.com/usuaris/libros/thumbs/6248e816-eb60-498f-b7fb-8bddf832951d/d_360_620/376935_portada_cuentos-inconclusos-edicion-revisada_j-r-r-tolkien_202208021319.webp");
				libro.setPrecio(34.99);
				libro.setCategoria(Set.of(Categoria.fantastico));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorTolkien);
				libroRepository.save(libro);
			}
			//236
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"Beren y Lúthien", "Primera edición", autorTolkien, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("Beren y Lúthien");
				libro.setDescripcion("El relato de Beren y Lúthien era, o se convirtió, en un elemento esencial en la evolución de El Silmarillion, los mitos y leyendas de la Primera Edad del Mundo concebidos por J.R.R. Tolkien. El autor escribió el relato durante el año siguiente a su regreso de Francia y de la batalla del Somme a finales de 1916. Esencial para la historia y sin haber sido nunca alterado, el elemento central del relato es el destino que ensombrece el amor de Beren y Lúthien, dado que Beren era un hombre mortal y Lúthien una elfa inmortal, cuyo padre, un gran señor elfo, en clara oposición a Beren, impuso a éste una tarea imposible que debía llevar a cabo si quería desposar a Lúthien. Éste es el núcleo de la leyenda, que acaba conduciendo al absolutamente heroico intento de Beren y Lúthien de robarle un Silmaril al más malvado de todos los seres: Melkor, también llamado Morgoth, el Enemigo Oscuro.");
				libro.setPaginas(304);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(93);
				libro.setFechaPublicacion(LocalDate.of(2017, 6, 1));
				libro.setUrlFoto("https://pdlibrosarg.cdnstatics2.com/usuaris/libros/thumbs/726c5d49-3140-4b6a-955e-2c315660e00b/d_360_620/portada_beren-y-luthien_j-r-r-tolkien_201901241705.webp");
				libro.setPrecio(32.99);
				libro.setCategoria(Set.of(Categoria.fantastico));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorTolkien);
				libroRepository.save(libro);
			}
			//237
			if (libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
					"La Caída de Gondolin", "Primera edición", autorTolkien, editorialPlaneta).isEmpty()) {
				Libro libro = new Libro();
				libro.setTitulo("La Caída de Gondolin");
				libro.setDescripcion("En el Cuento de La Caída de Gondolin chocan dos de los principales poderes del mundo. Por un lado está Morgoth, el mal más absoluto, que está al mando de un enorme poder militar que controla desde su fortaleza en Angband. En su oposición está Ulmo, el segundo Vala más poderoso. Trabaja secretamente en la Tierra Media para apoyar a los Noldor, el grupo de elfos entre los que se contaban Húrin y Túrin Turambar. En el centro de este conflicto entre deidades se encuentra la ciudad de Gondolin, bella pero escondida más allá de toda posibilidad de ser descubierta. Fue construida y habitada por elfos Noldor que se rebelaron contra el poder divino y huyeron desde Valinor, la tierra de los dioses, a la Tierra Media. Turgon, el rey de Gondolin, es el principal objeto tanto del odio como el miedo de Morgoth, quien trata en vano de descubrir la ciudad, escondida como por arte de magia. En este mundo entra Tuor, el primo de Túrin, como instrumento para hacer cumplir los planes de Ulmo. Guiado por el dios desde la invisibilidad, Tuor parte de la tierra donde nació y emprende un peligroso viaje en busca de Gondolin.");
				libro.setPaginas(304);
				libro.setEdicion("Primera edición");
				libro.setCalificacion(92);
				libro.setFechaPublicacion(LocalDate.of(2018, 8, 30));
				libro.setUrlFoto("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSS89V-qBYS3Ed-b8kA2dXBXL2ovhUp0nX1Pg&s");
				libro.setPrecio(33.99);
				libro.setCategoria(Set.of(Categoria.fantastico));
				libro.setEditorial(editorialPlaneta);
				libro.setAutor(autorTolkien);
				libroRepository.save(libro);
			}




			// ------------------------
			// Inicialización de Publicaciones
			// ------------------------
			Optional<Usuario> usuarioLectorUno = usuarioRepository.findByUsername("lector@gmail.com");
			Optional<Usuario> usuarioLectorDos = usuarioRepository.findByUsername("lector2@gmail.com");
			Optional<Usuario> usuarioLectorTres = usuarioRepository.findByUsername("jlopez@gmail.com");
			Optional<Usuario> usuarioLectorCuatro = usuarioRepository.findByUsername("paulamartinez@gmail.com");
			Optional<Usuario> usuarioLectorCinco = usuarioRepository.findByUsername("jrolando@gmail.com");


			Optional<Libro> harryPotter = libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
				"Harry Potter y la piedra filosofal", "Primera edición", autorJK, editorialHP);

			Optional<Libro> orgullo = libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
				"Orgullo y prejuicio", "Primera edición", autorAusten, editorialChapman);

            Optional<Libro> soledad = libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
				"Cien años de soledad", "Primera edición", autorGarciaMarquez, editorialPlaneta);

            Optional<Libro> orientExpress = libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
				"Asesinato en el Orient Express", "Primera edición", autorChristie, editorialPlaneta);

            Optional<Libro> codigo = libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
				"El código Da Vinci", "Primera edición", autorBrown, editorialDoubleday);

            Optional<Libro> it = libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
				"It", "Primera edición", autorKing, editorialViking);

            Optional<Libro> dune = libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
				"Dune", "Primera edición", autorHerbert, editorialChilton);

			Optional<Libro> hobbit = libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
				"El Hobbit", "Primera edición", autorTolkien, editorialPlaneta);

			Optional<Libro>  heroeOlimpo = libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
				"El último héroe del Olimpo", "Primera edición", autorRiordan, editorialPenguin);

			Optional<Libro>  durmientes = libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
				"Bellas durmientes", "Primera edición", autorKing, editorialPenguin);

			Optional<Libro>  sofia = libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
				"La magia de ser Sofía", "Primera edición", autorBenavent, editorialPenguin);

			Optional<Libro>  sinsajo = libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
				"Sinsajo", "Primera edición", autorCollins, editorialMolino);

			Optional<Libro>  amorSombra = libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
				"De amor y sombra", "Primera edición", autorAllende, editorialSudamericana);

			Optional<Libro>  constelaciones = libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
				"El chico que dibujaba constelaciones", "Primera edición", autorKellen, editorialPlaneta);

			Optional<Libro> lluvia = libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
				"Blackwater: lluvia", "Primera edición", autorMcdowell, editorialTitania);

			Optional<Libro> heartless = libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
				"Heartless", "Primera edición", autorMeyer, editorialPenguin);

			Optional<Libro> casa = libroRepository.findByTituloIgnoreCaseAndEdicionIgnoreCaseAndAutorAndEditorial(
				"Blackwater: la casa", "Primera edición", autorMcdowell, editorialTitania);

            //publicaciones
			if (usuarioLectorUno.isPresent() && harryPotter.isPresent()) {
				Usuario usuario = usuarioLectorUno.get();
				Libro libro = harryPotter.get();

				if (publicacionRepository.findByUsuarioAndLibroAndFechaCreacion(usuario, libro, LocalDate.now()).isEmpty()) {
                    Publicacion publicacion = new Publicacion();
                    publicacion.setUsuario(usuario);
                    publicacion.setLibro(libro);
                    publicacion.setFechaCreacion(LocalDate.now());
                    publicacion.setDescripcion("Ejemplar de Harry Potter y la piedra filosofal disponible para préstamo, libro en excelente estado.");
                    publicacion.setLimiteDias(20);
                    publicacion.setDetallesEstadoLibro("Nada");
                    publicacion.setEstadoPublicacion(Estado.Disponible);
                    publicacionRepository.save(publicacion);
				}
			}

            if (usuarioLectorDos.isPresent() && it.isPresent()) {
				Usuario usuario = usuarioLectorDos.get();
				Libro libro = it.get();

				if (publicacionRepository.findByUsuarioAndLibroAndFechaCreacion(usuario, libro, LocalDate.now()).isEmpty()) {
                    Publicacion publicacion = new Publicacion();
                    publicacion.setUsuario(usuario);
                    publicacion.setLibro(libro);
                    publicacion.setFechaCreacion(LocalDate.now());
                    publicacion.setDescripcion("It, un libro de Stephen King, en buenas condiciones, señalado con post-its.");
                    publicacion.setLimiteDias(14);
                    publicacion.setDetallesEstadoLibro("Nada");
                    publicacion.setEstadoPublicacion(Estado.Disponible);
                    publicacionRepository.save(publicacion);
				}
			}

            if (usuarioLectorCuatro.isPresent() && orgullo.isPresent()) {
				Usuario usuario = usuarioLectorCuatro.get();
				Libro libro = orgullo.get();

				if (publicacionRepository.findByUsuarioAndLibroAndFechaCreacion(usuario, libro, LocalDate.now()).isEmpty()) {
                    Publicacion publicacion = new Publicacion();
                    publicacion.setUsuario(usuario);
                    publicacion.setLibro(libro);
                    publicacion.setFechaCreacion(LocalDate.now());
                    publicacion.setDescripcion("Novela clasica de Jane Austen, Orgullo y prejuicio. Libro en muy buenas condiciones, con anotaciones al margen de las hojas.");
                    publicacion.setLimiteDias(25);
                    publicacion.setDetallesEstadoLibro("Nada");
                    publicacion.setEstadoPublicacion(Estado.Disponible);
                    publicacionRepository.save(publicacion);
				}
			}

            if (usuarioLectorTres.isPresent() && soledad.isPresent()) {
				Usuario usuario = usuarioLectorTres.get();
				Libro libro = soledad.get();

				if (publicacionRepository.findByUsuarioAndLibroAndFechaCreacion(usuario, libro, LocalDate.now()).isEmpty()) {
                    Publicacion publicacion = new Publicacion();
                    publicacion.setUsuario(usuario);
                    publicacion.setLibro(libro);
                    publicacion.setFechaCreacion(LocalDate.now());
                    publicacion.setDescripcion("Cien años de soledad, libro leido una sola vez. Sin marcas o señalaciones, como nuevo.");
                    publicacion.setLimiteDias(30);
                    publicacion.setDetallesEstadoLibro("Nada");
                    publicacion.setEstadoPublicacion(Estado.Disponible);
                    publicacionRepository.save(publicacion);
				}
			}

            if (usuarioLectorUno.isPresent() && orientExpress.isPresent()) {
				Usuario usuario = usuarioLectorUno.get();
				Libro libro = orientExpress.get();

				if (publicacionRepository.findByUsuarioAndLibroAndFechaCreacion(usuario, libro, LocalDate.now()).isEmpty()) {
                    Publicacion publicacion = new Publicacion();
                    publicacion.setUsuario(usuario);
                    publicacion.setLibro(libro);
                    publicacion.setFechaCreacion(LocalDate.now());
                    publicacion.setDescripcion("Ejemplar de Asesinato en el Orient Express disponible para préstamo, excelente condiciones (sin marcar).");
                    publicacion.setLimiteDias(23);
                    publicacion.setDetallesEstadoLibro("Nada");
                    publicacion.setEstadoPublicacion(Estado.Disponible);
                    publicacionRepository.save(publicacion);
				}
			}

            if (usuarioLectorDos.isPresent() && codigo.isPresent()) {
				Usuario usuario = usuarioLectorDos.get();
				Libro libro = codigo.get();

				if (publicacionRepository.findByUsuarioAndLibroAndFechaCreacion(usuario, libro, LocalDate.now()).isEmpty()) {
                    Publicacion publicacion = new Publicacion();
                    publicacion.setUsuario(usuario);
                    publicacion.setLibro(libro);
                    publicacion.setFechaCreacion(LocalDate.now());
                    publicacion.setDescripcion("El código Da Vinci, libro en muy buen estado.");
                    publicacion.setLimiteDias(18);
                    publicacion.setDetallesEstadoLibro("Nada");
                    publicacion.setEstadoPublicacion(Estado.Disponible);
                    publicacionRepository.save(publicacion);
				}
			}

            if (usuarioLectorDos.isPresent() && dune.isPresent()) {
				Usuario usuario = usuarioLectorDos.get();
				Libro libro = dune.get();

				if (publicacionRepository.findByUsuarioAndLibroAndFechaCreacion(usuario, libro, LocalDate.now()).isEmpty()) {
                    Publicacion publicacion = new Publicacion();
                    publicacion.setUsuario(usuario);
                    publicacion.setLibro(libro);
                    publicacion.setFechaCreacion(LocalDate.now());
                    publicacion.setDescripcion("Ejemplar de Dune, primer libro de la saga; disponible para préstamo, excelente estado.");
                    publicacion.setLimiteDias(14);
                    publicacion.setDetallesEstadoLibro("Nada");
                    publicacion.setEstadoPublicacion(Estado.Disponible);
                    publicacionRepository.save(publicacion);
				}
			}

			if (usuarioLectorDos.isPresent() && heroeOlimpo.isPresent()) {
				Usuario usuario = usuarioLectorDos.get();
				Libro libro = heroeOlimpo.get();

				if (publicacionRepository.findByUsuarioAndLibroAndFechaCreacion(usuario, libro, LocalDate.now()).isEmpty()) {
                    Publicacion publicacion = new Publicacion();
                    publicacion.setUsuario(usuario);
                    publicacion.setLibro(libro);
                    publicacion.setFechaCreacion(LocalDate.now());
                    publicacion.setDescripcion("Libro en excelente estado, sin marcas ni hojas dobladas. La tapa y el lomo están intactos, con muy poco desgaste por uso. Ideal para disfrutar de una buena lectura como nuevo.");
                    publicacion.setLimiteDias(22);
                    publicacion.setDetallesEstadoLibro("Nada");
                    publicacion.setEstadoPublicacion(Estado.Disponible);
                    publicacionRepository.save(publicacion);
				}
			}

			if (usuarioLectorTres.isPresent() && sofia.isPresent()) {
				Usuario usuario = usuarioLectorTres.get();
				Libro libro = sofia.get();

				if (publicacionRepository.findByUsuarioAndLibroAndFechaCreacion(usuario, libro, LocalDate.now()).isEmpty()) {
                    Publicacion publicacion = new Publicacion();
                    publicacion.setUsuario(usuario);
                    publicacion.setLibro(libro);
                    publicacion.setFechaCreacion(LocalDate.now());
                    publicacion.setDescripcion("El libro está en buen estado general, con algún que otro detalle por el uso (como una esquina doblada o una marca leve en la tapa).");
                    publicacion.setLimiteDias(17);
                    publicacion.setDetallesEstadoLibro("Nada");
                    publicacion.setEstadoPublicacion(Estado.Disponible);
                    publicacionRepository.save(publicacion);
				}
			}

			if (usuarioLectorCuatro.isPresent() && durmientes.isPresent()) {
				Usuario usuario = usuarioLectorCuatro.get();
				Libro libro = durmientes.get();

				if (publicacionRepository.findByUsuarioAndLibroAndFechaCreacion(usuario, libro, LocalDate.now()).isEmpty()) {
                    Publicacion publicacion = new Publicacion();
                    publicacion.setUsuario(usuario);
                    publicacion.setLibro(libro);
                    publicacion.setFechaCreacion(LocalDate.now());
                    publicacion.setDescripcion("El libro está en buen estado. Todas las páginas están completas y se puede leer sin problema, a pesar de algunas anotaciones en los margenes.");
                    publicacion.setLimiteDias(24);
                    publicacion.setDetallesEstadoLibro("Nada");
                    publicacion.setEstadoPublicacion(Estado.Disponible);
                    publicacionRepository.save(publicacion);
				}
			}

			if (usuarioLectorUno.isPresent() && amorSombra.isPresent()) {
				Usuario usuario = usuarioLectorUno.get();
				Libro libro = amorSombra.get();

				if (publicacionRepository.findByUsuarioAndLibroAndFechaCreacion(usuario, libro, LocalDate.now()).isEmpty()) {
                    Publicacion publicacion = new Publicacion();
                    publicacion.setUsuario(usuario);
                    publicacion.setLibro(libro);
                    publicacion.setFechaCreacion(LocalDate.now());
                    publicacion.setDescripcion("El libro tiene algunas marquitas del uso, pero nada grave. Está completo y en buen estado para leerlo sin problema.");
                    publicacion.setLimiteDias(30);
                    publicacion.setDetallesEstadoLibro("Nada");
                    publicacion.setEstadoPublicacion(Estado.Disponible);
                    publicacionRepository.save(publicacion);
				}
			}

			if (usuarioLectorDos.isPresent() && sinsajo.isPresent()) {
				Usuario usuario = usuarioLectorDos.get();
				Libro libro = sinsajo.get();

				if (publicacionRepository.findByUsuarioAndLibroAndFechaCreacion(usuario, libro, LocalDate.now()).isEmpty()) {
                    Publicacion publicacion = new Publicacion();
                    publicacion.setUsuario(usuario);
                    publicacion.setLibro(libro);
                    publicacion.setFechaCreacion(LocalDate.now());
                    publicacion.setDescripcion("Ejemplar en buen estado general, con leves signos de uso propios del paso del tiempo. Conserva todas sus páginas íntegras y es totalmente legible.");
                    publicacion.setLimiteDias(27);
                    publicacion.setDetallesEstadoLibro("Nada");
                    publicacion.setEstadoPublicacion(Estado.Disponible);
                    publicacionRepository.save(publicacion);
				}
			}

			if (usuarioLectorUno.isPresent() && hobbit.isPresent()) {
				Usuario usuario = usuarioLectorUno.get();
				Libro libro = hobbit.get();

				if (publicacionRepository.findByUsuarioAndLibroAndFechaCreacion(usuario, libro, LocalDate.now()).isEmpty()) {
                    Publicacion publicacion = new Publicacion();
                    publicacion.setUsuario(usuario);
                    publicacion.setLibro(libro);
                    publicacion.setFechaCreacion(LocalDate.now());
                    publicacion.setDescripcion("Libro en buen estado, lectura recomendable!");
                    publicacion.setLimiteDias(24);
                    publicacion.setDetallesEstadoLibro("Nada");
                    publicacion.setEstadoPublicacion(Estado.Disponible);
                    publicacionRepository.save(publicacion);
				}
			}

			if (usuarioLectorCinco.isPresent() && casa.isPresent()) {
				Usuario usuario = usuarioLectorCinco.get();
				Libro libro = casa.get();

				if (publicacionRepository.findByUsuarioAndLibroAndFechaCreacion(usuario, libro, LocalDate.now()).isEmpty()) {
                    Publicacion publicacion = new Publicacion();
                    publicacion.setUsuario(usuario);
                    publicacion.setLibro(libro);
                    publicacion.setFechaCreacion(LocalDate.now());
                    publicacion.setDescripcion("El libro es excelente y esta en muy buen estado.");
                    publicacion.setLimiteDias(17);
                    publicacion.setDetallesEstadoLibro("Nada");
                    publicacion.setEstadoPublicacion(Estado.Disponible);
                    publicacionRepository.save(publicacion);
				}
			}

			if (usuarioLectorCinco.isPresent() && lluvia.isPresent()) {
				Usuario usuario = usuarioLectorCinco.get();
				Libro libro = lluvia.get();

				if (publicacionRepository.findByUsuarioAndLibroAndFechaCreacion(usuario, libro, LocalDate.now()).isEmpty()) {
                    Publicacion publicacion = new Publicacion();
                    publicacion.setUsuario(usuario);
                    publicacion.setLibro(libro);
                    publicacion.setFechaCreacion(LocalDate.now());
                    publicacion.setDescripcion("Libro en muy buenas condiciones a pesar de las veces que fue leido, no presenta anotaciones pero si hay algunas hojas con puntas dobladas.");
                    publicacion.setLimiteDias(24);
                    publicacion.setDetallesEstadoLibro("Nada");
                    publicacion.setEstadoPublicacion(Estado.Disponible);
                    publicacionRepository.save(publicacion);
				}
			}

			if (usuarioLectorCinco.isPresent() && heartless.isPresent()) {
				Usuario usuario = usuarioLectorCinco.get();
				Libro libro = heartless.get();

				if (publicacionRepository.findByUsuarioAndLibroAndFechaCreacion(usuario, libro, LocalDate.now()).isEmpty()) {
                    Publicacion publicacion = new Publicacion();
                    publicacion.setUsuario(usuario);
                    publicacion.setLibro(libro);
                    publicacion.setFechaCreacion(LocalDate.now());
                    publicacion.setDescripcion("Ejemplar en excelente estado. Conserva todas sus páginas íntegras y es totalmente legible, señalado con post-its.");
                    publicacion.setLimiteDias(24);
                    publicacion.setDetallesEstadoLibro("Nada");
                    publicacion.setEstadoPublicacion(Estado.Disponible);
                    publicacionRepository.save(publicacion);
				}
			}

			if (usuarioLectorCinco.isPresent() && constelaciones.isPresent()) {
				Usuario usuario = usuarioLectorCinco.get();
				Libro libro = constelaciones.get();

				if (publicacionRepository.findByUsuarioAndLibroAndFechaCreacion(usuario, libro, LocalDate.now()).isEmpty()) {
                    Publicacion publicacion = new Publicacion();
                    publicacion.setUsuario(usuario);
                    publicacion.setLibro(libro);
                    publicacion.setFechaCreacion(LocalDate.now());
                    publicacion.setDescripcion("Copia de El chico que dibujaba constelaciones, en perfecto estado. Señalizado con post-its, los cuales estan categorizados por color.");
                    publicacion.setLimiteDias(20);
                    publicacion.setDetallesEstadoLibro("Nada");
                    publicacion.setEstadoPublicacion(Estado.Disponible);
                    publicacionRepository.save(publicacion);
				}
			}

			if (usuarioLectorCinco.isPresent() && amorSombra.isPresent()) {
				Usuario usuario = usuarioLectorCinco.get();
				Libro libro = amorSombra.get();

				if (publicacionRepository.findByUsuarioAndLibroAndFechaCreacion(usuario, libro, LocalDate.now()).isEmpty()) {
                    Publicacion publicacion = new Publicacion();
                    publicacion.setUsuario(usuario);
                    publicacion.setLibro(libro);
                    publicacion.setFechaCreacion(LocalDate.now());
                    publicacion.setDescripcion("Ejemplar relativamente en buen estado. Una parte de las primeras hojas estan algo arrugadas debido a que tuve un incidente con agua.");
                    publicacion.setLimiteDias(20);
                    publicacion.setDetallesEstadoLibro("Nada");
                    publicacion.setEstadoPublicacion(Estado.Disponible);
                    publicacionRepository.save(publicacion);
				}
			}


			// Prestamos prestados 

			Optional<Publicacion> publicacionHobbit = publicacionRepository.findByLibroTituloIgnoreCase("El Hobbit");
			Optional<Publicacion> publicacionSofia = publicacionRepository.findByLibroTituloIgnoreCase("La magia de ser Sofía");
			Optional<Publicacion> publicacionSinsajo = publicacionRepository.findByLibroTituloIgnoreCase("Sinsajo");
			Optional<Publicacion> publicacionAmorSombra = publicacionRepository.findByLibroTituloIgnoreCase("De amor y sombra");
			Optional<Publicacion> publicacionConstelaciones = publicacionRepository.findByLibroTituloIgnoreCase("El chicho que dibujaba constelaciones");


			if (usuarioLectorDos.isPresent() && publicacionHobbit.isPresent()) {
				Usuario usuarioSolicitante = usuarioLectorDos.get();
				Publicacion publicacion = publicacionHobbit.get();

				if (registroPrestamoRepository.findByPublicacionAndUsuario(publicacion, usuarioSolicitante).isEmpty()) {
					RegistroPrestamo prestamo = new RegistroPrestamo();
					prestamo.setPublicacion(publicacion);
					prestamo.setUsuario(usuarioSolicitante);
					prestamo.setFechaPrestamo(LocalDate.now().minusDays(3));
					prestamo.setFechaDevolucion(LocalDate.now().plusDays(10));
					registroPrestamoRepository.save(prestamo);
				}
			}

			if (usuarioLectorDos.isPresent() && publicacionSofia.isPresent()) {
				Usuario usuarioSolicitante = usuarioLectorDos.get();
				Publicacion publicacion = publicacionSofia.get();

				if (registroPrestamoRepository.findByPublicacionAndUsuario(publicacion, usuarioSolicitante).isEmpty()) {
					RegistroPrestamo prestamo = new RegistroPrestamo();
					prestamo.setPublicacion(publicacion);
					prestamo.setUsuario(usuarioSolicitante);
					prestamo.setFechaPrestamo(LocalDate.now().minusDays(19));
					prestamo.setFechaDevolucion(LocalDate.now().plusDays(-2));
					registroPrestamoRepository.save(prestamo);
				}
			}

			if (usuarioLectorCinco.isPresent() && publicacionSinsajo.isPresent()) {
				Usuario usuarioSolicitante = usuarioLectorCinco.get();
				Publicacion publicacion = publicacionSinsajo.get();

				if (registroPrestamoRepository.findByPublicacionAndUsuario(publicacion, usuarioSolicitante).isEmpty()) {
					RegistroPrestamo prestamo = new RegistroPrestamo();
					prestamo.setPublicacion(publicacion);
					prestamo.setUsuario(usuarioSolicitante);
					prestamo.setFechaPrestamo(LocalDate.now().minusDays(40));
					prestamo.setFechaDevolucion(LocalDate.now().plusDays(-13));
					registroPrestamoRepository.save(prestamo);
				}
			}

			if (usuarioLectorDos.isPresent() && publicacionAmorSombra.isPresent()) {
				Usuario usuarioSolicitante = usuarioLectorDos.get();
				Publicacion publicacion = publicacionAmorSombra.get();

				if (registroPrestamoRepository.findByPublicacionAndUsuario(publicacion, usuarioSolicitante).isEmpty()) {
					RegistroPrestamo prestamo = new RegistroPrestamo();
					prestamo.setPublicacion(publicacion);
					prestamo.setUsuario(usuarioSolicitante);
					prestamo.setFechaPrestamo(LocalDate.now().minusDays(40));
					prestamo.setFechaDevolucion(LocalDate.now().plusDays(-20));
					registroPrestamoRepository.save(prestamo);
				}
			}

			if (usuarioLectorUno.isPresent() && publicacionConstelaciones.isPresent()) {
				Usuario usuarioSolicitante = usuarioLectorUno.get();
				Publicacion publicacion = publicacionConstelaciones.get();

				if (registroPrestamoRepository.findByPublicacionAndUsuario(publicacion, usuarioSolicitante).isEmpty()) {
					RegistroPrestamo prestamo = new RegistroPrestamo();
					prestamo.setPublicacion(publicacion);
					prestamo.setUsuario(usuarioSolicitante);
					prestamo.setFechaPrestamo(LocalDate.now().minusDays(30));
					prestamo.setFechaDevolucion(LocalDate.now().plusDays(-10));
					registroPrestamoRepository.save(prestamo);
				}
			}



			Optional<Usuario> optUsuarioCinco = usuarioRepository.findByUsername("jrolando@gmail.com");

			if (optUsuarioCinco.isPresent()) {
				Usuario usuarioCinco = optUsuarioCinco.get();
				Biblioteca bibliotecaCinco = usuarioCinco.getBiblioteca();
				List<String> titulosLibros = List.of(
					"El chico que dibujaba constelaciones",
					"Heartless",
					"Blackwater: Lluvia",
					"Blackwater: La casa",
					"Llévame a cualquier lugar",
					"Volver a empezar", 
					"Romper el círculo",
					"Nosotros en la luna",
					"Donde todo brilla",
					"Cress",
					"Winter",
					"Scarlet"
				);
				for (String titulo : titulosLibros) {
					System.out.println("Buscando libro: " + titulo); // <--- log para verificar

					libroRepository.findByTituloIgnoreCaseAndEdicion(
							titulo, "Primera edición"
					).ifPresentOrElse(libro -> {
						System.out.println("Libro encontrado: " + libro.getTitulo()); // <--- log si lo encuentra

						boolean yaExiste = libroUsuarioRepository.existsByBibliotecaAndLibro(bibliotecaCinco, libro);
						if (!yaExiste) {
							LibroUsuario lu = new LibroUsuario();
							lu.setBiblioteca(bibliotecaCinco);
							lu.setLibro(libro);
							lu.setPaginaActual(0);
							lu.setEstadoLectura(EstadoLectura.pendiente);
							lu.setPuntuacion(0);
							libroUsuarioRepository.save(lu);
							System.out.println("Libro agregado a la biblioteca: " + libro.getTitulo());
						} else {
							System.out.println("El libro ya existía en la biblioteca: " + libro.getTitulo());
						}
					}, () -> {
						System.out.println("Libro NO encontrado en la base de datos: " + titulo); // <--- log si no lo encuentra
					});
				}
			}

			if (optUsuarioCinco.isPresent()) {
				Usuario usuarioCinco = optUsuarioCinco.get();
				Biblioteca bibliotecaCinco = usuarioCinco.getBiblioteca();
				List<String> titulosLibros = List.of(
					"La promesa de Julieta",
					"Cujo",
					"El resplandor",
					"Divergente",
					"Insurgente",
					"Leal",
					"Cuatro",
					"El cuaderno de Maya",
					"Testigo de cargo",
					"Después de ti",
					"Yo antes de ti",
					"Sigue lloviendo",
					"Sigo siendo yo"
				);
				for (String titulo : titulosLibros) {
					libroRepository.findByTituloIgnoreCaseAndEdicion(
							titulo, "Primera edición"
					).ifPresent(libro -> {
						boolean yaExiste = libroUsuarioRepository.existsByBibliotecaAndLibro(bibliotecaCinco, libro);
						if (!yaExiste) {
							LibroUsuario lu = new LibroUsuario();
							lu.setBiblioteca(bibliotecaCinco);
							lu.setLibro(libro);
							lu.setPaginaActual(0);
							lu.setEstadoLectura(EstadoLectura.pendiente);
							lu.setPuntuacion(0);
							libroUsuarioRepository.save(lu);
						}
					});
				}

			} else {
				System.out.println("Usuario no encontrado. Primero creá el usuario.");
			}

						if (optUsuarioCinco.isPresent()) {
				Usuario usuarioCinco = optUsuarioCinco.get();
				Biblioteca bibliotecaCinco = usuarioCinco.getBiblioteca();
				List<String> titulosLibros = List.of(
					"Emma",
					"Distancia de rescate"
				);
				for (String titulo : titulosLibros) {
					libroRepository.findByTituloIgnoreCaseAndEdicion(
							titulo, "Primera edición"
					).ifPresent(libro -> {
						boolean yaExiste = libroUsuarioRepository.existsByBibliotecaAndLibro(bibliotecaCinco, libro);
						if (!yaExiste) {
							LibroUsuario lu = new LibroUsuario();
							lu.setBiblioteca(bibliotecaCinco);
							lu.setLibro(libro);
							lu.setPaginaActual(175);
							lu.setEstadoLectura(EstadoLectura.leyendo);
							lu.setPuntuacion(0);
							libroUsuarioRepository.save(lu);
						}
					});
				}

			} else {
				System.out.println("Usuario no encontrado. Primero creá el usuario.");
			}

        };
    }
}