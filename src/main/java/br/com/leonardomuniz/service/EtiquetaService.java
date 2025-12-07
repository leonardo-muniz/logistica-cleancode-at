package br.com.leonardomuniz.service;

import br.com.leonardomuniz.model.Entrega;
import br.com.leonardomuniz.repository.CalculadoraFreteFactory;

/**
 * Serviço responsável apenas pela lógica de Apresentação/Geração de texto (SRP).
 * Ele utiliza o Padrão Injeção de Dependência via construtor para obter a Factory,
 * mantendo um baixo acoplamento com a lógica de cálculo do frete (item 2.c).
 */
public class EtiquetaService {

    // Dependência da Factory injetada (Injeção via Construtor)
    private final CalculadoraFreteFactory freteFactory;

    /**
     * Construtor que recebe e armazena a dependência.
     * @param freteFactory A fábrica responsável por fornecer a CalculadoraFrete correta.
     */
    public EtiquetaService(CalculadoraFreteFactory freteFactory) {
        this.freteFactory = freteFactory;
    }

    /**
     * Gera uma etiqueta de envio completa.
     * (Refatora o método original gerarEtiqueta)
     * @param entrega O objeto de domínio Entrega.
     * @return String formatada com os detalhes da etiqueta.
     */
    public String gerarEtiqueta(Entrega entrega) {
        // 1. Obtém a estratégia correta de cálculo
        CalculadoraFrete calculadora = freteFactory.getCalculadora(entrega.getTipoFrete());

        // 2. Calcula o valor
        double valorFrete = calculadora.calcular(entrega);

        // 3. Formata e retorna a apresentação
        return String.format(
                "Destinatário: %s\nEndereço: %s\nValor do Frete: R$ %.2f",
                entrega.getDestinatario(),
                entrega.getEndereco(),
                valorFrete
        );
    }

    /**
     * Gera um resumo simples do pedido.
     * (Refatora o método original gerarResumoPedido)
     * @param entrega O objeto de domínio Entrega.
     * @return String formatada com o resumo.
     */
    public String gerarResumoPedido(Entrega entrega) {
        // O EtiquetaService não se preocupa em COMO o frete é calculado, apenas em USÁ-LO.
        CalculadoraFrete calculadora = freteFactory.getCalculadora(entrega.getTipoFrete());
        double valorFrete = calculadora.calcular(entrega);

        return String.format(
                "Pedido para %s com frete tipo %s no valor de R$ %.2f",
                entrega.getDestinatario(),
                entrega.getTipoFrete(),
                valorFrete
        );
    }
}