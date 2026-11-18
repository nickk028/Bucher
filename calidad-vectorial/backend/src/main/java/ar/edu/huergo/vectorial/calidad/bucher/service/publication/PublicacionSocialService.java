package ar.edu.huergo.vectorial.calidad.bucher.service.publication;

import ar.edu.huergo.vectorial.calidad.bucher.entity.deletion.RegistroEliminacionPublicacionSocial;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.EtiquetaPublicacionSocial;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.OrdenPublicacionSocial;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.PublicacionSocial;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import ar.edu.huergo.vectorial.calidad.bucher.repository.publication.PublicacionSocialRepository;
import ar.edu.huergo.vectorial.calidad.bucher.repository.security.UsuarioRepository;
import ar.edu.huergo.vectorial.calidad.bucher.service.book.LibroService;
import ar.edu.huergo.vectorial.calidad.bucher.service.security.UsuarioService;
import ar.edu.huergo.vectorial.calidad.bucher.util.PaginacionUtil;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//Clase que maneja la lógica de PublicacionSocial
@Service
public class PublicacionSocialService {

    // Razón de eliminación cuando el propio autor elimina su publicación
    private static final String RAZON_ELIMINACION_USUARIO = "Eliminada por usuario";


    @Autowired
    private PublicacionSocialRepository publicacionSocialRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private LibroService libroService;

    /**
     * Obtiene las publicaciones sociales raíz (que no son respuestas) con el orden indicado.
     * Las eliminadas solo se incluyen para los admins.
     * @param orden El orden pedido (RECIENTES, ANTIGUAS o POPULARES, por defecto RECIENTES)
     * @param pagina El número de página (desde 0)
     * @param tamanio La cantidad de publicaciones por página
     * @param esAdmin Si quien consulta es admin
     * @return La página de publicaciones sociales
     */
    public Page<PublicacionSocial> obtenerTodasLasPublicacionesSociales(
        String orden,
        int pagina,
        int tamanio,
        boolean esAdmin
    ) {
        Pageable pageable = PaginacionUtil.crearPageable(pagina, tamanio, obtenerSort(OrdenPublicacionSocial.from(orden)));
        if (esAdmin) {
            return publicacionSocialRepository.findAllByPublicacionReferenciadaIsNull(pageable);
        }
        return publicacionSocialRepository.findAllByPublicacionReferenciadaIsNullAndRegistroEliminacionIsNull(
            pageable
        );
    }

    /**
     * Obtiene una publicación social por su ID (incluso si está eliminada)
     * @param id El ID de la publicación social
     * @return La publicación social correspondiente al ID
     * @throws EntityNotFoundException Si no se encuentra la publicación social
     */
    public PublicacionSocial obtenerPublicacionSocialPorId(Long id) throws EntityNotFoundException {
        return publicacionSocialRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Publicación social no encontrada"));
    }

    /**
     * Obtiene las respuestas directas de una publicación social, de la más reciente a la más antigua.
     * Las respuestas eliminadas se incluyen para que no se rompa la cadena de respuestas.
     * @param id El ID de la publicación social respondida
     * @param pagina El número de página (desde 0)
     * @param tamanio La cantidad de respuestas por página
     * @return La página de respuestas
     * @throws EntityNotFoundException Si no se encuentra la publicación social
     */
    public Page<PublicacionSocial> obtenerRespuestas(Long id, int pagina, int tamanio)
        throws EntityNotFoundException {
        obtenerPublicacionSocialPorId(id);
        Pageable pageable = PaginacionUtil.crearPageable(pagina, tamanio, obtenerSort(OrdenPublicacionSocial.RECIENTES));
        return publicacionSocialRepository.findAllByPublicacionReferenciadaId(id, pageable);
    }

