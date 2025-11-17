package org.example.domain.valueobject;

public class FrecuenciaRespiratoria extends Frecuencia{
    public FrecuenciaRespiratoria(Float value) {
        super(value);
    }

    @Override
    protected RuntimeException notificarError() {
        return new RuntimeException("La frecuencia respiratoria no puede ser negativa");
    }


    //70.0 rpm
    @Override
    public String getValorFormateado() {
        return String.format("%.1f rpm",this.value);
    }

}
