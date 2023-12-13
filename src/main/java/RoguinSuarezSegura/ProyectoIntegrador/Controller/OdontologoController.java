package RoguinSuarezSegura.ProyectoIntegrador.Controller;

import RoguinSuarezSegura.ProyectoIntegrador.DTO.OdontologoDTO;
import RoguinSuarezSegura.ProyectoIntegrador.Exception.ResourceNotFoundException;
import RoguinSuarezSegura.ProyectoIntegrador.Model.Odontologo;
import RoguinSuarezSegura.ProyectoIntegrador.Service.OdontologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/odontologos")
public class OdontologoController {
    private OdontologoService odontologoService;

    @Autowired
    public OdontologoController(OdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }

    //Acá van los métodos para las peticiones Http
    @PostMapping("/guardar")
    public ResponseEntity<OdontologoDTO> guardarOdontologo(@RequestBody Odontologo odontologo) {
        return ResponseEntity.ok(odontologoService.guardar(odontologo));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<OdontologoDTO>> buscarOdontologos(){
        return ResponseEntity.ok(odontologoService.buscarTodos());
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<Optional<Odontologo>> buscarOdontologoPorId(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarPorId(id);
        if(odontologoBuscado.isPresent()){
            return ResponseEntity.ok(odontologoService.buscarPorId(id));
        } else {
            throw new ResourceNotFoundException("No se encontró al odontólogo solicitado.");
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarOdontologo(@PathVariable Long id) throws ResourceNotFoundException{
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarPorId(id);
        if(odontologoBuscado.isPresent()){
            odontologoService.eliminar(id);
            return ResponseEntity.ok("Odontologo eliminado correctamente");
        } else {
            throw new ResourceNotFoundException("No se encontró al odontólogo solicitado.");
        }
    }

    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizarOdontologo(@RequestBody OdontologoDTO odontologoDTO) throws ResourceNotFoundException{
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarPorId(odontologoDTO.getId());
        if(odontologoBuscado.isPresent()){
            odontologoService.actualizar(odontologoDTO);
            return ResponseEntity.ok("Odontologo actualizado correctamente");
        } else {
            throw new ResourceNotFoundException("No se encontró al odontólogo solicitado.");
        }
    }

}
