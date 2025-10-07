package org.model.abstractFactory;

import org.model.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ClinicaEstandarFactory implements ClinicaAbstractFactory {

    @Override
    public Cita nuevaCita(Paciente p, Odontologo o, LocalDateTime fecha, String motivo) {
        return Cita.builder()
                .paciente(p)
                .odontologo(o)
                .fechaHora(fecha)
                .motivo(motivo)
                .estado(Cita.EstadoCita.AGENDADA)
                .build();
    }

    @Override
    public Factura nuevaFactura(Paciente p, BigDecimal montoTotal, String metodoPago) {
        return Factura.builder()
                .paciente(p)
                .fechaEmision(LocalDateTime.now())
                .montoTotal(montoTotal)
                .metodoPago(metodoPago) // es String
                .build();
    }

}
