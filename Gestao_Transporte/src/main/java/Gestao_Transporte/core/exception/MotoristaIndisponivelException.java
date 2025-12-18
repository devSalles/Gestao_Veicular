package Gestao_Transporte.core.exception;

public class MotoristaIndisponivelException extends RuntimeException {
    public MotoristaIndisponivelException(String message) {
        super(message);
    }
    public MotoristaIndisponivelException() {
        super("Motorista não está com status ativo para iniciar viagem");
    }
}
