package Gestao_Transporte.core.infra;

import Gestao_Transporte.core.exception.*;
import Gestao_Transporte.core.exception.Motorista.*;
import Gestao_Transporte.core.exception.Veiculo.*;
import Gestao_Transporte.core.exception.Viagem.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class HandlerException {

    //--------------------------- EXCEÇÕES GLOBAIS ---------------------------

    //Exceções globais
    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageRestError> Exception()
    {
        MessageRestError messageRestError = new MessageRestError(HttpStatus.INTERNAL_SERVER_ERROR,"Erro interno, tente novamente mais tarde");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageRestError);
    }

    //Exceção para requets invalidas
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MessageRestError> MethodArgumentNotValidException(MethodArgumentNotValidException ex)
    {
        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error->errors.put(error.getField(),error.getDefaultMessage()));

        MessageRestError messageRestError = new MessageRestError(HttpStatus.BAD_REQUEST,"Erro de validação nos campos enviados ",errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageRestError);
    }

    //Exceção de banco de dados vazio
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
