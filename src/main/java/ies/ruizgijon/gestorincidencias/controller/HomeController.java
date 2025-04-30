package ies.ruizgijon.gestorincidencias.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    // Método para mostrar la página de inicio de sesión
    @GetMapping("/")
	public String showLogin() {
		return "login"; // Retorna la vista de inicio de sesión.
	}
}
