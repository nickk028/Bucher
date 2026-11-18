package ar.edu.huergo.vectorial.calidad.bucher.controller.admin;

import ar.edu.huergo.vectorial.calidad.bucher.dto.admin.TablaEstadisticaPublicacionSocialDTO;
import ar.edu.huergo.vectorial.calidad.bucher.mapper.admin.TablaEstadisticaPublicacionSocialMapper;
import ar.edu.huergo.vectorial.calidad.bucher.service.admin.TablaEstadisticaPublicacionSocialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/publicacionSocial")
public class AdminPublicacionSocialController {

    @Autowired
    private TablaEstadisticaPublicacionSocialService tablaEstadisticaPublicacionSocialService;

    @Autowired
    private TablaEstadisticaPublicacionSocialMapper tablaEstadisticaPublicacionSocialMapper;

    /**
     * Obtiene la tabla de publicaciones sociales del panel de administración con el orden indicado
     * @param orden El orden pedido: RECIENTES (por defecto), DENUNCIAS o USUARIO
     * @param soloEliminadas Si es true, devuelve únicamente las publicaciones eliminadas
     * @param pagina El número de página (desde 0)
     * @param tamanio La cantidad de publicaciones por página
     * @return La página de publicaciones sociales (incluidas las respuestas y las eliminadas)
     */
    @GetMapping
    public ResponseEntity<Page<TablaEstadisticaPublicacionSocialDTO>> obtenerTablaPublicacionesSociales(
        @RequestParam(name = "orden", defaultValue = "RECIENTES") String orden,
        @RequestParam(name = "eliminadas", defaultValue = "false") boolean soloEliminadas,
        @RequestParam(name = "page", defaultValue = "0") int pagina,
        @RequestParam(name = "size", defaultValue = "20") int tamanio
    ) {
        return ResponseEntity.ok(
            tablaEstadisticaPublicacionSocialMapper.toDTOPage(
                tablaEstadisticaPublicacionSocialService.obtenerPublicacionesSociales(
                    orden,
                    soloEliminadas,
                    pagina,
                    tamanio
                )
            )
        );
    }
}