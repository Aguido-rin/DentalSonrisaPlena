package org.model.patterns.builder;

import org.model.DetalleFactura;
import org.model.Factura;
import org.model.Paciente;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FacturaBuilder {
    private Paciente paciente;
    private String metodoPago;
    private final List<DetalleFactura> detalles = new ArrayList<>();

    public FacturaBuilder paraPaciente(Paciente p) { this.paciente = p; return this; }
    public FacturaBuilder metodoPago(String metodo) { this.metodoPago = metodo; return this; }
    public FacturaBuilder agregarDetalle(DetalleFactura d) { this.detalles.add(d); return this; }

    private BigDecimal total() {
        return detalles.stream()
                .map(d -> d.getPrecioUnitario().multiply(BigDecimal.valueOf(d.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Factura build() {
        Factura f = Factura.builder()
                .paciente(paciente)
                .metodoPago(metodoPago)
                .montoTotal(total())
                .fechaEmision(LocalDateTime.now())
                .detalles(detalles)
                .build();
        detalles.forEach(d -> d.setFactura(f));
        return f;
    }
}
