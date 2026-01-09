package Gestao_Transporte.core.exception.Motorista;

public class CnhIncompativelException extends RuntimeException {
    public CnhIncompativelException(String message) {
        super(message);
    }
    public CnhIncompativelException() {
        super("CNH não compatível com veículo");
    }
}
