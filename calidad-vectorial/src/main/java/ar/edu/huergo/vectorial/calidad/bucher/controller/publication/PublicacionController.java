package ar.edu.huergo.vectorial.calidad.bucher.controller.publication;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import ar.edu.huergo.vectorial.calidad.bucher.dto.publication.PublicacionCreateDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.publication.PublicacionResponseDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.publication.PublicacionUpdateDTO;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.Publicacion;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import ar.edu.huergo.vectorial.calidad.bucher.mapper.publication.PublicacionMapper;
import ar.edu.huergo.vectorial.calidad.bucher.service.publication.PublicacionService;
import ar.edu.huergo.vectorial.calidad.bucher.service.security.UsuarioService;

@RestController
@RequestMapping("/publicacion")
public class PublicacionController {

    @Autowired PublicacionService publicacionService;

    @Autowired UsuarioService usuarioService;

    @Autowired PublicacionMapper publicacionMapper;

    @GetMapping
    public ResponseEntity<List<PublicacionResponseDTO>> obtenerTodasLasPublicaciones() {
        return ResponseEntity.ok(
            publicacionMapper.toDTOList(publicacionService.obtenerTodasLasPublicaciones()));
    }

    @GetMapping("/propias")
    public ResponseEntity<List<PublicacionResponseDTO>> obtenerPublicacionesPorUsuario(@AuthenticationPrincipal UserDetails usuarioAutenticado) {
        Usuario usuario = usuarioService.obtenerUsuarioPorNombre(usuarioAutenticado.getUsername());

        return ResponseEntity.ok(
            publicacionMapper.toDTOList(publicacionService.obtenerPublicacionesPorUsuario(usuario)));
    }

    @PostMapping("/crear")
    public ResponseEntity<PublicacionResponseDTO> crearPublicacion(@Valid @RequestBody PublicacionCreateDTO publicacionCreateDTO,
    @AuthenticationPrincipal UserDetails usuarioAutenticado) {

        Publicacion publicacionNueva = publicacionMapper.toEntity(publicacionCreateDTO);
        Publicacion publicacionCreada = publicacionService.crearPublicacion(publicacionNueva, publicacionCreateDTO.getTitulo(), usuarioAutenticado.getUsername());

        return ResponseEntity.ok(publicacionMapper.toDTO(publicacionCreada));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarPublicacion(@PathVariable("id") Long id,
    @AuthenticationPrincipal UserDetails usuarioAutenticado) {

        Usuario usuario = usuarioService.obtenerUsuarioPorNombre(usuarioAutenticado.getUsername());
        if (UsuarioService.hasRol(usuario, "ADMIN")) {
            publicacionService.eliminarPublicacion(id);
            return ResponseEntity.ok().body("Eliminado correctamente");
        }
        if (publicacionService.verificarPublicacionUsuario(usuario,id)) {
            publicacionService.eliminarPublicacion(id);
            return ResponseEntity.ok().body("Eliminado correctamente");
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<PublicacionResponseDTO> actualizarPublicacion(@PathVariable("id") Long id,
    @Valid @RequestBody PublicacionUpdateDTO publicacionDTO,
    @AuthenticationPrincipal UserDetails usuarioAutenticado) {

        Usuario usuario = usuarioService.obtenerUsuarioPorNombre(usuarioAutenticado.getUsername());
        Publicacion publicacion = publicacionMapper.toEntityUpdate(publicacionDTO);
        Publicacion actualizada;

        if (UsuarioService.hasRol(usuario,"ADMIN")) {
            actualizada = publicacionService.modificarPublicacionAdmin(id,publicacion);
        } else {
            actualizada = publicacionService.modificarPublicacionUsuario(id, publicacion, usuario);
        }

        return ResponseEntity.ok(publicacionMapper.toDTO(actualizada));
        }

}