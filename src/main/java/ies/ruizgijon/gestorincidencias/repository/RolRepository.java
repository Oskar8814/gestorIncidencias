package ies.ruizgijon.gestorincidencias.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ies.ruizgijon.gestorincidencias.model.Rol;

public interface RolRepository extends JpaRepository<Rol, Integer> {
    // Aquí se pueden agregar métodos personalizados si es necesario
    // Por ejemplo, para buscar roles por nombre o permisos específicos
    
}
