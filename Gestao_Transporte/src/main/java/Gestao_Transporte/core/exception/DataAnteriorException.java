package Gestao_Transporte.core.exception;

public class DataAnteriorException extends RuntimeException {
    public DataAnteriorException(String message) {
        super(message);
    }
    public DataAnteriorException() {
        super("Data final não pode ser anterior a data inicial");
    }
}
