package ies.ruizgijon.gestorincidencias.repository;

import ies.ruizgijon.gestorincidencias.model.Nota;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NotaRepository extends JpaRepository<Nota, Integer> {

    List<Nota> findAllByIncidenciaId(Integer idIncidencia);
}
