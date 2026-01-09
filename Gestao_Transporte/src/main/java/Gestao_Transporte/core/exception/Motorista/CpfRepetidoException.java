package Gestao_Transporte.core.exception.Motorista;

public class CpfRepetidoException extends RuntimeException {
    public CpfRepetidoException(String message) {
        super(message);
    }
    public CpfRepetidoException() {
        super("CPF já cadastrado no sistema");

    }
}
