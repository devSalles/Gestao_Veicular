package Gestao_Transporte.core.exception;

public class NenhumVeiculoComStatusException extends RuntimeException {
    public NenhumVeiculoComStatusException(String message) {
        super(message);
    }
    public NenhumVeiculoComStatusException() {
        super("Nenhum veiculo com esse Status");
    }
}
