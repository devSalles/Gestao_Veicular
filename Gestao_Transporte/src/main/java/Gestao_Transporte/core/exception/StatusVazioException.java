package Gestao_Transporte.core.exception;

public class StatusVazioException extends RuntimeException {
    public StatusVazioException(String message) {
        super(message);
    }
    public StatusVazioException() {
        super("Nenhum veiculo cadastrado com esse Status");
    }
}
