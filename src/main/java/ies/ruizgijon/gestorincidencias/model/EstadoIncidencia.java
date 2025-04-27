package ies.ruizgijon.gestorincidencias.model;

public enum EstadoIncidencia {
    PENDIENTE,
    REPARACION,
    RESUELTA;

    public static EstadoIncidencia fromString(String estado) {
        return EstadoIncidencia.valueOf(estado.toUpperCase());
    }
}
