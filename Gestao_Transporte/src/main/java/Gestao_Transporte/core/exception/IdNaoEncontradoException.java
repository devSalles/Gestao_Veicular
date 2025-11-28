package Gestao_Transporte.core.exception;

public class IdNaoEncontradoException extends RuntimeException {
    public IdNaoEncontradoException(String message) {
        super(message);
    }
    public IdNaoEncontradoException() {
        super("Id não encontrado");
    }
}
