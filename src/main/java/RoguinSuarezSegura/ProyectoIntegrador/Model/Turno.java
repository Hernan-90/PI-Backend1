package RoguinSuarezSegura.ProyectoIntegrador.Model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "turnos")
public class Turno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // "Un paciente tiene varios turnos"
    @JoinColumn(name = "paciente_id",referencedColumnName = "id")
    private Paciente paciente;

    @ManyToOne // "Un odontologo tiene varios turnos"
    @JoinColumn(name = "odontologo_id",referencedColumnName = "id")
    private Odontologo odontologo;

    @Column
    private LocalDate fechaTurno;

    public Turno() {
    }

    public Turno(Paciente paciente, Odontologo odontologo, LocalDate fechaTurno) {
        this.paciente = paciente;
        this.odontologo = odontologo;
        this.fechaTurno = fechaTurno;
    }

    public Turno(Long id, Paciente paciente, Odontologo odontologo, LocalDate fechaTurno) {
        this.id = id;
        this.paciente = paciente;
        this.odontologo = odontologo;
        this.fechaTurno = fechaTurno;
    }
}
