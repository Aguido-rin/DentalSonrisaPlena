
package org.model.State;


public class CitaAsistio implements EstadoCitaState {

    @Override
    public void siguiente(CitaContext cita) {
        System.out.println("Sesión realizada, no hay más cambios.");
    }

    @Override
    public void mostrarEstado() {
        System.out.println("Estado actual: ASISTIÓ");
    }
}