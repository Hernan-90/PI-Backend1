package RoguinSuarezSegura.ProyectoIntegrador.Controller;

import RoguinSuarezSegura.ProyectoIntegrador.DTO.PacienteDTO;
import RoguinSuarezSegura.ProyectoIntegrador.Exception.ResourceNotFoundException;
import RoguinSuarezSegura.ProyectoIntegrador.Model.Paciente;
import RoguinSuarezSegura.ProyectoIntegrador.Service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    private PacienteService pacienteService;

    @Autowired
    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    //Acá van los métodos para las peticiones Http
    @PostMapping("/guardar")
    public ResponseEntity<PacienteDTO> guardarPaciente(@RequestBody Paciente paciente){
        return ResponseEntity.ok(pacienteService.guardar(paciente));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<PacienteDTO>> buscarPacientes(){
        return ResponseEntity.ok(pacienteService.buscarTodos());
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<Optional<Paciente>> buscarPacientePorId(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPorId(id);
        if(pacienteBuscado.isPresent()){
            return ResponseEntity.ok(pacienteService.buscarPorId(id));
        } else {
            throw new ResourceNotFoundException("No se encontró al paciente solicitado.");
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarPaciente(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPorId(id);
        if(pacienteBuscado.isPresent()){
            pacienteService.eliminar(id);
            return ResponseEntity.ok("Paciente eliminado correctamente");
        } else {
            throw new ResourceNotFoundException("No se encontró al paciente solicitado.");
        }
    }

    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizarPaciente(@RequestBody Paciente paciente) throws ResourceNotFoundException {
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPorId(paciente.getId());
        if(pacienteBuscado.isPresent()){
            pacienteService.actualizar(paciente);
            return ResponseEntity.ok("Paciente actualizado correctamente");
        } else {
            throw new ResourceNotFoundException("No se encontró al paciente solicitado.");
        }
    }
}
