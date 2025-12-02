
package org.model.Observer;

import org.model.Cita;
import java.util.ArrayList;
import java.util.List;

public class CitaObservable implements Subject {

    private final List<Observer> observadores = new ArrayList<>();
    private final Cita cita;

    public CitaObservable(Cita cita) {
        this.cita = cita;
    }

    @Override
    public void registrar(Observer observer) {
        observadores.add(observer);
    }

    @Override
    public void eliminar(Observer observer) {
        observadores.remove(observer);
    }

    @Override
    public void notificar() {
        for (Observer obs : observadores) {
            obs.actualizar(cita);
        }
    }

    public void cambiarEstado(Cita.EstadoCita nuevoEstado) {
        cita.setEstado(nuevoEstado);
        notificar();
    }
}
