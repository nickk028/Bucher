package ar.edu.huergo.vectorial.calidad.bucher.service.bookuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.huergo.vectorial.calidad.bucher.entity.bookuser.LibroUsuario;
import ar.edu.huergo.vectorial.calidad.bucher.repository.bookuser.LibroUsuarioRepository;

@Service
public class LibroUsuarioService {
    
    @Autowired
    private LibroUsuarioRepository libroUsuarioRepository;

    public LibroUsuario crearLibroUsuario(LibroUsuario libroUsuario) {
        return libroUsuarioRepository.save(libroUsuario);
    }
}
