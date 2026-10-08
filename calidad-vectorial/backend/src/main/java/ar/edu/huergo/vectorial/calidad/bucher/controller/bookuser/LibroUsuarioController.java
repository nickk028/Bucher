package ar.edu.huergo.vectorial.calidad.bucher.controller.bookuser;

import ar.edu.huergo.vectorial.calidad.bucher.dto.bookuser.LibroUsuarioCreateDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.bookuser.LibroUsuarioResponseDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.bookuser.LibroUsuarioUpdateDTO;
import ar.edu.huergo.vectorial.calidad.bucher.entity.bookuser.EstadoLectura;
import ar.edu.huergo.vectorial.calidad.bucher.mapper.bookuser.LibroUsuarioMapper;
import ar.edu.huergo.vectorial.calidad.bucher.service.bookuser.LibroUsuarioService;
import jakarta.validation.Valid;
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

@RestController
@RequestMapping("/libro-usuario")
public class LibroUsuarioController {

    @Autowired
    private LibroUsuarioService libroUsuarioService;

    @Autowired
    private LibroUsuarioMapper libroUsuarioMapper;

    /**
     * Obtiene los libroUsuario del usuario autenticado
     * @param usuarioAutenticado El usuario autenticado
     * @return La lista de libroUsuario del usuario autenticado
     */
    @GetMapping
    public ResponseEntity<List<LibroUsuarioResponseDTO>> obtenerLibrosUsuario(
        @AuthenticationPrincipal UserDetails usuarioAutenticado
    ) {
        return ResponseEntity.ok(
            libroUsuarioMapper.toDTOList(
                libroUsuarioService.obtenerLibrosUsuario(usuarioAutenticado.getUsername())
            )
        );
    }

    /**
     * Obtiene un libroUsuario del usuario autenticado por su posición
     * @param posicion La posición del libroUsuario en la lista del usuario
     * @param usuarioAutenticado El usuario autenticado
     * @return El libroUsuario en la posición indicada
     */
    @GetMapping("/{posicion}")
    public ResponseEntity<LibroUsuarioResponseDTO> obtenerLibroUsuario(
        @PathVariable("posicion") int posicion,
        @AuthenticationPrincipal UserDetails usuarioAutenticado
    ) {
        return ResponseEntity.ok(
            libroUsuarioMapper.toDTO(
                libroUsuarioService.obtenerLibroUsuarioPorPosicion(
                    usuarioAutenticado.getUsername(),
                    posicion
                )
            )
        );
    }

    /**
     * Obtiene una lista de libroUsuario del usuario autenticado por su estadoLectura
     * @param estadoLectura El estadoLectura de los libroUsuario a obtener
     * @param usuarioAutenticado El usuario autenticado
     * @return La lista de libroUsuario del estadoLectura indicado
     */
    @GetMapping("/estado/{estadoLectura}")
    public ResponseEntity<List<LibroUsuarioResponseDTO>> obtenerLibroUsuarioPorEstado(
        @PathVariable("estadoLectura") EstadoLectura estadoLectura,
        @AuthenticationPrincipal UserDetails usuarioAutenticado
    ) {
        return ResponseEntity.ok(
            libroUsuarioMapper.toDTOList(
                libroUsuarioService.obtenerLibrosPorEstado(
                    usuarioAutenticado.getUsername(),
                    estadoLectura
                )
            )
        );
    }

    /**
     * Sube un libroUsuario a la lista del usuario autenticado
     * @param libroUsuarioCreateDTO El libroUsuario a subir
     * @param usuarioAutenticado El usuario autenticado
     * @return La lista actualizada de libroUsuario del usuario autenticado
     */
    @PostMapping
    public ResponseEntity<List<LibroUsuarioResponseDTO>> subirLibroUsuario(
        @Valid @RequestBody LibroUsuarioCreateDTO libroUsuarioCreateDTO,
        @AuthenticationPrincipal UserDetails usuarioAutenticado
    ) {
        return ResponseEntity.ok(
            libroUsuarioMapper.toDTOList(
                libroUsuarioService.subirLibroUsuario(
                    usuarioAutenticado.getUsername(),
                    libroUsuarioMapper.toEntity(libroUsuarioCreateDTO),
                    libroUsuarioCreateDTO.getTitulo()
                )
            )
        );
    }

    /**
     * Modifica un libroUsuario del usuario autenticado por su posición
     * @param posicion La posición del libroUsuario en la lista del usuario
     * @param libroUsuarioUpdateDTO El libroUsuario con los datos a modificar
     * @param usuarioAutenticado El usuario autenticado
     * @return El libroUsuario modificado
     */
    @PutMapping("/{posicion}")
    public ResponseEntity<LibroUsuarioResponseDTO> modificarLibroUsuario(
        @PathVariable("posicion") int posicion,
        @Valid @RequestBody LibroUsuarioUpdateDTO libroUsuarioUpdateDTO,
        @AuthenticationPrincipal UserDetails usuarioAutenticado
    ) {
        return ResponseEntity.ok(
            libroUsuarioMapper.toDTO(
                libroUsuarioService.modificarLibroUsuario(
                    usuarioAutenticado.getUsername(),
                    posicion,
                    libroUsuarioMapper.toEntity(libroUsuarioUpdateDTO)
                )
            )
        );
    }

    /**
     * Elimina un libroUsuario del usuario autenticado por su posición
     * @param posicion La posición del libroUsuario en la lista del usuario
     * @param usuarioAutenticado El usuario autenticado
     * @return OK (200)
     */
    @DeleteMapping("/{posicion}")
    public ResponseEntity<String> eliminarLibroUsuario(
        @PathVariable("posicion") int posicion,
        @AuthenticationPrincipal UserDetails usuarioAutenticado
    ) {
        libroUsuarioService.eliminarLibroUsuario(usuarioAutenticado.getUsername(), posicion);

        return ResponseEntity.ok().body("Eliminado correctamente");
    }
}