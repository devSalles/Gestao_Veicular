package Gestao_Transporte.core.exception.Motorista;

public class CnhRepetidaException extends RuntimeException {
    public CnhRepetidaException(String message) {super(message);}
    public CnhRepetidaException()
    {
        super("Cnh já cadastrada no sistema");
    }
}
