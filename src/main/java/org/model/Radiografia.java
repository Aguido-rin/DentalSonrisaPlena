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
    @Column(name = "id_radiografia")
    private Long idRadiografia;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_paciente", nullable = false)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tecnico")
    private Empleado tecnicoRadiologia;

    @Column(length = 50)
    private String tipo;

    @Column(name = "archivo_path", length = 300)
    private String archivoPath;

    @Column(name = "fecha_toma")
    private LocalDateTime fechaToma;

}