    /**
     * Obtiene las publicaciones sociales de un usuario (incluidas sus respuestas), de la más reciente a la más antigua.
     * Las eliminadas solo se incluyen para los admins.
     * @param usuarioId El ID del usuario
     * @param pagina El número de página (desde 0)
     * @param tamanio La cantidad de publicaciones por página
     * @param esAdmin Si quien consulta es admin
     * @return La página de publicaciones sociales del usuario
     * @throws EntityNotFoundException Si no se encuentra el usuario
     */
    public Page<PublicacionSocial> obtenerPublicacionesSocialesPorUsuario(
        Long usuarioId,
        int pagina,
        int tamanio,
        boolean esAdmin
    ) throws EntityNotFoundException {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new EntityNotFoundException("Usuario no encontrado");
        }
        Pageable pageable = PaginacionUtil.crearPageable(pagina, tamanio, obtenerSort(OrdenPublicacionSocial.RECIENTES));
        if (esAdmin) {
            return publicacionSocialRepository.findAllByUsuarioId(usuarioId, pageable);
        }
        return publicacionSocialRepository.findAllByUsuarioIdAndRegistroEliminacionIsNull(
            usuarioId,
            pageable
        );
    }

    /**
     * Obtiene las publicaciones sociales raíz de un libro, de la más reciente a la más antigua.
     * Las eliminadas solo se incluyen para los admins.
     * @param libroId El ID del libro
     * @param pagina El número de página (desde 0)
     * @param tamanio La cantidad de publicaciones por página
     * @param esAdmin Si quien consulta es admin
     * @return La página de publicaciones sociales del libro
     * @throws EntityNotFoundException Si no se encuentra el libro
     */
    public Page<PublicacionSocial> obtenerPublicacionesSocialesPorLibro(
        Long libroId,
        int pagina,
        int tamanio,
        boolean esAdmin
    ) throws EntityNotFoundException {
        libroService.obtenerLibroPorId(libroId);
        Pageable pageable = PaginacionUtil.crearPageable(pagina, tamanio, obtenerSort(OrdenPublicacionSocial.RECIENTES));
        if (esAdmin) {
            return publicacionSocialRepository.findAllByLibroIdAndPublicacionReferenciadaIsNull(
                libroId,
                pageable
            );
        }
        return publicacionSocialRepository.findAllByLibroIdAndPublicacionReferenciadaIsNullAndRegistroEliminacionIsNull(
            libroId,
            pageable
        );
    }

    /**
     * Obtiene la cantidad de publicaciones sociales no eliminadas de un usuario
     * @param username El username del usuario
     * @return La cantidad de publicaciones sociales del usuario
     * @throws EntityNotFoundException Si no se encuentra el usuario
     */
    public long obtenerCantidadPublicacionesSocialesPorUsuario(String username)
        throws EntityNotFoundException {
        Usuario usuario = usuarioService.obtenerUsuarioPorNombre(username);
        return publicacionSocialRepository.countByUsuarioIdAndRegistroEliminacionIsNull(
            usuario.getId()
        );
    }

    /**
     * Obtiene la cantidad de respuestas directas (incluidas las eliminadas) de cada publicación
     * @param ids Los IDs de las publicaciones
     * @return Mapa con el ID de la publicación y su cantidad de respuestas (las que no tienen respuestas no figuran)
     */
    public Map<Long, Long> obtenerCantidadRespuestas(Collection<Long> ids) {
        if (ids.isEmpty()) {
            return new HashMap<>();
        }
        return publicacionSocialRepository
            .contarRespuestasPorPublicacion(ids)
            .stream()
            .collect(
                Collectors.toMap(
                    PublicacionSocialRepository.ConteoRespuestas::getPublicacionId,
                    PublicacionSocialRepository.ConteoRespuestas::getCantidad
                )
            );
    }

    /**
     * Crea una nueva publicación social. Si responde a otra publicación, hereda su libro.
     * @param publicacion La publicación a crear
     * @param libroId El ID del libro referenciado (opcional, no se permite en respuestas)
     * @param publicacionReferenciadaId El ID de la publicación a la que responde (opcional)
     * @param nicknamesEtiquetados Los nicknames de los usuarios etiquetados
     * @param username El username del usuario que crea la publicación
     * @return La publicación social creada
     * @throws EntityNotFoundException Si no se encuentra el usuario, el libro o la publicación referenciada
     * @throws IllegalArgumentException Si una respuesta referencia un libro o responde a una publicación eliminada
     */
    @Transactional
    public PublicacionSocial crearPublicacionSocial(
        PublicacionSocial publicacion,
        Long libroId,
        Long publicacionReferenciadaId,
        List<String> nicknamesEtiquetados,
        String username
    ) throws EntityNotFoundException {
        Usuario usuario = usuarioService.obtenerUsuarioPorNombre(username);
        publicacion.setUsuario(usuario);

        if (publicacionReferenciadaId != null) {
            if (libroId != null) {
                throw new IllegalArgumentException("Las respuestas no pueden referenciar un libro");
            }
            PublicacionSocial referenciada = obtenerPublicacionSocialPorId(publicacionReferenciadaId);
            if (referenciada.getRegistroEliminacion() != null) {
                throw new IllegalArgumentException(
                    "No se puede responder una publicación eliminada"
                );
            }
            publicacion.setPublicacionReferenciada(referenciada);
            publicacion.setLibro(referenciada.getLibro());
        } else if (libroId != null) {
            publicacion.setLibro(libroService.obtenerLibroPorId(libroId));
        }

        publicacion.setEtiquetas(construirEtiquetas(publicacion, nicknamesEtiquetados, usuario));

        return publicacionSocialRepository.save(publicacion);
    }

    /**
     * Elimina una publicación social registrando la eliminación (la publicación se conserva).
     * Solo el autor o un admin pueden eliminarla. Si elimina el autor, la razón es automática;
     * si elimina un admin que no es el autor, la razón es obligatoria.
     * @param id El ID de la publicación social a eliminar
     * @param razon La razón de la eliminación (obligatoria si elimina un admin que no es el autor)
     * @param username El username del usuario que elimina
     * @param esAdmin Si quien elimina es admin
     * @throws EntityNotFoundException Si no se encuentra la publicación o no pertenece al usuario y este no es admin
     * @throws IllegalArgumentException Si ya está eliminada o falta la razón del admin
     */
    @Transactional
    public void eliminarPublicacionSocial(
        Long id,
        String razon,
        String username,
        boolean esAdmin
    ) throws EntityNotFoundException {
        Usuario usuario = usuarioService.obtenerUsuarioPorNombre(username);
        PublicacionSocial publicacion = obtenerPublicacionSocialPorId(id);

        boolean esAutor = publicacion.getUsuario().getId().equals(usuario.getId());
        if (!esAutor && !esAdmin) {
            throw new EntityNotFoundException("Publicación social no encontrada");
        }
        if (publicacion.getRegistroEliminacion() != null) {
            throw new IllegalArgumentException("La publicación ya está eliminada");
        }

        String razonEliminacion = RAZON_ELIMINACION_USUARIO;
        if (!esAutor) {
            if (razon == null || razon.isBlank()) {
                throw new IllegalArgumentException(
                    "La razón es obligatoria para eliminar la publicación de otro usuario"
                );
            }
            razonEliminacion = razon.trim();
        }

        RegistroEliminacionPublicacionSocial registro = new RegistroEliminacionPublicacionSocial();
        registro.setRazon(razonEliminacion);
        registro.setUsuarioEliminador(usuario);
        publicacion.setRegistroEliminacion(registro);

        publicacionSocialRepository.save(publicacion);
    }

    /**
     * Construye las etiquetas de una publicación. Ignora nicknames repetidos y el del propio autor.
     * Si el nickname no corresponde a ningún usuario, la etiqueta se guarda sin usuario.
     * @param publicacion La publicación a la que pertenecen las etiquetas
     * @param nicknames Los nicknames ingresados
     * @param autor El usuario que crea la publicación
     * @return La lista de etiquetas
     */
    private List<EtiquetaPublicacionSocial> construirEtiquetas(
        PublicacionSocial publicacion,
        List<String> nicknames,
        Usuario autor
    ) {
        List<EtiquetaPublicacionSocial> etiquetas = new ArrayList<>();
        if (nicknames == null) {
            return etiquetas;
        }

        Set<String> nicknamesProcesados = new HashSet<>();
        for (String nickname : nicknames) {
            String nicknameLimpio = nickname.trim();
            if (!nicknamesProcesados.add(nicknameLimpio) || nicknameLimpio.equals(autor.getNickname())) {
                continue;
            }

            EtiquetaPublicacionSocial etiqueta = new EtiquetaPublicacionSocial();
            etiqueta.setPublicacionSocial(publicacion);
            etiqueta.setNickname(nicknameLimpio);
            etiqueta.setUsuario(usuarioRepository.findByNickname(nicknameLimpio).orElse(null));
            etiquetas.add(etiqueta);
        }
        return etiquetas;
    }

    /**
     * Obtiene el criterio de ordenamiento según el orden pedido
     * @param orden El orden pedido
     * @return El Sort correspondiente
     */
    private Sort obtenerSort(OrdenPublicacionSocial orden) {
        return switch (orden) {
            case RECIENTES -> Sort.by(Sort.Direction.DESC, "fechaCreacion", "id");
            case ANTIGUAS -> Sort.by(Sort.Direction.ASC, "fechaCreacion", "id");
            case POPULARES -> Sort.by(Sort.Direction.DESC, "votosPositivos", "fechaCreacion", "id");
        };
    }
}