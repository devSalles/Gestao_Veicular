package Gestao_Transporte.core.exception.Veiculo;

public class VeiculoIndisponivelException extends RuntimeException {
    public VeiculoIndisponivelException(String message) {
        super(message);
    }
    public VeiculoIndisponivelException() {
        super("Veículo não está disponível para viagem");
    }
}
