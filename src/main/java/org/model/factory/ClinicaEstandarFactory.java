package org.model.factory;

import org.model.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ClinicaEstandarFactory implements ClinicaAbstractFactory {
    @Override
    public Cita crearCita(Paciente p, Odontologo o, LocalDateTime fecha, String motivo) {
        return Cita.builder()
                .paciente(p)
                .odontologo(o)
                .fechaHora(fecha)
                .motivo(motivo)
                .estado(Cita.EstadoCita.AGENDADA)
                .build();
    }

    @Override
    public Radiografia crearRadiografia(Paciente p, Empleado tecnico, String tipo, String archivoPath) {
        return Radiografia.builder()
                .paciente(p)
                .tecnicoRadiologia(tecnico)
                .tipo(tipo)
                .archivoPath(archivoPath)
                .fechaToma(null)
                .build();
    }

    @Override
    public Diagnostico crearDiagnostico(Paciente p, Odontologo o, String descripcion) {
        return Diagnostico.builder()
                .paciente(p)
                .odontologo(o)
                .descripcion(descripcion)
                .fechaDiagnostico(LocalDateTime.now())
                .build();
    }

    @Override
    public PlanTratamiento crearPlanTratamiento(Diagnostico d, String descripcion, BigDecimal costoTotal, Integer duracionDias) {
        return PlanTratamiento.builder()
                .diagnostico(d)
                .descripcion(descripcion)
                .costoTotal(costoTotal)
                .duracionEstimadaDias(duracionDias)
                .build();
    }

    @Override
    public Factura crearFactura(Paciente p, BigDecimal montoTotal, String metodoPago) {
        return Factura.builder()
                .paciente(p)
                .montoTotal(montoTotal)
                .metodoPago(metodoPago)
                .fechaEmision(LocalDateTime.now())
                .build();
    }
}
