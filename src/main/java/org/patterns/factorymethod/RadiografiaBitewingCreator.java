package org.patterns.factorymethod;

public class RadiografiaBitewingCreator extends RadiografiaCreator {
    @Override
    protected String getTipo() {
        return "BITEWING";
    }
}
