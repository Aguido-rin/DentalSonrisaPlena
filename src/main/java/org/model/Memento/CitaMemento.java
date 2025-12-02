
package org.model.Memento;

import org.model.Cita;
import java.time.LocalDateTime;

public class CitaMemento {

    private final Cita.EstadoCita estado;
    private final LocalDateTime fechaHora;
    private final String motivo;

    public CitaMemento(Cita cita) {
        this.estado = cita.getEstado();
        this.fechaHora = cita.getFechaHora();
        this.motivo = cita.getMotivo();
    }

    public Cita.EstadoCita getEstado() {
        return estado;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public String getMotivo() {
        return motivo;
    }
}
