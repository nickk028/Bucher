package ar.edu.huergo.vectorial.calidad.bucher.controller.publication;

import ar.edu.huergo.vectorial.calidad.bucher.dto.publication.PublicacionSocialCreateDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.publication.PublicacionSocialDeleteDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.publication.PublicacionSocialResponseDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.publication.VotoPublicacionSocialCreateDTO;
import ar.edu.huergo.vectorial.calidad.bucher.mapper.publication.PublicacionSocialMapper;
import ar.edu.huergo.vectorial.calidad.bucher.service.publication.PublicacionSocialService;
import ar.edu.huergo.vectorial.calidad.bucher.service.publication.ReportePublicacionSocialService;
import ar.edu.huergo.vectorial.calidad.bucher.service.publication.VotoPublicacionSocialService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/publicacionSocial")
public class PublicacionSocialController {

    @Autowired
    private PublicacionSocialService publicacionSocialService;

    @Autowired
    private PublicacionSocialMapper publicacionSocialMapper;

    @Autowired
    private VotoPublicacionSocialService votoPublicacionSocialService;

    @Autowired
    private ReportePublicacionSocialService reportePublicacionSocialService;

    /**
     * Obtiene las publicaciones sociales raíz con el orden indicado
     * @param orden El orden pedido: RECIENTES (por defecto), ANTIGUAS o POPULARES
     * @param pagina El número de página (desde 0)
     * @param tamanio La cantidad de publicaciones por página
     * @param usuarioAutenticado El usuario autenticado
     * @return La página de publicaciones sociales
     */
    @GetMapping
    public ResponseEntity<Page<PublicacionSocialResponseDTO>> obtenerTodasLasPublicacionesSociales(
        @RequestParam(name = "orden", defaultValue = "RECIENTES") String orden,
        @RequestParam(name = "page", defaultValue = "0") int pagina,
        @RequestParam(name = "size", defaultValue = "20") int tamanio,
        @AuthenticationPrincipal UserDetails usuarioAutenticado
    ) {
        boolean esAdmin = esAdmin(usuarioAutenticado);
        return ResponseEntity.ok(
            publicacionSocialMapper.toDTOPage(
                publicacionSocialService.obtenerTodasLasPublicacionesSociales(
                    orden,
                    pagina,
                    tamanio,
                    esAdmin
                ),
                usuarioAutenticado.getUsername(),
                esAdmin
            )
        );
    }

    /**
     * Obtiene una publicación social por su ID, con la publicación que responde y su cantidad de respuestas
     * @param id El ID de la publicación social
     * @param usuarioAutenticado El usuario autenticado
     * @return La publicación social buscada
     */
    @GetMapping("/{id}")
    public ResponseEntity<PublicacionSocialResponseDTO> obtenerPublicacionSocial(
        @PathVariable("id") Long id,
        @AuthenticationPrincipal UserDetails usuarioAutenticado
    ) {
        return ResponseEntity.ok(
            publicacionSocialMapper.toDTO(
                publicacionSocialService.obtenerPublicacionSocialPorId(id),
                usuarioAutenticado.getUsername(),
                esAdmin(usuarioAutenticado)
            )
        );
    }

    /**
     * Obtiene las respuestas directas de una publicación social
     * @param id El ID de la publicación social respondida
     * @param pagina El número de página (desde 0)
     * @param tamanio La cantidad de respuestas por página
     * @param usuarioAutenticado El usuario autenticado
     * @return La página de respuestas
     */
    @GetMapping("/respuestas/{id}")
    public ResponseEntity<Page<PublicacionSocialResponseDTO>> obtenerRespuestas(
        @PathVariable("id") Long id,
        @RequestParam(name = "page", defaultValue = "0") int pagina,
        @RequestParam(name = "size", defaultValue = "20") int tamanio,
        @AuthenticationPrincipal UserDetails usuarioAutenticado
    ) {
        return ResponseEntity.ok(
            publicacionSocialMapper.toDTOPage(
                publicacionSocialService.obtenerRespuestas(id, pagina, tamanio),
                usuarioAutenticado.getUsername(),
                esAdmin(usuarioAutenticado)
            )
        );
    }

    /**
     * Obtiene las publicaciones sociales de un usuario (incluidas sus respuestas)
     * @param id El ID del usuario
     * @param pagina El número de página (desde 0)
     * @param tamanio La cantidad de publicaciones por página
     * @param usuarioAutenticado El usuario autenticado
     * @return La página de publicaciones sociales del usuario
     */
    @GetMapping("/usuario/{id}")
    public ResponseEntity<Page<PublicacionSocialResponseDTO>> obtenerPublicacionesSocialesPorUsuario(
        @PathVariable("id") Long id,
        @RequestParam(name = "page", defaultValue = "0") int pagina,
        @RequestParam(name = "size", defaultValue = "20") int tamanio,
        @AuthenticationPrincipal UserDetails usuarioAutenticado
    ) {
        boolean esAdmin = esAdmin(usuarioAutenticado);
        return ResponseEntity.ok(
            publicacionSocialMapper.toDTOPage(
                publicacionSocialService.obtenerPublicacionesSocialesPorUsuario(
                    id,
                    pagina,
                    tamanio,
                    esAdmin
                ),
                usuarioAutenticado.getUsername(),
                esAdmin
            )
        );
    }

