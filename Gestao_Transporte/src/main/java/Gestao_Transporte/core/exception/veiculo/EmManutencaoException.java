package Gestao_Transporte.core.exception.veiculo;

public class EmManutencaoException extends RuntimeException {
    public EmManutencaoException(String message) {
        super(message);
    }
    public EmManutencaoException() {
        super("O veículo não está em manutenção");
    }
}
