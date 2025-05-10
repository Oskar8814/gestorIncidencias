package ies.ruizgijon.gestorincidencias.service;

import java.util.List;

import ies.ruizgijon.gestorincidencias.model.Nota;

public interface INotaService {
    /**
     * Crea una nueva nota asociada a una incidencia.
     *
     * @param idIncidencia ID de la incidencia a la que se asociará la nota.
     * @param contenido    Contenido de la nota.
     * @param idUsuario    ID del usuario que crea la nota.
     * @return La nota creada.
     */
    Nota crearNota(Integer idIncidencia, String contenido, Integer idUsuario);

    /**
     * Elimina una nota por su ID.
     *
     * @param idNota ID de la nota a eliminar.
     */
    void eliminarNota(Integer idNota);

    /**
     * Actualiza el contenido de una nota.
     *
     * @param idNota    ID de la nota a actualizar.
     * @param contenido Nuevo contenido de la nota.
     * @return La nota actualizada.
     */
    Nota actualizarNota(Integer idNota, String contenido);
    /**
     * Obtiene una nota por su ID.
     *
     * @param idNota ID de la nota a obtener.
     * @return La nota correspondiente al ID proporcionado.
     */
    Nota obtenerNotaPorId(Integer idNota);
    /**
     * Obtiene todas las notas asociadas a una incidencia.
     *
     * @param idIncidencia ID de la incidencia a la que se asociarán las notas.
     * @return Lista de notas asociadas a la incidencia.
     */
    List<Nota> obtenerNotasPorIncidencia(Integer idIncidencia);
    /**
     * Obtiene todas las notas.
     *
     * @return Lista de todas las notas.
     */
    List<Nota> obtenerTodasLasNotas();
    
}
