
package org.model.proxy;

import org.model.Paciente;


public class HistorialMedicoProxy implements HistorialMedicoService{
    private HistorialMedicoService servicioReal;
    @Override
    public void verHistorialCompleto(Paciente paciente, String rolUsuario) {
        // CONTROL DE ACCESO
        if (!"ODONTOLOGO".equalsIgnoreCase(rolUsuario) &&
            !"ADMIN".equalsIgnoreCase(rolUsuario)) {

            System.out.println("ACCESO DENEGADO. No tiene permisos.");
            return;
        }

        // inicializamos la implementación real sólo cuando se necesite
        if (servicioReal == null) {
            servicioReal = new HistorialMedicoRealService();
        }

        System.out.println("Acceso válido. Proxy autorizado.");

        // delegamos la llamada al servicio real
        servicioReal.verHistorialCompleto(paciente, rolUsuario);
    }
}