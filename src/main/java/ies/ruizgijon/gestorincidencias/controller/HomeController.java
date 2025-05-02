package ies.ruizgijon.gestorincidencias.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ies.ruizgijon.gestorincidencias.model.Usuario;
import ies.ruizgijon.gestorincidencias.service.IUsuarioService;

@Controller
public class HomeController {

	@Autowired
	private IUsuarioService usuarioService; // Inyección de dependencias para el servicio de usuarios

    // Método para mostrar la página de inicio de sesión
    @GetMapping("/")
	public String showLogin() {
		return "login"; // Retorna la vista de inicio de sesión.
	}

	//Metodo para modificar la contraseña
    @GetMapping("/modificarContrasena")
    public String modificarContrasena(Model model) {
        // Obtener el usuario actualmente logeado
        Usuario usuarioLogeado = usuarioService.getCurrentUser(); // Obtener el usuario actualmente logeado
		if (usuarioLogeado == null) {
			// Si no hay un usuario logeado, redirigir a la página de inicio de sesión
			return "redirect:/";
		}
		String mail = usuarioLogeado.getMail(); // Obtener el correo electrónico del usuario logeado
        model.addAttribute("mailLogeado", mail);
        return "modificarContrasena"; // Devuelve la vista para modificar la contraseña
    }

	// Metodo para guardar la nueva contraseña
	@PostMapping("/cambiarContrasena/save")
	public String cambiarContrasena (@RequestParam("mailLogeado") String mail, @RequestParam("password") String contrasena, RedirectAttributes attributes) {
		// Obtener el usuario actualmente logeado
		Usuario usuarioLogeado = usuarioService.getCurrentUser(); // Obtener el usuario actualmente logeado
		if (usuarioLogeado == null) {
			// Si no hay un usuario logeado, redirigir a la página de inicio de sesión
			return "redirect:/";
		}
		// Verificar si el correo electrónico coincide con el del usuario logeado
		if (!usuarioLogeado.getMail().equals(mail)) {
			// Si no coincide, redirigir a la página de inicio de sesión
			return "redirect:/";
		}
		// Llamar al servicio para cambiar la contraseña del usuario
		usuarioService.cambiarContrasena(usuarioLogeado.getId(), contrasena); // Cambiar la contraseña del usuario logeado
		
		// Agregar un mensaje de éxito al redirigir a la página después de cambiar la contraseña
		attributes.addFlashAttribute("confirmacion", "Contraseña modificada con éxito.");
		return "redirect:/"; 
	}

	@ModelAttribute()
    public void setGenericos(Model model) {
        Usuario usuario = usuarioService.getCurrentUser(); //Obtener el usuario actualmente logeado

        model.addAttribute("currentUser", usuario); // Agregar el usuario actual al modelo para la vista
    }
}
