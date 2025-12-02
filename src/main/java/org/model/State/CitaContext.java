
package org.model.State;

public class CitaContext {
   private EstadoCitaState estado;

    public CitaContext(EstadoCitaState estadoInicial) {
        this.estado = estadoInicial;
    }

    public void setEstado(EstadoCitaState nuevoEstado) {
    this.estado = nuevoEstado;
    }

    public void siguienteEstado() {
        estado.siguiente(this);
    }

    public void mostrarEstado() {
        estado.mostrarEstado();
    }
}