package ar.edu.huergo.vectorial.calidad.bucher.controller.publication;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;

import ar.edu.huergo.vectorial.calidad.bucher.dto.publication.PublicacionCreateDTO;
import ar.edu.huergo.vectorial.calidad.bucher.dto.publication.PublicacionResponseDTO;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.Publicacion;
import ar.edu.huergo.vectorial.calidad.bucher.mapper.publication.PublicacionMapper;
import ar.edu.huergo.vectorial.calidad.bucher.service.publication.PublicacionService;

@RestController
@RequestMapping("/publicacion")
public class PublicacionController {

    @Autowired PublicacionService publicacionService;

    @Autowired PublicacionMapper publicacionMapper;

    @PostMapping
    public ResponseEntity<PublicacionResponseDTO> crearPublicacion(@RequestBody PublicacionCreateDTO publicacionCreateDTO,
    @AuthenticationPrincipal UserDetails usuarioAutenticado) {

        Publicacion publicacionNueva = publicacionMapper.toEntity(publicacionCreateDTO);
        Publicacion publicacionCreada = publicacionService.crearPublicacion(publicacionNueva, publicacionCreateDTO.getTitulo(), usuarioAutenticado.getUsername());

        return ResponseEntity.ok(publicacionMapper.toDTO(publicacionCreada));
    }
}