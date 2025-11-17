package org.example.domain.valueobject;

public class FrecuenciaSistolica extends Frecuencia{

    public FrecuenciaSistolica(Float value) {
        super(value);
    }

    @Override
    protected RuntimeException notificarError() {
        return new RuntimeException("La frecuencia sistolica no puede ser negativa");
    }

    @Override
    public String getValorFormateado() {
        return "";
    }
}
