package org.example.app;

import org.example.app.interfaces.RepositorioPacientes;
import org.example.domain.Enfermera;
import org.example.domain.Ingreso;
import org.example.domain.NivelEmergencia;
import org.example.domain.Paciente;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ServicioUrgencias {

    //SEGREGACION DE INTERFAZ
    //PATRON ADAPTADOR
    private RepositorioPacientes dbPacientes;

    private final List<Ingreso> listaEspera;

    //INYECCION DE DEPENDENCIA -> Pruebas
    public ServicioUrgencias(RepositorioPacientes dbPacientes) {
        this.dbPacientes = dbPacientes;
        this.listaEspera = new ArrayList<>();
    }


    public void registrarUrgencia(String cuilPaciente,
                                   Enfermera enfermera,
                                   String informe,
                                  NivelEmergencia emergencia,
                                   Float temperatura,
                                   Float frecuenciaCardiaca,
                                   Float frecuenciaRespiratoria,
                                   Float frecuenciaSistolica,
                                   Float frecuenciaDiastolica){
        validarCampos(cuilPaciente,enfermera,informe,emergencia,temperatura,frecuenciaCardiaca,frecuenciaRespiratoria,frecuenciaSistolica,frecuenciaDiastolica);
        Paciente paciente = dbPacientes.buscarPacientePorCuil(cuilPaciente)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        Ingreso ingreso = new Ingreso(
                paciente,
                enfermera,
                informe,
                emergencia,
                temperatura,
                frecuenciaCardiaca,
                frecuenciaRespiratoria,
                frecuenciaSistolica,
                frecuenciaDiastolica);

        listaEspera.add(ingreso);
        listaEspera.sort(Ingreso::compareTo);
    }

    public List<Ingreso> obtenerIngresosPendientes(){
        return this.listaEspera;
    }
    public void validarCampos(String cuilPaciente,
                              Enfermera enfermera,
                              String informe,
                              NivelEmergencia emergencia,
                              Float temperatura,
                              Float frecuenciaCardiaca,
                              Float frecuenciaRespiratoria,
                              Float frecuenciaSistolica,
                              Float frecuenciaDiastolica){
        // Validar CUIL
        if (cuilPaciente == null || cuilPaciente.trim().isEmpty()) {
            throw new RuntimeException("El CUIL del paciente es obligatorio");
        }

        // Validar enfermera
        if (enfermera == null) {
            throw new RuntimeException("La enfermera es obligatoria");
        }

        // Validar informe médico
        if (informe == null || informe.trim().isEmpty()) {
            throw new RuntimeException("El informe médico es obligatorio");
        }

        // Validar nivel de emergencia
        if (emergencia == null) {
            throw new RuntimeException("El nivel de emergencia es obligatorio");
        }

        // Validar temperatura
        if (temperatura == null) {
            throw new RuntimeException("La temperatura es obligatoria");
        }

        // Validar frecuencia cardíaca
        if (frecuenciaCardiaca == null) {
            throw new RuntimeException("La frecuencia cardíaca es obligatoria");
        }
        if (frecuenciaCardiaca < 0) {
            throw new RuntimeException("La frecuencia cardíaca no puede ser negativa");
        }

        // Validar frecuencia respiratoria
        if (frecuenciaRespiratoria == null) {
            throw new RuntimeException("La frecuencia respiratoria es obligatoria");
        }
        if (frecuenciaRespiratoria < 0) {
            throw new RuntimeException("La frecuencia respiratoria no puede ser negativa");
        }

        // Validar tensión arterial sistólica
        if (frecuenciaSistolica == null) {
            throw new RuntimeException("La tensión arterial es obligatoria");
        }

        // Validar tensión arterial diastólica
        if (frecuenciaDiastolica == null) {
            throw new RuntimeException("La tensión arterial es obligatoria");
        }

    }
}
