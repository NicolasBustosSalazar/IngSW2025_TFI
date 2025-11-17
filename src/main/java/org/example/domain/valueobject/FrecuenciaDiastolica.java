package org.example.domain.valueobject;

public class FrecuenciaDiastolica extends Frecuencia{

    public FrecuenciaDiastolica(Float value) {
        super(value);
    }

    @Override
    protected RuntimeException notificarError() {
        return new RuntimeException("La frecuencia diastolica no puede ser negativa");
    }

    @Override
    public String getValorFormateado() {
        return "";
    }
}
