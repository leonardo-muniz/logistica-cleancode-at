package br.com.leonardomuniz.repository;

import br.com.leonardomuniz.model.exceptions.DominioInvalidoException;
import br.com.leonardomuniz.service.CalculadoraFrete;
import br.com.leonardomuniz.service.FreteEconomico;
import br.com.leonardomuniz.service.FreteExpresso;
import br.com.leonardomuniz.service.FretePadrao;

/**
 * Implementa o padrão Factory para centralizar a criação de objetos CalculadoraFrete.
 * Esta Factory isola o ponto de decisão e permite a fácil extensão do sistema
 * com novos tipos de frete (item 4 / OCP).
 */
public class CalculadoraFreteFactory {

    /**
     * Retorna a implementação correta da CalculadoraFrete baseada no tipo de frete fornecido.
     * @param tipoFrete O código do tipo de frete (EXP, PAD, ECO).
     * @return Uma instância concreta de CalculadoraFrete.
     * @throws DominioInvalidoException Se o tipo de frete for desconhecido.
     */
    public CalculadoraFrete getCalculadora(String tipoFrete) {
        // Usa o switch (ou Map em projetos maiores) para evitar if-else encadeado rígido [cite: 65, 71]
        switch (tipoFrete) {
            case "EXP":
                return new FreteExpresso();
            case "PAD":
                return new FretePadrao();
            case "ECO":
                return new FreteEconomico();
            default:
                // Tratamento de erro robusto e explícito para tipos desconhecidos [cite: 64]
                throw new DominioInvalidoException("Tipo de frete '" + tipoFrete + "' não suportado.");
        }
    }
}