package ar.edu.huergo.vectorial.calidad.bucher.service.book;

import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Autor;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import ar.edu.huergo.vectorial.calidad.bucher.repository.book.AutorRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//Clase que maneja la lógica de Autor
@Service
public class AutorService {

    @Autowired
    AutorRepository autorRepository;

    /**
     * Obtiene todos los autores
     * @return Lista con todos los autores
     */
    public List<Autor> obtenerTodosLosAutores() {
        return autorRepository.findAll();
    }

    /**
     * Obtiene un autor por su ID
     * @param id ID del autor
     * @return Autor encontrado
     * @throws EntityNotFoundException Si el autor no es encontrado
     */
    public Autor obtenerAutorPorId(Long id) throws EntityNotFoundException {
        return autorRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Autor no encontrado"));
    }

    /**
     * Guarda un nuevo autor con la información del usuario
     * @param autor
     * @return El autor creado
     */
    public Autor crearAutor(Autor autor) {
        return autorRepository.save(autor);
    }

    /**
     * Obtiene el autor asociado a un usuario, creándolo con los datos del usuario si no existe
     * @param usuario El usuario del cual obtener el autor
     * @return El autor asociado al usuario
     */
    public Autor obtenerOCrearAutorDeUsuario(Usuario usuario) {
        return autorRepository
            .findByUsuario(usuario)
            .orElseGet(() -> {
                Autor autor = new Autor(
                    generarNombreUnico(usuario.getNickname()),
                    usuario.getDescripcion(),
                    null,
                    usuario.getAvatar().getUrl()
                );
                autor.setUsuario(usuario);
                return autorRepository.save(autor);
            });
    }

    /**
     * Genera un nombre de autor único a partir de un nombre base, agregando un sufijo numérico de dos dígitos si ya existe.
     * @param nombreBase El nombre sugerido para el autor
     * @return Un nombre de autor disponible en la base de datos
     */
    private String generarNombreUnico(String nombreBase) {
        String nombre = nombreBase;
        int contador = 1;
        while (autorRepository.existsByNombreIgnoringCase(nombre)) {
            nombre = nombreBase + String.format("%02d", contador++);
        }
        return nombre;
    }
}