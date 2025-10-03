package org.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "citas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime fechaHora;
    @ManyToOne(optional = false)
    private Paciente paciente;
    @ManyToOne
    private Empleado profesional;
    @Enumerated(EnumType.STRING)
    private EstadoCita estado;
    public enum EstadoCita { AGENDADA, ASISTIO, CANCELADA }

}