package RoguinSuarezSegura.ProyectoIntegrador.Service;

import RoguinSuarezSegura.ProyectoIntegrador.DTO.TurnoDTO;
import RoguinSuarezSegura.ProyectoIntegrador.Model.Odontologo;
import RoguinSuarezSegura.ProyectoIntegrador.Model.Paciente;
import RoguinSuarezSegura.ProyectoIntegrador.Model.Turno;
import RoguinSuarezSegura.ProyectoIntegrador.Repository.TurnosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoService {
    @Autowired
    private TurnosRepository turnosRepository;

    //Nuestros Métodos y los que devuelven el turno le devolvemos el turnoDTO para evitar información sensible
    public TurnoDTO guardar(TurnoDTO turnoDTO){
        Turno turnoGuardado = turnosRepository.save(turnoDTOConverter(turnoDTO));
        return turnoConverter(turnoGuardado);
    }
    public List<TurnoDTO> buscarTodos(){
        List<Turno> turnos = turnosRepository.findAll();
        List<TurnoDTO> turnosDTO = new ArrayList<>();

        for (Turno turno : turnos) {
            turnosDTO.add(turnoConverter(turno));
        }
        return turnosDTO;
    }
    public Optional<TurnoDTO> buscarPorId(Long id) {
        Optional<Turno> turnoEncontrado = turnosRepository.findById(id);
        if(turnoEncontrado.isPresent()){
            return Optional.of(turnoConverter(turnoEncontrado.get())); // con el metodo get obetenemos la info del turno encontrado
        }
        return Optional.empty();
    }
    public void eliminar(Long id){
        turnosRepository.deleteById(id);
    }
    public void actualizar(TurnoDTO turnoDTO){
        turnosRepository.save(turnoDTOConverter(turnoDTO));
    }

    //Con este método convertimos el turno a un turnoDTO de manera manual
    private TurnoDTO turnoConverter(Turno turno){
        TurnoDTO response = new TurnoDTO();
        response.setId(turno.getId());
        response.setPacienteId(turno.getPaciente().getId());
        response.setOdontologoId(turno.getOdontologo().getId());
        response.setFechaTurno(turno.getFechaTurno());
        return response;
    }

    //Con este método hacemos la inversa de la converción DTO -> normal
    private Turno turnoDTOConverter(TurnoDTO turnoDTO){
        Turno turno = new Turno();

        //creo un paciente para tener el id
        Paciente paciente = new Paciente();
        paciente.setId(turnoDTO.getPacienteId());

        //creo un odontologo para tener el id
        Odontologo odontologo = new Odontologo();
        odontologo.setId(turnoDTO.getOdontologoId());

        //Armo el turno normal
        turno.setId(turnoDTO.getId());
        turno.setPaciente(paciente);
        turno.setOdontologo(odontologo);
        turno.setFechaTurno(turnoDTO.getFechaTurno());
        return turno;
    }
}
