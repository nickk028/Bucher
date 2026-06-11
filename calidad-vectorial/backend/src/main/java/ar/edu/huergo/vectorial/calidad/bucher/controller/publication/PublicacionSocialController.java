package ar.edu.huergo.vectorial.calidad.bucher.controller.publication;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import ar.edu.huergo.vectorial.calidad.bucher.dto.publication.PublicacionSocialResponseDTO;
import ar.edu.huergo.vectorial.calidad.bucher.mapper.publication.PublicacionSocialMapper;
import ar.edu.huergo.vectorial.calidad.bucher.service.publication.PublicacionSocialService;

@RestController
@RequestMapping("/publicacionSocial")
public class PublicacionSocialController {

    @Autowired
    private PublicacionSocialService publicacionSocialService;


    @Autowired
    private PublicacionSocialMapper publicacionSocialMapper;


    /**
    * Obtiene todas las publicaciones sociales.
    * @return Un conjunto de todas las publicaciones sociales
    */
    @GetMapping
    public ResponseEntity<List<PublicacionSocialResponseDTO>> obtenerTodasLasPublicacionesSociales() {
        return ResponseEntity.ok(
            publicacionSocialMapper.toResponseDTOList(publicacionSocialService.obtenerTodasLasPublicacionesSociales()));
    }
}