package org.model.adapter;

import org.model.Cita;
import org.model.Paciente;

public interface ReminderNotifier {
    void sendRecordatorioCita(Cita cita, Paciente paciente);
}
