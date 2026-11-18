package ar.edu.huergo.vectorial.calidad.bucher.service.admin;

import ar.edu.huergo.vectorial.calidad.bucher.entity.security.OrdenTablaUsuario;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import ar.edu.huergo.vectorial.calidad.bucher.repository.publication.PublicacionRepository;
import ar.edu.huergo.vectorial.calidad.bucher.repository.publication.PublicacionSocialRepository;
import ar.edu.huergo.vectorial.calidad.bucher.repository.security.UsuarioRepository;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

//Clase que maneja la lógica de la tabla de usuarios del panel de administración
@Service
public class TablaEstadisticaUsuarioService {

    // Tamaño máximo de página permitido en el listado
    private static final int TAMANIO_MAXIMO_PAGINA = 50;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Autowired
    private PublicacionSocialRepository publicacionSocialRepository;

    /**
     * Obtiene una página de usuarios con el orden indicado
     * @param orden El orden pedido (ID o DENUNCIAS, por defecto ID)
     * @param pagina El número de página (desde 0)
     * @param tamanio La cantidad de usuarios por página
     * @return La página de usuarios
     */
    public Page<Usuario> obtenerUsuarios(String orden, int pagina, int tamanio) {
        return switch (OrdenTablaUsuario.from(orden)) {
            case ID -> usuarioRepository.findAll(
                crearPageable(pagina, tamanio, Sort.by(Sort.Direction.DESC, "id"))
            );
            case DENUNCIAS -> usuarioRepository.findAllOrdenadosPorDenuncias(
                crearPageable(pagina, tamanio, Sort.unsorted())
            );
        };
    }

    /**
     * Obtiene la cantidad de publicaciones de cada usuario
     * @param ids Los IDs de los usuarios
     * @return Mapa con el ID del usuario y su cantidad de publicaciones (los que no tienen no figuran)
     */
    public Map<Long, Long> obtenerCantidadPublicaciones(Collection<Long> ids) {
        if (ids.isEmpty()) {
            return new HashMap<>();
        }
        return convertirAMapa(publicacionRepository.contarPublicacionesPorUsuario(ids));
    }

    /**
     * Obtiene la cantidad de posteos (incluidos los eliminados) de cada usuario
     * @param ids Los IDs de los usuarios
     * @return Mapa con el ID del usuario y su cantidad de posteos (los que no tienen no figuran)
     */
    public Map<Long, Long> obtenerCantidadPosteos(Collection<Long> ids) {
        if (ids.isEmpty()) {
            return new HashMap<>();
        }
        return convertirAMapa(publicacionSocialRepository.contarPosteosPorUsuario(ids));
    }

    /**
     * Obtiene la cantidad de denuncias recibidas en los posteos (incluidos los eliminados) de cada usuario
     * @param ids Los IDs de los usuarios
     * @return Mapa con el ID del usuario y su cantidad de denuncias (los que no tienen no figuran)
     */
    public Map<Long, Long> obtenerCantidadDenuncias(Collection<Long> ids) {
        if (ids.isEmpty()) {
            return new HashMap<>();
        }
        return convertirAMapa(publicacionSocialRepository.contarDenunciasPorUsuario(ids));
    }

    /**
     * Convierte una lista de conteos por usuario en un mapa
     * @param conteos La lista de conteos
     * @return Mapa con el ID del usuario y su cantidad
     */
    private Map<Long, Long> convertirAMapa(List<UsuarioRepository.ConteoUsuario> conteos) {
        return conteos
            .stream()
            .collect(
                Collectors.toMap(
                    UsuarioRepository.ConteoUsuario::getUsuarioId,
                    UsuarioRepository.ConteoUsuario::getCantidad
                )
            );
    }

    /**
     * Crea el Pageable limitando el tamaño de página
     * @param pagina El número de página (desde 0)
     * @param tamanio La cantidad de elementos por página
     * @param sort El criterio de ordenamiento
     * @return El Pageable correspondiente
     */
    private Pageable crearPageable(int pagina, int tamanio, Sort sort) {
        return PageRequest.of(
            Math.max(pagina, 0),
            Math.min(Math.max(tamanio, 1), TAMANIO_MAXIMO_PAGINA),
            sort
        );
    }
}