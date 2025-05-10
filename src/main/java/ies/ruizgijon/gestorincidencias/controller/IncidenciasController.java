package ies.ruizgijon.gestorincidencias.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import ies.ruizgijon.gestorincidencias.model.EstadoIncidencia;
import ies.ruizgijon.gestorincidencias.model.Incidencia;
import ies.ruizgijon.gestorincidencias.model.Nota;
import ies.ruizgijon.gestorincidencias.model.Usuario;
import ies.ruizgijon.gestorincidencias.service.IIncidenciasService;
import ies.ruizgijon.gestorincidencias.service.INotaService;
import ies.ruizgijon.gestorincidencias.service.IUsuarioService;
import ies.ruizgijon.gestorincidencias.util.GConstants;

@Controller
@RequestMapping(value="/incidencias")
public class IncidenciasController {
    

    private final IIncidenciasService incidenciasService; 
    private final IUsuarioService usuarioService; 
    private final INotaService notaService;

    // Constructor para la inyección de dependencias
    @Autowired
    public IncidenciasController(IIncidenciasService incidenciasService, IUsuarioService usuarioService, INotaService notaService) {
        this.incidenciasService = incidenciasService;
        this.usuarioService = usuarioService;
        this.notaService = notaService;
    }

    // Aquí vamos a agregar métodos para manejar las solicitudes HTTP y llamar a los métodos del servicio de incidencias
    // Método para listar incidencias pendientes
    @GetMapping("/index")
    public String listarIncidenciasPendientes(Model model) {
        // Llamar al servicio para obtener la lista de incidencias y devolverla a la vista
        List<Incidencia> incidenciasPendientes = incidenciasService.buscarPorEstado(EstadoIncidencia.PENDIENTE);
        
        // Verificar si la lista de incidencias pendientes está vacía
        if (incidenciasPendientes.isEmpty()) {
            // Si la lista está vacía, puedes agregar un mensaje al modelo para mostrarlo en la vista
            model.addAttribute(GConstants.ATTRIBUTE_MESSAGENOPENDIENTE, "No hay incidencias pendientes.");
        }

        // Agregar la lista de incidencias al modelo para que esté disponible en la vista
        model.addAttribute(GConstants.ATTRIBUTE_INCIDENCIASPENDIENTES, incidenciasPendientes);
        return GConstants.VIEW_INCIDENCIASPENDIENTES; // Devuelve la vista de listar incidencias pendientes
    }

    //Metodo para listar la busqueda en las incidencias pendientes 
    @PostMapping("/incidenciasPendientes/buscar")
    public String buscarIncidenciasPendientes(@ModelAttribute ("search") Incidencia busqueda, Model model) {
        // Llamar al servicio para obtener la lista de incidencias pendientes que coinciden con la búsqueda
        busqueda.setEstado(EstadoIncidencia.PENDIENTE); // Establecer el estado de búsqueda a PENDIENTE
        
        //Personaliza el tipo de busqueda
        ExampleMatcher matcher = ExampleMatcher.matching().withMatcher(GConstants.FIELD_TITULO, ExampleMatcher.GenericPropertyMatchers.contains());

        Example<Incidencia> example = Example.of(busqueda, matcher);
        List<Incidencia> incidenciasPendientes = incidenciasService.buscarByExample(example); // Buscar incidencias pendientes que coinciden con el ejemplo

        // Verificar si la lista de incidencias pendientes está vacía
        if (incidenciasPendientes.isEmpty()) {
            // Si la lista está vacía, agregar un mensaje al modelo para mostrarlo en la vista
            model.addAttribute(GConstants.ATTRIBUTE_MESSAGENOPENDIENTE, "No hay incidencias pendientes con el título indicado.");
        }

        // Agregar la lista de incidencias al modelo para que esté disponible en la vista
        model.addAttribute(GConstants.ATTRIBUTE_INCIDENCIASPENDIENTES, incidenciasPendientes);
        return GConstants.VIEW_INCIDENCIASPENDIENTES; // Devuelve la vista de listar incidencias pendientes
    }

