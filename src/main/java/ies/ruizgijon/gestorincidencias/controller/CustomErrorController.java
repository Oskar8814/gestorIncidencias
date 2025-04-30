package ies.ruizgijon.gestorincidencias.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            model.addAttribute("statusCode", statusCode);

            if (statusCode == 404) {
                model.addAttribute("errorMessage", "Página no encontrada.");
            } else if (statusCode == 500) {
                model.addAttribute("errorMessage", "Error interno del servidor.");
            } else if (statusCode == 403) {
                model.addAttribute("errorMessage", "Acceso denegado.");
            } else if (statusCode == 400) {
                model.addAttribute("errorMessage", "Solicitud incorrecta.");
            } else if (statusCode == 401) {
                model.addAttribute("errorMessage", "No autorizado.");
            } else {
                model.addAttribute("errorMessage", "Algo salió mal.");
            }
        } else {
            model.addAttribute("statusCode", "Error desconocido");
            model.addAttribute("errorMessage", "Algo salió mal.");
        }

        return "error"; // templates/error.html
    }
}


