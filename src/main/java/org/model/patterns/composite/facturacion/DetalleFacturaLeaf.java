package org.model.patterns.composite.facturacion;

import org.model.DetalleFactura;

import java.math.BigDecimal;

public class DetalleFacturaLeaf implements FacturableComponent {
    private final DetalleFactura detalle;

    public DetalleFacturaLeaf(DetalleFactura detalle) {
        this.detalle = detalle;
    }

    @Override
    public BigDecimal total() {
        if (detalle.getPrecioUnitario() == null || detalle.getCantidad() == null) return BigDecimal.ZERO;
        return detalle.getPrecioUnitario().multiply(BigDecimal.valueOf(detalle.getCantidad()));
    }

    public DetalleFactura getDetalle() { return detalle; }
}