    // Método para listar incidencias en progreso
    @GetMapping("/incidenciasProgreso")
    public String listarIncidenciasProgreso(Model model) {
        // Llamar al servicio para obtener la lista de incidencias en progreso y devolverla a la vista
        List<Incidencia> incidenciasProgreso = incidenciasService.buscarPorEstado(EstadoIncidencia.REPARACION);
        
        // Verificar si la lista de incidencias en progreso está vacía
        if (incidenciasProgreso.isEmpty()) {
            // Si la lista está vacía, puedes agregar un mensaje al modelo para mostrarlo en la vista
            model.addAttribute(GConstants.ATTRIBUTE_MESSAGENOPROGRESO, "No hay incidencias en progreso.");
        }

        // Agregar la lista de incidencias al modelo para que esté disponible en la vista
        model.addAttribute(GConstants.ATTRIBUTE_INCIDENCIASPROGRESO, incidenciasProgreso);
        return GConstants.VIEW_INCIDENCIASPROGRESO ; // Devuelve la vista de listar incidencias en progreso
    }

    //Metodo para listar la busqueda en las incidencias en progreso 
    @PostMapping("/incidenciasProgreso/buscar")
    public String buscarIncidenciasProgreso(@ModelAttribute ("search") Incidencia busqueda, Model model) {

        busqueda.setEstado(EstadoIncidencia.REPARACION); // Establecer el estado de búsqueda a PENDIENTE

        //Personalizar el tipo de busqueda
        ExampleMatcher matcher = ExampleMatcher.matching().withMatcher(GConstants.FIELD_TITULO, ExampleMatcher.GenericPropertyMatchers.contains());

        Example<Incidencia> example = Example.of(busqueda, matcher);
        List<Incidencia> incidenciasProgreso = incidenciasService.buscarByExample(example); // Buscar incidencias en progreso que coinciden con el ejemplo

        // Verificar si la lista de incidencias en progreso está vacía
        if (incidenciasProgreso.isEmpty()) {
            // Si la lista está vacía, agregar un mensaje al modelo para mostrarlo en la vista
            model.addAttribute(GConstants.ATTRIBUTE_MESSAGENOPROGRESO, "No hay incidencias en reparación con el título y/o encargado indicado.");
        }

        // Agregar la lista de incidencias al modelo para que esté disponible en la vista
        model.addAttribute(GConstants.ATTRIBUTE_INCIDENCIASPROGRESO, incidenciasProgreso);
        return GConstants.VIEW_INCIDENCIASPROGRESO; // Devuelve la vista de listar incidencias en progreso
    }

    // Método para listar incidencias cerradas
    @GetMapping("/incidenciasResueltas")
    public String listarIncidenciasResueltas(Model model,Pageable page) {
        // Llamar al servicio para obtener la lista de incidencias cerradas y devolverla a la vista
        
        Page<Incidencia> incidenciasResueltas = incidenciasService.buscarIncidenciasPorEstadoPaginadas(EstadoIncidencia.RESUELTA, page);
        // Verificar si la lista de incidencias cerradas está vacía
        if (incidenciasResueltas.isEmpty()) {
            // Si la lista está vacía, puedes agregar un mensaje al modelo para mostrarlo en la vista
            model.addAttribute(GConstants.ATTRIBUTE_MESSAGENORESUELTA, "No hay incidencias resueltas.");
        }

        // Agregar la lista de incidencias al modelo para que esté disponible en la vista
        model.addAttribute(GConstants.ATTRIBUTE_INCIDENCIASRESUELTAS, incidenciasResueltas);
        return GConstants.VIEW_INCIDENCIASRESUELTAS; // Devuelve la vista de listar incidencias cerradas
    }

    //Metodo para listar la busqueda en las incidencias en progreso 
    @PostMapping("/incidenciasResueltas/buscar")
    public String buscarIncidenciasResueltas(@ModelAttribute ("search") Incidencia busqueda, Model model,Pageable page) {

        busqueda.setEstado(EstadoIncidencia.RESUELTA); // Establecer el estado de búsqueda a RESUELTA
        
        //Personalizar el tipo de busqueda
        ExampleMatcher matcher = ExampleMatcher.matching().withMatcher(GConstants.FIELD_TITULO, ExampleMatcher.GenericPropertyMatchers.contains());
        
        Example<Incidencia> example = Example.of(busqueda, matcher);
        
        Page<Incidencia> incidenciasResueltas = incidenciasService.buscarIncidenciasByExamplePaginadas(example, page);// Buscar incidencias resueltas que coinciden con el ejemplo
        // Verificar si la lista de incidencias en progreso está vacía
        if (incidenciasResueltas.isEmpty()) {
            // Si la lista está vacía, agregar un mensaje al modelo para mostrarlo en la vista
            model.addAttribute(GConstants.ATTRIBUTE_MESSAGENORESUELTA, "No hay incidencias resueltas con el título indicado.");
        }

        // Agregar la lista de incidencias al modelo para que esté disponible en la vista
        model.addAttribute(GConstants.ATTRIBUTE_INCIDENCIASRESUELTAS, incidenciasResueltas);
        return GConstants.VIEW_INCIDENCIASRESUELTAS; // Devuelve la vista de listar incidencias resueltas
    }

