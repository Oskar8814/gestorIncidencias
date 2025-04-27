package ies.ruizgijon.gestorincidencias.service;

import java.util.List;
import ies.ruizgijon.gestorincidencias.model.Rol;

public interface IRolService {
    // Aquí puedes definir los métodos necesarios para gestionar los roles
    void guardarRol(Rol rol);
    void eliminarRol(Integer idRol);
    Rol buscarRolPorId(Integer idRol);
    List<Rol> buscarTodos();
}
