package org.model.abstractFactory;

import org.model.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface ClinicaAbstractFactory{

    Cita nuevaCita(Paciente p, Odontologo o, LocalDateTime fecha, String motivo);

    Factura nuevaFactura(Paciente p, BigDecimal montoTotal, String metodoPago);
}
