package ies.ruizgijon.gestorincidencias.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ies.ruizgijon.gestorincidencias.model.Nota;
import ies.ruizgijon.gestorincidencias.repository.IncidenciasRepository;
import ies.ruizgijon.gestorincidencias.repository.NotaRepository;
import ies.ruizgijon.gestorincidencias.repository.UsuarioRepository;

@Service
public class NotaServiceJpa implements INotaService {

    // Inyección de dependencias
    private final NotaRepository notaRepository;
    private final IncidenciasRepository incidenciaRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public NotaServiceJpa(NotaRepository notaRepository, IncidenciasRepository incidenciaRepository, UsuarioRepository usuarioRepository) {
        this.notaRepository = notaRepository;
        this.incidenciaRepository = incidenciaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Nota crearNota(Integer idIncidencia, String contenido, Integer idUsuario) {
        // Lógica para crear una nueva nota
        Nota nuevaNota = new Nota();
        nuevaNota.setContenido(contenido);
        nuevaNota.setAutor(usuarioRepository.findById(idUsuario).orElse(null));
        nuevaNota.setIncidencia(incidenciaRepository.findById(idIncidencia).orElse(null));
        
        return notaRepository.save(nuevaNota);
    }

    @Override
    public void eliminarNota(Integer idNota) {
        // Lógica para eliminar una nota por su ID
        notaRepository.deleteById(idNota);
    }

    @Override
    public Nota actualizarNota(Integer idNota, String contenido) {
        // Lógica para actualizar el contenido de una nota
        Nota notaExistente = notaRepository.findById(idNota).orElse(null);
        if (notaExistente != null) {
            notaExistente.setContenido(contenido);
            return notaRepository.save(notaExistente);
        }
        return null;
    }

    @Override
    public Nota obtenerNotaPorId(Integer idNota) {
        // Lógica para obtener una nota por su ID
        return notaRepository.findById(idNota).orElse(null);
    }

    @Override
    public List<Nota> obtenerNotasPorIncidencia(Integer idIncidencia) {
        // Lógica para obtener todas las notas asociadas a una incidencia
        return notaRepository.findAllByIncidenciaId(idIncidencia);
    }

    @Override
    public List<Nota> obtenerTodasLasNotas() {
        // Lógica para obtener todas las notas
        return notaRepository.findAll();
    }

}
