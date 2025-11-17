package mock;

import io.cucumber.java.bs.A;
import org.example.app.interfaces.RepositorioPacientes;
import org.example.domain.Paciente;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DBPruebaEnMemoria implements RepositorioPacientes {
    private Map<String,Paciente> pacientes;

    public DBPruebaEnMemoria() {
        this.pacientes = new HashMap<>();
    }

    @Override
    public void guardarPaciente(Paciente paciente) {
        this.pacientes.put(paciente.getCuil(), paciente);
    }

    @Override
    public Optional<Paciente> buscarPacientePorCuil(String cuil) {
        return Optional.ofNullable(pacientes.get(cuil));
    }
}
