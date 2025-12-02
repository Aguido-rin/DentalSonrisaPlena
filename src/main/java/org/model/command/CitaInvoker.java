/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.model.command;

/**
 *
 * @author Usuario
 */
public class CitaInvoker {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void ejecutarAccion() {
        command.ejecutar();
    }
}