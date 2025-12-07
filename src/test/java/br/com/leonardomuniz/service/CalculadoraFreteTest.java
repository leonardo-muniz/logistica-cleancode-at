package br.com.leonardomuniz.service;

import br.com.leonardomuniz.model.Entrega;
import br.com.leonardomuniz.repository.CalculadoraFreteFactory;
import br.com.leonardomuniz.model.exceptions.DominioInvalidoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculadoraFreteTest {

    private final String ENDERECO = "Rua Teste, 1";
    private final String DESTINATARIO = "Cliente Teste";
    private final CalculadoraFreteFactory factory = new CalculadoraFreteFactory();

    // --- 1. Frete Padrão (PAD) ---

    @Test
    @DisplayName("Deve calcular Frete PADRAO corretamente (peso * 1.2)")
    void deveCalcularFretePadrao() {
        Entrega entrega = new Entrega(ENDERECO, 10.0, "PAD", DESTINATARIO); // 10.0 kg
        CalculadoraFrete calculadora = factory.getCalculadora("PAD");

        // Esperado: 10.0 * 1.2 = 12.0
        assertEquals(12.0, calculadora.calcular(entrega), 0.001);
    }

    // --- 2. Frete Expresso (EXP) ---

    @Test
    @DisplayName("Deve calcular Frete EXPRESSO sem promoção (peso * 1.5 + 10.0)")
    void deveCalcularFreteExpressoSemPromocao() {
        Entrega entrega = new Entrega(ENDERECO, 5.0, "EXP", DESTINATARIO); // 5.0 kg (abaixo de 10)
        CalculadoraFrete calculadora = factory.getCalculadora("EXP");

        // Esperado: 5.0 * 1.5 + 10.0 = 7.5 + 10.0 = 17.5
        assertEquals(17.5, calculadora.calcular(entrega), 0.001);
    }

    @Test
    @DisplayName("Deve calcular Frete EXPRESSO COM promoção (peso > 10, subtrai 1kg)")
    void deveCalcularFreteExpressoComPromocao() {
        Entrega entrega = new Entrega(ENDERECO, 11.0, "EXP", DESTINATARIO); // 11.0 kg (acima de 10)
        CalculadoraFrete calculadora = factory.getCalculadora("EXP");

        // Regra: (11.0 - 1.0) * 1.5 + 10.0 = 10.0 * 1.5 + 10.0 = 25.0
        assertEquals(25.0, calculadora.calcular(entrega), 0.001);
    }

    // --- 3. Frete Econômico (ECO) ---

    @Test
    @DisplayName("Deve calcular Frete ECONOMICO como Grátis (peso < 2)")
    void deveCalcularFreteEconomicoGratis() {
        Entrega entrega = new Entrega(ENDERECO, 1.5, "ECO", DESTINATARIO); // 1.5 kg (grátis)
        CalculadoraFrete calculadora = factory.getCalculadora("ECO");

        // Esperado: 0.0
        assertEquals(0.0, calculadora.calcular(entrega), 0.001);
    }

    @Test
    @DisplayName("Deve calcular Frete ECONOMICO com valor normal (peso >= 2)")
    void deveCalcularFreteEconomicoNormal() {
        Entrega entrega = new Entrega(ENDERECO, 3.0, "ECO", DESTINATARIO); // 3.0 kg (não grátis)
        CalculadoraFrete calculadora = factory.getCalculadora("ECO");

        // Esperado: 3.0 * 1.15 = 3.45
        assertEquals(3.45, calculadora.calcular(entrega), 0.001);
    }

    // --- 4. Teste da Factory (Robustez contra tipos desconhecidos) ---

    @Test
    @DisplayName("Factory deve lançar exceção para Tipo de Frete desconhecido")
    void factoryDeveLancarExcecaoParaTipoDesconhecido() {
        assertThrows(DominioInvalidoException.class, () -> {
            factory.getCalculadora("MOTO"); // Tipo inexistente
        }, "A Factory deve lançar exceção ao invés de falhar silenciosamente.");
    }
}