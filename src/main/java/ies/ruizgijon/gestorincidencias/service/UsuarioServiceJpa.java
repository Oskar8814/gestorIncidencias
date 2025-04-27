package ies.ruizgijon.gestorincidencias.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ies.ruizgijon.gestorincidencias.model.Usuario;
import ies.ruizgijon.gestorincidencias.repository.UsuarioRepository;

@Service
public class UsuarioServiceJpa implements IUsuarioService {
    // Aquí se implementan los métodos de la interfaz IUsuarioService utilizando el repositorio de usuarios
    // Inyección de dependencias para el repositorio de usuarios
    @Autowired
    private UsuarioRepository usuarioRepository;

    // Método para guardar un usuario en la base de datos
    @Override
    public void guardarUsuario(Usuario usuario) {
        // Verifica si el usuario ya existe en la base de datos
        if (usuarioRepository.existsById(usuario.getId())) {
            // Si existe, actualiza el usuario
            usuarioRepository.save(usuario);
        } else {
            // Si no existe, guarda un nuevo usuario
            usuarioRepository.save(usuario);
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
        return usuarioRepository.findById(idUsuario).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + idUsuario));
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

}
