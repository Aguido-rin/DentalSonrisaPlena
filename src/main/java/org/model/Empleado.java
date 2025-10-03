package org.model;

import jakarta.persistence.*;
import lombok.*;
import org.model.enums.AreaLaboral;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "empleados")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Empleado implements Serializable, Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String codigo;
    private String nombres;
    @Enumerated(EnumType.STRING)
    private AreaLaboral areaLaboral;
    private BigDecimal sueldo;
    private Integer horasExtras;
    private Boolean afiliadoAFP;

    public BigDecimal calcularMontoHorasExtras() {
        if (sueldo == null || horasExtras == null) return BigDecimal.ZERO;
        return sueldo.multiply(BigDecimal.valueOf(horasExtras))
                .divide(BigDecimal.valueOf(240), 2, java.math.RoundingMode.HALF_UP);
    }

    @Override
    public Empleado clone() {
        try {
            return (Empleado) super.clone();
        } catch (CloneNotSupportedException e) {

            throw new RuntimeException(e);
        }
    }

}