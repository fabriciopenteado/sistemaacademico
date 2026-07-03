package com.cursojava.sistemaacademico.model;

public class Nota {
    private String descricao;
    private double valor;

    public Nota(String descricao, double valor) {
        this.descricao = descricao;
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getValor() {
        return valor;
    }
}
