package Gestao_Transporte.dto.motorista;

import jakarta.validation.constraints.NotNull;

public record VincularVeiculoDTO(

        @NotNull(message = "Id de motorista obrigatório")
        Long idMotorista,

        @NotNull(message = "Id de veículo obrigatório")
        Long idVeiculo
) {
}
