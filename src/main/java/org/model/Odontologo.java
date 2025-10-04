package org.model;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Table(name = "odontologo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Odontologo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombres;
    private String especialidad;
    private String telefono;
}
