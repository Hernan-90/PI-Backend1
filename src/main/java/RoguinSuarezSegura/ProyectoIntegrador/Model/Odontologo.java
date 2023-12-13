package RoguinSuarezSegura.ProyectoIntegrador.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "odontologos")
public class Odontologo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String matricula;

    @Column
    private String nombre;

    @Column
    private String apellido;

    @OneToMany(mappedBy = "odontologo", fetch = FetchType.LAZY) // "Un odontologo tiene varios turnos"
    @JsonIgnore
    private Set<Turno> turnos = new HashSet<>();

    public Odontologo() {
    }

    public Odontologo(String matricula, String nombre, String apellido) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public Odontologo(Long id, String matricula, String nombre, String apellido) {
        this.id = id;
        this.matricula = matricula;
        this.nombre = nombre;
        this.apellido = apellido;
    }
}
