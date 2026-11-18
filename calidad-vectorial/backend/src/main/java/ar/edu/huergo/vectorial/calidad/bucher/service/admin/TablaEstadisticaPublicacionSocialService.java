package ar.edu.huergo.vectorial.calidad.bucher.service.admin;

import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.OrdenTablaPublicacionSocial;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.PublicacionSocial;
import ar.edu.huergo.vectorial.calidad.bucher.repository.publication.PublicacionSocialRepository;
import ar.edu.huergo.vectorial.calidad.bucher.util.PaginacionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

//Clase que maneja la lógica de la tabla de publicaciones sociales del panel de administración
@Service
public class TablaEstadisticaPublicacionSocialService {

    @Autowired
    private PublicacionSocialRepository publicacionSocialRepository;

    /**
     * Obtiene una página de publicaciones sociales (incluidas las respuestas y las eliminadas) con el orden indicado
     * @param orden El orden pedido (RECIENTES, DENUNCIAS o USUARIO, por defecto RECIENTES)
     * @param soloEliminadas Si se quieren obtener únicamente las publicaciones eliminadas
     * @param pagina El número de página (desde 0)
     * @param tamanio La cantidad de publicaciones por página
     * @return La página de publicaciones sociales
     */
    public Page<PublicacionSocial> obtenerPublicacionesSociales(
        String orden,
        boolean soloEliminadas,
        int pagina,
        int tamanio
    ) {
        Pageable pageable = PaginacionUtil.crearPageable(
            pagina,
            tamanio,
            obtenerSort(OrdenTablaPublicacionSocial.from(orden))
        );
        if (soloEliminadas) {
            return publicacionSocialRepository.findAllByRegistroEliminacionIsNotNull(pageable);
        }
        return publicacionSocialRepository.findAll(pageable);
    }

    /**
     * Obtiene el criterio de ordenamiento según el orden pedido
     * @param orden El orden pedido
     * @return El Sort correspondiente
     */
    private Sort obtenerSort(OrdenTablaPublicacionSocial orden) {
        return switch (orden) {
            case RECIENTES -> Sort.by(Sort.Direction.DESC, "fechaCreacion", "id");
            case DENUNCIAS -> Sort.by(Sort.Direction.DESC, "reportes", "fechaCreacion", "id");
            case USUARIO -> Sort.by(Sort.Direction.ASC, "usuario.nickname").and(
                Sort.by(Sort.Direction.DESC, "fechaCreacion", "id")
            );
        };
    }
}