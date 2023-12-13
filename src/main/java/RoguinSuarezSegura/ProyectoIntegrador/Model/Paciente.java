package RoguinSuarezSegura.ProyectoIntegrador.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "pacientes")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nombre;

    @Column
    private String apellido;

    @Column(unique = true)
    private String email;

    @Column
    private String cedula;

    @Column
    private LocalDate fechaIngreso;

    @OneToOne(cascade = CascadeType.ALL) // "Un paciente tiene una unica dirección"
    @JoinColumn(name="domicilio_id", referencedColumnName = "id")
    private Domicilio domicilio;

    @OneToMany(mappedBy = "paciente", fetch = FetchType.LAZY) // "Un paciente tiene varios turnos"
    @JsonIgnore
    private Set<Turno> turnos = new HashSet<>();

    public Paciente (){

    }

    public Paciente(String nombre, String apellido, String email, String cedula, LocalDate fechaIngreso, Domicilio domicilio) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.cedula = cedula;
        this.fechaIngreso = fechaIngreso;
        this.domicilio = domicilio;
    }

    public Paciente(Long id, String nombre, String apellido, String email, String cedula, LocalDate fechaIngreso, Domicilio domicilio) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.cedula = cedula;
        this.fechaIngreso = fechaIngreso;
        this.domicilio = domicilio;
    }
}
