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
// Servicio para gestionar operaciones para la entidad usuario
public class UsuarioService {

    // Repositorios de Usuario y Rol
    private final UsuarioRepository usuarioRepository;
    // Codificador de contraseñas
    private final PasswordEncoder passwordEncoder;
    // Repositorio de Rol
    private final RolRepository rolRepository;

    // Método para obtener todos los usuarios
    public Set<Usuario> obtenerTodosUsuarios() {
        return new HashSet<>(usuarioRepository.findAll());
    }

    /*
        * Registra un nuevo usuario con la contraseña codificada y asigna el rol "LECTOR" por defecto.
        * @param usuario El usuario a registrar
        * @param password La contraseña en texto plano
        * @param verificacionPassword La verificación de la contraseña en texto plano
        * @return El usuario registrado
        * @throws IllegalArgumentException si las contraseñas no coinciden o el nombre de usuario ya está en uso
    */
    public Usuario registrar(Usuario usuario, String password, String verificacionPassword) {
        if (!password.equals(verificacionPassword)) {
            throw new IllegalArgumentException("Las contraseñas no coinciden");
        }
        if (usuarioRepository.existsByUsername(usuario.getUsername())) {
            throw new IllegalArgumentException("El nombre de usuario ya está en uso");
        }

        usuario.setPassword(passwordEncoder.encode(password));
        Rol rolCliente = rolRepository.findByNombre("LECTOR").orElseThrow(() -> new IllegalArgumentException("Rol 'CLIENTE' no encontrado"));
        usuario.setRoles(Set.of(rolCliente));
        return usuarioRepository.save(usuario);
    }

    /*
        * Obtiene un usuario por su nombre de usuario.
        * @param username El nombre de usuario del usuario a obtener
        * @return El usuario correspondiente
        * @throws EntityNotFoundException si no se encuentra el usuario
    */
    public Usuario obtenerUsuarioPorNombre(String username) throws EntityNotFoundException {
        return usuarioRepository.findByUsername(username)
            .orElseThrow(() -> new EntityNotFoundException("Plato no encontrado"));
    }
}