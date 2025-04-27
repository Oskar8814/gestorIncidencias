package ies.ruizgijon.gestorincidencias.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import ies.ruizgijon.gestorincidencias.model.EstadoIncidencia;
import ies.ruizgijon.gestorincidencias.model.Incidencia;
import ies.ruizgijon.gestorincidencias.model.Usuario;
import ies.ruizgijon.gestorincidencias.service.IIncidenciasService;
import ies.ruizgijon.gestorincidencias.service.IUsuarioService;

@Controller
public class IncidenciasController {
    
    @Autowired
    private IIncidenciasService incidenciasService; // Inyección de dependencias para el servicio de incidencias

    @Autowired
    private IUsuarioService usuarioService; // Inyección de dependencias para el servicio de usuarios

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

    // Metodo para añadir una incidencia
    @GetMapping("/crearIncidencia")
    public String crearIncidencia(Model model) {
        // Crear una nueva instancia de Incidencia y agregarla al modelo
        Incidencia nuevaIncidencia = new Incidencia();
        List<Usuario> usuarios = usuarioService.buscarTodos(); // Obtener la lista de usuarios para el formulario

        model.addAttribute("incidencia", nuevaIncidencia);
        model.addAttribute("usuarios", usuarios); // Agregar la lista de usuarios al modelo para el formulario
        return "crearIncidenciaForm"; // Devuelve la vista para crear una nueva incidencia
    }

    // Método para guardar una nueva incidencia
    @PostMapping("/creaIncidencia/save")
    public String guardarIncidencia(Incidencia incidencia, RedirectAttributes attributes) {
        //Usuario gestor null por defecto al crear la incidencia Pendiente.
        incidencia.setUsuario(null);

        // Llamar al servicio para guardar la nueva incidencia
        incidenciasService.guardarIncidencia(incidencia);

        // Agregar un mensaje de éxito al redirigir a la página después de guardar la incidencia
        attributes.addFlashAttribute("confirmacion", "Incidencia creada o modificada con éxito.");

        return "redirect:/"; // Redirigir a la lista de incidencias después de guardar
    }

    // Método para eliminar una incidencia por su ID
    @GetMapping("/admin/eliminarIncidencia/{id}")
    public String eliminarIncidencia(@PathVariable("id") int id, RedirectAttributes attributes) {
        // Llamar al servicio para eliminar la incidencia por su ID
        incidenciasService.eliminarIncidencia(id);

        // Agregar un mensaje de éxito al redirigir a la página después de eliminar la incidencia
        attributes.addFlashAttribute("confirmacionEliminar", "Incidencia eliminada con éxito.");

        return "redirect:/"; // Redirigir a la lista de incidencias después de eliminar
    }

    // Método para editar una incidencia por su ID
    @GetMapping("/admin/editarIncidenciaPendiente/{id}")
    public String editarIncidencia(@PathVariable("id") int id, Model model) {
        // Llamar al servicio para obtener la incidencia por su ID
        Incidencia incidencia = incidenciasService.buscarIncidenciaPorId(id);
        
        // Obtener la lista de usuarios para el formulario de edición
        List<Usuario> usuarios = usuarioService.buscarTodos();

        // Agregar la incidencia y la lista de usuarios al modelo para la vista de edición
        model.addAttribute("incidencia", incidencia);
        model.addAttribute("usuarios", usuarios); // Agregar la lista de usuarios al modelo para el formulario
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
        model.addAttribute("incidencia", incidencia);
        model.addAttribute("usuarios", usuarios); // Agregar la lista de usuarios al modelo para el formulario
        return "editarIncidenciaForm"; // Devuelve la vista para editar una incidencia existente
    }

    // Método para guardar la edición de una incidencia
    @PostMapping("/admin/incidencia/edit")
    public String editarIncidencia(Incidencia incidencia, RedirectAttributes attributes) {
        // Llamar al servicio para guardar la incidencia editada
        incidenciasService.guardarIncidencia(incidencia);

        // Agregar un mensaje de éxito al redirigir a la página después de editar la incidencia
        attributes.addFlashAttribute("confirmacion", "Incidencia editada con éxito.");

        return "redirect:/"; // Redirigir a la lista de incidencias después de editar
    }

    // Método para cambiar el estado de una incidencia a "En Progreso"
    @GetMapping("/incidencia/asignar/{id}")
    public String cambiarEstadoIncidenciaProgreso(@PathVariable("id") int idIncidencia, RedirectAttributes attributes) {

        // Obtener el ID del usuario que está asignando la incidencia (esto debería ser parte de la sesión o contexto actual)
        Integer idUsuario = 1; // Cambia esto por el ID del usuario actual (por ejemplo, desde la sesión
        
        // Llamar al servicio para cambiar el estado de la incidencia a "En Progreso"
        incidenciasService.asignarIncidencia(idIncidencia, idUsuario);

        // Agregar un mensaje de éxito al redirigir a la página después de cambiar el estado
        attributes.addFlashAttribute("confirmacion", "Incidencia " + idIncidencia + " en progreso.");

        return "redirect:/incidenciasProgreso"; // Redirigir a la lista de incidencias en progreso
    }

    // Método para desasignar una incidencia (cambiar su estado a "Pendiente")
    @GetMapping("/incidencia/desasignar/{id}")
    public String desasignarIncidencia(@PathVariable("id") int idIncidencia, RedirectAttributes attributes) {
        // Llamar al servicio para desasignar la incidencia (cambiar su estado a "Pendiente")
        incidenciasService.desasignarIncidencia(idIncidencia);

        // Agregar un mensaje de éxito al redirigir a la página después de desasignar la incidencia
        attributes.addFlashAttribute("confirmacion", "Incidencia " + idIncidencia + " desasignada.");

        return "redirect:/"; // Redirigir a la lista de incidencias después de desasignar
    }

    // Método para cambiar el estado de una incidencia a "Resuelta"
    @GetMapping("/incidencia/resolver/{id}")
    public String cambiarEstadoIncidenciaResuelta(@PathVariable("id") int idIncidencia, RedirectAttributes attributes) {
        // Llamar al servicio para cambiar el estado de la incidencia a "Resuelta"
        incidenciasService.cerrarIncidencia(idIncidencia);

        // Agregar un mensaje de éxito al redirigir a la página después de cambiar el estado
        attributes.addFlashAttribute("confirmacion", "Incidencia  " + idIncidencia + "  resuelta.");

        return "redirect:/incidenciasResueltas"; // Redirigir a la lista de incidencias resueltas
    }

    
    
}
