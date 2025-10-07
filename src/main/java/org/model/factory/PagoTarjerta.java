package org.model.factory;

public class PagoTarjerta implements ProcesadorPago{
    public void procesarPago(double monto) {
        System.out.println("Pago con tarjeta de: " + monto);
    }
}
