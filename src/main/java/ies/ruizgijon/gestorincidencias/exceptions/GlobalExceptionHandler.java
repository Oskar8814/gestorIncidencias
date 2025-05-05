package ies.ruizgijon.gestorincidencias.exceptions;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import ies.ruizgijon.gestorincidencias.util.GConstants;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsuarioNoValidoException.class)
    public String handleUsuarioNoValidoException(UsuarioNoValidoException ex, Model model) {
        model.addAttribute(GConstants.ATTRIBUTE_STATUSCODE, 400);
        model.addAttribute(GConstants.ATTRIBUTE_ERRORMESSAGE, "El usuario no es válido:");
        model.addAttribute("errores", ex.getErrores());
        return "error"; // Usa error.html
    }
    @ExceptionHandler(IncidenciaNoValidoException.class)
    public String handleIncidenciaNoValidoException(IncidenciaNoValidoException ex, Model model) {
        model.addAttribute(GConstants.ATTRIBUTE_STATUSCODE, 400);
        model.addAttribute(GConstants.ATTRIBUTE_ERRORMESSAGE, "La incidencia no es válida:");
        model.addAttribute("errores", ex.getErrores());
        return "error"; // Usa error.html
    }
}

