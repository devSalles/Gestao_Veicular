package Gestao_Transporte.core.exception;

public class KmInvalidoException extends RuntimeException {
    public KmInvalidoException(String message) {
        super(message);
    }
    public KmInvalidoException() {
        super("KM inválido");
    }
}
