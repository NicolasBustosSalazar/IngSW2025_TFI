import com.sun.tools.jconsole.JConsoleContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import mock.DBPruebaEnMemoria;
import org.example.app.ServicioUrgencias;
import org.example.domain.Enfermera;
import org.example.domain.Ingreso;
import org.example.domain.NivelEmergencia;
import org.example.domain.Paciente;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class ModuloUrgenciasStepDefinitions{

    private Enfermera enfermera;
    private DBPruebaEnMemoria dbMockeada;
    private ServicioUrgencias servicioUrgencias;
    private Exception excepcionEsperada;

    public ModuloUrgenciasStepDefinitions() {
        this.dbMockeada = new DBPruebaEnMemoria();
        this.servicioUrgencias = new ServicioUrgencias(dbMockeada);
    }

    @Given("que la siguiente enfermera esta registrada:")
    public void queLaSiguienteEnfermeraEstaRegistrada(List<Map<String, String>> tabla) {
        String nombre = tabla.getFirst().get("nombre");
        String apeliido = tabla.getFirst().get("apellido");

        enfermera = new Enfermera(nombre, apeliido);
    }

    @Given("que estan registrados los siguientes pacientes:")
    public void dadoQueEstanRegistradosLosSiguientesPacientes(List<Map<String, String>> tabla) {
        for(Map<String, String> fila: tabla) {
            String cuil = fila.get("Cuil");
            String nombre = fila.get("Nombre");
            String apellido = fila.get("Apellido");
            String obraSocial = fila.get("Obra Social");

            Paciente paciente = new Paciente(cuil, nombre, apellido, obraSocial);

            dbMockeada.guardarPaciente(paciente);
        }
    }

    @When("Ingresan a urgencias los siguientes pacientes:")
    public void ingresaAUrgenciasElSiguientePaciente(List<Map<String, String>> tabla) {
        excepcionEsperada = null;
        for(Map<String, String> fila: tabla) {
            String cuil = fila.get("Cuil");
            String informe = fila.get("Informe");

            // Manejar nivel de emergencia (puede estar vacío en el Scenario Outline)
            NivelEmergencia nivelEmergencia = null;
            String nivelEmergenciaStr = fila.get("Nivel de Emergencia");
            if (nivelEmergenciaStr != null && !nivelEmergenciaStr.trim().isEmpty()) {
                nivelEmergencia = Arrays.stream(NivelEmergencia.values())
                        .filter(nivel -> nivel.tieneNombre(nivelEmergenciaStr))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Nivel desconocido"));
            }

            // Manejar temperatura (puede estar vacío)
            Float temperatura = null;
            String temperaturaStr = fila.get("Temperatura");
            if (temperaturaStr != null && !temperaturaStr.trim().isEmpty()) {
                try {
                    temperatura = Float.parseFloat(temperaturaStr);
                } catch (NumberFormatException e) {
                    // Si no se puede parsear, se queda como null
                }
            }

            // Manejar frecuencia cardíaca (puede estar vacío)
            Float frecuenciaCardiaca = null;
            String frecuenciaCardiacaStr = fila.get("Frecuencia Cardiaca");
            if (frecuenciaCardiacaStr != null && !frecuenciaCardiacaStr.trim().isEmpty()) {
                try {
                    frecuenciaCardiaca = Float.parseFloat(frecuenciaCardiacaStr);
                } catch (NumberFormatException e) {
                    // Si no se puede parsear, se queda como null
                }
            }

            // Manejar frecuencia respiratoria (puede estar vacío)
            Float frecuenciaRespiratoria = null;
            String frecuenciaRespiratoriaStr = fila.get("Frecuencia Respiratoria");
            if (frecuenciaRespiratoriaStr != null && !frecuenciaRespiratoriaStr.trim().isEmpty()) {
                try {
                    frecuenciaRespiratoria = Float.parseFloat(frecuenciaRespiratoriaStr);
                } catch (NumberFormatException e) {
                    // Si no se puede parsear, se queda como null
                }
            }

            // Manejar tensión arterial (puede estar vacío)
            Float tensionSistolica = null;
            Float tensionDiastolica = null;
            String tensionArterialStr = fila.get("Tension Arterial");
            if (tensionArterialStr != null && !tensionArterialStr.trim().isEmpty()) {
                try {
                    List<Float> tensionArterial = Arrays.stream(tensionArterialStr.split("/"))
                            .map(Float::parseFloat)
                            .toList();
                    tensionSistolica = tensionArterial.get(0);
                    tensionDiastolica = tensionArterial.get(1);
                } catch (NumberFormatException e) {
                    // Si no se puede parsear, se queda como null
                }
            }

            try{
                servicioUrgencias.registrarUrgencia(
                        cuil, enfermera, informe, nivelEmergencia,
                        temperatura, frecuenciaCardiaca, frecuenciaRespiratoria,
                        tensionSistolica, tensionDiastolica
                );
            } catch (Exception e){
                this.excepcionEsperada = e;
                break;
            }
        }
    }

    @Then("La lista de espera esta ordenada por cuil de la siguiente manera:")
    public void laListaDeEsperaEstaOrdenadaPorCuilDeLaSiguienteManera(List<String> lista) {
        List<String> cuilsPendientes = servicioUrgencias.obtenerIngresosPendientes()
                .stream()
                .map(Ingreso::getCuilPaciente)
                .toList();

        assertThat(cuilsPendientes)
                .hasSize(lista.size())
                .containsExactlyElementsOf(lista);
    }

    @Then("el sistema muestra el siguiente error: {string}")
    public void elSistemaMuestraElSiguienteError(String mensajeError) {
        assertThat(excepcionEsperada)
                .isNotNull()
                .hasMessage(mensajeError);
    }
}