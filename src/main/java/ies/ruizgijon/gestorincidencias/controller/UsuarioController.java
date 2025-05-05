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

import ies.ruizgijon.gestorincidencias.model.Rol;
import ies.ruizgijon.gestorincidencias.model.Usuario;
import ies.ruizgijon.gestorincidencias.service.IRolService;
import ies.ruizgijon.gestorincidencias.service.IUsuarioService;
import ies.ruizgijon.gestorincidencias.util.GConstants;

@Controller
public class UsuarioController {

    private final IUsuarioService usuarioService; 
    private final IRolService rolService;

    //Constructor para la inyeccion de dependencias
    @Autowired
    public UsuarioController(IUsuarioService usuarioService, IRolService rolService) {
        this.usuarioService = usuarioService; // Inicializa el servicio de usuarios
        this.rolService = rolService; // Inicializa el servicio de roles
    }

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
        model.addAttribute(GConstants.ATTRIBUTE_USUARIOS, usuarios);
        return "usuarios"; // Devuelve la vista de listar usuarios
    }

    //Metodo para eliminar un usuario por su id
    @GetMapping("/usuario/delete/{id}")
    public String eliminarUsuario(@PathVariable("id") Integer idUsuario, Model model, RedirectAttributes attributes) {
        // Verificar si el ID del usuario existe antes de intentar eliminarlo
        Usuario usuario = usuarioService.buscarUsuarioPorId(idUsuario);
        if (usuario == null) {
            // Si el usuario no existe, redirigir a la lista de usuarios y mostrar un mensaje de error
            model.addAttribute(GConstants.ATTRIBUTE_MESSAGEERROR, "Usuario no encontrado con ID: " + idUsuario);
            return GConstants.REDIRECT_USUARIOLISTAR; // Redirigir a la lista de usuarios si no se encuentra el usuario
        }
        // Llamar al servicio para eliminar el usuario por su ID
        usuarioService.eliminarUsuario(idUsuario);

        // Agregar un mensaje de éxito al redirigir a la página después de eliminar el usuario
        attributes.addFlashAttribute(GConstants.ATTRIBUTE_CONFIRMACION, "Usuario eliminado con éxito.");

        return GConstants.REDIRECT_USUARIOLISTAR; // Redirigir a la lista de usuarios después de eliminar
    }

    //Metodo para crear un nuevo usuario
    @GetMapping("/usuario/crear")
    public String crearUsuario(Model model) {
        // Crear un nuevo objeto Usuario y agregarlo al modelo para que esté disponible en la vista
        Usuario nuevoUsuario = new Usuario();

        //Listado de roles para el formulario de creación de usuario
        List<Rol> roles = rolService.buscarTodos(); // Obtener la lista de roles desde el servicio

        model.addAttribute(GConstants.ATTRIBUTE_ROLES, roles); // Agregar la lista de roles al modelo para que esté disponible en la vista
        model.addAttribute("usuario", nuevoUsuario);
        return "crearUsuarioForm"; // Devuelve la vista para crear un nuevo usuario
    }

    //Metodo para guardar un nuevo usuario
    @PostMapping("/usuario/save")
    public String guardarUsuario(Usuario usuario, Model model, RedirectAttributes attributes) {
        // Llamar al servicio para guardar el nuevo usuario
        // Verificar si el usuario se encuentra en la base de datos
        Usuario usuarioExistente = usuarioService.buscarUsuarioPorMail(usuario.getMail());
        if (usuarioExistente != null) {
            usuarioService.modificarUsuario(usuario); // Modificar el usuario existente
            // Agregar un mensaje de éxito al redirigir a la página después de modificar el usuario
            attributes.addFlashAttribute(GConstants.ATTRIBUTE_CONFIRMACION, "Usuario modificado con éxito.");
        }else {
            // Si el usuario no existe, se guarda como un nuevo usuario
            usuarioService.guardarUsuario(usuario);
            attributes.addFlashAttribute(GConstants.ATTRIBUTE_CONFIRMACION, "Usuario guardado con éxito.");
        }
        return GConstants.REDIRECT_USUARIOLISTAR; // Redirigir a la lista de usuarios después de guardar
    }

    //Metodo para editar un usuario por su id
    @GetMapping("/usuario/edit/{id}")
    public String editarUsuario(@PathVariable("id") Integer idUsuario, Model model) {
        // Buscar el usuario por su ID
        Usuario usuario = usuarioService.buscarUsuarioPorId(idUsuario);
        if (usuario == null) {
            // Si el usuario no existe, redirigir a la lista de usuarios y mostrar un mensaje de error
            model.addAttribute(GConstants.ATTRIBUTE_MESSAGEERROR, "Usuario no encontrado con ID: " + idUsuario);
            return GConstants.REDIRECT_USUARIOLISTAR; // Redirigir a la lista de usuarios si no se encuentra el usuario
        }

        //Listado de roles para el formulario de edición de usuario
        List<Rol> roles = rolService.buscarTodos(); // Obtener la lista de roles desde el servicio

        model.addAttribute(GConstants.ATTRIBUTE_ROLES, roles); // Agregar la lista de roles al modelo para que esté disponible en la vista
        model.addAttribute("usuario", usuario); // Agregar el usuario al modelo para que esté disponible en la vista
        return "crearUsuarioForm"; // Devuelve la vista para editar un usuario existente
    }

    

    @ModelAttribute()
    public void setGenericos(Model model) {
        Usuario usuario = usuarioService.getCurrentUser(); //Obtener el usuario actualmente logeado

        model.addAttribute(GConstants.ATTRIBUTE_CURRENTUSER, usuario); // Agregar el usuario actual al modelo para la vista
    }

}
