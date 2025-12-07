package br.com.leonardomuniz.service;

import br.com.leonardomuniz.model.Entrega;

public class FreteExpresso implements CalculadoraFrete {
    // Nomenclatura clara para substituir Valores Mágicos
    private static final double FATOR_PESO = 1.5;
    private static final double TAXA_FIXA = 10.0;
    private static final double PESO_PROMOCIONAL_LIMITE = 10.0;
    private static final double DESCONTO_PESO = 1.0;

    @Override
    public double calcular(Entrega entrega) {
        double pesoParaCalculo = entrega.getPeso();

        // Lógica de promoção do frete (aplicarFretePromocional refatorado)
        if (pesoParaCalculo > PESO_PROMOCIONAL_LIMITE) {
            pesoParaCalculo -= DESCONTO_PESO;
        }
        return (pesoParaCalculo * FATOR_PESO) + TAXA_FIXA;
    }
}