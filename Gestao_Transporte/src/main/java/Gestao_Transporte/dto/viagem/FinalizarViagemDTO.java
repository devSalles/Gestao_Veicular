package Gestao_Transporte.dto.viagem;

import jakarta.validation.constraints.NotNull;

public record FinalizarViagemDTO(

        @NotNull(message = "Km percorrido obrigatório")
        Double kmPercorrido
) {
}
