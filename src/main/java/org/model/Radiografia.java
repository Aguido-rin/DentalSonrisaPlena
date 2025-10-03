package org.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "radiografias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Radiografia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    private Paciente paciente;
    @ManyToOne
    private Empleado tecnicoRadiologia;
    private String tipo;
    private String archivoPath;
    private LocalDateTime fechaToma;

}