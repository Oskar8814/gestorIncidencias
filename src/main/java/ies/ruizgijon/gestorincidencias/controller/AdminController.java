package ies.ruizgijon.gestorincidencias.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ies.ruizgijon.gestorincidencias.model.Incidencia;
import ies.ruizgijon.gestorincidencias.model.Usuario;
import ies.ruizgijon.gestorincidencias.service.IIncidenciasService;
import ies.ruizgijon.gestorincidencias.service.IUsuarioService;
import ies.ruizgijon.gestorincidencias.util.GConstants;

@Controller // Anotación para indicar que esta clase es un controlador de Spring
public class AdminController {
    
    private final IIncidenciasService incidenciasService; // Inyección de dependencias para el servicio de incidencias
    private final IUsuarioService usuarioService; // Inyección de dependencias para el servicio de usuarios

    @Autowired
    public AdminController( IIncidenciasService incidenciasService, IUsuarioService usuarioService){
        this.incidenciasService = incidenciasService;
        this.usuarioService = usuarioService;
    }

    // Método para eliminar una incidencia por su ID
    @GetMapping("/admin/eliminarIncidencia/{id}")
    public String eliminarIncidencia(@PathVariable("id") int id, RedirectAttributes attributes) {
        // Llamar al servicio para eliminar la incidencia por su ID
        incidenciasService.eliminarIncidencia(id);

        // Agregar un mensaje de éxito al redirigir a la página después de eliminar la incidencia
        attributes.addFlashAttribute(GConstants.ATTRIBUTE_CONFIRMACION, "Incidencia "+ id +" eliminada con éxito.");

        return "redirect:/incidencias/index"; // Redirigir a la lista de incidencias después de eliminar
    }

    // Método para editar una incidencia por su ID
    @GetMapping("/admin/editarIncidenciaPendiente/{id}")
    public String editarIncidencia(@PathVariable("id") int id, Model model) {
        // Llamar al servicio para obtener la incidencia por su ID
        Incidencia incidencia = incidenciasService.buscarIncidenciaPorId(id);
        
        // Obtener la lista de usuarios para el formulario de edición
        List<Usuario> usuarios = usuarioService.buscarTodos();

        // Agregar la incidencia y la lista de usuarios al modelo para la vista de edición
        model.addAttribute(GConstants.ATTRIBUTE_INCIDENCIA, incidencia);
        model.addAttribute(GConstants.ATTRIBUTE_USUARIOS, usuarios); // Agregar la lista de usuarios al modelo para el formulario
        return "crearIncidenciaForm"; // Devuelve la vista para editar una incidencia existente
    }

    // Método para editar una incidencia en progreso por su ID
    @GetMapping("/admin/editarIncidencia/{id}")
    public String editarIncidenciaProgreso(@PathVariable("id") int id, Model model) {
        // Llamar al servicio para obtener la incidencia por su ID
        Incidencia incidencia = incidenciasService.buscarIncidenciaPorId(id);
        
        // Obtener la lista de usuarios para el formulario de edición
        List<Usuario> usuarios = usuarioService.buscarTodos();

        // Agregar la incidencia y la lista de usuarios al modelo para la vista de edición
        model.addAttribute(GConstants.ATTRIBUTE_INCIDENCIA, incidencia);
        model.addAttribute(GConstants.ATTRIBUTE_USUARIOS, usuarios); // Agregar la lista de usuarios al modelo para el formulario
        return "editarIncidenciaForm"; // Devuelve la vista para editar una incidencia existente
    }

    // Método para guardar la edición de una incidencia
    @PostMapping("/admin/incidencia/edit")
    public String editarIncidencia(Incidencia incidencia, RedirectAttributes attributes) {
        // Llamar al servicio para guardar la incidencia editada
        incidenciasService.guardarIncidencia(incidencia);

        // Agregar un mensaje de éxito al redirigir a la página después de editar la incidencia
        attributes.addFlashAttribute(GConstants.ATTRIBUTE_CONFIRMACION, "Incidencia editada con éxito.");

        return "redirect:/incidencias/index"; // Redirigir a la lista de incidencias después de editar
    }

    @ModelAttribute()
    public void setGenericos(Model model) {
        Usuario usuario = usuarioService.getCurrentUser(); //Obtener el usuario actualmente logeado

        model.addAttribute(GConstants.ATTRIBUTE_CURRENTUSER, usuario); // Agregar el usuario actual al modelo para la vista
    }

}
