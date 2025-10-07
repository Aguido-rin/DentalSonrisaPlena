package org.model.factoryMethod;

import org.model.enums.AreaLaboral;
import org.model.Empleado;

import java.math.BigDecimal;

public class OdontologoCreator {

    public Empleado create(String codigo, String nombres) {
        return Empleado.builder()
                .codigo(codigo)
                .nombres(nombres)
                .areaLaboral(AreaLaboral.ODONTOLOGIA)
                .sueldo(BigDecimal.valueOf(3500))
                .horasExtras(0)
                .afiliadoAFP(Boolean.TRUE)
                .build();
    }

}