    // Metodo para añadir una incidencia
    @GetMapping("/crearIncidencia")
    public String crearIncidencia(Model model) {
        // Crear una nueva instancia de Incidencia y agregarla al modelo
        Incidencia nuevaIncidencia = new Incidencia();

        model.addAttribute(GConstants.ATTRIBUTE_INCIDENCIA, nuevaIncidencia);

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
        attributes.addFlashAttribute(GConstants.ATTRIBUTE_CONFIRMACION, "Incidencia creada o modificada con éxito.");

        return "redirect:/incidencias/index"; // Redirigir a la lista de incidencias después de guardar
    }

    // Método para cambiar el estado de una incidencia a "En Progreso"
    @GetMapping("/incidencia/asignar/{id}")
    public String cambiarEstadoIncidenciaProgreso(@PathVariable("id") int idIncidencia, RedirectAttributes attributes) {

        // Obtener el ID del usuario que está asignando la incidencia (esto debería ser parte de la sesión o contexto actual)
        Usuario usuario = usuarioService.getCurrentUser(); //Obtener el usuario actualmente logeado

        Integer idUsuario = usuario.getId(); // Cambia esto por el ID del usuario actual (por ejemplo, desde la sesión
        
        // Llamar al servicio para cambiar el estado de la incidencia a "En Progreso"
        incidenciasService.asignarIncidencia(idIncidencia, idUsuario);

        // Agregar un mensaje de éxito al redirigir a la página después de cambiar el estado
        attributes.addFlashAttribute(GConstants.ATTRIBUTE_CONFIRMACION, "Incidencia " + idIncidencia + " en progreso.");

        return "redirect:/incidencias/incidenciasProgreso"; // Redirigir a la lista de incidencias en progreso
    }

    // Método para desasignar una incidencia (cambiar su estado a "Pendiente")
    @GetMapping("/incidencia/desasignar/{id}")
    public String desasignarIncidencia(@PathVariable("id") int idIncidencia, RedirectAttributes attributes) {
        // Llamar al servicio para desasignar la incidencia (cambiar su estado a "Pendiente")
        incidenciasService.desasignarIncidencia(idIncidencia);

        // Agregar un mensaje de éxito al redirigir a la página después de desasignar la incidencia
        attributes.addFlashAttribute(GConstants.ATTRIBUTE_CONFIRMACION, "Incidencia " + idIncidencia + " desasignada.");

        return "redirect:/incidencias/index"; // Redirigir a la lista de incidencias después de desasignar
    }

    // Método para cambiar el estado de una incidencia a "Resuelta"
    @GetMapping("/incidencia/resolver/{id}")
    public String cambiarEstadoIncidenciaResuelta(@PathVariable("id") int idIncidencia, RedirectAttributes attributes) {
        // Llamar al servicio para cambiar el estado de la incidencia a "Resuelta"
        incidenciasService.cerrarIncidencia(idIncidencia);

        // Agregar un mensaje de éxito al redirigir a la página después de cambiar el estado
        attributes.addFlashAttribute(GConstants.ATTRIBUTE_CONFIRMACION, "Incidencia  " + idIncidencia + "  resuelta.");

        return "redirect:/incidencias/incidenciasResueltas"; // Redirigir a la lista de incidencias resueltas
    }

