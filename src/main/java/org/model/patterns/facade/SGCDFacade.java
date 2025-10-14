package org.model.patterns.facade;

import org.model.patterns.adapter.DigitalSigner;
import org.model.patterns.adapter.ReminderNotifier;
import org.model.*;
import org.model.patterns.factorymethod.CitaProgramadaCreator;
import org.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class SGCDFacade {

    private final PacienteRepository pacienteRepo;
    private final OdontologoRepository odontologoRepo;
    private final CitaRepository citaRepo;
    private final RadiografiaRepository radiografiaRepo;
    private final DiagnosticoRepository diagnosticoRepo;
    private final PlanTratamientoRepository planRepo;
    private final SesionTratamientoRepository sesionRepo;
    private final FacturaRepository facturaRepo;

    private final ReminderNotifier notifier;
    private final DigitalSigner signer;

    public SGCDFacade(PacienteRepository pacienteRepo,
                      OdontologoRepository odontologoRepo,
                      CitaRepository citaRepo,
                      RadiografiaRepository radiografiaRepo,
                      DiagnosticoRepository diagnosticoRepo,
                      PlanTratamientoRepository planRepo,
                      SesionTratamientoRepository sesionRepo,
                      FacturaRepository facturaRepo,
                      ReminderNotifier notifier,
                      DigitalSigner signer) {
        this.pacienteRepo = pacienteRepo;
        this.odontologoRepo = odontologoRepo;
        this.citaRepo = citaRepo;
        this.radiografiaRepo = radiografiaRepo;
        this.diagnosticoRepo = diagnosticoRepo;
        this.planRepo = planRepo;
        this.sesionRepo = sesionRepo;
        this.facturaRepo = facturaRepo;
        this.notifier = notifier;
        this.signer = signer;
    }

    // RF-01: Registro de pacientes
    public Paciente registrarPaciente(Paciente p) {
        return pacienteRepo.save(p);
    }

    // RF-02: Registro de citas (agenda y gestión)
    public Cita agendarCita(Long idPaciente, Long idOdontologo, LocalDateTime fecha, String motivo) {
        Paciente p = pacienteRepo.findById(idPaciente).orElseThrow();
        Odontologo o = odontologoRepo.findById(idOdontologo).orElseThrow();
        Cita cita = new CitaProgramadaCreator().crear(p, o, fecha, motivo);
        Cita saved = citaRepo.save(cita);
        notifier.sendRecordatorioCita(saved, p);
        return saved;
    }

    // RF-03: Orden de radiografías (se modela como creación de registro pendiente de archivo)
    public Radiografia emitirOrdenRadiografias(Long idPaciente, String tipo) {
        Paciente p = pacienteRepo.findById(idPaciente).orElseThrow();
        Radiografia r = Radiografia.builder()
                .paciente(p)
                .tipo(tipo)
                .build();
        return radiografiaRepo.save(r);
    }

    // RF-04: Carga de imágenes radiográficas
    public Radiografia cargarRadiografia(Long idRadiografia, String archivoPath, Long idTecnico) {
        Radiografia r = radiografiaRepo.findById(idRadiografia).orElseThrow();
        Empleado tecnico = new Empleado();
        tecnico.setId(idTecnico);
        r.setTecnicoRadiologia(tecnico);
        r.setArchivoPath(archivoPath);
        r.setFechaToma(LocalDateTime.now());
        return radiografiaRepo.save(r);
    }

    // RF-05: Registro de diagnóstico
    public Diagnostico registrarDiagnostico(Long idPaciente, Long idOdontologo, String descripcion) {
        Paciente p = pacienteRepo.findById(idPaciente).orElseThrow();
        Odontologo o = odontologoRepo.findById(idOdontologo).orElseThrow();
        Diagnostico d = Diagnostico.builder()
                .paciente(p)
                .odontologo(o)
                .descripcion(descripcion)
                .fechaDiagnostico(LocalDateTime.now())
                .build();
        return diagnosticoRepo.save(d);
    }

    // RF-06: Generación de plan de tratamiento
    public PlanTratamiento generarPlanTratamiento(Long idDiagnostico, String descripcion, BigDecimal costoTotal, Integer duracionDias) {
        Diagnostico d = diagnosticoRepo.findById(idDiagnostico).orElseThrow();
        PlanTratamiento plan = PlanTratamiento.builder()
                .diagnostico(d)
                .descripcion(descripcion)
                .costoTotal(costoTotal)
                .duracionEstimadaDias(duracionDias)
                .build();
        return planRepo.save(plan);
    }

    // RF-07: Seguimiento de tratamiento (registrar una sesión)
    public SesionTratamiento registrarSesion(Long idPlan, Long idPaciente, Long idOdontologo, LocalDateTime fecha, String descripcion, boolean realizada) {
        PlanTratamiento plan = planRepo.findById(idPlan).orElseThrow();
        Paciente p = pacienteRepo.findById(idPaciente).orElseThrow();
        Odontologo o = idOdontologo != null ? odontologoRepo.findById(idOdontologo).orElse(null) : null;
        SesionTratamiento s = SesionTratamiento.builder()
                .planTratamiento(plan)
                .paciente(p)
                .odontologo(o)
                .fechaHora(fecha)
                .descripcionProcedimiento(descripcion)
                .realizada(realizada)
                .build();
        return sesionRepo.save(s);
    }

    // RF-08: Generación de factura
    public Factura generarFactura(Long idPaciente, BigDecimal total, String metodoPago, List<DetalleFactura> detalles) {
        Paciente p = pacienteRepo.findById(idPaciente).orElseThrow();
        Factura f = Factura.builder()
                .paciente(p)
                .montoTotal(total)
                .metodoPago(metodoPago)
                .fechaEmision(LocalDateTime.now())
                .detalles(detalles)
                .build();
        if (detalles != null) detalles.forEach(d -> d.setFactura(f));
        return facturaRepo.save(f);
    }

    // RF-10: Recordatorios automáticos (al menos 24h antes)
    public void programarRecordatorioCita(Long idCita) {
        Cita c = citaRepo.findById(idCita).orElseThrow();
        notifier.sendRecordatorioCita(c, c.getPaciente());
    }
}
