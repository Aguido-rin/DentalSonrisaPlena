package org.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "detalle_facturas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleFactura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Long idDetalle;

    private String descripcionServicio;
    private Integer cantidad;
    private BigDecimal precioUnitario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_factura")
    private Factura factura;

}
