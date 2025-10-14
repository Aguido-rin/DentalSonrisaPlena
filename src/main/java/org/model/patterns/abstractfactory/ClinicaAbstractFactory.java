package org.model.patterns.abstractfactory;

import org.model.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface ClinicaAbstractFactory {
    Cita crearCita(Paciente p, Odontologo o, LocalDateTime fecha, String motivo);
    Radiografia crearRadiografia(Paciente p, Empleado tecnico, String tipo, String archivoPath);
    Diagnostico crearDiagnostico(Paciente p, Odontologo o, String descripcion);
    PlanTratamiento crearPlanTratamiento(Diagnostico d, String descripcion, BigDecimal costoTotal, Integer duracionDias);
    Factura crearFactura(Paciente p, BigDecimal montoTotal, String metodoPago);
}
