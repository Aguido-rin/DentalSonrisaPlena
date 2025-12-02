package org.model.singleton;

import java.time.Duration;

public enum ClinicaRules {
    INSTANCE;

    private final Duration MAX_TIEMPO_DIAGNOSTICO = Duration.ofHours(2);
    private final Duration MAX_DEMORA_RADIOLOGIA = Duration.ofMinutes(5);

    public Duration maxTiempoDiagnostico() { return MAX_TIEMPO_DIAGNOSTICO; }
    public Duration maxDemoraRadiologia() { return MAX_DEMORA_RADIOLOGIA; }
}
