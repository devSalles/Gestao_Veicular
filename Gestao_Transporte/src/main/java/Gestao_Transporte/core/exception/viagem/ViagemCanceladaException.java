package Gestao_Transporte.core.exception.viagem;

public class ViagemCanceladaException extends RuntimeException {
    public ViagemCanceladaException(String message) {
        super(message);
    }
    public ViagemCanceladaException() {
        super("Viagem cancelada não pode ser iniciada");
    }
}
