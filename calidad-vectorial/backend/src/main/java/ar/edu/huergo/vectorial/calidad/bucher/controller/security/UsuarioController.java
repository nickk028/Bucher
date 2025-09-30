package ar.edu.huergo.vectorial.calidad.bucher.controller.security;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;

import ar.edu.huergo.vectorial.calidad.bucher.dto.security.UsuarioResponseDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.security.RegistrarDTO;

import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import ar.edu.huergo.vectorial.calidad.bucher.mapper.security.UsuarioMapper;
import ar.edu.huergo.vectorial.calidad.bucher.service.security.UsuarioService;

@RestController // Marca la clase como un controlador REST
@RequiredArgsConstructor // Genera un constructor con los campos finales
@RequestMapping("/usuario")
// Controlador REST para la gestión de usuarios
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    /**
     * Obtiene todos los usuarios
     * @return Una lista de todos los usuarios
     */
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> obtenerTodosUsuarios() {
        return ResponseEntity.ok(
            usuarioMapper.toDTOList(usuarioService.obtenerTodosUsuarios()));
    }

    @GetMapping("/propio")
    public ResponseEntity<UsuarioResponseDTO> obtenerUsuario(@AuthenticationPrincipal UserDetails usuarioAutenticado) {
        Usuario usuario = usuarioService.obtenerUsuarioPorNombre(usuarioAutenticado.getUsername());
        return ResponseEntity.ok(
            usuarioMapper.toDTO(usuario));
    }

    /**
     * Registra un nuevo usuario
     * @param registrarDTO El DTO con los datos del usuario a registrar
     * @return El usuario registrado
     */
    @PostMapping("/registrar")
    public ResponseEntity<UsuarioResponseDTO> registrarCliente(@Valid @RequestBody RegistrarDTO registrarDTO) {
        Usuario usuario = usuarioMapper.toEntity(registrarDTO);
        usuario.setNickname(usuario.getUsername());
        Usuario nuevoUsuario = usuarioService.registrar(usuario, registrarDTO.password(), registrarDTO.verificationPassword());
        UsuarioResponseDTO nuevoUsuarioDTO = usuarioMapper.toDTO(nuevoUsuario);
        return ResponseEntity.ok(nuevoUsuarioDTO);
    }
}