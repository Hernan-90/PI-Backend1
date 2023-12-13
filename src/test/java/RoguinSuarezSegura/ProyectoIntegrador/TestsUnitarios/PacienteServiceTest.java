package RoguinSuarezSegura.ProyectoIntegrador.TestsUnitarios;

import RoguinSuarezSegura.ProyectoIntegrador.DTO.PacienteDTO;
import RoguinSuarezSegura.ProyectoIntegrador.Exception.ResourceNotFoundException;
import RoguinSuarezSegura.ProyectoIntegrador.Model.Domicilio;
import RoguinSuarezSegura.ProyectoIntegrador.Model.Paciente;
import RoguinSuarezSegura.ProyectoIntegrador.Service.PacienteService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class PacienteServiceTest {
    @Autowired
    private PacienteService pacienteService;

    @Test
    @Order(1)
    public void guardarPaciente(){
        //que necesitamos para guardar un paciente
        Paciente paciente = new Paciente("Hernan", "Suarez Segura", "hernan@gmail.com", "123456789", LocalDate.of(2023,12,3), new Domicilio("calle falsa", 123, "cap fed", "bs as"));
        pacienteService.guardar(paciente);
        assertEquals(1L,paciente.getId());
    }
    @Test
    @Order(2)
    public void buscarPacientePorId() throws ResourceNotFoundException{
        Long id=1L;
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPorId(id);
        assertNotNull(pacienteBuscado);
    }
    @Test
    @Order(3)
    public void buscarPacientesTest(){
        List<PacienteDTO> pacientes = pacienteService.buscarTodos();
        assertEquals(1,pacientes.size());
    }
    @Test
    @Order(4)
    public void actualizarPaciente() throws ResourceNotFoundException {
        Paciente pacienteActualizar = new Paciente(1L, "Jana","Roguin", "jana@gmail.com", "11111", LocalDate.of(2023,12,3), new Domicilio("calle falsa", 123, "cap fed", "bs as"));
        pacienteService.actualizar(pacienteActualizar);
        Optional<Paciente> pacienteBuscado= pacienteService.buscarPorId(1L);
        assertEquals("Jana",pacienteBuscado.get().getNombre());
    }
    @Test
    @Order(5)
    public void eliminarPaciente() throws ResourceNotFoundException{
        Optional<Paciente> pacienteEliminado = pacienteService.buscarPorId(1L);
        assertTrue(pacienteEliminado.isPresent()); //Primero compruebo que exista
        pacienteService.eliminar(1L);
        assertThrows(ResourceNotFoundException.class, () -> pacienteService.buscarPorId(1L)); //Como tengo la excepcion por si no existe, hago la prueba con la excepción
    }

}
