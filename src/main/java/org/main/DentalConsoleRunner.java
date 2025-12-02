package org.main;

import org.model.*;
import org.model.Bridge.*;
import org.model.Observer.*;
import org.model.State.CitaContext;
import org.model.State.CitaAgendada;
import org.model.command.*;
import org.model.facade.SGCDFacade;
import org.model.abstractFactory.*;
import org.model.proxy.HistorialMedicoProxy;
import org.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.model.enums.AreaLaboral;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

@Component
public class DentalConsoleRunner implements CommandLineRunner {

    private final SGCDFacade facade;
    private final PacienteRepository pacienteRepo;
    private final OdontologoRepository odontologoRepo;
    private final DiagnosticoRepository diagnosticoRepo;
    private final PlanTratamientoRepository planRepo;
    private final SesionTratamientoRepository sesionRepo;
    private final RadiografiaRepository radiografiaRepo;
    private final FacturaRepository facturaRepo;

    public DentalConsoleRunner(SGCDFacade facade,
                               PacienteRepository pacienteRepo,
                               OdontologoRepository odontologoRepo,
                               DiagnosticoRepository diagnosticoRepo,
                               PlanTratamientoRepository planRepo,
                               SesionTratamientoRepository sesionRepo,
                               RadiografiaRepository radiografiaRepo,
                               FacturaRepository facturaRepo) {
        this.facade = facade;
        this.pacienteRepo = pacienteRepo;
        this.odontologoRepo = odontologoRepo;
        this.diagnosticoRepo = diagnosticoRepo;
        this.planRepo = planRepo;
        this.sesionRepo = sesionRepo;
        this.radiografiaRepo = radiografiaRepo;
        this.facturaRepo = facturaRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=================================================");
        System.out.println("    CLÍNICA DENTAL SONRISA PLENA - CONSOLE APP   ");
        System.out.println("=================================================");

        // --- 1. REGISTRO DE DATOS MAESTROS (Personas) ---
        System.out.println("\n--- [1] REGISTRO DE DATOS MAESTROS ---");

        // A) Pacientes
        Paciente p1 = obtenerOCrearPaciente("12345678", "Ricardo", "Aguilar", "ricardo@gmail.com");
        Paciente p2 = obtenerOCrearPaciente("87654321", "Peter", "Adams", "peter@gmail.com");

        // B) Odontólogos
        Odontologo o1 = obtenerOCrearOdontologo("Pedro", "Pedrozki", "Ortodoncia", "COP-111");
        Odontologo o2 = obtenerOCrearOdontologo("Aleki", "Liam", "Endodoncia", "COP-222");

        // C) Empleado Técnico (Objeto en memoria para demos)
        Empleado tecnicoRadio = Empleado.builder()
                .codigo("TEC-001")
                .nombres("Juan Manolo")
                .areaLaboral(AreaLaboral.ODONTOLOGIA)
                .sueldo(BigDecimal.valueOf(2500))
                .build();
        System.out.println(">> Técnico de radiología listo: " + tecnicoRadio.getNombres());


        // --- 2. GESTIÓN DE CITAS ---
        System.out.println("\n--- [2] GESTIÓN DE CITAS ---");
        Cita cita = facade.agendarCita(p1.getIdPaciente(), o1.getIdOdontologo(), LocalDateTime.now().plusDays(1), "Dolor de muela");
        System.out.println(">> Cita agendada ID: " + cita.getIdCita() + " para " + p1.getNombres());


        // --- 3. DIAGNÓSTICO Y PLAN DE TRATAMIENTO ---
        System.out.println("\n--- [3] DIAGNÓSTICO Y TRATAMIENTO ---");
        
        // A) Diagnóstico
        Diagnostico diagnostico = obtenerOCrearDiagnostico(p1, o1, "Caries profunda en premolar superior");
        
        // B) Plan de Tratamiento
        PlanTratamiento plan = obtenerOCrearPlan(diagnostico, "Endodoncia y reconstrucción", new BigDecimal("850.00"));

        // C) Sesión de Tratamiento
        SesionTratamiento sesion = obtenerOCrearSesion(plan, p1, o1, "Limpieza de conductos");


        // --- 4. RADIOGRAFÍAS ---
        System.out.println("\n--- [4] RADIOGRAFÍAS ---");
        Radiografia radio = obtenerOCrearRadiografia(p1, "PANORAMICA");


        // --- 5. FACTURACIÓN ---
        System.out.println("\n--- [5] FACTURACIÓN ---");
        Factura factura = obtenerOCrearFactura(p1, new BigDecimal("850.00"), "TARJETA");


        // --- 6. DEMOSTRACIÓN DE PATRONES (Lógica de negocio) ---
        System.out.println("\n--- [6] DEMOSTRACIÓN DE PATRONES EN ACCIÓN ---");
        
        // Observer
        System.out.println("> [Observer] Notificando cambio de cita...");
        CitaObservable citaObservable = new CitaObservable(cita);
        citaObservable.registrar(new PacienteObserver());
        citaObservable.registrar(new OdontologoObserver());
        citaObservable.cambiarEstado(Cita.EstadoCita.ASISTIO);

        // State
        System.out.println("> [State] Flujo de estado de la cita...");
        CitaContext context = new CitaContext(new CitaAgendada());
        context.mostrarEstado();
        context.siguienteEstado(); // Pasa a ASISTIO
        
        // Command
        System.out.println("> [Command] Ejecutando comandos...");
        CitaService citaService = new CitaService(cita);
        CitaInvoker invoker = new CitaInvoker();
        invoker.setCommand(new ConfirmarCitaCommand(citaService));
        invoker.ejecutarAccion();

        // Abstract Factory
        System.out.println("> [Abstract Factory] Creando insumos 'Express'...");
        ClinicaAbstractFactory fabricaExpress;
        fabricaExpress = new ClinicaExpressFactory();
        Radiografia radioExpress = fabricaExpress.crearRadiografia(p2, tecnicoRadio, "PERIAPICAL", "/tmp/img.jpg");
        System.out.println("   Radiografía Express creada: " + radioExpress.getTipo());

        // Bridge
        System.out.println("> [Bridge] Procesando pago desacoplado...");
        FacturaBridge pagoBridge = new FacturaFisica(new PagoTarjeta());
        pagoBridge.pagar(new BigDecimal("150.00"));

        // Proxy
        System.out.println("> [Proxy] Control de acceso al historial...");
        HistorialMedicoProxy proxy = new HistorialMedicoProxy();
        proxy.verHistorialCompleto(p1, "RECEPCIONISTA"); // Denegado
        proxy.verHistorialCompleto(p1, "ODONTOLOGO");    // Permitido

        System.out.println("\n=================================================");
        System.out.println("         FIN DE LA EJECUCIÓN DEL SISTEMA         ");
        System.out.println("=================================================");
    }

