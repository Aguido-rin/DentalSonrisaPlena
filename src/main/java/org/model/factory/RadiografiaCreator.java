package org.model.factory;

import org.model.Empleado;
import org.model.Paciente;
import org.model.Radiografia;

import java.time.LocalDateTime;

public abstract class RadiografiaCreator {
    protected abstract String getTipo();

    public Radiografia crear(Paciente p, Empleado tecnico, String archivoPath) {
        return Radiografia.builder()
                .paciente(p)
                .tecnicoRadiologia(tecnico)
                .tipo(getTipo())
                .archivoPath(archivoPath)
                .fechaToma(archivoPath != null ? LocalDateTime.now() : null)
                .build();
    }
}
