
package org.model.State;

public class CitaCancelada implements EstadoCitaState {

    @Override
    public void siguiente(CitaContext cita) {
        System.out.println("Cita cancelada. No se puede cambiar.");
    }

    @Override
    public void mostrarEstado() {
        System.out.println("Estado actual: CANCELADA");
    }
}