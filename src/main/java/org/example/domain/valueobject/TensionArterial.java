package org.example.domain.valueobject;

public class TensionArterial {
    private FrecuenciaDiastolica frecuenciaDiastolica;
    private FrecuenciaSistolica frecuenciaSistolica;

    public TensionArterial(Float frecuenciaSistolica, Float frecuenciaDiastolica) {
        this.frecuenciaSistolica = new FrecuenciaSistolica(frecuenciaSistolica);
        this.frecuenciaDiastolica = new FrecuenciaDiastolica(frecuenciaDiastolica);
    }
}
