package RoguinSuarezSegura.ProyectoIntegrador.TestsIntegracion;

import RoguinSuarezSegura.ProyectoIntegrador.Model.Domicilio;
import RoguinSuarezSegura.ProyectoIntegrador.Model.Odontologo;
import RoguinSuarezSegura.ProyectoIntegrador.Model.Paciente;
import RoguinSuarezSegura.ProyectoIntegrador.Model.Turno;
import RoguinSuarezSegura.ProyectoIntegrador.Service.OdontologoService;
import RoguinSuarezSegura.ProyectoIntegrador.Service.PacienteService;
import RoguinSuarezSegura.ProyectoIntegrador.Service.TurnoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class TurnosTest {
    @Autowired
    private TurnoService turnoService;
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private OdontologoService odontologoService;
    @Autowired
    private MockMvc mockMvc;

    public void agregarDatosIniciales(){
        Paciente paciente = new Paciente("Hernan", "Suarez Segura", "hernan@gmail.com", "123456789", LocalDate.of(2023,12,3), new Domicilio("calle falsa", 123, "cap fed", "bs as"));
        Odontologo odontologo = new Odontologo("MAT-0001", "Jana", "Roguin");
        Turno turno = new Turno( paciente, odontologo, LocalDate.of(2023,12,3) );

        pacienteService.guardar(paciente);
        odontologoService.guardar(odontologo);
        turnoService.guardar(turno);
    }

    @Test
    public void listarTurnosTest() throws Exception {
        agregarDatosIniciales();
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get("/turnos/listar").accept(MediaType.APPLICATION_JSON) )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        assertFalse( response.getResponse().getContentAsString().isEmpty() );
    }
}
