package br.com.leonardomuniz.model;

import br.com.leonardomuniz.model.exceptions.DominioInvalidoException;

/**
 * Entidade de domínio imutável com contrato de validade no construtor.
 * Garante que o objeto Entrega seja sempre criado em um estado válido,
 * promovendo robustez e previsibilidade (item 2.a, item 3, item 74).
 */
public class Entrega {
    // Atributos privados e finais para garantir o encapsulamento e a imutabilidade
    private final String endereco;
    private final double peso;
    private final String tipoFrete;
    private final String destinatario;

    public static final double PESO_MINIMO = 0.1;

    /**
     * Construtor que define contratos explícitos para garantir a integridade do objeto (item 72).
     * Todas as validações são realizadas aqui, utilizando exceções personalizadas (item 63, 73).
     */
    public Entrega(String endereco, double peso, String tipoFrete, String destinatario) {
        // Validações (impedindo a criação de estados inválidos)
        if (endereco == null || endereco.trim().isEmpty()) {
            throw new DominioInvalidoException("Endereço não pode ser vazio.");
        }
        if (peso < PESO_MINIMO) {
            throw new DominioInvalidoException("Peso deve ser maior que " + PESO_MINIMO + ".");
        }
        if (tipoFrete == null || tipoFrete.trim().isEmpty()) {
            throw new DominioInvalidoException("Tipo de frete não pode ser vazio.");
        }
        if (destinatario == null || destinatario.trim().isEmpty()) {
            throw new DominioInvalidoException("Destinatário não pode ser vazio.");
        }

        // Atribuição de estado
        this.endereco = endereco;
        this.peso = peso;
        this.tipoFrete = tipoFrete.toUpperCase(); // Padroniza a entrada para maiúsculas
        this.destinatario = destinatario;
    }

    // Getters públicos (sem setters, mantendo a imutabilidade e o encapsulamento)
    public String getEndereco() { return endereco; }
    public double getPeso() { return peso; }
    public String getTipoFrete() { return tipoFrete; }
    public String getDestinatario() { return destinatario; }

    /**
     * Lógica de Domínio: Verifica se a entrega é elegível para frete grátis.
     * Esta é uma regra intrínseca à entidade, por isso permanece aqui.
     * (Refatoração da lógica original de isFreteGratis)
     */
    public boolean isFreteGratis() {
        // Regra original: tipoFrete é "ECO" E peso é menor que 2 (item 46)
        return "ECO".equals(this.tipoFrete) && this.peso < 2;
    }
}