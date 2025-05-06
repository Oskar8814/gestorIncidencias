package ies.ruizgijon.gestorIncidencias.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ies.ruizgijon.gestorincidencias.model.Rol;
import ies.ruizgijon.gestorincidencias.repository.RolRepository;
import ies.ruizgijon.gestorincidencias.service.IRolService;
import ies.ruizgijon.gestorincidencias.service.RolServiceJpa;

class RolServiceJpaTest {
    @Mock
    private IRolService rolService;
    @InjectMocks
    private RolServiceJpa rolServiceJpa;
    @Mock
    private RolRepository rolRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa los mocks
    }
    
    @Test
    void testGuardarRol() {
        // Arrange
        Rol rol = new Rol();
        rol.setId(1);
        rol.setName("ADMIN");

        // Act
        rolServiceJpa.guardarRol(rol);

        // Assert
        verify(rolRepository).save(rol);
    }

    @Test
    void testEliminarRol_Existe() {
        // Arrange
        Integer idRol = 1;
        when(rolRepository.existsById(idRol)).thenReturn(true);

        // Act
        rolServiceJpa.eliminarRol(idRol);

        // Assert
        verify(rolRepository).deleteById(idRol);
    }

    @Test
    void testEliminarRol_NoExiste() {
        // Arrange
        Integer idRol = 999;
        when(rolRepository.existsById(idRol)).thenReturn(false);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            rolServiceJpa.eliminarRol(idRol);
        });

        assertEquals("Rol no encontrado con ID: " + idRol, exception.getMessage());
        verify(rolRepository, never()).deleteById(any());
    }

    @Test
    void testBuscarRolPorId_Existe() {
        // Arrange
        Integer idRol = 1;
        Rol rol = new Rol();
        rol.setId(idRol);
        rol.setName("ADMIN");

        when(rolRepository.findById(idRol)).thenReturn(Optional.of(rol));

        // Act
        Rol result = rolServiceJpa.buscarRolPorId(idRol);

        // Assert
        assertNotNull(result);
        assertEquals(idRol, result.getId());
        assertEquals("ADMIN", result.getName());
    }

    @Test
    void testBuscarRolPorId_NoExiste() {
        // Arrange
        Integer idRol = 999;
        when(rolRepository.findById(idRol)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            rolServiceJpa.buscarRolPorId(idRol);
        });

        assertEquals("Rol no encontrado con ID: " + idRol, exception.getMessage());
    }

    @Test
    void testBuscarTodos() {
        // Arrange
        Rol rol1 = new Rol();
        rol1.setId(1);
        rol1.setName("ADMIN");

        Rol rol2 = new Rol();
        rol2.setId(2);
        rol2.setName("USER");

        when(rolRepository.findAll()).thenReturn(Arrays.asList(rol1, rol2));

        // Act
        List<Rol> roles = rolServiceJpa.buscarTodos();

        // Assert
        assertNotNull(roles);
        assertEquals(2, roles.size());
        assertEquals("ADMIN", roles.get(0).getName());
        assertEquals("USER", roles.get(1).getName());
        verify(rolRepository).findAll();
    }
}
