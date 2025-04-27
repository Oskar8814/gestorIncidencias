package ies.ruizgijon.gestorincidencias.service;

import java.util.List;

import ies.ruizgijon.gestorincidencias.model.EstadoIncidencia;
import ies.ruizgijon.gestorincidencias.model.Incidencia; // Import Incidencia class

public interface IIncidenciasService {
    void guardarIncidencia(Incidencia incidencia);
    void eliminarIncidencia(Integer idIncidencia); // Cambiado a integer para que coincida con el tipo de id en la clase Incidencia
    Incidencia buscarIncidenciaPorId(Integer idIncidencia);
    List<Incidencia> buscarTodas();
    List<Incidencia> buscarPorEstado(EstadoIncidencia estado); // Cambiado a Enum para que coincida con el tipo de estado en la clase Incidencia
    public void asignarIncidencia(Integer idIncidencia, Integer idUsuario);
    public void desasignarIncidencia(Integer idIncidencia);
    public void cerrarIncidencia(Integer idIncidencia);
    // Page<Incidencia> buscarPorEstadoPaginado(Pageable page);
    // Page<Incidencia> buscarTodasPaginado(Pageable page); // Cambiado a String para que coincida con el tipo de nombre en la clase Usuario
}
