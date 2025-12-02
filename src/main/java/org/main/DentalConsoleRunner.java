package org.main;

import org.model.*;
import org.model.Bridge.*;
import org.model.Observer.*;
import org.model.State.CitaContext;
import org.model.State.CitaAgendada;
import org.model.command.*;
import org.model.facade.SGCDFacade;
import org.model.patterns.abstractfactory.*;
import org.model.proxy.HistorialMedicoProxy;
import org.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class DentalConsoleRunner implements CommandLineRunner {

    private final SGCDFacade facade;
    // Inyectamos repositorios solo para verificar datos si fuera necesario, 
    // aunque la Facade maneja casi todo.
    private final PacienteRepository pacienteRepo;
    private final OdontologoRepository odontologoRepo;

    public DentalConsoleRunner(SGCDFacade facade, 
                               PacienteRepository pacienteRepo, 
                               OdontologoRepository odontologoRepo) {
        this.facade = facade;
        this.pacienteRepo = pacienteRepo;
        this.odontologoRepo = odontologoRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=================================================");
        System.out.println("   CLÍNICA DENTAL SONRISA PLENA - CONSOLE APP    ");
        System.out.println("=================================================");

        // 1. REGISTRO DE ACTORES (Facade)
        System.out.println("\n--- [1] REGISTRO DE DATOS MAESTROS ---");
        
        Paciente p1 = Paciente.builder()
                .dni("12345678")
                .nombres("Shinra")
                .apellidos("Nanase")
                .email("shinra@gmail.com")
                .telefono("999888777")
                .build();
        p1 = facade.registrarPaciente(p1);
        System.out.println("Paciente registrado: " + p1.getNombres());

        Odontologo o1 = Odontologo.builder()
                .nombres("Arthur")
                .apellidos("Doyle")
                .especialidad("Ortodoncia")
                .colegiatura("COP-555")
                .build();
        // Guardamos manualmente el odontólogo ya que la fachada asume que existen para la cita
        o1 = odontologoRepo.save(o1); 
        System.out.println("Odontólogo registrado: " + o1.getNombres());


        // 2. GESTIÓN DE CITAS (Patrón Abstract Factory + Facade)
        System.out.println("\n--- [2] GESTIÓN DE CITA (FACADE & FACTORY) ---");
        
        // Usamos la Facade que internamente usa CitaProgramadaCreator (Factory Method)
        Cita cita = facade.agendarCita(p1.getIdPaciente(), o1.getIdOdontologo(), LocalDateTime.now().plusDays(1), "Dolor de muela");
        System.out.println("Cita agendada con ID: " + cita.getIdCita() + " | Motivo: " + cita.getMotivo());


        // 3. PATRÓN OBSERVER (Notificaciones)
        System.out.println("\n--- [3] DEMOSTRACIÓN OBSERVER (Notificación de cambios) ---");
        
        CitaObservable citaObservable = new CitaObservable(cita);
        citaObservable.registrar(new PacienteObserver());
        citaObservable.registrar(new OdontologoObserver());
        
        System.out.println("> Cambiando estado de cita para disparar observadores...");
        citaObservable.cambiarEstado(Cita.EstadoCita.ASISTIO);


        // 4. PATRÓN STATE (Flujo de estados)
        System.out.println("\n--- [4] DEMOSTRACIÓN STATE ---");
        CitaContext context = new CitaContext(new CitaAgendada());
        context.mostrarEstado();
        context.siguienteEstado(); // Pasa a ASISTIÓ
        context.mostrarEstado();
        

        // 5. PATRÓN COMMAND (Ejecución de acciones encapsuladas)
        System.out.println("\n--- [5] DEMOSTRACIÓN COMMAND ---");
        CitaService citaService = new CitaService(cita);
        CitaInvoker invoker = new CitaInvoker();
        
        Command confirmar = new ConfirmarCitaCommand(citaService);
        invoker.setCommand(confirmar);
        invoker.ejecutarAccion(); // Ejecuta la confirmación


        // 6. PATRÓN ABSTRACT FACTORY (Modo Express vs Estándar)
        System.out.println("\n--- [6] DEMOSTRACIÓN ABSTRACT FACTORY (Clínica Express) ---");
        ClinicaAbstractFactory fabricaExpress = new ClinicaExpressFactory();
        Diagnostico diagExpress = fabricaExpress.crearDiagnostico(p1, o1, "Revisión rápida");
        System.out.println("Diagnóstico creado: " + diagExpress.getDescripcion());


        // 7. PATRÓN BRIDGE (Pagos Desacoplados)
        System.out.println("\n--- [7] DEMOSTRACIÓN BRIDGE (Pagos) ---");
        BigDecimal monto = new BigDecimal("150.00");
        
        // Factura Física pagada con Tarjeta
        FacturaBridge facturaFisica = new FacturaFisica(new PagoTarjeta()); 
        facturaFisica.pagar(monto);

        // Podríamos cambiar a PagoEfectivo sin cambiar la clase FacturaFisica
        FacturaBridge facturaEfectivo = new FacturaFisica(new PagoEfectivo());
        facturaEfectivo.pagar(new BigDecimal("20.00"));


        // 8. PATRÓN PROXY (Seguridad en Historial Médico)
        System.out.println("\n--- [8] DEMOSTRACIÓN PROXY (Seguridad) ---");
        HistorialMedicoProxy proxy = new HistorialMedicoProxy();
        
        System.out.println("> Intento de acceso como 'RECEPCIONISTA':");
        proxy.verHistorialCompleto(p1, "RECEPCIONISTA"); // Debería denegar
        
        System.out.println("> Intento de acceso como 'ODONTOLOGO':");
        proxy.verHistorialCompleto(p1, "ODONTOLOGO"); // Debería permitir


        System.out.println("\n=================================================");
        System.out.println("        FIN DE LA EJECUCIÓN DEL SISTEMA          ");
        System.out.println("=================================================");
    }
}