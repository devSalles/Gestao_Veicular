package Gestao_Transporte.core.exception.Viagem;

public class ViagensAtivasOuFinalizadasException extends RuntimeException {
    public ViagensAtivasOuFinalizadasException(String message) {super(message);}
    public ViagensAtivasOuFinalizadasException()
    {
        super("Veículo possui viagens em andamento ou finalizadas");
    }
}
