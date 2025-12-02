/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.model.command;


public class AsistioCitaCommand implements Command {

    private final CitaService citaService;

    public AsistioCitaCommand(CitaService citaService) {
        this.citaService = citaService;
    }

    @Override
    public void ejecutar() {
        citaService.marcarAsistencia();
    }
}