    // Metodo para crear una nota en la incidencia
    @GetMapping("/incidencia/crearNota/{id}")
    public String crearNota(@PathVariable("id") int idIncidencia, Model model) {
        // Crear una nueva instancia de Nota y agregarla al modelo
        Nota nuevaNota = new Nota();
        Incidencia incidencia = incidenciasService.buscarIncidenciaPorId(idIncidencia); // Obtener la incidencia por su ID

        nuevaNota.setIncidencia(incidencia); // Establecer la incidencia en la nota

        model.addAttribute("nuevaNota", nuevaNota); // Agregar la nueva nota al modelo
        model.addAttribute(GConstants.ATTRIBUTE_INCIDENCIA, incidencia); // Agregar la incidencia al modelo
        model.addAttribute("notas", notaService.obtenerNotasPorIncidencia(idIncidencia)); // Obtener y agregar las notas de la incidencia al modelo

        return "verNotas"; // Devuelve la vista para crear una nueva nota
    }

    // Método para guardar una nueva nota en la incidencia
    @PostMapping("/incidencia/crearNota/save")
    public String guardarNota(@ModelAttribute("nuevaNota") Nota nota, RedirectAttributes attributes) {
        // Obtener el ID del usuario que está creando la nota (esto debería ser parte de la sesión o contexto actual)
        Usuario usuario = usuarioService.getCurrentUser(); //Obtener el usuario actualmente logeado

        Integer idUsuario = usuario.getId(); // Cambia esto por el ID del usuario actual (por ejemplo, desde la sesión)

        // Obtener la incidencia a la que se le va a agregar la nota
        Integer idIncidencia = nota.getIncidencia().getId(); // Obtener el ID de la incidencia desde la nota
        
        // Llamar al servicio para guardar la nueva nota
        notaService.crearNota(idIncidencia, nota.getContenido(), idUsuario);

        // Agregar un mensaje de éxito al redirigir a la página después de guardar la nota
        attributes.addFlashAttribute(GConstants.ATTRIBUTE_CONFIRMACION, "Nota creada con éxito.");

        return "redirect:/incidencias/incidencia/crearNota/" + idIncidencia; // Redirigir a la vista de notas de la incidencia
    }

    @GetMapping("/incidencia/eliminarNota/{id}")
    public String eliminarNota(@PathVariable("id") int idNota, RedirectAttributes attributes) {
        // Obtener el usuario actualmente autenticado
        Usuario usuarioActual = usuarioService.getCurrentUser();

        // Obtener la nota a eliminar
        Nota nota = notaService.obtenerNotaPorId(idNota);
        if (nota == null) {
            attributes.addFlashAttribute(GConstants.ATTRIBUTE_MESSAGEERROR, "Nota no encontrada.");
            return "redirect:/incidencias";
        }

        // Verificar si el usuario es el autor o tiene rol ADMIN
        boolean esAutor = nota.getAutor().getId().equals(usuarioActual.getId());
        boolean esAdmin = usuarioActual.getRol().getName().equalsIgnoreCase("ADMIN");

        if (!esAutor && !esAdmin) {
            attributes.addFlashAttribute(GConstants.ATTRIBUTE_MESSAGEERROR, "No tienes permiso para eliminar esta nota.");
            return "redirect:/incidencias/incidencia/crearNota/" + nota.getIncidencia().getId();
        }

        // Obtener el ID de la incidencia antes de eliminar
        Integer idIncidencia = nota.getIncidencia().getId();

        // Eliminar la nota
        notaService.eliminarNota(idNota);

        attributes.addFlashAttribute(GConstants.ATTRIBUTE_CONFIRMACION, "Nota eliminada con éxito.");
        return "redirect:/incidencias/incidencia/crearNota/" + idIncidencia;
    }


    
    @ModelAttribute()
    public void setGenericos(Model model) {
        Incidencia incidenciaSearch = new Incidencia();
        List<Usuario> usuarios = usuarioService.buscarTodos(); // Obtener la lista de usuarios para el formulario
        Usuario usuario = usuarioService.getCurrentUser(); //Obtener el usuario actualmente logeado

        model.addAttribute(GConstants.ATTRIBUTE_CURRENTUSER, usuario); // Agregar el usuario actual al modelo para la vista
        model.addAttribute("search", incidenciaSearch); // Agregar el objeto de búsqueda al modelo para la vista
        model.addAttribute(GConstants.ATTRIBUTE_USUARIOS, usuarios); // Agregar la lista de usuarios al modelo para el formulario
    }
}
