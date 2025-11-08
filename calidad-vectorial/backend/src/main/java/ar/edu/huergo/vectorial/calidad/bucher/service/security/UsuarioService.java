package ar.edu.huergo.vectorial.calidad.bucher.service.security;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ar.edu.huergo.vectorial.calidad.bucher.entity.bookuser.Biblioteca;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Avatar;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Rol;
import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Usuario;
import ar.edu.huergo.vectorial.calidad.bucher.repository.security.RolRepository;
import ar.edu.huergo.vectorial.calidad.bucher.repository.security.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

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

    /**
     * Método para obtener todos los usuarios
     * @return Un HashSet de todos los usuarios
     */
    public Set<Usuario> obtenerTodosUsuarios() {
        return new HashSet<>(usuarioRepository.findAll());
    }

    /**
     * Obtiene los detalles del usuario actualmente autenticado.
     * @return Los detalles del usuario autenticado o null si no hay ninguno.
     */
    public UserDetails getUserDetailsActual() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            return userDetails;
        }
        return null;
    }

    /**
     * Registra un nuevo usuario con la contraseña codificada y asigna el rol "LECTOR" por defecto.
     * @param usuario El usuario a registrar
     * @param password password La contraseña en texto plano
     * @param verificacionPassword La verificación de la contraseña en texto plano
     * @return El usuario registrado
     * @throws IllegalArgumentException Si no existe el rol LECTOR al momento de asignarselo al usuario
     */
    public Usuario registrar(Usuario usuario, String password, String verificacionPassword) {
        if (!password.equals(verificacionPassword)) {
            throw new IllegalArgumentException("Las contraseñas no coinciden");
        }
        if (usuarioRepository.existsByUsername(usuario.getUsername())) {
            throw new IllegalArgumentException("El nombre de usuario ya está en uso");
        }

        usuario.setPassword(passwordEncoder.encode(password));
        Rol rolCliente = rolRepository.findByNombre("LECTOR").orElseThrow(() -> new IllegalArgumentException("Rol 'LECTOR' no encontrado"));
        Biblioteca biblioteca = new Biblioteca();
        usuario.setRoles(Set.of(rolCliente));
        usuario.setBiblioteca(biblioteca);
        biblioteca.setUsuario(usuario);
        usuario.setAvatar(getAvatarRandom());
        return usuarioRepository.save(usuario);
    }

    /**
    * Obtiene un usuario por su nombre de usuario.
    * @param username El nombre de usuario del usuario a obtener
    * @return El usuario correspondiente
    * @throws EntityNotFoundException si no se encuentra el usuario
    */
    public Usuario obtenerUsuarioPorNombre(String username) throws EntityNotFoundException {
        return usuarioRepository.findByUsername(username)
            .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
    }

    /**
     * Verifica si un usuario tiene un rol específico
     * @param usuario El usuario a verificar
     * @param rolIngresado El rol a verificar
     * @return true si el usuario tiene el rol, false en caso contrario
     */
    public static boolean hasRol(Usuario usuario, String rolIngresado) {
        ArrayList<Rol> roles = new ArrayList<>(usuario.getRoles());
        for (Rol rolUsuario : roles) {
            String nombreRol = rolUsuario.getNombre();
            if (nombreRol.equals(rolIngresado)) {
                return true;
            }
        }
        return false;
    }

    // Inicializa el random
    private static final Random random = new Random();

    public static Avatar getAvatarRandom() {
        Avatar[] avatares = Avatar.values();
        int indiceAleatorio = random.nextInt(avatares.length);
        if (indiceAleatorio >= avatares.length) {
            indiceAleatorio = avatares.length - 1;
        }
        return (avatares[indiceAleatorio]);
    }

    public Usuario modificarUsuario(Usuario usuarioAModificar, Usuario usuarioNuevo) {
        if (usuarioNuevo.getPronombres() != null) {
            usuarioAModificar.setPronombres(usuarioNuevo.getPronombres());
        }
        if (usuarioNuevo.getDescripcion() != null) {
            usuarioAModificar.setDescripcion(usuarioNuevo.getDescripcion());
        }
        if (usuarioNuevo.getNickname() != null) {
            usuarioAModificar.setNickname(usuarioNuevo.getNickname());
        }
        if (usuarioNuevo.getDireccion() != null) {
            usuarioAModificar.setDireccion(usuarioNuevo.getDireccion());
        }
        if (usuarioNuevo.getPiso() != null) {
            usuarioAModificar.setPiso(usuarioNuevo.getPiso());
        }
        if (usuarioNuevo.getCodigoPostal() != null) {
            usuarioAModificar.setCodigoPostal(usuarioNuevo.getCodigoPostal());
        }

        return usuarioRepository.save(usuarioAModificar);
    }
}