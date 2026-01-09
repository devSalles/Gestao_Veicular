package Gestao_Transporte.core.exception.Veiculo;

public class EmManutencaoException extends RuntimeException {
    public EmManutencaoException(String message) {
        super(message);
    }
    public EmManutencaoException() {
        super("O veículo não está em manutenção");
    }
}
