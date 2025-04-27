package ies.ruizgijon.gestorincidencias.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ies.ruizgijon.gestorincidencias.model.Usuario;
import ies.ruizgijon.gestorincidencias.service.IUsuarioService;

@Controller
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService; // Inyección de dependencias para el servicio de usuarios

    //Metodo para listar los usuarios 
    @GetMapping("/usuarios")
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
}
