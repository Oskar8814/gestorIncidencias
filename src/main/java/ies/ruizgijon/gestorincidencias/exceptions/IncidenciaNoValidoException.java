package ies.ruizgijon.gestorincidencias.exceptions;

import java.util.List;

public class IncidenciaNoValidoException extends RuntimeException {

    private List<String> errores;

    public IncidenciaNoValidoException(List<String> errores) {
        super("Errores de validación en la Incidencia.");
        this.errores = errores;
    }

    public List<String> getErrores() {
        return errores;
    }
}
