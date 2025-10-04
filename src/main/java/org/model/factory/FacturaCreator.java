package org.model.factory;

import org.model.DetalleFactura;
import org.model.Factura;
import org.model.Paciente;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class FacturaCreator {
    public Factura generarFactura(Paciente paciente, List<DetalleFactura> detalles, String detalleTexto) {
        Factura factura = Factura.builder()
                .paciente(paciente)
                .fechaEmision(LocalDateTime.now())
                .detalle(detalleTexto)
                .detalles(detalles)
                .build();

        return factura;
    }
}
