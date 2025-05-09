package ies.ruizgijon.gestorincidencias.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ies.ruizgijon.gestorincidencias.exceptions.UsuarioNoValidoException;
import ies.ruizgijon.gestorincidencias.model.PasswordResetToken;
import ies.ruizgijon.gestorincidencias.model.Usuario;
import ies.ruizgijon.gestorincidencias.repository.PasswordResetTokenRepository;
import ies.ruizgijon.gestorincidencias.repository.UsuarioRepository;
import ies.ruizgijon.gestorincidencias.util.Validaciones;
import jakarta.transaction.Transactional;

@Service
public class UsuarioServiceJpa implements IUsuarioService {
    // Aquí se implementan los métodos de la interfaz IUsuarioService utilizando el
    // repositorio de usuarios

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final PasswordResetTokenRepository tokenRepository;

    // Constructor para la inyección de dependencias
    @Autowired
    public UsuarioServiceJpa(UsuarioRepository usuarioRepository, BCryptPasswordEncoder passwordEncoder,
            PasswordResetTokenRepository tokenRepository) {
        this.usuarioRepository = usuarioRepository; // Inicializa el repositorio de usuarios
        this.passwordEncoder = passwordEncoder; // Inicializa el codificador de contraseñas
        this.tokenRepository = tokenRepository; // Inicializa el repositorio de tokens de restablecimiento de contraseña
    }

    // Método para guardar un usuario en la base de datos
    @Override
    public void guardarUsuario(Usuario usuario) {
        comprobarCorreoExistente(usuario);
        // Verifica si el usuario es válido antes de guardarlo
        List<String> errores = Validaciones.obtenerErroresValidacionUsuario(usuario);
        if (!errores.isEmpty()) {
            throw new UsuarioNoValidoException(errores);
        }

        // Codifica la contraseña del usuario antes de guardarlo
        String contrasenaCodificada = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(contrasenaCodificada);
        // Verifica si el usuario ya existe en la base de datos antes de guardarlo
        usuarioRepository.save(usuario);
    }

    // Método para modificar un usuario en la base de datos
    @Override
    public void modificarUsuario(Usuario usuario) {
        // Verifica si el usuario es válido antes de modificarlo
        List<String> errores = Validaciones.obtenerErroresValidacionUsuario(usuario);
        if (!errores.isEmpty()) {
            throw new UsuarioNoValidoException(errores);
        }
        // Verifica si el usuario ya existe en la base de datos antes de modificarlo
        if (usuarioRepository.existsById(usuario.getId())) {
            // Codifica la contraseña del usuario antes de guardarlo
            String contrasenaCodificada = passwordEncoder.encode(usuario.getPassword());
            usuario.setPassword(contrasenaCodificada);
            usuarioRepository.save(usuario);
        } else {
            throw new IllegalArgumentException("Usuario no encontrado con ID: " + usuario.getId());
        }
    }

    // Método para eliminar un usuario por su ID
    @Override
    public void eliminarUsuario(Integer idUsuario) {
        // Verifica si el usuario existe antes de eliminarlo
        if (usuarioRepository.existsById(idUsuario)) {
            usuarioRepository.deleteById(idUsuario);
        } else {
            throw new IllegalArgumentException("Usuario no encontrado con ID: " + idUsuario);
        }
    }

    // Método para buscar un usuario por su ID
    @Override
    public Usuario buscarUsuarioPorId(Integer idUsuario) {
        // Busca el usuario por su ID y devuelve el resultado
        return usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + idUsuario));
    }

    // Método para buscar un usuario por su nombre
    @Override
    public Usuario buscarUsuarioPorNombre(String nombreUsuario) {
        Usuario usuario = usuarioRepository.findByNombre(nombreUsuario).orElse(null);
        // Verifica si el usuario existe antes de devolverlo
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario no encontrado con nombre: " + nombreUsuario);
        }
        // Si existe, devuelve el usuario. Si no existe, lanza una excepción
        return usuario;
    }

    // Método para buscar todos los usuarios
    @Override
    public List<Usuario> buscarTodos() {
        // Devuelve todos los usuarios de la base de datos
        return usuarioRepository.findAll();
    }

    // Método para buscar un usuario por su correo electrónico
    @Override
    public Usuario buscarUsuarioPorMail(String mail) {
        return usuarioRepository.findByMail(mail).orElse(null);
    }

    private void comprobarCorreoExistente(Usuario usuario) {
        if (usuarioRepository.findByMail(usuario.getMail()).isPresent()) {
            List<String> errores = new ArrayList<>();
            errores.add("El correo electrónico ya está registrado en el sistema.");
            throw new UsuarioNoValidoException(errores);
        }
    }

    @Override
    public Usuario getCurrentUser() {
        // Obtiene el contexto de seguridad actual
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Verifica si el usuario está autenticado y no es anónimo
        String correo = authentication.getName(); // el username es el correo

        return usuarioRepository.findByMail(correo).orElse(null);
    }

    @Override
    public void cambiarContrasena(Integer id, String contrasena) {
        // Busca el usuario por su ID
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario no encontrado con ID: " + id);
        }

        usuario.setPassword(contrasena); // Establece la nueva contraseña

        // Verifica si el usuario es válido antes de modificarlo
        List<String> errores = Validaciones.obtenerErroresValidacionUsuario(usuario);
        if (!errores.isEmpty()) {
            throw new UsuarioNoValidoException(errores);
        }

        // Codifica la nueva contraseña del usuario antes de guardarlo
        String contrasenaCodificada = passwordEncoder.encode(contrasena);
        usuario.setPassword(contrasenaCodificada);

        // Guarda el usuario modificado en la base de datos
        usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public void guardarTokenDeRecuperacion(Usuario usuario, String token) {
        tokenRepository.deleteByUsuario(usuario); // Elimina cualquier token existente del user
        PasswordResetToken prt = new PasswordResetToken();
        prt.setToken(token); // Asignacion del token
        prt.setUsuario(usuario); // Asignamos el usuario al token
        prt.setFechaExpiracion(LocalDateTime.now().plusHours(1)); // Token valido para 1 hora
        tokenRepository.save(prt);
    }

    @Override
    public boolean validarToken(String token) {
        return tokenRepository.findByToken(token)
                .map(t -> !t.isExpirado())
                .orElse(false);
    }

    @Override
    @Transactional
    public boolean actualizarPasswordConToken(String token, String nuevaPassword) {
        Optional<PasswordResetToken> optional = tokenRepository.findByToken(token);
        // Verificamos si el token existe y no ha expirado
        if (optional.isPresent() && !optional.get().isExpirado()) {
            Usuario usuario = optional.get().getUsuario(); // Obtener el usuario asociado al token

            usuario.setPassword(nuevaPassword);
            // Verifica si el usuario es válido antes de modificarlo
            List<String> errores = Validaciones.obtenerErroresValidacionUsuario(usuario);
            if (!errores.isEmpty()) {
                throw new UsuarioNoValidoException(errores);
            }

            usuario.setPassword(passwordEncoder.encode(nuevaPassword));
            usuarioRepository.save(usuario);
            tokenRepository.delete(optional.get()); // Elimina el token tras usarlo
            return true;
        }
        return false;
    }

    // Método para buscar usuarios por example
    @Override
    public List<Usuario> buscarByExample(Example<Usuario> example) {
        return usuarioRepository.findAll(example);
    }

}
