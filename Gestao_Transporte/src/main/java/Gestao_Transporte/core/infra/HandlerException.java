package Gestao_Transporte.core.infra;

import Gestao_Transporte.core.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandlerException {

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<MessageRestError> excecoesGlobais()
//    {
//        MessageRestError messageRestError = new MessageRestError(HttpStatus.INTERNAL_SERVER_ERROR,"Erro interno, tente novamente mais tarde");
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageRestError);
//    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MessageRestError> excecaoParaCamposDeRequestInvalido(MethodArgumentNotValidException ex)
    {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream().map(e->e.getDefaultMessage()).findFirst().orElse("Erro de validação");
        MessageRestError messageRestError = new MessageRestError(HttpStatus.BAD_REQUEST,errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageRestError);
    }

    @ExceptionHandler(NenhumCadastroException.class)
    public ResponseEntity<MessageRestError> NenhumCadastroException(NenhumCadastroException ex)
    {
        MessageRestError messageRestError = new MessageRestError(HttpStatus.BAD_REQUEST,ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageRestError);
    }

    @ExceptionHandler(IdNaoEncontradoException.class)
    public ResponseEntity<MessageRestError> IdNaoEncontradoException(IdNaoEncontradoException ex)
    {
        MessageRestError messageRestError = new MessageRestError(HttpStatus.NOT_FOUND,ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageRestError);
    }

    //--------------------------- EXCEÇÕES DE MOTORISTA ---------------------------

    @ExceptionHandler(CnhNaoEncontradaException.class)
    public ResponseEntity<MessageRestError> CnhNaoEncontradaException(CnhNaoEncontradaException ex)
    {
        MessageRestError messageRestError = new MessageRestError(HttpStatus.NOT_FOUND,ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageRestError);
    }

    @ExceptionHandler(CnhRepetidaException.class)
    public ResponseEntity<MessageRestError> CnhRepetidaException (CnhRepetidaException ex)
    {
        MessageRestError messageRestError =new MessageRestError(HttpStatus.CONFLICT,ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(messageRestError);
    }

    @ExceptionHandler(CnhIncompativelException.class)
    public ResponseEntity<MessageRestError> CnhIncompativelException (CnhIncompativelException ex)
    {
        MessageRestError messageRestError = new MessageRestError(HttpStatus.BAD_REQUEST,ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageRestError);
    }

    @ExceptionHandler(CpfRepetidoException.class)
    public ResponseEntity<MessageRestError> CpfRepetidoException(CpfRepetidoException ex)
    {
        MessageRestError messageRestError = new MessageRestError(HttpStatus.CONFLICT,ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(messageRestError);
    }

    @ExceptionHandler(CpfNaoEncontradoException.class)
    public ResponseEntity<MessageRestError> CpfNaoEncontradoException(CpfNaoEncontradoException ex)
    {
        MessageRestError messageRestError = new MessageRestError(HttpStatus.NOT_FOUND,ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageRestError);
    }

    @ExceptionHandler(MotoristaIndisponivelException.class)
    public ResponseEntity<MessageRestError> MotoristaIndisponivelException(MotoristaIndisponivelException ex)
    {
        MessageRestError messageRestError = new MessageRestError(HttpStatus.BAD_REQUEST,ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageRestError);
    }

    @ExceptionHandler(ViagemAtivaOuAgendadaException.class)
    public ResponseEntity<MessageRestError> ViagemAtivaOuAgendadaException(ViagemAtivaOuAgendadaException ex)
    {
        MessageRestError messageRestError = new MessageRestError(HttpStatus.BAD_REQUEST,ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageRestError);
    }

    //--------------------------- EXCEÇÕES DE VEÍCULOS ---------------------------

    @ExceptionHandler(PlacaDuplicadaException.class)
    public ResponseEntity<MessageRestError> PlacaDuplicadaException(PlacaDuplicadaException ex)
    {
        MessageRestError messageRestError = new MessageRestError(HttpStatus.CONFLICT,ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(messageRestError);
    }

    @ExceptionHandler(PlacaInexistenteException.class)
    public ResponseEntity<MessageRestError> PlacaInexistenteException(PlacaInexistenteException ex)
    {
        MessageRestError messageRestError = new MessageRestError(HttpStatus.NOT_FOUND,ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageRestError);
    }

    @ExceptionHandler(NenhumVeiculoComStatusException.class)
    public ResponseEntity<MessageRestError> StatusVazioException(NenhumVeiculoComStatusException ex)
    {
        MessageRestError messageRestError = new MessageRestError(HttpStatus.NOT_FOUND,ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageRestError);
    }

    @ExceptionHandler(EmManutencaoException.class)
    public ResponseEntity<MessageRestError> EmManutencaoException(EmManutencaoException ex)
    {
        MessageRestError messageRestError = new MessageRestError(HttpStatus.CONFLICT, ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(messageRestError);
    }

    @ExceptionHandler(VeiculoIndisponivelException.class)
    public ResponseEntity<MessageRestError> VeiculoIndisponivelException(VeiculoIndisponivelException ex)
    {
        MessageRestError messageRestError = new MessageRestError(HttpStatus.BAD_REQUEST,ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageRestError);
    }

    @ExceptionHandler(VeiculoDisponivelException.class)
    public ResponseEntity<MessageRestError> VeiculoDisponivelException(VeiculoDisponivelException ex)
    {
        MessageRestError messageRestError = new MessageRestError(HttpStatus.BAD_REQUEST,ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageRestError);
    }

    @ExceptionHandler(VeiculoVinculadoException.class)
    public ResponseEntity<MessageRestError> VeiculoVinculadoException(VeiculoVinculadoException ex)
    {
        MessageRestError messageRestError = new MessageRestError(HttpStatus.CONFLICT,ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(messageRestError);
    }

    @ExceptionHandler(ViagensAtivasOuFinalizadasException.class)
    public ResponseEntity<MessageRestError> ViagensAtivasOuFinalizadasException(ViagensAtivasOuFinalizadasException ex)
    {
        MessageRestError messageRestError = new MessageRestError(HttpStatus.BAD_REQUEST,ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageRestError);
    }

    //--------------------------- EXCEÇÕES DE VIAGENS ---------------------------

    @ExceptionHandler(ViagemJaFinalizadaException.class)
    public ResponseEntity<MessageRestError> ViagemJaFinalizadaException(ViagemJaFinalizadaException ex)
    {
        MessageRestError messageRestError = new MessageRestError(HttpStatus.BAD_REQUEST,ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageRestError);
    }

    @ExceptionHandler(KmInvalidoException.class)
    public ResponseEntity<MessageRestError> KmInvalidoException(KmInvalidoException ex)
    {
        MessageRestError messageRestError = new MessageRestError(HttpStatus.BAD_REQUEST,ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageRestError);
    }

    @ExceptionHandler(DataException.class)
    public ResponseEntity<MessageRestError> DataAnteriorException(DataException ex)
    {
        MessageRestError messageRestError = new MessageRestError(HttpStatus.BAD_REQUEST,ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageRestError);
    }
}
