package org.model.patterns.adapter;

import org.model.PlanTratamiento;
import org.model.Paciente;

public interface DigitalSigner {
    boolean signPlan(PlanTratamiento plan, Paciente paciente);
}
