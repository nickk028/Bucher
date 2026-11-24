package ar.edu.huergo.vectorial.calidad.bucher.controller.admin;

import ar.edu.huergo.vectorial.calidad.bucher.dto.admin.TablaEstadisticaUsuarioDTO;
import ar.edu.huergo.vectorial.calidad.bucher.mapper.admin.TablaEstadisticaUsuarioMapper;
import ar.edu.huergo.vectorial.calidad.bucher.service.admin.TablaEstadisticaUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/usuarios")
public class AdminUsuarioController {

    @Autowired
    private TablaEstadisticaUsuarioService tablaEstadisticaUsuarioService;

    @Autowired
    private TablaEstadisticaUsuarioMapper tablaEstadisticaUsuarioMapper;

    /**
     * Obtiene la tabla de usuarios del panel de administración con el orden indicado
     * @param orden El orden pedido: ID (por defecto) o DENUNCIAS
     * @param pagina El número de página (desde 0)
     * @param tamanio La cantidad de usuarios por página
     * @return La página de usuarios con sus cantidades de publicaciones, posteos y denuncias
     */
    @GetMapping
    public ResponseEntity<Page<TablaEstadisticaUsuarioDTO>> obtenerTablaUsuarios(
        @RequestParam(name = "orden", defaultValue = "ID") String orden,
        @RequestParam(name = "page", defaultValue = "0") int pagina,
        @RequestParam(name = "size", defaultValue = "20") int tamanio
    ) {
        return ResponseEntity.ok(
            tablaEstadisticaUsuarioMapper.toDTOPage(
                tablaEstadisticaUsuarioService.obtenerUsuarios(orden, pagina, tamanio)
            )
        );
    }
}