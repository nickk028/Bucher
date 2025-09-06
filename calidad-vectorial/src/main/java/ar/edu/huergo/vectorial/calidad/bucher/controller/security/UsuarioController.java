package ar.edu.huergo.vectorial.calidad.bucher.controller.security;

import java.util.List;

import org.springframework.http.ResponseEntity;
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

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> obtenerTodosUsuarios() {
        return ResponseEntity.ok(
            usuarioMapper.toDTOList(usuarioService.obtenerTodosUsuarios()));
    }

    @PostMapping("/registrar")
    public ResponseEntity<UsuarioResponseDTO> registrarCliente(@Valid @RequestBody RegistrarDTO registrarDTO) {
        Usuario usuario = usuarioMapper.toEntity(registrarDTO);
        Usuario nuevoUsuario = usuarioService.registrar(usuario, registrarDTO.password(), registrarDTO.verificationPassword());
        UsuarioResponseDTO nuevoUsuarioDTO = usuarioMapper.toDTO(nuevoUsuario);
        return ResponseEntity.ok(nuevoUsuarioDTO);
    }
}