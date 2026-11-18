package ar.edu.huergo.vectorial.calidad.bucher.controller.admin;

import ar.edu.huergo.vectorial.calidad.bucher.dto.admin.PanelLibrosDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.admin.PanelPosteosDTO;
import ar.edu.huergo.vectorial.calidad.bucher.mapper.admin.PanelLibrosMapper;
import ar.edu.huergo.vectorial.calidad.bucher.mapper.admin.PanelPosteosMapper;
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
}