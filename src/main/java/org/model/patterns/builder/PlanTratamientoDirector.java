package org.model.patterns.builder;

import org.model.Diagnostico;
import org.model.PlanTratamiento;

import java.math.BigDecimal;

public class PlanTratamientoDirector {
    public PlanTratamiento limpiezaBasica(Diagnostico d) {
        return new PlanTratamientoBuilder()
                .paraDiagnostico(d)
                .descripcion("Limpieza dental básica")
                .costoTotal(new BigDecimal("120.00"))
                .duracionDias(1)
                .build();
    }
}
