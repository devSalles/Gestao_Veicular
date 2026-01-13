package Gestao_Transporte.dto.viagem;

import jakarta.validation.constraints.NotNull;

public record FinalizarViagemRequestDTO(

        @NotNull(message = "Km percorrido obrigatório")
        Double kmPercorrido
) {
}
