package ies.ruizgijon.gestorincidencias.service;

import java.util.List;

import ies.ruizgijon.gestorincidencias.model.Usuario;

public interface IUsuarioService {
    void guardarUsuario(Usuario usuario);
    void eliminarUsuario(Integer idUsuario); // Cambiado a integer para que coincida con el tipo de id en la clase Usuario
    Usuario buscarUsuarioPorId(Integer idUsuario);
    Usuario buscarUsuarioPorNombre(String nombreUsuario); // Cambiado a String para que coincida con el tipo de nombre en la clase Usuario
    List<Usuario> buscarTodos(); // Cambiado a String para que coincida con el tipo de nombre en la clase Usuario
    
}
