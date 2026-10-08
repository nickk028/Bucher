package ar.edu.huergo.vectorial.calidad.bucher.config.seed;

import ar.edu.huergo.vectorial.calidad.bucher.config.seed.dto.UsuarioSeed;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Rol;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import ar.edu.huergo.vectorial.calidad.bucher.repository.security.RolRepository;
import ar.edu.huergo.vectorial.calidad.bucher.repository.security.UsuarioRepository;
import ar.edu.huergo.vectorial.calidad.bucher.util.PasswordValidator;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Order(2)
@RequiredArgsConstructor
public class UsuarioSeeder implements Seeder {

    private static final String ARCHIVO = "seed/usuarios.json";

    private final SeedDataReader reader;
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder encoder;

    @Override
    public void ejecutar() {
        reader.leer(ARCHIVO, UsuarioSeed.class).forEach(this::crearSiNoExiste);
    }

    private void crearSiNoExiste(UsuarioSeed seed) {
        if (usuarioRepository.existsByUsername(seed.username())) {
            return;
        }
        PasswordValidator.validate(seed.password());

        Usuario usuario = new Usuario(seed.username(), encoder.encode(seed.password()));
        usuario.setNickname(seed.nickname());
        usuario.setAvatar(seed.avatar());
        usuario.setRoles(buscarRoles(seed.roles()));
        usuario.setPronombres(seed.pronombres());
        usuario.setDescripcion(seed.descripcion());
        usuario.setDireccion(seed.direccion());
        usuario.setPiso(seed.piso());
        usuario.setCodigoPostal(seed.codigoPostal());
        usuarioRepository.save(usuario);
    }

    private Set<Rol> buscarRoles(Set<String> nombres) {
        return nombres
            .stream()
            .map((nombre) ->
                rolRepository
                    .findByNombre(nombre)
                    .orElseThrow(() -> new IllegalStateException("Rol de seed inexistente: " + nombre))
            )
            .collect(Collectors.toSet());
    }
}