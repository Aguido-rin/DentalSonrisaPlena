
package org.model.Bridge;

import java.math.BigDecimal;

public class FacturaFisica extends FacturaBridge {

    public FacturaFisica(MetodoPago metodoPago) {
        super(metodoPago);
    }

    @Override
    public void pagar(BigDecimal monto) {
        System.out.println("Factura física");
        metodoPago.procesarPago(monto);
    }
}