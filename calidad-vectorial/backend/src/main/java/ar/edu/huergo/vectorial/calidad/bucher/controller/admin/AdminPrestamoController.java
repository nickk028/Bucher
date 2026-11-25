package ar.edu.huergo.vectorial.calidad.bucher.controller.admin;

import ar.edu.huergo.vectorial.calidad.bucher.dto.admin.PrestamoControlDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.admin.TablaEstadisticaRegistroPrestamoDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.publication.RegistroPrestamoResponseDTO;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.RegistroPrestamo;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import ar.edu.huergo.vectorial.calidad.bucher.mapper.admin.TablaEstadisticaRegistroPrestamoMapper;
import ar.edu.huergo.vectorial.calidad.bucher.mapper.publication.RegistroPrestamoMapper;
import ar.edu.huergo.vectorial.calidad.bucher.service.admin.TablaEstadisticaRegistroPrestamoService;
import ar.edu.huergo.vectorial.calidad.bucher.service.publication.RegistroPrestamoService;
import ar.edu.huergo.vectorial.calidad.bucher.service.security.UsuarioService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequestMapping("/admin/prestamos")
public class AdminPrestamoController {

    @Autowired
    private TablaEstadisticaRegistroPrestamoService tablaEstadisticaRegistroPrestamoService;

    @Autowired
    private TablaEstadisticaRegistroPrestamoMapper tablaEstadisticaRegistroPrestamoMapper;

    @Autowired
    private RegistroPrestamoService registroPrestamoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RegistroPrestamoMapper registroPrestamoMapper;

    /**
     * Obtiene la tabla de préstamos activos del panel de administración
     * @param pagina El número de página (desde 0)
     * @param tamanio La cantidad de registros por página
     * @return La página de préstamos activos
     */
    @GetMapping
    public ResponseEntity<Page<TablaEstadisticaRegistroPrestamoDTO>> obtenerTablaPrestamos(
        @RequestParam(name = "page", defaultValue = "0") int pagina,
        @RequestParam(name = "size", defaultValue = "20") int tamanio
    ) {
        return ResponseEntity.ok(
            tablaEstadisticaRegistroPrestamoMapper.toDTOPage(
                tablaEstadisticaRegistroPrestamoService.obtenerPrestamosActivos(pagina, tamanio)
            )
        );
    }

    @PutMapping("/control/{id}")
    public ResponseEntity<RegistroPrestamoResponseDTO> controlPrestamo(
        @AuthenticationPrincipal UserDetails usuarioAutenticado,
        @PathVariable("id") Long id,
        @Valid @RequestBody PrestamoControlDTO prestamoControlDTO
        ) {
        Usuario usuario = usuarioService.obtenerUsuarioPorNombre(usuarioAutenticado.getUsername());
        RegistroPrestamo registroPrestamo = registroPrestamoService.obtenerRegistroPrestamoPorId(id, usuario);

        return ResponseEntity.ok(
            registroPrestamoMapper.toDTO(
                registroPrestamoService.realizarControlRegistroPrestamos(registroPrestamoMapper.toEntity(prestamoControlDTO), registroPrestamo)
            )
        );
    }

    @PostMapping("/control/completar/{id}")
    public ResponseEntity<RegistroPrestamoResponseDTO> marcarControlCompletado(
        @AuthenticationPrincipal UserDetails usuarioAutenticado,
        @PathVariable("id") Long id
    ) {
        Usuario usuario = usuarioService.obtenerUsuarioPorNombre(usuarioAutenticado.getUsername());
        RegistroPrestamo registroPrestamo = registroPrestamoService.obtenerRegistroPrestamoPorId(id, usuario);
        
        return ResponseEntity.ok(
            registroPrestamoMapper.toDTO(
                registroPrestamoService.marcarRevisionCompletada(registroPrestamo)
            )
        );
    }
    
}