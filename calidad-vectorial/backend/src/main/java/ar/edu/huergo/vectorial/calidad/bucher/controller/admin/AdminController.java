package ar.edu.huergo.vectorial.calidad.bucher.controller.admin;

import ar.edu.huergo.vectorial.calidad.bucher.dto.admin.PanelLibrosDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.admin.PanelPosteosDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.admin.PanelPrestamosDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.admin.PanelUsuariosDTO;
import ar.edu.huergo.vectorial.calidad.bucher.mapper.admin.PanelLibrosMapper;
import ar.edu.huergo.vectorial.calidad.bucher.mapper.admin.PanelPosteosMapper;
import ar.edu.huergo.vectorial.calidad.bucher.mapper.admin.PanelPrestamosMapper;
import ar.edu.huergo.vectorial.calidad.bucher.mapper.admin.PanelUsuariosMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/estadisticas")
public class AdminController {

    @Autowired
    private PanelLibrosMapper panelLibrosMapper;

    @Autowired
    private PanelPosteosMapper panelPosteosMapper;

    @Autowired
    private PanelPrestamosMapper panelPrestamosMapper;

    @Autowired
    private PanelUsuariosMapper panelUsuariosMapper;

    /**
     * Obtiene las estadísticas de libros del panel de administración
     * @return Response entity con las estadísticas de libros
     */
    @GetMapping("/libros")
    public ResponseEntity<PanelLibrosDTO> obtenerPanelLibros() {
        return ResponseEntity.ok(panelLibrosMapper.toDTO());
    }

    /**
     * Obtiene las estadísticas de posteos del panel de administración
     * @return Response entity con las estadísticas de posteos
     */
    @GetMapping("/posteos")
    public ResponseEntity<PanelPosteosDTO> obtenerPanelPosteos() {
        return ResponseEntity.ok(panelPosteosMapper.toDTO());
    }

    /**
     * Obtiene las estadísticas de préstamos del panel de administración
     * @return Response entity con las estadísticas de préstamos
     */
    @GetMapping("/prestamos")
    public ResponseEntity<PanelPrestamosDTO> obtenerPanelPrestamos() {
        return ResponseEntity.ok(panelPrestamosMapper.toDTO());
    }

    /**
     * Obtiene las estadísticas de usuarios del panel de administración
     * @return Response entity con las estadísticas de usuarios
     */
    @GetMapping("/usuarios")
    public ResponseEntity<PanelUsuariosDTO> obtenerPanelUsuarios() {
        return ResponseEntity.ok(panelUsuariosMapper.toDTO());
    }
}