package org.adapter;

import org.model.Paciente;

public class PacienteToDtoAdapter implements Adapter<Paciente, PacienteDTO> {
    @Override
    public PacienteDTO convert(Paciente p) {
        PacienteDTO dto = new PacienteDTO();
        dto.id = p.getIdPaciente();
        dto.dni = p.getDni();
        dto.nombres = p.getNombres();
        dto.apellidos = p.getApellidos();
        dto.email = p.getEmail();
        return dto;
    }
}
