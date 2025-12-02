
package org.model.Memento;

import org.model.Cita;

public class CitaOriginator {

    private Cita cita;

    public CitaOriginator(Cita cita) {
        this.cita = cita;
    }

    public CitaMemento guardar() {
        return new CitaMemento(cita);
    }

    public void restaurar(CitaMemento memento) {
        cita.setEstado(memento.getEstado());
        cita.setFechaHora(memento.getFechaHora());
        cita.setMotivo(memento.getMotivo());

        System.out.println("Estado restaurado correctamente.");
    }
}