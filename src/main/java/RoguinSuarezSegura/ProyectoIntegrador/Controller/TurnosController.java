package RoguinSuarezSegura.ProyectoIntegrador.Controller;

import RoguinSuarezSegura.ProyectoIntegrador.DTO.OdontologoDTO;
import RoguinSuarezSegura.ProyectoIntegrador.DTO.PacienteDTO;
import RoguinSuarezSegura.ProyectoIntegrador.DTO.TurnoDTO;
import RoguinSuarezSegura.ProyectoIntegrador.Exception.BadRequestException;
import RoguinSuarezSegura.ProyectoIntegrador.Exception.ResourceNotFoundException;
import RoguinSuarezSegura.ProyectoIntegrador.Model.Odontologo;
import RoguinSuarezSegura.ProyectoIntegrador.Model.Paciente;
import RoguinSuarezSegura.ProyectoIntegrador.Model.Turno;
import RoguinSuarezSegura.ProyectoIntegrador.Service.OdontologoService;
import RoguinSuarezSegura.ProyectoIntegrador.Service.PacienteService;
import RoguinSuarezSegura.ProyectoIntegrador.Service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turnos")
public class TurnosController {
    private TurnoService turnoService;
    private PacienteService pacienteService;
    private OdontologoService odontologoService;

    @Autowired
    public TurnosController(TurnoService turnoService, PacienteService pacienteService, OdontologoService odontologoService) {
        this.turnoService = turnoService;
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
    }

    //Acá van los métodos para las peticiones Http
    @PostMapping("/guardar")
    public ResponseEntity<TurnoDTO> guardarTurno(@RequestBody TurnoDTO turnoDTO) throws BadRequestException, ResourceNotFoundException {
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPorId(turnoDTO.getPacienteId());
        Optional<Odontologo> odotologoBuscado = odontologoService.buscarPorId(turnoDTO.getOdontologoId());
        if( pacienteBuscado.isPresent() && odotologoBuscado.isPresent() ){ //En esta parte estoy verificando si tanto paciente como odontologo, existen.
            return ResponseEntity.ok(turnoService.guardar(turnoDTO));
        } else {
            throw new BadRequestException("Alguno de los datos administrados es incorrecto.");
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<TurnoDTO>> buscarTurnos(){
        return ResponseEntity.ok(turnoService.buscarTodos());
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<Optional<TurnoDTO>> buscarTurnoPorId(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<TurnoDTO> turnobuscado = turnoService.buscarPorId(id);
        if(turnobuscado.isPresent()){
            return ResponseEntity.ok(turnoService.buscarPorId(id));
        } else {
            throw new ResourceNotFoundException("No se encontro el turno en el sistema.");
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarTurno(@PathVariable Long id) throws ResourceNotFoundException{
        Optional<TurnoDTO> turnoBuscado = turnoService.buscarPorId(id);
        if(turnoBuscado.isPresent()){
            turnoService.eliminar(id);
            return ResponseEntity.ok("Turno eliminado correctamente");
        } else {
            throw new ResourceNotFoundException("No se encontró el turno solicitado para eliminar.");
        }
    }

    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizarTurno(@RequestBody TurnoDTO turnoDTO) throws ResourceNotFoundException {
        Optional<TurnoDTO> turnoBuscado = turnoService.buscarPorId(turnoDTO.getId());
        if(turnoBuscado.isPresent()){
            Optional<Paciente> pacienteBuscado = pacienteService.buscarPorId(turnoDTO.getPacienteId());
            Optional<Odontologo> odotologoBuscado = odontologoService.buscarPorId(turnoDTO.getOdontologoId());
            if( pacienteBuscado.isPresent() && odotologoBuscado.isPresent() ){ //En esta parte estoy verificando si tanto paciente como odontologo, existen.
                turnoService.actualizar(turnoDTO);
                return ResponseEntity.ok("Turno actualizado correctamente");
            } else {
                throw new ResourceNotFoundException("No se encontro paciente y/o odontologo");
            }
        } else {
            throw new ResourceNotFoundException("No se encontro el turno en el sistema para actualizar");
        }
    }
}
