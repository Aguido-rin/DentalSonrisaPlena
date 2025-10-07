package org.patterns.factorymethod;

import org.model.DetalleFactura;
import org.model.Factura;
import org.model.Paciente;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class FacturaCreator {

    public Factura generarFactura(Paciente paciente, List<DetalleFactura> detalles, String metodoPago) {
        BigDecimal total = detalles == null ? BigDecimal.ZERO : detalles.stream()
                .map(d -> d.getPrecioUnitario().multiply(BigDecimal.valueOf(d.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        Factura factura = Factura.builder()
                .paciente(paciente)
                .fechaEmision(LocalDateTime.now())
                .metodoPago(metodoPago)
                .montoTotal(total)
                .detalles(detalles)
                .build();
        if (detalles != null) detalles.forEach(d -> d.setFactura(factura));
        return factura;
    }
}
