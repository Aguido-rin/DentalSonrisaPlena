package org.model.factory;

import model.Empleado;

public abstract class EmpleadoCreator {
    public abstract Empleado create(String codigo, String nombres);
}
