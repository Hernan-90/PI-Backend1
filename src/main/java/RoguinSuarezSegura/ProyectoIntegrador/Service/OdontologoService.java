package RoguinSuarezSegura.ProyectoIntegrador.Service;

import RoguinSuarezSegura.ProyectoIntegrador.DTO.OdontologoDTO;
import RoguinSuarezSegura.ProyectoIntegrador.Exception.ResourceNotFoundException;
import RoguinSuarezSegura.ProyectoIntegrador.Model.Odontologo;
import RoguinSuarezSegura.ProyectoIntegrador.Repository.OdontologoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService {
    private OdontologoRepository odontologoRepository;

    @Autowired
    public OdontologoService(OdontologoRepository odontologoRepository) {
        this.odontologoRepository = odontologoRepository;
    }

    //Nuestros Métodos
    public OdontologoDTO guardar(Odontologo odontologo){
        Odontologo odontologoGuardado = odontologoRepository.save(odontologo);
        return odontologoConverter(odontologoGuardado);
    }
    public List<OdontologoDTO> buscarTodos(){
        List<Odontologo> odontologos = odontologoRepository.findAll();
        List<OdontologoDTO> odontologosDTO = new ArrayList<>();
        for (Odontologo odontologo : odontologos) {
            odontologosDTO.add(odontologoConverter(odontologo));
        }
        return odontologosDTO;
    }
    public Optional<Odontologo> buscarPorId(Long id){
        Optional<Odontologo> odontologobuscado = odontologoRepository.findById(id);
        if(odontologobuscado.isPresent()){
            return Optional.of(odontologobuscado.get());
        }
        return Optional.empty();
    }
    public void eliminar(Long id){
        odontologoRepository.deleteById(id);
    }
    public void actualizar(OdontologoDTO odontologoDTO) throws ResourceNotFoundException {
        odontologoRepository.save(odontologoDTOConverter(odontologoDTO));
    }

    private OdontologoDTO odontologoConverter(Odontologo odontologo){
        OdontologoDTO response = new OdontologoDTO();
        response.setId(odontologo.getId());
        response.setNombre(odontologo.getNombre());
        response.setApellido(odontologo.getApellido());
        return response;
    }

    private Odontologo odontologoDTOConverter(OdontologoDTO odontologoDTO) throws ResourceNotFoundException{
        Odontologo odontologo = new Odontologo();
        Optional<Odontologo> odontologoBuscado = odontologoRepository.findById(odontologoDTO.getId());
        if(odontologoBuscado.isPresent()){
            odontologo.setId(odontologoDTO.getId());
            odontologo.setMatricula(odontologoBuscado.get().getMatricula());
            odontologo.setNombre(odontologoDTO.getNombre());
            odontologo.setApellido(odontologoDTO.getApellido());
        } else {
            throw new ResourceNotFoundException("No se encontró al odontólogo en el sistema.");
        }
        return odontologo;
    }
}
