package Gestao_Transporte.core.exception.Viagem;

public class VeiculoDisponivelException extends RuntimeException {
    public VeiculoDisponivelException(String message) {
        super(message);
    }
    public VeiculoDisponivelException() {
        super("Veículo já está disponivel");
    }
}
