
package org.model.Observer;


public interface Subject {

    void registrar(Observer observer);
    void eliminar(Observer observer);
    void notificar();

}