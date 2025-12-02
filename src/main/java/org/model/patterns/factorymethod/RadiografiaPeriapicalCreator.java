package org.model.patterns.factorymethod;

public class RadiografiaPeriapicalCreator extends RadiografiaCreator {
    @Override
    protected String getTipo() {
        return "PERIAPICAL";
    }
}
