package Gestao_Transporte.core.exception;

public class ViagemAtivaOuAgendadaException extends RuntimeException {
    public ViagemAtivaOuAgendadaException(String message) {
        super(message);
    }
    public ViagemAtivaOuAgendadaException() {

        super("Não e possível excluir pois o motorista já possui uma viagem agendada ou ativa");
    }
}
