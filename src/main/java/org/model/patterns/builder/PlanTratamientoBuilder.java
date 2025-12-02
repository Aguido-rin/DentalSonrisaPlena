package org.model.patterns.builder;

import org.model.Diagnostico;
import org.model.PlanTratamiento;
import org.model.SesionTratamiento;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PlanTratamientoBuilder {
    private Diagnostico diagnostico;
    private String descripcion;
    private BigDecimal costoTotal;
    private Integer duracionDias;
    private final List<SesionTratamiento> sesiones = new ArrayList<>();

    public PlanTratamientoBuilder paraDiagnostico(Diagnostico d) { this.diagnostico = d; return this; }
    public PlanTratamientoBuilder descripcion(String desc) { this.descripcion = desc; return this; }
    public PlanTratamientoBuilder costoTotal(BigDecimal costo) { this.costoTotal = costo; return this; }
    public PlanTratamientoBuilder duracionDias(Integer dias) { this.duracionDias = dias; return this; }
    public PlanTratamientoBuilder agregarSesion(SesionTratamiento s) { this.sesiones.add(s); return this; }

    public PlanTratamiento build() {
        PlanTratamiento plan = PlanTratamiento.builder()
                .diagnostico(diagnostico)
                .descripcion(descripcion)
                .costoTotal(costoTotal)
                .duracionEstimadaDias(duracionDias)
                .sesiones(new ArrayList<>(sesiones))
                .build();
        plan.getSesiones().forEach(s -> s.setPlanTratamiento(plan));
        return plan;
    }
}
