package org.model.factory;

import org.model.Cita;
import org.model.Odontologo;
import org.model.Paciente;

import java.time.LocalDateTime;

public class CitaProgramadaCreator extends CitaCreator {
    @Override
    public Cita crear(Paciente p, Odontologo o, LocalDateTime cuando, String motivo) {
        return Cita.builder()
                .paciente(p)
                .odontologo(o)
                .fechaHora(cuando)
                .motivo(motivo)
                .estado(Cita.EstadoCita.AGENDADA)
                .build();
    }
}
