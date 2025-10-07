package org.patterns.factorymethod;

public class RadiografiaPeriapicalCreator extends RadiografiaCreator {
    @Override
    protected String getTipo() {
        return "PERIAPICAL";
    }
}
