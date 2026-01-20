package Gestao_Transporte.core.exception.viagem;

public class ViagemEmAndamentoException extends RuntimeException {
    public ViagemEmAndamentoException(String message) {
        super(message);
    }
    public ViagemEmAndamentoException() {
        super("Apenas viagens em andamentos podem ser finalizadas");
    }
}
