package br.com.leonardomuniz.service;

import br.com.leonardomuniz.model.Entrega;

public class FreteEconomico implements CalculadoraFrete {
    private static final double FATOR_PESO = 1.15;

    @Override
    public double calcular(Entrega entrega) {
        // Aproveita a lógica de domínio, isolando a decisão do cálculo
        if (entrega.isFreteGratis()) {
            return 0.0;
        }
        return entrega.getPeso() * FATOR_PESO;
    }
}