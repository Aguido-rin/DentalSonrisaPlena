package org.model.patterns.adapter;

import org.model.Cita;
import org.model.Paciente;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component("smsReminderPattern")
@Primary
public class SmsReminderAdapter implements ReminderNotifier {
    @Override
    public void sendRecordatorioCita(Cita cita, Paciente paciente) {
        // Integrar con proveedor SMS real aquí.
        // Stub: no-op
    }
}
