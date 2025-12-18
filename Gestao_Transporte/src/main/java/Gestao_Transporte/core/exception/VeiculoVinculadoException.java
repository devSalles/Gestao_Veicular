package Gestao_Transporte.core.exception;

public class VeiculoVinculadoException extends RuntimeException {
    public VeiculoVinculadoException(String message) {
        super(message);
    }
    public VeiculoVinculadoException() {
        super("Este veículo já foi vinculado a outro motorista");
    }
}
