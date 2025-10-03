package org.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "sesiones_tratamiento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SesionTratamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    private Tratamiento tratamiento;
    @ManyToOne(optional = false)
    private Paciente paciente;
    @ManyToOne
    private Empleado odontologo;
    private LocalDateTime fechaHora;
    private Boolean realizada;

}
