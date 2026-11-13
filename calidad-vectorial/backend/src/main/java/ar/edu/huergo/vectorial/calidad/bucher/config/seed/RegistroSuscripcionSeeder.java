package ar.edu.huergo.vectorial.calidad.bucher.config.seed;

import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import ar.edu.huergo.vectorial.calidad.bucher.repository.security.UsuarioRepository;
import ar.edu.huergo.vectorial.calidad.bucher.repository.subscription.RegistroSuscripcionRepository;
import ar.edu.huergo.vectorial.calidad.bucher.service.subscription.RegistroSuscripcionService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(12)
@RequiredArgsConstructor
public class RegistroSuscripcionSeeder implements Seeder {

    private final UsuarioRepository usuarioRepository;
    private final RegistroSuscripcionRepository registroSuscripcionRepository;
    private final RegistroSuscripcionService registroSuscripcionService;

    @Override
    public void ejecutar() {
        usuarioRepository.findAll().forEach(this::crearSiNoExiste);
    }

    private void crearSiNoExiste(Usuario usuario) {
        if (registroSuscripcionRepository.existsByUsuario(usuario)) {
            return;
        }
        registroSuscripcionService.asignarSuscripcionGratuita(usuario);
    }
}