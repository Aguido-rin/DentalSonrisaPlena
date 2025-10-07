package org.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    private String nombreUsuario;
    private String contrasena;
    private String rol;

    @OneToOne
    @JoinColumn(name = "id_odontologo")
    private Odontologo odontologo;

    @OneToOne
    @JoinColumn(name = "id_empleado")
    private Empleado empleado;

}
