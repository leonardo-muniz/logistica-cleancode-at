package br.com.leonardomuniz.model;

import br.com.leonardomuniz.model.exceptions.DominioInvalidoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EntregaTest {

    private final String ENDERECO_VALIDO = "Rua Teste, 100";
    private final double PESO_VALIDO = 5.0;
    private final String TIPO_FRETE_VALIDO = "PAD";
    private final String DESTINATARIO_VALIDO = "Cliente Teste";

    // --- Testes de Validação (Tratamento de Erros Explícito) ---

    @Test
    @DisplayName("Deve lançar DominioInvalidoException ao tentar criar Entrega com Peso negativo")
    void deveLancarExcecaoQuandoPesoForNegativo() {
        assertThrows(DominioInvalidoException.class, () -> {
            new Entrega(ENDERECO_VALIDO, -1.0, TIPO_FRETE_VALIDO, DESTINATARIO_VALIDO);
        }, "A validação para peso negativo falhou.");
    }

    @Test
    @DisplayName("Deve lançar DominioInvalidoException ao tentar criar Entrega com Endereço nulo")
    void deveLancarExcecaoQuandoEnderecoForNulo() {
        assertThrows(DominioInvalidoException.class, () -> {
            new Entrega(null, PESO_VALIDO, TIPO_FRETE_VALIDO, DESTINATARIO_VALIDO);
        }, "A validação de Endereço nulo falhou.");
    }

    @Test
    @DisplayName("Deve lançar DominioInvalidoException ao tentar criar Entrega com Tipo de Frete vazio")
    void deveLancarExcecaoQuandoTipoFreteForVazio() {
        assertThrows(DominioInvalidoException.class, () -> {
            new Entrega(ENDERECO_VALIDO, PESO_VALIDO, " ", DESTINATARIO_VALIDO);
        }, "A validação de Tipo Frete vazio falhou.");
    }

    // --- Testes da Regra de Domínio (isFreteGratis) ---

    @Test
    @DisplayName("Deve retornar true para Frete Grátis (ECO e Peso < 2)")
    void deveRetornarTrueParaFreteGratis() {
        // Frete ECO (Econômico) e peso 1.9 (abaixo de 2)
        Entrega entrega = new Entrega(ENDERECO_VALIDO, 1.9, "ECO", DESTINATARIO_VALIDO);
        assertTrue(entrega.isFreteGratis());
    }

    @Test
    @DisplayName("Deve retornar false para Frete Não Grátis (ECO, mas Peso >= 2)")
    void deveRetornarFalseParaFreteNaoGratis() {
        // Frete ECO (Econômico), mas peso 2.0 (igual a 2)
        Entrega entrega = new Entrega(ENDERECO_VALIDO, 2.0, "ECO", DESTINATARIO_VALIDO);
        assertFalse(entrega.isFreteGratis());
    }
}