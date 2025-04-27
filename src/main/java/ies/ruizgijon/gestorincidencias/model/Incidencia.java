package ies.ruizgijon.gestorincidencias.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Incidencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100, nullable = false)
    private String titulo;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @Column(length = 20)
    private String aula;

    @Column(length = 255)
    private String imagen;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private EstadoIncidencia estado = EstadoIncidencia.PENDIENTE;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = true)
    private Usuario usuario; // Quien gestiona

    @ManyToOne
    @JoinColumn(name = "creador_id", nullable = false)
    private Usuario creador; // Quien reportó
}

