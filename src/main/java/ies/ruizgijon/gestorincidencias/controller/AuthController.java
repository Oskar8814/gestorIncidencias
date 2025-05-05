package ies.ruizgijon.gestorincidencias.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ies.ruizgijon.gestorincidencias.model.Usuario;
import ies.ruizgijon.gestorincidencias.service.EmailService;
import ies.ruizgijon.gestorincidencias.service.IUsuarioService;

@Controller
public class AuthController {

    @Value("${app.url.base}")
    private String baseUrl;

    private final IUsuarioService usuarioService;
    private final EmailService emailService;

    //Constructor para la inyeccion de dependencias
    @Autowired
    public AuthController(IUsuarioService usuarioService, EmailService emailService) {
        this.usuarioService = usuarioService;
        this.emailService = emailService;
    }

    @GetMapping("/recuperar-password")
    public String mostrarFormularioRecuperacion() {
        return "recuperar-password";
    }

    @PostMapping("/recuperar-password")
    public String procesarRecuperacion(@RequestParam String email, RedirectAttributes attrs) {
        Usuario usuario = usuarioService.buscarUsuarioPorMail(email);
        if (usuario != null) {
            String token = UUID.randomUUID().toString();
            usuarioService.guardarTokenDeRecuperacion(usuario, token);
            emailService.enviarRecuperacion(email, baseUrl.trim() + "/reset-password?token=" + token); // URL local,cambiar por la real
        }
        attrs.addFlashAttribute("confirmacion", "Si el correo existe, se enviaron instrucciones.");
        return "redirect:/";
    }

    @GetMapping("/reset-password")
    public String mostrarResetForm(@RequestParam String token, Model model) {
        if (!usuarioService.validarToken(token))
            return "redirect:/?errorToken";
        model.addAttribute("token", token);
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String procesarReset(@RequestParam String token,
            @RequestParam String password,
            RedirectAttributes attrs) {
        boolean exito = usuarioService.actualizarPasswordConToken(token, password);
        attrs.addFlashAttribute(exito ? "confirmacion" : "error",
                exito ? "Contraseña actualizada correctamente." : "Token inválido o expirado.");
        return "redirect:/";
    }

}
