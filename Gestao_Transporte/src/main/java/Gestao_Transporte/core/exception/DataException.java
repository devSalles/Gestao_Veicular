package Gestao_Transporte.core.exception;

public class DataException extends RuntimeException {
    public DataException(String message) {
        super(message);
    }
    public DataException() {
        super("Data final não pode ser anterior a data inicial");
    }
}
