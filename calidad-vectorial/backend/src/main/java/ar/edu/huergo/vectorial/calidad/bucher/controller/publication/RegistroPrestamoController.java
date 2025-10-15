package ar.edu.huergo.vectorial.calidad.bucher.controller.publication;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.huergo.vectorial.calidad.bucher.dto.publication.RegistroPrestamoResponseDTO;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import ar.edu.huergo.vectorial.calidad.bucher.mapper.publication.RegistroPrestamoMapper;
import ar.edu.huergo.vectorial.calidad.bucher.service.publication.RegistroPrestamoService;
import ar.edu.huergo.vectorial.calidad.bucher.service.security.UsuarioService;


@RestController
@RequestMapping("/registro")
public class RegistroPrestamoController {

    @Autowired
    private RegistroPrestamoMapper registroPrestamoMapper;

    @Autowired
    private RegistroPrestamoService registroPrestamoService;

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Obtiene los registros de préstamo de un usuario específico
     * @param usuario El usuario cuyos registros se desean obtener
     * @return Una lista de registros de préstamo del usuario
     */
    @GetMapping
    public List<RegistroPrestamoResponseDTO> obtenerRegistrosUsuario(@AuthenticationPrincipal UserDetails usuarioAutenticado) {
        Usuario usuario = usuarioService.obtenerUsuarioPorNombre(usuarioAutenticado.getUsername());
        return registroPrestamoMapper.toDTOList(registroPrestamoService.obtenerRegistrosPrestamoPorUsuario(usuario));
    }
    
}