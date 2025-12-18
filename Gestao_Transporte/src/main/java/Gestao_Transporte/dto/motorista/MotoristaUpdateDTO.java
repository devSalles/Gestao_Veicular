package Gestao_Transporte.dto.motorista;

import Gestao_Transporte.Enum.motoristaEnum.CategoriaCNH;
import Gestao_Transporte.Enum.motoristaEnum.StatusMotorista;
import Gestao_Transporte.entity.Motorista;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MotoristaUpdateDTO(

        @NotNull(message = "Nome obrigatório") @NotBlank(message = "Nome obrigatório")
        String nome,

        @NotNull(message = "Categoria obrigatória") @Enumerated(EnumType.STRING)
        CategoriaCNH categoriaCNH,

        @NotNull(message = "Status de motorista obrigatória") @Enumerated(EnumType.STRING)
        StatusMotorista statusMotorista
) {

    public Motorista updateMotorista(Motorista motorista)
    {
        motorista.setNome(this.nome);
        motorista.setCategoria(this.categoriaCNH);
        motorista.setStatusMotorista(statusMotorista);

        return motorista;
    }
}
