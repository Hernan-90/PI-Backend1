package RoguinSuarezSegura.ProyectoIntegrador.Repository;

import RoguinSuarezSegura.ProyectoIntegrador.Model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
}
