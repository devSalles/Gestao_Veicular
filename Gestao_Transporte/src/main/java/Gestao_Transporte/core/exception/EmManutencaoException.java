package Gestao_Transporte.core.exception;

public class EmManutencaoException extends RuntimeException {
    public EmManutencaoException(String message) {
        super(message);
    }
    public EmManutencaoException() {
        super("O veículo já está em manutenção");
    }
}
