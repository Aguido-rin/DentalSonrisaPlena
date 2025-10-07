package org.patterns.factorymethod;

import org.model.Cita;
import org.model.Odontologo;
import org.model.Paciente;

import java.time.LocalDateTime;

public abstract class CitaCreator {
    public abstract Cita crear(Paciente p, Odontologo o, LocalDateTime cuando, String motivo);
}
