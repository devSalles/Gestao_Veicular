package Gestao_Transporte.core.exception.Veiculo;

public class VeiculoVinculadoException extends RuntimeException {
    public VeiculoVinculadoException(String message) {
        super(message);
    }
    public VeiculoVinculadoException() {
        super("Este veículo já foi vinculado a outro motorista");
    }
}
