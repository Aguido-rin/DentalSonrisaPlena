package org.model.patterns.factorymethod;

public class RadiografiaPanoramicaCreator extends RadiografiaCreator {
    @Override
    protected String getTipo() {
        return "PANORAMICA";
    }
}
