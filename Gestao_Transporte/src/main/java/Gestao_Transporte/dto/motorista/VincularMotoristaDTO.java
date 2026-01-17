package Gestao_Transporte.dto.motorista;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record VincularMotoristaDTO(

        @NotNull(message = "Id de motorista obrigatório") @Positive(message = "Valor não pode ser negativo")
        Long idMotorista,

        @NotNull(message = "Id de veículo obrigatório") @Positive(message = "Valor não pode ser negativo")
        Long idVeiculo
) {
}
