package ies.ruizgijon.gestorincidencias.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;


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
    List<Incidencia> buscarByExample(Example<Incidencia> example);
    Page<Incidencia> buscarIncidenciasPaginadas(Pageable pageable);
    public Page<Incidencia> buscarIncidenciasPorEstadoPaginadas(EstadoIncidencia estado, Pageable pageable);
    public Page<Incidencia> buscarIncidenciasByExamplePaginadas(Example<Incidencia> example, Pageable pageable); 
}
