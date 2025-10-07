package org.model.factory;

public class PagoEfectivo implements ProcesadorPago{
    public void procesarPago(double monto) {
        System.out.println("Pago en efectivo de: " + monto);
   }
}
