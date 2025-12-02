
package org.model.command;

public class CancelarCitaCommand implements Command {

    private final CitaService citaService;

    public CancelarCitaCommand(CitaService citaService) {
        this.citaService = citaService;
    }

    @Override
    public void ejecutar() {
        citaService.cancelar();
    }
}