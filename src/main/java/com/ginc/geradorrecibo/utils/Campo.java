package com.ginc.geradorrecibo.utils;

public class Campo {
    private String campo;
    private String label;
    private Integer tipo;
    private boolean obrigatorio;
    private Integer minimo;
    private Integer maximo;

    public Campo(){}

    public Campo(String campo, String label, Integer tipo, boolean obrigatorio, Integer minimo, Integer maximo) {
        this.campo = campo;
        this.label = label;
        this.tipo = tipo;
        this.obrigatorio = obrigatorio;
        this.minimo = minimo;
        this.maximo = maximo;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public boolean isObrigatorio() {
        return obrigatorio;
    }

    public void setObrigatorio(boolean obrigatorio) {
        this.obrigatorio = obrigatorio;
    }

    public Integer getMinimo() {
        return minimo;
    }

    public void setMinimo(Integer minimo) {
        this.minimo = minimo;
    }

    public Integer getMaximo() {
        return maximo;
    }

    public void setMaximo(Integer maximo) {
        this.maximo = maximo;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
