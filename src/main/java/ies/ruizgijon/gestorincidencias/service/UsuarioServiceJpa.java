package ies.ruizgijon.gestorincidencias.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ies.ruizgijon.gestorincidencias.exceptions.UsuarioNoValidoException;
import ies.ruizgijon.gestorincidencias.model.Usuario;
import ies.ruizgijon.gestorincidencias.repository.UsuarioRepository;
import ies.ruizgijon.gestorincidencias.util.Validaciones;

@Service
public class UsuarioServiceJpa implements IUsuarioService {
    // Aquí se implementan los métodos de la interfaz IUsuarioService utilizando el
    // repositorio de usuarios
    // Inyección de dependencias para el repositorio de usuarios
    @Autowired
    private UsuarioRepository usuarioRepository;

    // Inyección de dependencias para el codificador de contraseñas
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Método para guardar un usuario en la base de datos
    @Override
    public void guardarUsuario(Usuario usuario) {
        comprobarCorreoExistente(usuario);
        // Verifica si el usuario es válido antes de guardarlo
        if (!Validaciones.validarUsuario(usuario)) {
            throw new UsuarioNoValidoException("El usuario no es válido");
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
        if (!Validaciones.validarUsuario(usuario)) {
            throw new UsuarioNoValidoException("El usuario no es válido");
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

    // Metodo para modificar la contraseña de un usuario
    @Override
    public void modificarContrasena(Usuario usuario) {
        // Verifica si el usuario es válido antes de modificarlo
        if (!Validaciones.validarUsuario(usuario)) {
            throw new UsuarioNoValidoException("El usuario no es válido");
        }
        // Codifica la nueva contraseña del usuario antes de guardarlo
        String contrasenaCodificada = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(contrasenaCodificada);
        // Guarda el usuario modificado en la base de datos
        usuarioRepository.save(usuario);
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
        // Busca el usuario por su correo electrónico y devuelve el resultado
        Usuario usuario = usuarioRepository.findByMail(mail).orElse(null);
        return usuario;
    }

    private void comprobarCorreoExistente(Usuario usuario) {
        if (usuarioRepository.findByMail(usuario.getMail()).isPresent()) {
            // logger.error("Se intenta registrar un usuario con un correo ya existente");
            throw new UsuarioNoValidoException("El correo ya existe");
        }
    }

    @Override
    public Usuario getCurrentUser() {
        // Obtiene el contexto de seguridad actual
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Verifica si el usuario está autenticado y no es anónimo
        String correo = authentication.getName(); // el username es el correo
        
        Usuario usuario = usuarioRepository.findByMail(correo).orElse(null);

        return usuario;
    }

    @Override
    public void cambiarContrasena(Integer id, String contrasena) {
        // Busca el usuario por su ID
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario no encontrado con ID: " + id);
        }
        // Codifica la nueva contraseña del usuario antes de guardarlo
        String contrasenaCodificada = passwordEncoder.encode(contrasena);
        usuario.setPassword(contrasenaCodificada);
        // Guarda el usuario modificado en la base de datos
        usuarioRepository.save(usuario);
    }

}
