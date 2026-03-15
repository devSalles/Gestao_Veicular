package Gestao_Transporte.core.exception.motorista;

public class VeiculoNaoVinculadoException extends RuntimeException {
    public VeiculoNaoVinculadoException(String message) {
        super(message);
    }
    public VeiculoNaoVinculadoException() {
        super("Motorista não foi vinculado a veículo");
    }
}
