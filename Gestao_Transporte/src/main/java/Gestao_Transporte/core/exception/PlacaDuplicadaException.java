package Gestao_Transporte.core.exception;

public class PlacaDuplicadaException extends RuntimeException {
    public PlacaDuplicadaException(String message) {
        super(message);
    }
    public PlacaDuplicadaException() {
        super("Essa placa já está cadastrada no sistema");
    }
}
