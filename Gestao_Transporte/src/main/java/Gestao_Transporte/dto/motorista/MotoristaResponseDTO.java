package Gestao_Transporte.dto.motorista;

import Gestao_Transporte.Enum.motoristaEnum.CategoriaCNH;
import Gestao_Transporte.entity.Motorista;

import java.util.List;
import java.util.Set;

public record MotoristaResponseDTO(
        Long id,
        String nome,
        String cpf,
        String cnh,
        CategoriaCNH categoriaCNH
) {
    public static MotoristaResponseDTO fromMotorista(Motorista motorista)
    {
        return new MotoristaResponseDTO(motorista.getId(),motorista.getNome(),motorista.getCpf(),motorista.getCnh(),motorista.getCategoria());
    }

    //Conversão de SET em LIST para exibição de motorista na DTO de veiculo
    public static List<MotoristaResponseDTO> fromMotoristaConversao(Set<Motorista> motoristas)
    {
        return motoristas.stream().map(MotoristaResponseDTO::fromMotorista).toList();
    }
}
