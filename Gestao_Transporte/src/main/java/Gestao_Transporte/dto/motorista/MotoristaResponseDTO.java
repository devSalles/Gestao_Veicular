package Gestao_Transporte.dto.motorista;

import Gestao_Transporte.Enum.motoristaEnum.CategoriaCNH;
import Gestao_Transporte.entity.Motorista;

public record MotoristaResponseDTO(
        Long id,
        String nome,
        String cpf,
        String cnh,
        CategoriaCNH categoriaCNH
) {
    public MotoristaResponseDTO fromMotorista(Motorista motorista)
    {
        return new MotoristaResponseDTO(motorista.getId(),motorista.getNome(),motorista.getCpf(),motorista.getCnh(),motorista.getCategoria());
    }
}
