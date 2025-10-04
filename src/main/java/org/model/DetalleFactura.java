package org.model;

import jakarta.persistence.*;
import lombok.*;


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
    private Long id;
    private String descripcionServicio;
    private int cantidad;
    private double precioUnitario;
    private double subtotal;
    @ManyToOne(optional = false)
    private Factura factura;
}
