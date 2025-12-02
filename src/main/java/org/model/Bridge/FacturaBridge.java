
package org.model.Bridge;

import java.math.BigDecimal;


public abstract class FacturaBridge {
     protected MetodoPago metodoPago; // 👈 ESTA LÍNEA VA AQUÍ

    protected FacturaBridge(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public abstract void pagar(BigDecimal monto);
}