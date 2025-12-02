/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.model.command;

/**
 *
 * @author Usuario
 */
public class ConfirmarCitaCommand implements Command {

    private final CitaService citaService;

    public ConfirmarCitaCommand(CitaService citaService) {
        this.citaService = citaService;
    }

    @Override
    public void ejecutar() {
        citaService.confirmar();
    }
}
