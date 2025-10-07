package org.patterns.adapter;

import org.model.Cita;
import org.model.Paciente;
import org.springframework.stereotype.Component;

@Component
public class WhatsAppReminderAdapter implements ReminderNotifier {
    @Override
    public void sendRecordatorioCita(Cita cita, Paciente paciente) {
        // Integrar con API de WhatsApp real aquí.
        // Stub: no-op
    }
}
