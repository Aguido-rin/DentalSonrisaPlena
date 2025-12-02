package org.model.factoryMethod;

import org.model.DetalleFactura;
import org.model.Factura;
import org.model.Paciente;

import java.time.LocalDateTime;
import java.util.List;

public class FacturaCreator {

    public Factura generarFactura(Paciente paciente, List<DetalleFactura> detalles) {
        Factura factura = Factura.builder()
                .paciente(paciente)
                .fechaEmision(LocalDateTime.now())
                .detalles(detalles)
                .build();
        return factura;
    }

}