    // =========================================================================
    // MÉTODOS AUXILIARES (Buscan en BD antes de crear para evitar duplicados)
    // =========================================================================

    private Paciente obtenerOCrearPaciente(String dni, String nombre, String apellido, String email) {
        // Buscamos en memoria trayendo todo (solo para demo, en prod usar findByDni)
        Optional<Paciente> existente = pacienteRepo.findAll().stream()
                .filter(p -> dni.equals(p.getDni()))
                .findFirst();

        if (existente.isPresent()) {
            System.out.println("   (Recuperado) Paciente: " + existente.get().getNombres());
            return existente.get();
        }
        Paciente nuevo = Paciente.builder()
                .dni(dni).nombres(nombre).apellidos(apellido).email(email).telefono("999000000")
                .build();
        return facade.registrarPaciente(nuevo);
    }

    private Odontologo obtenerOCrearOdontologo(String nombre, String apellido, String especialidad, String colegiatura) {
        Optional<Odontologo> existente = odontologoRepo.findAll().stream()
                .filter(o -> colegiatura.equals(o.getColegiatura()))
                .findFirst();

        if (existente.isPresent()) {
            System.out.println("   (Recuperado) Odontólogo: " + existente.get().getNombres());
            return existente.get();
        }
        Odontologo nuevo = Odontologo.builder()
                .nombres(nombre).apellidos(apellido).especialidad(especialidad).colegiatura(colegiatura)
                .build();
        return odontologoRepo.save(nuevo);
    }

