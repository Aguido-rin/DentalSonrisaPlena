
package org.model.proxy;

import org.model.Paciente;

public class HistorialMedicoRealService implements HistorialMedicoService{

    @Override
    public void verHistorialCompleto(Paciente paciente, String rolUsuario) {
        System.out.println("===== HISTORIAL MÉDICO REAL =====");
        System.out.println("Paciente: " + paciente.getNombres() + " " + paciente.getApellidos());
        System.out.println("Diagnósticos, sesiones, radiografías y facturas mostradas correctamente...");
    }
}
