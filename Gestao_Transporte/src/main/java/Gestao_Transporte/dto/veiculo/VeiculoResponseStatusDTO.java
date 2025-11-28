package Gestao_Transporte.dto.veiculo;

import Gestao_Transporte.Enum.StatusVeiculo;
import Gestao_Transporte.entity.Veiculo;

public record VeiculoResponseStatusDTO(
        Long id,
        String placa,
        String marca,
        String modelo,
        Integer ano,
        StatusVeiculo statusVeiculo
) {
    public static VeiculoResponseStatusDTO fromVeiculo(Veiculo veiculo)
    {
        return new VeiculoResponseStatusDTO(veiculo.getId(), veiculo.getPlaca(), veiculo.getMarca(), veiculo.getModelo(), veiculo.getAno(), veiculo.getStatus());
    }
}
