package ies.ruizgijon.gestorincidencias.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import ies.ruizgijon.gestorincidencias.model.Usuario;
import ies.ruizgijon.gestorincidencias.service.IUsuarioService;
import ies.ruizgijon.gestorincidencias.util.GConstants;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    
    private final IUsuarioService usuarioService; // Inyección de dependencias para el servicio de usuarios

    //Constructor para la inyeccion de dependencias
    @Autowired
    public CustomErrorController(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            model.addAttribute(GConstants.ATTRIBUTE_STATUSCODE, statusCode);

            if (statusCode == 404) {
                model.addAttribute(GConstants.ATTRIBUTE_ERRORMESSAGE, "Página no encontrada.");
            } else if (statusCode == 500) { 
                model.addAttribute(GConstants.ATTRIBUTE_ERRORMESSAGE, "Error interno del servidor.");
            } else if (statusCode == 403) {
                model.addAttribute(GConstants.ATTRIBUTE_ERRORMESSAGE, "Acceso denegado.");
            } else if (statusCode == 400) {
                model.addAttribute(GConstants.ATTRIBUTE_ERRORMESSAGE, "Solicitud incorrecta.");
            } else if (statusCode == 401) {
                model.addAttribute(GConstants.ATTRIBUTE_ERRORMESSAGE, "No autorizado.");
            } else {
                model.addAttribute(GConstants.ATTRIBUTE_ERRORMESSAGE, "Algo salió mal.");
            }
        } else {
            model.addAttribute(GConstants.ATTRIBUTE_STATUSCODE, "Error desconocido");
            model.addAttribute(GConstants.ATTRIBUTE_ERRORMESSAGE, "Algo salió mal.");
        }

        return "error"; // templates/error.html
    }

    @ModelAttribute()
    public void setGenericos(Model model) {
        Usuario usuario = usuarioService.getCurrentUser(); //Obtener el usuario actualmente logeado

        model.addAttribute(GConstants.ATTRIBUTE_CURRENTUSER, usuario); // Agregar el usuario actual al modelo para la vista
    }
}


