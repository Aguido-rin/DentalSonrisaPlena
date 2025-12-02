package org.view;

import org.model.*;
import org.model.proxy.HistorialMedicoProxy;
import org.repository.*;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.main.DentalSonrisaPlenaApplication;
import org.model.abstractFactory.ClinicaAbstractFactory;
import org.model.command.ConfirmarCitaCommand;
import org.model.facade.SGCDFacade;
import org.model.factory.ClinicaExpressFactory;

/**
 * Interfaz Gráfica Principal para Dental Sonrisa Plena.
 * Se conecta al contexto de Spring Boot para usar los servicios y repositorios existentes.
 */
public class DentalVistas extends JFrame {

    private final SGCDFacade facade;
    private final PacienteRepository pacienteRepo;
    private final OdontologoRepository odontologoRepo;
    private final CitaRepository citaRepo;
    
    // Componentes de UI
    private JTabbedPane tabbedPane;
    private JPanel pnlPacientes, pnlCitas, pnlTratamientos, pnlPagos;
    private JTable tblPacientes, tblCitas;
    private DefaultTableModel modeloPacientes, modeloCitas;

    // Usuario "Logueado" para pruebas de Proxy
    private String rolUsuarioActual = "RECEPCIONISTA"; 
    private JComboBox<String> cmbRolSimulado;

    public DentalVistas(ConfigurableApplicationContext context) {
        // Inyectamos dependencias manualmente desde el contexto de Spring
        this.facade = context.getBean("sgcdFacadePattern", SGCDFacade.class); // Nombre del bean corregido
        this.pacienteRepo = context.getBean(PacienteRepository.class);
        this.odontologoRepo = context.getBean(OdontologoRepository.class);
        this.citaRepo = context.getBean(CitaRepository.class);

        initComponents();
        cargarDatosIniciales();
    }

    private void initComponents() {
        setTitle("Sistema de Gestión - Dental Sonrisa Plena");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // --- BARRA SUPERIOR (Simulación de Login) ---
        JPanel pnlTop = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlTop.add(new JLabel("Rol Actual:"));
        cmbRolSimulado = new JComboBox<>(new String[]{"RECEPCIONISTA", "ODONTOLOGO", "ADMIN"});
        cmbRolSimulado.addActionListener(e -> rolUsuarioActual = (String) cmbRolSimulado.getSelectedItem());
        pnlTop.add(cmbRolSimulado);
        add(pnlTop, BorderLayout.NORTH);

        // --- PESTAÑAS PRINCIPALES ---
        tabbedPane = new JTabbedPane();

        // 1. PESTAÑA PACIENTES
        pnlPacientes = crearPanelPacientes();
        tabbedPane.addTab("Pacientes", new ImageIcon(), pnlPacientes, "Gestión de Pacientes");

        // 2. PESTAÑA CITAS
        pnlCitas = crearPanelCitas();
        tabbedPane.addTab("Citas", new ImageIcon(), pnlCitas, "Agenda de Citas");

        // 3. PESTAÑA TRATAMIENTOS (Demo Abstract Factory)
        pnlTratamientos = crearPanelTratamientos();
        tabbedPane.addTab("Clínica Express", new ImageIcon(), pnlTratamientos, "Servicios Rápidos");

        add(tabbedPane, BorderLayout.CENTER);
    }

    // ========================================================================
    // PANEL DE PACIENTES
    // ========================================================================
    private JPanel crearPanelPacientes() {
        JPanel panel = new JPanel(new BorderLayout());

        // Tabla
        String[] columnas = {"ID", "DNI", "Nombres", "Apellidos", "Email"};
        modeloPacientes = new DefaultTableModel(columnas, 0);
        tblPacientes = new JTable(modeloPacientes);
        panel.add(new JScrollPane(tblPacientes), BorderLayout.CENTER);

        // Botones
        JPanel pnlBotones = new JPanel();
        JButton btnNuevo = new JButton("Nuevo Paciente");
        JButton btnHistorial = new JButton("Ver Historial (Proxy)");
        JButton btnRefrescar = new JButton("Refrescar Lista");

        btnNuevo.addActionListener(e -> mostrarFormularioPaciente());
        btnHistorial.addActionListener(e -> verHistorialSeleccionado());
        btnRefrescar.addActionListener(e -> cargarPacientes());

        pnlBotones.add(btnNuevo);
        pnlBotones.add(btnHistorial);
        pnlBotones.add(btnRefrescar);
        panel.add(pnlBotones, BorderLayout.SOUTH);

        return panel;
    }

