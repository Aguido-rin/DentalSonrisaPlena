package org.model.builder;

import org.model.DetalleFactura;
import org.model.Factura;
import org.model.Paciente;

import java.util.List;

public class FacturaDirector {
    public Factura facturaBasica(Paciente p, String metodoPago, List<DetalleFactura> detalles) {
        org.model.builder.FacturaBuilder b = new FacturaBuilder()
                .paraPaciente(p)
                .metodoPago(metodoPago);
        detalles.forEach(b::agregarDetalle);
        return b.build();
    }
}
