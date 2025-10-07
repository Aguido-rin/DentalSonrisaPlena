package org.model.factory;

public class PagoTransferencia implements ProcesadorPago {
    public void procesarPago(double monto) {
        System.out.println("Pago con transferencia de: " + monto);
    }
}
