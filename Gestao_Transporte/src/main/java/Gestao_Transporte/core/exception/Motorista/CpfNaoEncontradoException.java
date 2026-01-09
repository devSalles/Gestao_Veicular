package Gestao_Transporte.core.exception.Motorista;

public class CpfNaoEncontradoException extends RuntimeException {
    public CpfNaoEncontradoException(String message) {
        super(message);
    }
    public CpfNaoEncontradoException() {
        super("Cpf não encontrado");
    }
}
