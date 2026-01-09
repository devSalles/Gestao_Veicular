package Gestao_Transporte.core.infra;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class MessageRestError {

    private HttpStatus status;
    private String message;
    private LocalDateTime timeStamp;

    //Atributo para erros de validação
    private Map<String,String> fieldErrors = new HashMap<>();

    public MessageRestError(HttpStatus status, String message)
    {
        this.status=status;
        this.message=message;
        this.timeStamp=LocalDateTime.now();
    }

    //Construtor para erros de validação
    public MessageRestError(HttpStatus status,String message, Map<String,String>fieldErros)
    {
        this.status=status;
        this.message=message;
        this.fieldErrors=fieldErros;
        this.timeStamp= LocalDateTime.now();
    }
}
