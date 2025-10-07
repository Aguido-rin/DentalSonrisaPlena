package org.patterns.prototype;

import org.model.Empleado;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EmpleadoPrototypeRegistry {
    private final Map<String, Empleado> prototipos = new ConcurrentHashMap<>();

    public void registrar(String clave, Empleado prototipo) {
        prototipos.put(clave, prototipo);
    }

    public Empleado crearDesde(String clave) {
        Empleado base = prototipos.get(clave);
        if (base == null) throw new IllegalArgumentException("No existe prototipo: " + clave);
        return base.clone();
    }
}
