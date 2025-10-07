package org.adapter;

import org.model.Paciente;
import org.model.PlanTratamiento;
import org.springframework.stereotype.Component;

@Component
public class DummyDigitalSignerAdapter implements DigitalSigner {
    @Override
    public boolean signPlan(PlanTratamiento plan, Paciente paciente) {
        // Integración real con proveedor de firma digital
        return true; // Stub
    }
}
