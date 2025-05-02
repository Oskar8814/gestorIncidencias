package ies.ruizgijon.gestorincidencias.exceptions;

import java.util.List;

public class UsuarioNoValidoException extends RuntimeException {

    private List<String> errores;

    public UsuarioNoValidoException(List<String> errores) {
        super("Errores de validación en el usuario.");
        this.errores = errores;
    }

    public List<String> getErrores() {
        return errores;
    }
}
