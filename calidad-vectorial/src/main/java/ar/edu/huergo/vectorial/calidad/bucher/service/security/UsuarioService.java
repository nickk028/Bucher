package ar.edu.huergo.vectorial.calidad.bucher.service.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Rol;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import ar.edu.huergo.vectorial.calidad.bucher.repository.security.RolRepository;
import ar.edu.huergo.vectorial.calidad.bucher.repository.security.UsuarioRepository;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final RolRepository rolRepository;

    public Set<Usuario> obtenerTodosUsuarios() {
        return new HashSet<>(usuarioRepository.findAll());
    }

    public Usuario registrar(Usuario usuario, String password, String verificacionPassword) {
        if (!password.equals(verificacionPassword)) {
            throw new IllegalArgumentException("Las contraseñas no coinciden");
        }
        if (usuarioRepository.existsByUsername(usuario.getUsername())) {
            throw new IllegalArgumentException("El nombre de usuario ya está en uso");
        }

        usuario.setPassword(passwordEncoder.encode(password));
        Rol rolCliente = rolRepository.findByNombre("CLIENTE").orElseThrow(() -> new IllegalArgumentException("Rol 'CLIENTE' no encontrado"));
        usuario.setRoles(Set.of(rolCliente));
        return usuarioRepository.save(usuario);
    }

    public Usuario obtenerUsuarioPorNombre(String username) throws EntityNotFoundException {
        return usuarioRepository.findByUsername(username)
            .orElseThrow(() -> new EntityNotFoundException("Plato no encontrado"));
    }
}