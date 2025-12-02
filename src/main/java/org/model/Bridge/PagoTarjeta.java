
package org.model.Bridge;

import java.math.BigDecimal;

public class PagoTarjeta implements MetodoPago{

    @Override
    public void procesarPago(BigDecimal monto) {
        System.out.println(" Pago por transferencia procesado: S/ " + monto);
    }

}
