package ar.edu.huergo.vectorial.calidad.bucher.controller.admin;

import ar.edu.huergo.vectorial.calidad.bucher.dto.admin.TablaEstadisticaRegistroPrestamoDTO;
import ar.edu.huergo.vectorial.calidad.bucher.mapper.admin.TablaEstadisticaRegistroPrestamoMapper;
import ar.edu.huergo.vectorial.calidad.bucher.service.admin.TablaEstadisticaRegistroPrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/prestamos")
public class AdminPrestamoController {

    @Autowired
    private TablaEstadisticaRegistroPrestamoService tablaEstadisticaRegistroPrestamoService;

    @Autowired
    private TablaEstadisticaRegistroPrestamoMapper tablaEstadisticaRegistroPrestamoMapper;

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
}