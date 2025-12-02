package org.model.factory;

import org.model.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.model.abstractFactory.ClinicaAbstractFactory;

public class ClinicaExpressFactory implements ClinicaAbstractFactory {
    @Override
    public Cita crearCita(Paciente p, Odontologo o, LocalDateTime fecha, String motivo) {
        return Cita.builder()
                .paciente(p)
                .odontologo(o)
                .fechaHora(fecha)
                .motivo("[EXPRESS] " + motivo)
                .estado(Cita.EstadoCita.AGENDADA)
                .build();
    }

    @Override
    public Radiografia crearRadiografia(Paciente p, Empleado tecnico, String tipo, String archivoPath) {
        // En flujo express, se asume carga inmediata si hay archivo disponible
        return Radiografia.builder()
                .paciente(p)
                .tecnicoRadiologia(tecnico)
                .tipo(tipo)
                .archivoPath(archivoPath)
                .fechaToma(archivoPath != null ? LocalDateTime.now() : null)
                .build();
    }

    @Override
    public Diagnostico crearDiagnostico(Paciente p, Odontologo o, String descripcion) {
        return Diagnostico.builder()
                .paciente(p)
                .odontologo(o)
                .descripcion("[EXPRESS] " + descripcion)
                .fechaDiagnostico(LocalDateTime.now())
                .build();
    }

    @Override
    public PlanTratamiento crearPlanTratamiento(Diagnostico d, String descripcion, BigDecimal costoTotal, Integer duracionDias) {
        return PlanTratamiento.builder()
                .diagnostico(d)
                .descripcion("[EXPRESS] " + descripcion)
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
