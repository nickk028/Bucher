package ar.edu.huergo.vectorial.calidad.bucher.service.publication;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.huergo.vectorial.calidad.bucher.repository.publication.PublicacionSocialRepository;
import ar.edu.huergo.vectorial.calidad.bucher.entity.publication.PublicacionSocial;

//Clase que maneja la lógica de Publicacion
@Service
public class PublicacionSocialService {
    @Autowired
    private PublicacionSocialRepository publicacionSocialRepository;

    /**
    * Obtiene todas las publicaciones sociales
    * @return Un conjunto de todas las publicaciones sociales
    */
    public Set<PublicacionSocial> obtenerTodasLasPublicacionesSociales() {
        return new HashSet<>(publicacionSocialRepository.findAll());
    }
}
