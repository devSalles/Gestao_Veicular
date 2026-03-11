package Gestao_Transporte.core.exception.motorista;

public class MotoristaIndisponivelException extends RuntimeException {
    public MotoristaIndisponivelException(String message) {
        super(message);
    }
    public MotoristaIndisponivelException() {
        super("Motorista já está em viagem ou está indisponível");
    }
}
