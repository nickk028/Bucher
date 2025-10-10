package ar.edu.huergo.vectorial.calidad.bucher.controller.publication;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.huergo.vectorial.calidad.bucher.dto.publication.PublicacionBasicDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.publication.PublicacionCreateDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.publication.PublicacionResponseDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.publication.PublicacionUpdateDTO;
import ar.edu.huergo.vectorial.calidad.bucher.entity.book.Categoria;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.Estado;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.Publicacion;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.RegistroPrestamo;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import ar.edu.huergo.vectorial.calidad.bucher.mapper.publication.PublicacionMapper;
import ar.edu.huergo.vectorial.calidad.bucher.service.publication.PublicacionService;
import ar.edu.huergo.vectorial.calidad.bucher.service.publication.RegistroPrestamoService;
import ar.edu.huergo.vectorial.calidad.bucher.service.security.UsuarioService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/publicacion")
public class PublicacionController {

    @Autowired
    private PublicacionService publicacionService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PublicacionMapper publicacionMapper;

    @Autowired
    private RegistroPrestamoService registroPrestamoService;

    /**
     * Obtiene todas las publicaciones
     * @return Un conjunto de todas las publicaciones
     */
    @GetMapping
    public ResponseEntity<List<PublicacionBasicDTO>> obtenerTodasLasPublicaciones() {
        return ResponseEntity.ok(
            publicacionMapper.toBasicDTOList(publicacionService.obtenerTodasLasPublicaciones()));
    }

