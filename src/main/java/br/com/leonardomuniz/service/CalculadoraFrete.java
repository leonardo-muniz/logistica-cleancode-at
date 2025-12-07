package br.com.leonardomuniz.service;

import br.com.leonardomuniz.model.Entrega;

/**
 * Interface que define o contrato do Padrão Strategy.
 * (Item 2.b - Implementar a interface CalculadoraFrete)
 */
public interface CalculadoraFrete {
    double calcular(Entrega entrega);
}