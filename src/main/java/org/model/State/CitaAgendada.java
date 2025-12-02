
package org.model.State;

public class CitaAgendada implements EstadoCitaState {

    @Override
    public void siguiente(CitaContext cita) {
        System.out.println("Cita confirmada : pasa a ASISTIÓ");
        cita.setEstado(new CitaAsistio());
    }

    @Override
    public void mostrarEstado() {
        System.out.println("Estado actual: AGENDADA");
    }
}
