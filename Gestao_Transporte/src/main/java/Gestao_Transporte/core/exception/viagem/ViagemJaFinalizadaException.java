package Gestao_Transporte.core.exception.viagem;

public class ViagemJaFinalizadaException extends RuntimeException {
    public ViagemJaFinalizadaException(String message) {
        super(message);
    }
    public ViagemJaFinalizadaException() {
        super("Viagem já foi finalizada");
    }
}
