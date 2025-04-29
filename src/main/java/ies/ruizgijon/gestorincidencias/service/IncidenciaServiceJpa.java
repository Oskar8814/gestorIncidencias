package ies.ruizgijon.gestorincidencias.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service; 

import ies.ruizgijon.gestorincidencias.model.EstadoIncidencia;
import ies.ruizgijon.gestorincidencias.model.Incidencia;
import ies.ruizgijon.gestorincidencias.model.Usuario;
import ies.ruizgijon.gestorincidencias.repository.IncidenciasRepository;
import ies.ruizgijon.gestorincidencias.repository.UsuarioRepository;

@Service
public class IncidenciaServiceJpa implements IIncidenciasService {
    
    // Inyección de dependencias para el repositorio de incidencias y usuarios
    @Autowired
    private IncidenciasRepository incidenciasRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;



    // Método para guardar una incidencia en la base de datos
    @Override
    public void guardarIncidencia(Incidencia incidencia) {
        incidenciasRepository.save(incidencia);
    }

    // Método para eliminar una incidencia por su ID
    @Override
    public void eliminarIncidencia(Integer idIncidencia) {
        // Verifica si la incidencia existe antes de eliminarla
        if (incidenciasRepository.existsById(idIncidencia)) {
            incidenciasRepository.deleteById(idIncidencia);
        } else {
            throw new IllegalArgumentException("Incidencia no encontrada con ID: " + idIncidencia);
        }
    }

    // Método para buscar una incidencia por su ID
    @Override
    public Incidencia buscarIncidenciaPorId(Integer idIncidencia) {
        Optional<Incidencia> incidenciaOpt = incidenciasRepository.findById(idIncidencia);
        if (incidenciaOpt.isPresent()) {
            return incidenciaOpt.get();
        } else {
            throw new IllegalArgumentException("Incidencia no encontrada con ID: " + idIncidencia);
        }
    }

    // Método para buscar todas las incidencias
    @Override
    public List<Incidencia> buscarTodas() {
        // Devuelve todas las incidencias de la base de datos
        return incidenciasRepository.findAll();
    }

    // Método para buscar incidencias por estado
    @Override
    public List<Incidencia> buscarPorEstado(EstadoIncidencia estado) {
        // Devuelve todas las incidencias que coinciden con el estado dado
        return incidenciasRepository.findByEstado(estado);
    }

    // //Metodo para asignar una incidencia a un usuario
    @Override
    public void asignarIncidencia(Integer idIncidencia, Integer idUsuario) {
        // Busca la incidencia por su ID
        Optional<Incidencia> incidenciaOpt = incidenciasRepository.findById(idIncidencia);
        // Busca el usuario por su ID (aquí se asume que tienes un método para buscar usuarios)
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(idUsuario);
        // Verifica si la incidencia existe
        if (incidenciaOpt.isPresent() && usuarioOpt.isPresent()) {
            // Obtiene la incidencia y el usuario
            Incidencia incidencia = incidenciaOpt.get();
            Usuario usuario = usuarioOpt.get();

            // Asigna el usuario a la incidencia
            incidencia.setUsuario(usuario);
            // Cambia el estado de la incidencia a "EN PROGRESO/REPARACION"
            incidencia.setEstado(EstadoIncidencia.REPARACION);
            
            // Guarda la incidencia actualizada en la base de datos
            incidenciasRepository.save(incidencia);
        } else {
            throw new IllegalArgumentException("Incidencia no encontrada con ID: " + idIncidencia);
        }
    }

    // Metodo para desasignar una incidencia a un usuario
    @Override
    public void desasignarIncidencia(Integer idIncidencia) {
        // Busca la incidencia por su ID
        Optional<Incidencia> incidenciaOpt = incidenciasRepository.findById(idIncidencia);
        // Verifica si la incidencia existe
        if (incidenciaOpt.isPresent()) {
            // Obtiene la incidencia
            Incidencia incidencia = incidenciaOpt.get();
            // Cambia el estado de la incidencia a "PENDIENTE"
            incidencia.setEstado(EstadoIncidencia.PENDIENTE);
            // Desasigna el usuario de la incidencia
            incidencia.setUsuario(null);
            // Guarda la incidencia actualizada en la base de datos
            incidenciasRepository.save(incidencia);
        } else {
            throw new IllegalArgumentException("Incidencia no encontrada con ID: " + idIncidencia);
        }
    }

    // Método para cerrar una incidencia
    @Override
    public void cerrarIncidencia(Integer idIncidencia) {
        // Busca la incidencia por su ID
        Optional<Incidencia> incidenciaOpt = incidenciasRepository.findById(idIncidencia);
        // Verifica si la incidencia existe
        if (incidenciaOpt.isPresent()) {
            // Obtiene la incidencia
            Incidencia incidencia = incidenciaOpt.get();
            // Cambia el estado de la incidencia a "RESUELTA"
            incidencia.setEstado(EstadoIncidencia.RESUELTA);
            // Guarda la incidencia actualizada en la base de datos
            incidenciasRepository.save(incidencia);
        } else {
            throw new IllegalArgumentException("Incidencia no encontrada con ID: " + idIncidencia);
        }
    }

    // Método para buscar incidencias por ejemplo (usando Spring Data JPA)
    @Override
    public List<Incidencia> buscarByExample(Example<Incidencia> example) {
        return incidenciasRepository.findAll(example);
    }

}
