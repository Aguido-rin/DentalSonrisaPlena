/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.model.Memento;

import java.util.Stack;

public class HistorialCita {

    private Stack<CitaMemento> historial = new Stack<>();

    public void guardar(CitaMemento memento) {
        historial.push(memento);
    }

    public CitaMemento deshacer() {
        return historial.isEmpty() ? null : historial.pop();
    }
}