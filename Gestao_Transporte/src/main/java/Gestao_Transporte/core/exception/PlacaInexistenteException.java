package Gestao_Transporte.core.exception;

public class PlacaInexistenteException extends RuntimeException {
    public PlacaInexistenteException(String message) {
        super(message);
    }
    public PlacaInexistenteException() {
        super("Placa consultada inexistente");
    }
}
