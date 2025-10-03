package org.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "facturas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    private Paciente paciente;
    private LocalDateTime fechaEmision;
    private BigDecimal montoBruto;
    private BigDecimal montoDescuentos;
    private BigDecimal montoNeto;
    private String detalle;

}