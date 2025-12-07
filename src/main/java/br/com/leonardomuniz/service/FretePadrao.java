package br.com.leonardomuniz.service;

import br.com.leonardomuniz.model.Entrega;

public class FretePadrao implements CalculadoraFrete {
    private static final double FATOR_PESO = 1.2;

    @Override
    public double calcular(Entrega entrega) {
        return entrega.getPeso() * FATOR_PESO;
    }
}