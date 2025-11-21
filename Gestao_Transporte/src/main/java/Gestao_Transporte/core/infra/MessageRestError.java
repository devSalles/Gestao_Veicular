package Gestao_Transporte.core.infra;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class MessageRestError {

    private HttpStatus status;
    private String message;
    private LocalDate timeStamp;

    public MessageRestError(HttpStatus status, String message)
    {
        this.status=status;
        this.message=message;
        this.timeStamp=LocalDate.now();
    }
}