    /**
     * Obtiene las publicaciones sociales raíz de un libro
     * @param id El ID del libro
     * @param pagina El número de página (desde 0)
     * @param tamanio La cantidad de publicaciones por página
     * @param usuarioAutenticado El usuario autenticado
     * @return La página de publicaciones sociales del libro
     */
    @GetMapping("/libro/{id}")
    public ResponseEntity<Page<PublicacionSocialResponseDTO>> obtenerPublicacionesSocialesPorLibro(
        @PathVariable("id") Long id,
        @RequestParam(name = "page", defaultValue = "0") int pagina,
        @RequestParam(name = "size", defaultValue = "20") int tamanio,
        @AuthenticationPrincipal UserDetails usuarioAutenticado
    ) {
        boolean esAdmin = esAdmin(usuarioAutenticado);
        return ResponseEntity.ok(
            publicacionSocialMapper.toDTOPage(
                publicacionSocialService.obtenerPublicacionesSocialesPorLibro(
                    id,
                    pagina,
                    tamanio,
                    esAdmin
                ),
                usuarioAutenticado.getUsername(),
                esAdmin
            )
        );
    }

    /**
     * Crea una nueva publicación social
     * @param publicacionSocialCreateDTO El DTO con los datos de la publicación a crear
     * @param usuarioAutenticado El usuario autenticado
     * @return La publicación social creada
     */
    @PostMapping("/crear")
    public ResponseEntity<PublicacionSocialResponseDTO> crearPublicacionSocial(
        @Valid @RequestBody PublicacionSocialCreateDTO publicacionSocialCreateDTO,
        @AuthenticationPrincipal UserDetails usuarioAutenticado
    ) {
        return ResponseEntity.ok(
            publicacionSocialMapper.toDTO(
                publicacionSocialService.crearPublicacionSocial(
                    publicacionSocialMapper.toEntity(publicacionSocialCreateDTO),
                    publicacionSocialCreateDTO.getLibroId(),
                    publicacionSocialCreateDTO.getPublicacionReferenciadaId(),
                    publicacionSocialCreateDTO.getEtiquetas(),
                    usuarioAutenticado.getUsername()
                ),
                usuarioAutenticado.getUsername(),
                esAdmin(usuarioAutenticado)
            )
        );
    }

    /**
     * Elimina una publicación social (solo el usuario que la creó o un admin)
     * @param id El ID de la publicación social a eliminar
     * @param publicacionSocialDeleteDTO El DTO con la razón (obligatoria si un admin elimina la publicación de otro usuario)
     * @param usuarioAutenticado El usuario autenticado
     * @return OK (200) si se eliminó correctamente
     */
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarPublicacionSocial(
        @PathVariable("id") Long id,
        @Valid @RequestBody(required = false) PublicacionSocialDeleteDTO publicacionSocialDeleteDTO,
        @AuthenticationPrincipal UserDetails usuarioAutenticado
    ) {
        String razon = publicacionSocialDeleteDTO == null
            ? null
            : publicacionSocialDeleteDTO.getRazon();

        publicacionSocialService.eliminarPublicacionSocial(
            id,
            razon,
            usuarioAutenticado.getUsername(),
            esAdmin(usuarioAutenticado)
        );

        return ResponseEntity.ok().body("Eliminado correctamente");
    }

    /**
     * Registra o cambia el voto del usuario autenticado en una publicación social
     * @param id El ID de la publicación social
     * @param votoPublicacionSocialCreateDTO El DTO con el tipo de voto
     * @param usuarioAutenticado El usuario autenticado
     * @return La publicación social actualizada
     */
    @PostMapping("/voto/{id}")
    public ResponseEntity<PublicacionSocialResponseDTO> votarPublicacionSocial(
        @PathVariable("id") Long id,
        @Valid @RequestBody VotoPublicacionSocialCreateDTO votoPublicacionSocialCreateDTO,
        @AuthenticationPrincipal UserDetails usuarioAutenticado
    ) {
        votoPublicacionSocialService.votarPublicacion(
            id,
            votoPublicacionSocialCreateDTO.getTipo(),
            usuarioAutenticado.getUsername()
        );

        return ResponseEntity.ok(
            publicacionSocialMapper.toDTO(
                publicacionSocialService.obtenerPublicacionSocialPorId(id),
                usuarioAutenticado.getUsername(),
                esAdmin(usuarioAutenticado)
            )
        );
    }

    /**
     * Quita el voto del usuario autenticado en una publicación social
     * @param id El ID de la publicación social
     * @param usuarioAutenticado El usuario autenticado
     * @return La publicación social actualizada
     */
    @DeleteMapping("/voto/{id}")
    public ResponseEntity<PublicacionSocialResponseDTO> quitarVotoPublicacionSocial(
        @PathVariable("id") Long id,
        @AuthenticationPrincipal UserDetails usuarioAutenticado
    ) {
        votoPublicacionSocialService.quitarVoto(id, usuarioAutenticado.getUsername());

        return ResponseEntity.ok(
            publicacionSocialMapper.toDTO(
                publicacionSocialService.obtenerPublicacionSocialPorId(id),
                usuarioAutenticado.getUsername(),
                esAdmin(usuarioAutenticado)
            )
        );
    }

    /**
     * Reporta una publicación social con el usuario autenticado
     * @param id El ID de la publicación social a reportar
     * @param usuarioAutenticado El usuario autenticado
     * @return OK (200) si se registró el reporte
     */
    @PostMapping("/reporte/{id}")
    public ResponseEntity<String> reportarPublicacionSocial(
        @PathVariable("id") Long id,
        @AuthenticationPrincipal UserDetails usuarioAutenticado
    ) {
        reportePublicacionSocialService.reportarPublicacion(id, usuarioAutenticado.getUsername());

        return ResponseEntity.ok().body("Reporte registrado");
    }

    private boolean esAdmin(UserDetails usuarioAutenticado) {
        return usuarioAutenticado
            .getAuthorities()
            .stream()
            .anyMatch((autoridad) -> autoridad.getAuthority().equals("ROLE_ADMIN"));
    }
}