package ies.ruizgijon.gestorincidencias.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ies.ruizgijon.gestorincidencias.model.Usuario;
import ies.ruizgijon.gestorincidencias.service.IUsuarioService;

@Controller
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService; // Inyección de dependencias para el servicio de usuarios

    //Metodo para listar los usuarios 
    @GetMapping("/usuario/listar")
    public String listarUsuarios(Model model) {
        // Llamar al servicio para obtener la lista de usuarios y devolverla a la vista
        List<Usuario> usuarios = usuarioService.buscarTodos();
        
        // Verificar si la lista de usuarios está vacía
        if (usuarios.isEmpty()) {
            // Si la lista está vacía, puedes agregar un mensaje al modelo para mostrarlo en la vista
            model.addAttribute("mensajeNoUsuarios", "No hay usuarios registrados.");
        }

        // Agregar la lista de usuarios al modelo para que esté disponible en la vista
        model.addAttribute("usuarios", usuarios);
        return "usuarios"; // Devuelve la vista de listar usuarios
    }

    //Metodo para eliminar un usuario por su id
    @GetMapping("/usuario/delete/{id}")
    public String eliminarUsuario(@PathVariable("id") Integer idUsuario, Model model) {
        // Verificar si el ID del usuario existe antes de intentar eliminarlo
        Usuario usuario = usuarioService.buscarUsuarioPorId(idUsuario);
        if (usuario == null) {
            // Si el usuario no existe, redirigir a la lista de usuarios y mostrar un mensaje de error
            model.addAttribute("mensajeError", "Usuario no encontrado con ID: " + idUsuario);
            return "redirect:/usuario/listar"; // Redirigir a la lista de usuarios si no se encuentra el usuario
        }
        // Llamar al servicio para eliminar el usuario por su ID
        usuarioService.eliminarUsuario(idUsuario);
        return "redirect:/usuario/listar"; // Redirigir a la lista de usuarios después de eliminar
    }

}
