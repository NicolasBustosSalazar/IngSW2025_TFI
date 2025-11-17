package org.example.domain;

public enum NivelEmergencia {
    CRITICA("Critica", 0),
    EMERGENCIA("Emergencia", 1),
    URGENCIA("Urgencia",2),
    URGENCIA_MENOR("Urgencia Menor",3),
    SIN_URGENCIA("Sin Urgencia",4),;

    String nombre;
    Integer prioridad;

    NivelEmergencia(String nombre, Integer prioridad){
        this.nombre = nombre;
        this.prioridad = prioridad;
    }

    public boolean tieneNombre(String nombre){
        return this.nombre.equals(nombre);
    }

    public int compararCon(NivelEmergencia otro){
        return this.prioridad.compareTo(otro.prioridad);
    }
}
