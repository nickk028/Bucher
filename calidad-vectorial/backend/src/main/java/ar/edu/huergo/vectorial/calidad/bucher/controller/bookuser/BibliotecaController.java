package ar.edu.huergo.vectorial.calidad.bucher.controller.bookuser;

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

import jakarta.validation.Valid;

import ar.edu.huergo.vectorial.calidad.bucher.dto.bookuser.LibroUsuarioCreateDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.bookuser.LibroUsuarioResponseDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.bookuser.LibroUsuarioUpdateDTO;
import ar.edu.huergo.vectorial.calidad.bucher.entity.bookuser.Biblioteca;
import ar.edu.huergo.vectorial.calidad.bucher.entity.bookuser.LibroUsuario;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import ar.edu.huergo.vectorial.calidad.bucher.mapper.bookuser.LibroUsuarioMapper;
import ar.edu.huergo.vectorial.calidad.bucher.service.bookuser.BibliotecaService;
import ar.edu.huergo.vectorial.calidad.bucher.service.bookuser.LibroUsuarioService;
import ar.edu.huergo.vectorial.calidad.bucher.service.security.UsuarioService;

@RestController
@RequestMapping("/biblioteca")
public class BibliotecaController {

    @Autowired BibliotecaService bibliotecaService;

    @Autowired LibroUsuarioService libroUsuarioService;

    @Autowired LibroUsuarioMapper libroUsuarioMapper;

    @Autowired UsuarioService usuarioService;

    /**
     * Obtiene la biblioteca del usuario autenticado
     * @param usuarioAutenticado El usuario autenticado
     * @return La biblioteca del usuario autenticado
     */
    @GetMapping
    public ResponseEntity<List<LibroUsuarioResponseDTO>> obtenerBibliotecaPropia(@AuthenticationPrincipal UserDetails usuarioAutenticado) {
        Usuario usuario = usuarioService.obtenerUsuarioPorNombre(usuarioAutenticado.getUsername());

        return ResponseEntity.ok(
            libroUsuarioMapper.toDTOList(libroUsuarioService.extraerLibrosUsuario(bibliotecaService.obtenerBiblioteca(usuario.getId()))));
    }

    /**
    * Obtiene un libroUsuario de la biblioteca del usuario autenticado por su posición
    * @param posicion La posición del libroUsuario en la biblioteca
    * @param usuarioAutenticado El usuario autenticado
    * @return El libroUsuario en la posición indicada
    */
    @GetMapping("/{posicion}")
    public ResponseEntity<LibroUsuarioResponseDTO> obtenerLibroUsuario(@PathVariable("posicion") int posicion,
    @AuthenticationPrincipal UserDetails usuarioAutenticado) {

        Usuario usuario = usuarioService.obtenerUsuarioPorNombre(usuarioAutenticado.getUsername());
        Biblioteca bibliotecaUsuario = bibliotecaService.obtenerBiblioteca(usuario.getId());

        return ResponseEntity.ok(
            libroUsuarioMapper.toDTO(bibliotecaService.obtenerLibroUsuarioPorPosicion(posicion, bibliotecaUsuario)));
    }

    /**
    * Sube un libroUsuario a la biblioteca del usuario autenticado
    * @param libroUsuarioCreateDTO El libroUsuario a subir
    * @param usuarioAutenticado El usuario autenticado
    * @return La biblioteca actualizada del usuario autenticado
    */
    @PostMapping
    public ResponseEntity<List<LibroUsuarioResponseDTO>> subirLibroUsuario(@Valid @RequestBody LibroUsuarioCreateDTO libroUsuarioCreateDTO,
    @AuthenticationPrincipal UserDetails usuarioAutenticado) {

        Usuario usuario = usuarioService.obtenerUsuarioPorNombre(usuarioAutenticado.getUsername());
        Biblioteca bibliotecaUsuario = bibliotecaService.obtenerBiblioteca(usuario.getId());

        LibroUsuario libroUsuarioIngresado = libroUsuarioMapper.toEntity(libroUsuarioCreateDTO);
        Biblioteca bibliotecaActualizada = bibliotecaService.subirLibroUsuario(bibliotecaUsuario, libroUsuarioIngresado, libroUsuarioCreateDTO.getTitulo());

        return ResponseEntity.ok(
            libroUsuarioMapper.toDTOList(libroUsuarioService.extraerLibrosUsuario(bibliotecaActualizada)));
    }

    /**
     * Modifica un libroUsuario de la biblioteca del usuario autenticado por su posición
     * @param posicion La posición del libroUsuario en la biblioteca
     * @param libroUsuarioUpdateDTO El libroUsuario con los datos a modificar
     * @param usuarioAutenticado El usuario autenticado
     * @return El libroUsuario modificado
     */
    @PutMapping("/{posicion}")
    public ResponseEntity<LibroUsuarioResponseDTO> modificarLibroUsuario(@PathVariable("posicion") int posicion, @Valid @RequestBody LibroUsuarioUpdateDTO libroUsuarioUpdateDTO,
    @AuthenticationPrincipal UserDetails usuarioAutenticado) {

        Usuario usuario = usuarioService.obtenerUsuarioPorNombre(usuarioAutenticado.getUsername());
        Biblioteca bibliotecaUsuario = bibliotecaService.obtenerBiblioteca(usuario.getId());

        LibroUsuario libroUsuarioIngresado = libroUsuarioMapper.toEntity(libroUsuarioUpdateDTO);
        LibroUsuario libroUsuarioAModificar = bibliotecaService.obtenerLibroUsuarioPorPosicion(posicion, bibliotecaUsuario);
        libroUsuarioService.modificarLibroUsuario(libroUsuarioAModificar, libroUsuarioIngresado);

        Biblioteca bibliotecaActualizada = bibliotecaService.actualizarBiblioteca(bibliotecaUsuario);

        return ResponseEntity.ok(
            libroUsuarioMapper.toDTO(bibliotecaService.obtenerLibroUsuarioPorPosicion(posicion, bibliotecaActualizada)));
    }

    /**
     * Elimina un libroUsuario de la biblioteca del usuario autenticado por su posición
     * @param posicion La posición del libroUsuario en la biblioteca
     * @param usuarioAutenticado El usuario autenticado
     * @return OK (200)
     */
    @DeleteMapping("/{posicion}")
    public ResponseEntity<String> eliminarLibroUsuario(@PathVariable("posicion") int posicion,
    @AuthenticationPrincipal UserDetails usuarioAutenticado) {

        Usuario usuario = usuarioService.obtenerUsuarioPorNombre(usuarioAutenticado.getUsername());
        Biblioteca bibliotecaUsuario = bibliotecaService.obtenerBiblioteca(usuario.getId());
        
        bibliotecaService.obtenerLibroUsuarioPorPosicion(posicion, bibliotecaUsuario); // Existe el libro usuario
        bibliotecaService.eliminarLibroUsuarioDeBiblioteca(bibliotecaUsuario, posicion);

        return ResponseEntity.ok().body("Eliminado correctamente");
    }

    
}