    private Diagnostico obtenerOCrearDiagnostico(Paciente p, Odontologo o, String descripcion) {
        // Simplificamos búsqueda: si el paciente ya tiene diagnósticos, devolvemos el último
        List<Diagnostico> lista = diagnosticoRepo.findAll();
        Optional<Diagnostico> existente = lista.stream()
                .filter(d -> d.getPaciente().getIdPaciente().equals(p.getIdPaciente()) && d.getDescripcion().equals(descripcion))
                .findFirst();

        if (existente.isPresent()) {
            System.out.println("   (Recuperado) Diagnóstico ID: " + existente.get().getIdDiagnostico());
            return existente.get();
        }
        System.out.println("   (Nuevo) Creando Diagnóstico...");
        return facade.registrarDiagnostico(p.getIdPaciente(), o.getIdOdontologo(), descripcion);
    }

    private PlanTratamiento obtenerOCrearPlan(Diagnostico d, String descripcion, BigDecimal costo) {
        List<PlanTratamiento> lista = planRepo.findAll();
        Optional<PlanTratamiento> existente = lista.stream()
                .filter(pt -> pt.getDiagnostico().getIdDiagnostico().equals(d.getIdDiagnostico()))
                .findFirst();
        
        if (existente.isPresent()) {
            System.out.println("   (Recuperado) Plan Tratamiento ID: " + existente.get().getIdPlan());
            return existente.get();
        }
        System.out.println("   (Nuevo) Creando Plan de Tratamiento...");
        return facade.generarPlanTratamiento(d.getIdDiagnostico(), descripcion, costo, 7);
    }

    private SesionTratamiento obtenerOCrearSesion(PlanTratamiento plan, Paciente p, Odontologo o, String desc) {
        List<SesionTratamiento> lista = sesionRepo.findAll();
        Optional<SesionTratamiento> existente = lista.stream()
                .filter(s -> s.getPlanTratamiento().getIdPlan().equals(plan.getIdPlan()))
                .findFirst();
        
        if (existente.isPresent()) {
            System.out.println("   (Recuperado) Sesión ID: " + existente.get().getIdSesion());
            return existente.get();
        }
        System.out.println("   (Nuevo) Creando Sesión...");
        return facade.registrarSesion(plan.getIdPlan(), p.getIdPaciente(), o.getIdOdontologo(), LocalDateTime.now(), desc, false);
    }

    private Radiografia obtenerOCrearRadiografia(Paciente p, String tipo) {
        List<Radiografia> lista = radiografiaRepo.findAll();
        Optional<Radiografia> existente = lista.stream()
                .filter(r -> r.getPaciente().getIdPaciente().equals(p.getIdPaciente()) && r.getTipo().equals(tipo))
                .findFirst();
        
        if (existente.isPresent()) {
            System.out.println("   (Recuperado) Radiografía ID: " + existente.get().getIdRadiografia());
            return existente.get();
        }
        System.out.println("   (Nuevo) Emitiendo orden de Radiografía...");
        return facade.emitirOrdenRadiografias(p.getIdPaciente(), tipo);
    }

    private Factura obtenerOCrearFactura(Paciente p, BigDecimal total, String metodo) {
        List<Factura> lista = facturaRepo.findAll();
        Optional<Factura> existente = lista.stream()
                .filter(f -> f.getPaciente().getIdPaciente().equals(p.getIdPaciente()) && f.getMontoTotal().compareTo(total) == 0)
                .findFirst();

        if (existente.isPresent()) {
            System.out.println("   (Recuperado) Factura ID: " + existente.get().getIdFactura());
            return existente.get();
        }
        System.out.println("   (Nuevo) Generando Factura...");
        return facade.generarFactura(p.getIdPaciente(), total, metodo, new ArrayList<>());
    }
}