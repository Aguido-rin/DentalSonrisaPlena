package org.model.builder;

import org.model.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FacturaBuilder {

    private Paciente paciente;
    private String metodoPago;
    private final List<DetalleFactura> detalles = new ArrayList<>();
    private BigDecimal montoTotal = BigDecimal.ZERO;

    public FacturaBuilder paraPaciente(Paciente p) { this.paciente = p; return this; }
    public FacturaBuilder metodoPago(String metodo) { this.metodoPago = metodo; return this; }
    public FacturaBuilder agregarDetalle(DetalleFactura d) { this.detalles.add(d); return this; }
    public FacturaBuilder montoTotal(BigDecimal monto) { this.montoTotal = monto; return this; }

    public Factura build() {
        return Factura.builder()
                .paciente(paciente)
                .fechaEmision(LocalDateTime.now())
                .montoTotal(montoTotal) // si quieres, puedes sumarizar detalles aquí
                .metodoPago(metodoPago)
                .detalles(detalles)
                .build();
    }

}
