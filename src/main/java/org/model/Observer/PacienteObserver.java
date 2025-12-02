
package org.model.Observer;

import org.model.Cita;

public class PacienteObserver implements Observer {

    @Override
    public void actualizar(Cita cita) {
        System.out.println("Notificación al PACIENTE:");
        System.out.println("Su cita cambió a estado: " + cita.getEstado());
    }
}