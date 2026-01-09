package Gestao_Transporte.core.exception.Viagem;

public class VeiculoEmViagemException extends RuntimeException {
    public VeiculoEmViagemException(String message) {
        super(message);
    }
    public VeiculoEmViagemException() {
        super("O veículo está em viagem e não pode ser alterado");
    }
}
