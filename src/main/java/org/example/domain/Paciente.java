package org.example.domain;

public class Paciente {
    private String cuil;
    private String nombre;
    private String apellido;
    private String obraSocial;

    public Paciente(String cuil, String nombre, String apellido, String obraSocial) {
        this.cuil = cuil;
        this.nombre = nombre;
        this.apellido = apellido;
        this.obraSocial = obraSocial;
    }

    public String getCuil() {
        return cuil;
    }
}
