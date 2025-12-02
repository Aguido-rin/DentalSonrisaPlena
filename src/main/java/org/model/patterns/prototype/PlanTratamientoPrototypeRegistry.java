package org.model.patterns.prototype;

import org.model.Diagnostico;
import org.model.PlanTratamiento;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PlanTratamientoPrototypeRegistry {
    private final Map<String, PlanTratamiento> prototipos = new ConcurrentHashMap<>();

    public void registrar(String clave, PlanTratamiento prototipo) {
        prototipos.put(clave, prototipo);
    }

    public PlanTratamiento crearDesde(String clave, Diagnostico diag) {
        PlanTratamiento base = prototipos.get(clave);
        if (base == null) throw new IllegalArgumentException("No existe prototipo de plan: " + clave);
        return PlanTratamiento.builder()
                .diagnostico(diag)
                .descripcion(base.getDescripcion())
                .costoTotal(base.getCostoTotal())
                .duracionEstimadaDias(base.getDuracionEstimadaDias())
                .build();
    }

    public static PlanTratamientoPrototypeRegistry defaults() {
        PlanTratamientoPrototypeRegistry reg = new PlanTratamientoPrototypeRegistry();
        reg.registrar("LIMPIEZA", PlanTratamiento.builder()
                .descripcion("Limpieza dental")
                .costoTotal(new BigDecimal("120.00"))
                .duracionEstimadaDias(1)
                .build());
        reg.registrar("ENDODONCIA", PlanTratamiento.builder()
                .descripcion("Endodoncia")
                .costoTotal(new BigDecimal("800.00"))
                .duracionEstimadaDias(7)
                .build());
        reg.registrar("EXTRACCION", PlanTratamiento.builder()
                .descripcion("Extracción")
                .costoTotal(new BigDecimal("200.00"))
                .duracionEstimadaDias(1)
                .build());
        reg.registrar("BLANQUEAMIENTO", PlanTratamiento.builder()
                .descripcion("Blanqueamiento")
                .costoTotal(new BigDecimal("600.00"))
                .duracionEstimadaDias(3)
                .build());
        reg.registrar("ORTODONCIA", PlanTratamiento.builder()
                .descripcion("Ortodoncia")
                .costoTotal(new BigDecimal("5000.00"))
                .duracionEstimadaDias(365)
                .build());
        return reg;
    }
}
