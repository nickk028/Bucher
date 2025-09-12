package ar.edu.huergo.vectorial.calidad.bucher.controller.bookuser;

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

import ar.edu.huergo.vectorial.calidad.bucher.dto.bookuser.LibroUsuarioCreateDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.bookuser.LibroUsuarioResponseDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.bookuser.LibroUsuarioUpdateDTO;
import ar.edu.huergo.vectorial.calidad.bucher.entity.bookuser.Biblioteca;
import ar.edu.huergo.vectorial.calidad.bucher.entity.bookuser.LibroUsuario;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import ar.edu.huergo.vectorial.calidad.bucher.mapper.bookuser.LibroUsuarioMapper;
import ar.edu.huergo.vectorial.calidad.bucher.service.bookuser.BibliotecaService;
import ar.edu.huergo.vectorial.calidad.bucher.service.bookuser.LibroUsuarioService;
import ar.edu.huergo.vectorial.calidad.bucher.service.security.UsuarioService;

@RestController
@RequestMapping("/biblioteca")
public class BibliotecaController {

    @Autowired BibliotecaService bibliotecaService;

    @Autowired LibroUsuarioService libroUsuarioService;

    @Autowired LibroUsuarioMapper libroUsuarioMapper;

    @Autowired UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<LibroUsuarioResponseDTO>> obtenerBibliotecaPropia(@AuthenticationPrincipal UserDetails usuarioAutenticado) {
        Usuario usuario = usuarioService.obtenerUsuarioPorNombre(usuarioAutenticado.getUsername());

        return ResponseEntity.ok(
            libroUsuarioMapper.toDTOList(libroUsuarioService.extraerLibrosUsuario(bibliotecaService.obtenerBiblioteca(usuario.getId()))));
    }

    @GetMapping("/{posicion}")
    public ResponseEntity<LibroUsuarioResponseDTO> obtenerLibroUsuario(@PathVariable("posicion") int posicion,
    @AuthenticationPrincipal UserDetails usuarioAutenticado) {

        Usuario usuario = usuarioService.obtenerUsuarioPorNombre(usuarioAutenticado.getUsername());
        Biblioteca bibliotecaUsuario = bibliotecaService.obtenerBiblioteca(usuario.getId());

        return ResponseEntity.ok(
            libroUsuarioMapper.toDTO(bibliotecaService.obtenerLibroUsuarioPorPosicion(posicion, bibliotecaUsuario)));
    }

    @PostMapping
    public ResponseEntity<List<LibroUsuarioResponseDTO>> subirLibroUsuario(@Valid @RequestBody LibroUsuarioCreateDTO libroUsuarioCreateDTO,
    @AuthenticationPrincipal UserDetails usuarioAutenticado) {

        Usuario usuario = usuarioService.obtenerUsuarioPorNombre(usuarioAutenticado.getUsername());

        LibroUsuario libroUsuarioIngresado = libroUsuarioMapper.toEntity(libroUsuarioCreateDTO);
        Biblioteca bibliotecaActualizada = bibliotecaService.subirLibroUsuario(usuario.getId(), libroUsuarioIngresado, libroUsuarioCreateDTO.getTitulo());

        return ResponseEntity.ok(
            libroUsuarioMapper.toDTOList(libroUsuarioService.extraerLibrosUsuario(bibliotecaActualizada)));
    }

    @PutMapping("/{posicion}")
    public ResponseEntity<LibroUsuarioResponseDTO> modificarLibroUsuario(@PathVariable("posicion") int posicion, @Valid @RequestBody LibroUsuarioUpdateDTO libroUsuarioUpdateDTO,
    @AuthenticationPrincipal UserDetails usuarioAutenticado) {

        Usuario usuario = usuarioService.obtenerUsuarioPorNombre(usuarioAutenticado.getUsername());
        Biblioteca bibliotecaUsuario = bibliotecaService.obtenerBiblioteca(usuario.getId());

        LibroUsuario libroUsuarioIngresado = libroUsuarioMapper.toEntity(libroUsuarioUpdateDTO);
        LibroUsuario libroUsuarioAModificar = bibliotecaService.obtenerLibroUsuarioPorPosicion(posicion, bibliotecaUsuario);
        libroUsuarioService.modificarLibroUsuario(libroUsuarioAModificar, libroUsuarioIngresado);

        Biblioteca bibliotecaActualizada = bibliotecaService.actualizarLibroUsuario(bibliotecaUsuario);

        return ResponseEntity.ok(
            libroUsuarioMapper.toDTO(bibliotecaService.obtenerLibroUsuarioPorPosicion(posicion, bibliotecaActualizada)));
    }
}