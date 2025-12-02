
package org.model.Bridge;


import java.math.BigDecimal;


public class PagoEfectivo implements MetodoPago{

    @Override
    public void procesarPago(BigDecimal monto) {
   
        System.out.println("Pago en efectivo procesado: S/ " + monto);
    }
}
   