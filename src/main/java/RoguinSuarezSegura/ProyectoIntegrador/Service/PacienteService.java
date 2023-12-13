package RoguinSuarezSegura.ProyectoIntegrador.Service;

import RoguinSuarezSegura.ProyectoIntegrador.DTO.PacienteDTO;
import RoguinSuarezSegura.ProyectoIntegrador.Exception.ResourceNotFoundException;
import RoguinSuarezSegura.ProyectoIntegrador.Model.Paciente;
import RoguinSuarezSegura.ProyectoIntegrador.Repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {
    private PacienteRepository pacienteRepository;

    @Autowired
    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    //Nuestros Métodos
    public PacienteDTO guardar(Paciente paciente){
        Paciente pacienteGuardado = pacienteRepository.save(paciente);
        return pacienteConverter(pacienteGuardado);
    }
    public List<PacienteDTO> buscarTodos(){
        List<Paciente> pacientes = pacienteRepository.findAll();
        List<PacienteDTO> pacientesDTO = new ArrayList<>();
        for (Paciente paciente : pacientes) {
            pacientesDTO.add(pacienteConverter(paciente));
        }
        return pacientesDTO;
    }
    public Optional<Paciente> buscarPorId(Long id) throws ResourceNotFoundException{
        Optional<Paciente> pacienteBuscado = pacienteRepository.findById(id);
        if(pacienteBuscado.isPresent()){
            return Optional.of(pacienteBuscado.get());
        } else {
            throw new ResourceNotFoundException("No se encuentró el paciente en el sistema");
        }
    }
    public void eliminar(Long id){
        pacienteRepository.deleteById(id);
    }
    public void actualizar(Paciente paciente){
        pacienteRepository.save(paciente);
    }

    private PacienteDTO pacienteConverter(Paciente paciente){
        PacienteDTO response = new PacienteDTO();
        response.setId(paciente.getId());
        response.setNombre(paciente.getNombre());
        response.setApellido(paciente.getApellido());
        response.setEmail(paciente.getEmail());
        return response;
    }

    private Paciente pacienteDTOConverter(PacienteDTO pacienteDTO) throws ResourceNotFoundException {
        Paciente paciente = new Paciente();
        Optional<Paciente> pacienteBuscado = pacienteRepository.findById(pacienteDTO.getId());
        if(pacienteBuscado.isPresent()){
            paciente.setId(pacienteDTO.getId());
            paciente.setNombre(pacienteDTO.getNombre());
            paciente.setApellido(pacienteDTO.getApellido());
            paciente.setEmail(pacienteDTO.getEmail());
            paciente.setCedula(pacienteBuscado.get().getCedula());
            paciente.setDomicilio(pacienteBuscado.get().getDomicilio());
        } else {
            throw new ResourceNotFoundException("No se encuentró el paciente en el sistema");
        }
        return paciente;
    }
}