    private void mostrarFormularioPaciente() {
        JTextField txtDni = new JTextField();
        JTextField txtNombres = new JTextField();
        JTextField txtApellidos = new JTextField();
        JTextField txtEmail = new JTextField();

        Object[] message = {
            "DNI:", txtDni,
            "Nombres:", txtNombres,
            "Apellidos:", txtApellidos,
            "Email:", txtEmail
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Nuevo Paciente", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                Paciente p = Paciente.builder()
                        .dni(txtDni.getText())
                        .nombres(txtNombres.getText())
                        .apellidos(txtApellidos.getText())
                        .email(txtEmail.getText())
                        .telefono("000000") // Default
                        .build();
                facade.registrarPaciente(p);
                cargarPacientes();
                JOptionPane.showMessageDialog(this, "Paciente registrado con éxito.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al registrar: " + ex.getMessage());
            }
        }
    }

    private void verHistorialSeleccionado() {
        int row = tblPacientes.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un paciente primero.");
            return;
        }
        Long id = (Long) modeloPacientes.getValueAt(row, 0);
        Paciente p = pacienteRepo.findById(id).orElse(null);

        // Uso del PATRÓN PROXY
        HistorialMedicoProxy proxy = new HistorialMedicoProxy();
        
        // Redirigimos la salida de consola a un Dialogo para ver el resultado visualmente
        // NOTA: En una app real, el proxy retornaría datos, aquí imprime en consola.
        // Simularemos la respuesta visual basada en la lógica del proxy.
        
        System.out.println("--- Intento de acceso PROXY ---");
        proxy.verHistorialCompleto(p, rolUsuarioActual);

        if ("ODONTOLOGO".equals(rolUsuarioActual) || "ADMIN".equals(rolUsuarioActual)) {
            JOptionPane.showMessageDialog(this, 
                "ACCESO CONCEDIDO.\nMostrando historial médico completo de " + p.getNombres() + "...\n(Ver detalles en consola)", 
                "Historial Médico", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, 
                "ACCESO DENEGADO.\nEl rol " + rolUsuarioActual + " no tiene permisos para ver historias clínicas.", 
                "Seguridad", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarPacientes() {
        modeloPacientes.setRowCount(0);
        List<Paciente> lista = pacienteRepo.findAll();
        for (Paciente p : lista) {
            modeloPacientes.addRow(new Object[]{p.getIdPaciente(), p.getDni(), p.getNombres(), p.getApellidos(), p.getEmail()});
        }
    }

    // ========================================================================
    // PANEL DE CITAS
    // ========================================================================
    private JPanel crearPanelCitas() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] columnas = {"ID", "Paciente", "Odontólogo", "Fecha", "Motivo", "Estado"};
        modeloCitas = new DefaultTableModel(columnas, 0);
        tblCitas = new JTable(modeloCitas);
        panel.add(new JScrollPane(tblCitas), BorderLayout.CENTER);

        JPanel pnlBotones = new JPanel();
        JButton btnAgendar = new JButton("Agendar Cita");
        JButton btnConfirmar = new JButton("Confirmar (Command)");
        
        btnAgendar.addActionListener(e -> mostrarFormularioCita());
        btnConfirmar.addActionListener(e -> confirmarCitaSeleccionada());
        
        pnlBotones.add(btnAgendar);
        pnlBotones.add(btnConfirmar);
        panel.add(pnlBotones, BorderLayout.SOUTH);

        return panel;
    }

    private void mostrarFormularioCita() {
        // Simplificado: Usamos IDs o el primer odontólogo disponible
        List<Odontologo> odontologos = odontologoRepo.findAll();
        if(odontologos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay odontólogos registrados.");
            return;
        }
        
        JComboBox<Odontologo> cmbOdontologos = new JComboBox<>(odontologos.toArray(new Odontologo[0]));
        // Renderer para ver nombres en el combo
        cmbOdontologos.setRenderer((list, value, index, isSelected, cellHasFocus) -> {
            if(value != null) return new JLabel(value.getNombres() + " " + value.getApellidos());
            return new JLabel("");
        });

        JTextField txtIdPaciente = new JTextField();
        JTextField txtMotivo = new JTextField("Revisión General");

        Object[] message = {
            "ID Paciente (Ver tab Pacientes):", txtIdPaciente,
            "Odontólogo:", cmbOdontologos,
            "Motivo:", txtMotivo
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Agendar Cita", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                Long idPac = Long.parseLong(txtIdPaciente.getText());
                Odontologo o = (Odontologo) cmbOdontologos.getSelectedItem();
                
                facade.agendarCita(idPac, o.getIdOdontologo(), LocalDateTime.now().plusDays(1), txtMotivo.getText());
                cargarCitas();
                JOptionPane.showMessageDialog(this, "Cita agendada. (Patrón Observer notificado)");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }
    
    private void confirmarCitaSeleccionada() {
        int row = tblCitas.getSelectedRow();
        if(row == -1) return;
        Long idCita = (Long) modeloCitas.getValueAt(row, 0);
        Cita cita = citaRepo.findById(idCita).orElseThrow();
        
        // PATRÓN COMMAND
        // No tenemos la clase CitaService inyectada, la instanciamos para el comando
        org.model.command.CitaService service = new org.model.command.CitaService(cita);
        ConfirmarCitaCommand cmd = new ConfirmarCitaCommand(service);
        cmd.ejecutar();
        
        citaRepo.save(cita);
        cargarCitas();
        JOptionPane.showMessageDialog(this, "Cita confirmada mediante Command.");
    }

    private void cargarCitas() {
        modeloCitas.setRowCount(0);
        List<Cita> lista = citaRepo.findAll();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        for (Cita c : lista) {
            modeloCitas.addRow(new Object[]{
                c.getIdCita(),
                c.getPaciente().getNombres(),
                c.getOdontologo().getApellidos(),
                c.getFechaHora().format(fmt),
                c.getMotivo(),
                c.getEstado()
            });
        }
    }

    // ========================================================================
    // PANEL TRATAMIENTOS (ABSTRACT FACTORY DEMO)
    // ========================================================================
    private JPanel crearPanelTratamientos() {
        JPanel panel = new JPanel(new FlowLayout());
        
        JButton btnExpress = new JButton("Simular Consulta Express");
        JTextArea txtLog = new JTextArea(10, 40);
        txtLog.setEditable(false);
        
        btnExpress.addActionListener(e -> {
            // PATRÓN ABSTRACT FACTORY
            ClinicaAbstractFactory factory = new ClinicaExpressFactory();
            
            // Creamos objetos dummy en memoria para la demo visual
            Paciente pDummy = new Paciente(); pDummy.setNombres("Paciente Express");
            Odontologo oDummy = new Odontologo(); oDummy.setApellidos("Dr. Veloz");
            
            Cita c = factory.crearCita(pDummy, oDummy, LocalDateTime.now(), "Dolor súbito");
            Diagnostico d = factory.crearDiagnostico(pDummy, oDummy, "Inflamación");
            
            txtLog.setText("");
            txtLog.append("--- MODO CLÍNICA EXPRESS ACTIVADO ---\n");
            txtLog.append("Cita Creada: " + c.getMotivo() + "\n");
            txtLog.append("Diagnóstico: " + d.getDescripcion() + "\n");
            txtLog.append("Nota: Estos objetos se crearon con la fábrica 'Express'.\n");
        });
        
        panel.add(btnExpress);
        panel.add(new JScrollPane(txtLog));
        return panel;
    }

    private void cargarDatosIniciales() {
        cargarPacientes();
        cargarCitas();
    }

    // ========================================================================
    // MAIN PARA EJECUTAR LA INTERFAZ
    // ========================================================================
    public static void main(String[] args) {
        // Iniciamos Spring Boot en modo "headless(false)" para permitir Swing
        ConfigurableApplicationContext ctx = new SpringApplicationBuilder(DentalSonrisaPlenaApplication.class)
                .headless(false)
                .run(args);

        // Iniciamos la ventana en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            DentalVistas frame = new DentalVistas(ctx);
            frame.setVisible(true);
        });
    }
}