package br.com.leonardomuniz.model.exceptions;

// Exceção personalizada para tratamento de erros explícito (item 3)
public class DominioInvalidoException extends IllegalArgumentException {
    public DominioInvalidoException(String mensagem) {
        super(mensagem);
    }
}