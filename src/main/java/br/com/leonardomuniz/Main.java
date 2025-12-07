package br.com.leonardomuniz;

import br.com.leonardomuniz.model.Entrega;
import br.com.leonardomuniz.model.exceptions.DominioInvalidoException;
import br.com.leonardomuniz.repository.CalculadoraFreteFactory;
import br.com.leonardomuniz.service.EtiquetaService;

/**
 * Ponto de entrada da aplicação (Simulação da camada de Apresentação/Infraestrutura).
 * Demonstra a orquestração das camadas e o tratamento de erros.
 */
public class Main {

    public static void main(String[] args) {

        System.out.println("--- DEMONSTRAÇÃO DO SISTEMA REFATORADO ---");

        // 1. Inicializa dependências
        // A Factory e o Service são criados aqui (Simulação de Injeção de Dependência)
        CalculadoraFreteFactory freteFactory = new CalculadoraFreteFactory();
        EtiquetaService etiquetaService = new EtiquetaService(freteFactory);

        // 2. Casos de Uso Válidos

        // Caso A: Frete Padrão
        try {
            Entrega pedidoPadrao = new Entrega("Rua Alpha, 50", 5.0, "PAD", "João Silva");
            System.out.println("\n--- A. Pedido Padrão (5.0kg) ---");
            System.out.println(etiquetaService.gerarEtiqueta(pedidoPadrao));
            // Valor esperado: 5.0 * 1.2 = R$ 6.00

        } catch (DominioInvalidoException e) {
            System.err.println("Erro ao processar pedido PADRÃO: " + e.getMessage());
        }

        // Caso B: Frete Expresso com Promoção (Peso > 10)
        try {
            Entrega pedidoExpressoPromocional = new Entrega("Av. Beta, 110", 11.0, "EXP", "Maria Souza");
            System.out.println("\n--- B. Pedido Expresso (11.0kg c/ Promoção) ---");
            System.out.println(etiquetaService.gerarResumoPedido(pedidoExpressoPromocional));
            // Valor esperado: (11.0 - 1.0) * 1.5 + 10.0 = R$ 25.00

        } catch (DominioInvalidoException e) {
            System.err.println("Erro ao processar pedido EXPRESSO: " + e.getMessage());
        }

        // Caso C: Frete Econômico Grátis (ECO e Peso < 2)
        try {
            Entrega pedidoGratis = new Entrega("Travessa Gama, 20", 1.5, "ECO", "Pedro Santos");
            System.out.println("\n--- C. Pedido Econômico (1.5kg - Frete Grátis) ---");
            System.out.println(etiquetaService.gerarEtiqueta(pedidoGratis));
            // Valor esperado: R$ 0.00

        } catch (DominioInvalidoException e) {
            System.err.println("Erro ao processar pedido GRÁTIS: " + e.getMessage());
        }

        // 3. Caso de Uso Inválido (Tratamento de Erros/Robustez - Item 3)
        System.out.println("\n--- D. Teste de Validação de Domínio ---");
        try {
            // Tenta criar um pedido com peso negativo (estado inválido)
            new Entrega("Rua Inválida", -5.0, "PAD", "Cliente Inválido");
        } catch (DominioInvalidoException e) {
            System.out.println("SUCESSO: Erro de Domínio capturado com sucesso!");
            System.err.println("Mensagem de erro: " + e.getMessage());
        }
    }
}