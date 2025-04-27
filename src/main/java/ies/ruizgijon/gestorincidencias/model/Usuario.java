package ies.ruizgijon.gestorincidencias.model;

import jakarta.persistence.*;
import lombok.*;
// import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50, nullable = false)
    private String nombre;

    @Column(length = 50, nullable = false)
    private String apellido1;

    @Column(length = 50)
    private String apellido2;

    @Column(length = 100, nullable = false, unique = true)
    private String mail;

    @Column(length = 255, nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;
}

