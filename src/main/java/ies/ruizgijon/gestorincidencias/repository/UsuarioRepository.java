package ies.ruizgijon.gestorincidencias.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ies.ruizgijon.gestorincidencias.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    // Aquí se puede agregar métodos personalizados si es necesario
    Optional<Usuario> findByNombre(String nombreUsuario);
}
