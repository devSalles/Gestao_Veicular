package Gestao_Transporte.dto.veiculo;

import Gestao_Transporte.Enum.veiculoEnum.StatusVeiculo;
import Gestao_Transporte.dto.motorista.MotoristaResponseDTO;
import Gestao_Transporte.entity.Veiculo;
import java.util.List;

public record VeiculoResponseDTO(
        Long id,
        String placa,
        String marca,
        String modelo,
        Integer ano,
        StatusVeiculo statusVeiculo,
        List<MotoristaResponseDTO> motorista
) {
    public static VeiculoResponseDTO fromVeiculo(Veiculo veiculo)
    {
        return new VeiculoResponseDTO(veiculo.getId(), veiculo.getPlaca(), veiculo.getMarca(), veiculo.getModelo(), veiculo.getAno(), veiculo.getStatus(),
        MotoristaResponseDTO.fromMotoristaConversao(veiculo.getMotoristas()));
    }
}
