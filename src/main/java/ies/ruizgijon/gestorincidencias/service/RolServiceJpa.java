package ies.ruizgijon.gestorincidencias.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ies.ruizgijon.gestorincidencias.model.Rol;
import ies.ruizgijon.gestorincidencias.repository.RolRepository;

@Service
public class RolServiceJpa implements IRolService {

    @Autowired
    private RolRepository rolRepository; // Inyección de dependencia del servicio de roles

    @Override
    public void guardarRol(Rol rol) {
        // Implementación para guardar un rol en la base de datos
        rolRepository.save(rol);        
    }

    @Override
    public void eliminarRol(Integer idRol) {
        // Verifica si el rol existe antes de eliminarlo
        if (rolRepository.existsById(idRol)) {
            rolRepository.deleteById(idRol);
        } else {
            throw new IllegalArgumentException("Rol no encontrado con ID: " + idRol);
        }
    }

    @Override
    public Rol buscarRolPorId(Integer idRol) {
        // Busca un rol por su ID en la base de datos
        return rolRepository.findById(idRol).orElseThrow(() -> new IllegalArgumentException("Rol no encontrado con ID: " + idRol));
    }

    @Override
    public List<Rol> buscarTodos() {
        // Devuelve todos los roles de la base de datos
        return rolRepository.findAll();
    }
    
}
