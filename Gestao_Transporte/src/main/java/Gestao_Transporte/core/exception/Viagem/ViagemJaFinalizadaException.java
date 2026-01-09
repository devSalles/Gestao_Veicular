package Gestao_Transporte.core.exception.Viagem;

public class ViagemJaFinalizadaException extends RuntimeException {
    public ViagemJaFinalizadaException(String message) {
        super(message);
    }
    public ViagemJaFinalizadaException() {
        super("Viagem não pode ser finalizada pois está finalizada");
    }
}
