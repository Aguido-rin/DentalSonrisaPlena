
package org.model.Observer;

import org.model.Cita;

public class OdontologoObserver implements Observer {

    @Override
    public void actualizar(Cita cita) {
        System.out.println("Notificación al ODONTÓLOGO:");
        System.out.println("Estado de la cita actualizado: " + cita.getEstado());
    }
}