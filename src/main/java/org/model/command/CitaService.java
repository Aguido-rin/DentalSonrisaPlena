/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.model.command;

import org.model.Cita;

public class CitaService {
    private final Cita cita;

    public CitaService(Cita cita) {
        this.cita = cita;
    }

    public void cancelar() {
        cita.setEstado(Cita.EstadoCita.CANCELADA);
        System.out.println("Cita cancelada correctamente.");
    }

    public void marcarAsistencia() {
        cita.setEstado(Cita.EstadoCita.ASISTIO);
        System.out.println("Cita marcada como ASISTIÓ.");
    }

    public void confirmar() {
        cita.setEstado(Cita.EstadoCita.AGENDADA);
        System.out.println("Cita confirmada.");
    }
}