    /**
     * Obtiene una publicacion por id
     * @param El id de la publiacion
     * @return La publiacion buscada
     */
    @GetMapping("/{id}")
    public ResponseEntity<PublicacionResponseDTO> obtenerPublicacion(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
            publicacionMapper.toDTO(publicacionService.obtenerPublicacionPorId(id)));
    }

    /**
     * Obtiene las publicaciones del usuario autenticado
     * @param usuarioAutenticado El usuario autenticado
     * @return Las publicaciones del usuario autenticado
     */
    @GetMapping("/propias")
    public ResponseEntity<List<PublicacionResponseDTO>> obtenerPublicacionesPorUsuario(@AuthenticationPrincipal UserDetails usuarioAutenticado) {
        Usuario usuario = usuarioService.obtenerUsuarioPorNombre(usuarioAutenticado.getUsername());

        return ResponseEntity.ok(
            publicacionMapper.toDTOList(publicacionService.obtenerPublicacionesPorUsuario(usuario)));
    }

    /**
     * Obtiene las publicaciones de una categoria
     * @param categoria Categoria buscada
     * @return Las publicaciones de una categoria
     */
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<PublicacionBasicDTO>> obtenerPublicacionesPorCategoria(@PathVariable("categoria") Categoria categoria) {
        return ResponseEntity.ok(publicacionMapper.toBasicDTOList(publicacionService.obtenerPublicacionPorCategoria(categoria)));
    }

    /**
    * Crea una nueva publicación
    * @param publicacionCreateDTO El DTO con los datos de la publicación a crear
    * @param usuarioAutenticado El usuario autenticado
    * @return La publicación creada
    */
    @PostMapping("/crear")
    public ResponseEntity<PublicacionResponseDTO> crearPublicacion(@Valid @RequestBody PublicacionCreateDTO publicacionCreateDTO,
    @AuthenticationPrincipal UserDetails usuarioAutenticado) {

        Publicacion publicacionNueva = publicacionMapper.toEntity(publicacionCreateDTO);
        Publicacion publicacionCreada = publicacionService.crearPublicacion(publicacionNueva, publicacionCreateDTO.getTitulo(), usuarioAutenticado.getUsername());

        return ResponseEntity.ok(publicacionMapper.toDTO(publicacionCreada));
    }

    /**
     * Elimina una publicación por su ID (solo el usuario que la creo o un admin)
     * @param id El ID de la publicación a eliminar
     * @param usuarioAutenticado El usuario autenticado
     * @return OK (200) si se eliminó correctamente, Not Found (404) si no se encontró o no tiene permisos
     */
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarPublicacion(@PathVariable("id") Long id,
    @AuthenticationPrincipal UserDetails usuarioAutenticado) {

        Usuario usuario = usuarioService.obtenerUsuarioPorNombre(usuarioAutenticado.getUsername());
        if (UsuarioService.hasRol(usuario, "ADMIN")) {
            publicacionService.eliminarPublicacion(id);
            return ResponseEntity.ok().body("Eliminado correctamente");
        }
        if (publicacionService.verificarPublicacionUsuario(usuario,id)) {
            publicacionService.eliminarPublicacion(id);
            return ResponseEntity.ok().body("Eliminado correctamente");
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Modifica una publicación (solo el usuario que la creo o un admin)
     * @param id El ID de la publicación a modificar
     * @param publicacionDTO El DTO con los datos a modificar
     * @param usuarioAutenticado El usuario autenticado
     * @return La publicación modificada
     */
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<PublicacionResponseDTO> actualizarPublicacion(@PathVariable("id") Long id,
    @Valid @RequestBody PublicacionUpdateDTO publicacionDTO,
    @AuthenticationPrincipal UserDetails usuarioAutenticado) {

        Usuario usuario = usuarioService.obtenerUsuarioPorNombre(usuarioAutenticado.getUsername());
        Publicacion publicacion = publicacionMapper.toEntity(publicacionDTO);
        Publicacion actualizada;

        if (UsuarioService.hasRol(usuario,"ADMIN")) {
            actualizada = publicacionService.modificarPublicacionAdmin(id,publicacion);
        } else {
            actualizada = publicacionService.modificarPublicacionUsuario(id, publicacion, usuario);
        }

        return ResponseEntity.ok(publicacionMapper.toDTO(actualizada));
    }

    /**
     * Permite a un usuario pedir prestada una publicación
     * @param id El ID de la publicación a pedir prestada
     * @param usuarioAutenticado El usuario autenticado
     * @return OK (200) o Unprocessable Entity (422)
     */
    @PostMapping("/prestamo/{id}")
    public ResponseEntity<String> pedirPrestadoPublicacion(@PathVariable("id") Long id,
    @AuthenticationPrincipal UserDetails usuarioAutenticado) {
        Usuario usuario = usuarioService.obtenerUsuarioPorNombre(usuarioAutenticado.getUsername());
        Publicacion publicacion = publicacionService.obtenerPublicacionPorId(id);

        if (publicacion.getUsuario().equals(usuario)) {
            return ResponseEntity.unprocessableEntity().body("No se puede pedir un préstamo de una publicación propia");
        }
        if (!publicacion.getEstadoPublicacion().equals(Estado.Disponible)   ) {
            return ResponseEntity.unprocessableEntity().body("La publicación no esta disponible");
        }

        publicacionService.modificarEstadoPublicacion(publicacion, Estado.Prestado);
        registroPrestamoService.crearRegistroUsuario(usuario, publicacion);
        return ResponseEntity.ok().body("Préstamo creado");
    }

    /**
     * Permite a un usuario devolver una publicación
     * @param id El ID de la publicación a devolver
     * @return OK (200) o Unprocessable Entity (422)
     */
    @PostMapping("/devolucion/{id}")
    public ResponseEntity<String> devolverPublicacion(@PathVariable("id") Long id) {
        Publicacion publicacion = publicacionService.obtenerPublicacionPorId(id);
        
        if (publicacion.getEstadoPublicacion() != Estado.Prestado) {
            return ResponseEntity.unprocessableEntity().body("La publicación no está prestada");
        }
        RegistroPrestamo registro = registroPrestamoService.obtenerRegistroPrestamo(publicacion);
        publicacionService.modificarEstadoPublicacion(publicacion, Estado.Disponible);
        registroPrestamoService.marcarRegistroDevolucion(registro);
        return ResponseEntity.ok().body("Devolución realizada");
    }
}