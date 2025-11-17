package org.example.domain.valueobject;

public abstract class Frecuencia {
    protected Float value;

    public Frecuencia(Float value) {
        validarFrecuenciaNoNegativa(value);
        this.value = value;
    }

    private void validarFrecuenciaNoNegativa(Float value){
        if (value < 0){
            throw this.notificarError();
        }
    }

    protected abstract RuntimeException notificarError();
    public abstract String getValorFormateado();

}
