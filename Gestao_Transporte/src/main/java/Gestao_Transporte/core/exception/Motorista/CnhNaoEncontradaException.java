package Gestao_Transporte.core.exception.Motorista;

public class CnhNaoEncontradaException extends RuntimeException {
    public CnhNaoEncontradaException(String message) {
        super(message);
    }
    public CnhNaoEncontradaException() {
        super("CNH não encontrada");
    }
}
