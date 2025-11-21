package Gestao_Transporte.dto.veiculo;

import Gestao_Transporte.Enum.StatusVeiculo;
import Gestao_Transporte.entity.Veiculo;

public record VeiculoResponseDTO(
        Long id,
        String placa,
        String marca,
        String modelo,
        Integer ano,
        StatusVeiculo statusVeiculo
) {
    public VeiculoResponseDTO fromVeiculo(Veiculo veiculo)
    {
        return new VeiculoResponseDTO(veiculo.getId(), veiculo.getPlaca(), veiculo.getMarca(), veiculo.getModelo(), veiculo.getAno(), veiculo.getStatus());
    }
}
