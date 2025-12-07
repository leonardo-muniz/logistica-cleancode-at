package br.com.leonardomuniz.service;

import br.com.leonardomuniz.model.Entrega;
import br.com.leonardomuniz.repository.CalculadoraFreteFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EtiquetaServiceTest {

    private EtiquetaService etiquetaService;
    private CalculadoraFreteFactory freteFactory;

    // Configuração executada antes de cada teste
    @BeforeEach
    void setUp() {
        // Inicializa as dependências antes de cada teste
        freteFactory = new CalculadoraFreteFactory();
        etiquetaService = new EtiquetaService(freteFactory);
    }

    // --- Testes de Geração de Etiquetas ---

    @Test
    @DisplayName("Deve gerar a etiqueta Expresso com valor correto e formatado (R$ 17.50)")
    void deveGerarEtiquetaExpressoCorretamente() {
        // Frete Expresso: 5.0 * 1.5 + 10.0 = 17.50
        Entrega entrega = new Entrega("Rua A", 5.0, "EXP", "Cliente Expresso");

        String resultado = etiquetaService.gerarEtiqueta(entrega);

        // Verifica se a saída contém os dados e o valor esperado formatado
        assertTrue(resultado.contains("Cliente Expresso"), "Deve conter o nome do destinatário.");
        assertTrue(resultado.contains("Valor do Frete: R$ 17.50"), "Deve conter o valor correto de R$ 17.50.");
    }

    @Test
    @DisplayName("Deve gerar a etiqueta Padrão com valor correto e formatado (R$ 6.00)")
    void deveGerarEtiquetaPadraoCorretamente() {
        // Frete Padrão: 5.0 * 1.2 = 6.00
        Entrega entrega = new Entrega("Rua B", 5.0, "PAD", "Cliente Padrão");

        String resultado = etiquetaService.gerarEtiqueta(entrega);

        // Verifica se a saída contém o valor esperado
        assertTrue(resultado.contains("Valor do Frete: R$ 6.00"), "Deve conter o valor correto de R$ 6.00.");
    }

    @Test
    @DisplayName("Deve gerar o resumo do pedido Econômico com Frete Grátis (R$ 0.00)")
    void deveGerarResumoComFreteGratis() {
        // Frete Econômico Grátis: peso 1.0 < 2
        Entrega entrega = new Entrega("Rua C", 1.0, "ECO", "Cliente Econômico");

        String resultado = etiquetaService.gerarResumoPedido(entrega);

        // Verifica se a saída contém o tipo de frete e o valor zero
        assertTrue(resultado.contains("tipo ECO"), "Deve conter o tipo de frete ECO.");
        assertTrue(resultado.contains("valor de R$ 0.00"), "Deve conter o valor R$ 0.00.");
    }

    // --- Testes Adicionais (Garantia de que o cálculo é delegado corretamente) ---

    @Test
    @DisplayName("Deve gerar etiqueta Expresso com promoção aplicada (R$ 25.00)")
    void deveGerarEtiquetaExpressoComPromocao() {
        // Frete Expresso Promocional: (11.0 - 1.0) * 1.5 + 10.0 = 25.00
        Entrega entrega = new Entrega("Rua D", 11.0, "EXP", "Cliente Pesado");

        String resultado = etiquetaService.gerarEtiqueta(entrega);

        assertTrue(resultado.contains("Valor do Frete: R$ 25.00"), "Deve aplicar a promoção e calcular R$ 25.00.");
    }
}