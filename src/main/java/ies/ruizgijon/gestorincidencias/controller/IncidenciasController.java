package ies.ruizgijon.gestorincidencias.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import ies.ruizgijon.gestorincidencias.model.EstadoIncidencia;
import ies.ruizgijon.gestorincidencias.model.Incidencia;
import ies.ruizgijon.gestorincidencias.service.IIncidenciasService;

@Controller
public class IncidenciasController {
    
    @Autowired
    private IIncidenciasService incidenciasService; // Inyección de dependencias para el servicio de incidencias

    // Aquí vamos a agregar métodos para manejar las solicitudes HTTP y llamar a los métodos del servicio de incidencias
    @GetMapping("/")
    public String listarIncidenciasPendientes(Model model) {
        // Llamar al servicio para obtener la lista de incidencias y devolverla a la vista
        List<Incidencia> incidenciasPendientes = incidenciasService.buscarPorEstado(EstadoIncidencia.PENDIENTE);
        
        // Verificar si la lista de incidencias pendientes está vacía
        if (incidenciasPendientes.isEmpty()) {
            // Si la lista está vacía, puedes agregar un mensaje al modelo para mostrarlo en la vista
            model.addAttribute("mensajeNoPendientes", "No hay incidencias pendientes.");
        }

        // Agregar la lista de incidencias al modelo para que esté disponible en la vista
        model.addAttribute("incidenciasPendientes", incidenciasPendientes);
        return "incidenciasPendientes"; // Devuelve la vista de listar incidencias pendientes
    }

    // Método para listar incidencias en progreso
    @GetMapping("/incidenciasProgreso")
    public String listarIncidenciasProgreso(Model model) {
        // Llamar al servicio para obtener la lista de incidencias en progreso y devolverla a la vista
        List<Incidencia> incidenciasProgreso = incidenciasService.buscarPorEstado(EstadoIncidencia.REPARACION);
        
        // Verificar si la lista de incidencias en progreso está vacía
        if (incidenciasProgreso.isEmpty()) {
            // Si la lista está vacía, puedes agregar un mensaje al modelo para mostrarlo en la vista
            model.addAttribute("mensajeNoProgreso", "No hay incidencias en progreso.");
        }

        // Agregar la lista de incidencias al modelo para que esté disponible en la vista
        model.addAttribute("incidenciasProgreso", incidenciasProgreso);
        return "incidenciasProgreso"; // Devuelve la vista de listar incidencias en progreso
    }

    // Método para listar incidencias cerradas
    @GetMapping("/incidenciasResueltas")
    public String listarIncidenciasResueltas(Model model) {
        // Llamar al servicio para obtener la lista de incidencias cerradas y devolverla a la vista
        List<Incidencia> incidenciasResueltas = incidenciasService.buscarPorEstado(EstadoIncidencia.RESUELTA);
        
        // Verificar si la lista de incidencias cerradas está vacía
        if (incidenciasResueltas.isEmpty()) {
            // Si la lista está vacía, puedes agregar un mensaje al modelo para mostrarlo en la vista
            model.addAttribute("mensajeNoResueltas", "No hay incidencias resueltas.");
        }

        // Agregar la lista de incidencias al modelo para que esté disponible en la vista
        model.addAttribute("incidenciasResueltas", incidenciasResueltas);
        return "incidenciasResueltas"; // Devuelve la vista de listar incidencias cerradas
    }

    @GetMapping("/admin/eliminarIncidencia/{id}")
    public String eliminarIncidencia(@PathVariable("id") int id, RedirectAttributes attributes) {
        // Llamar al servicio para eliminar la incidencia por su ID
        incidenciasService.eliminarIncidencia(id);

        // Agregar un mensaje de éxito al redirigir a la página después de eliminar la incidencia
        attributes.addFlashAttribute("confirmacionEliminar", "Incidencia eliminada con éxito.");

        return "redirect:/"; // Redirigir a la lista de incidencias después de eliminar
    }
}
