package ies.ruizgijon.gestorincidencias.exceptions;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsuarioNoValidoException.class)
    public String handleUsuarioNoValidoException(UsuarioNoValidoException ex, Model model) {
        model.addAttribute("statusCode", 400);
        model.addAttribute("errorMessage", "El usuario no es válido:");
        model.addAttribute("errores", ex.getErrores());
        return "error"; // Usa error.html
    }
    @ExceptionHandler(IncidenciaNoValidoException.class)
    public String handleIncidenciaNoValidoException(IncidenciaNoValidoException ex, Model model) {
        model.addAttribute("statusCode", 400);
        model.addAttribute("errorMessage", "La incidencia no es válida:");
        model.addAttribute("errores", ex.getErrores());
        return "error"; // Usa error.html
    }
}

