package ies.ruizgijon.gestorincidencias.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ies.ruizgijon.gestorincidencias.model.EstadoIncidencia;
import ies.ruizgijon.gestorincidencias.model.Incidencia;

public interface IncidenciasRepository extends JpaRepository<Incidencia, Integer> {

    // Aquí puedes agregar métodos personalizados si es necesario
    // Por ejemplo, para buscar incidencias por estado o por fecha
    List<Incidencia> findByEstado(EstadoIncidencia estado);
}
