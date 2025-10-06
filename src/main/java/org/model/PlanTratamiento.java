package org.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "planes_tratamiento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanTratamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plan")
    private Long idPlan;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String descripcion;

    @Column(name = "costo_total", precision = 12, scale = 2, nullable = false)
    private BigDecimal costoTotal;


    @Column(name = "duracion_estimada_dias")
    private Integer duracionEstimadaDias;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_diagnostico", nullable = false)
    private Diagnostico diagnostico;

    @OneToMany(mappedBy = "planTratamiento", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<SesionTratamiento> sesiones = new ArrayList<>();

}
