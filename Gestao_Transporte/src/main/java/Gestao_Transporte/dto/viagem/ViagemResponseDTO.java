package Gestao_Transporte.dto.viagem;

import Gestao_Transporte.Enum.StatusViagem;
import Gestao_Transporte.dto.motorista.MotoristaResponseDTO;
import Gestao_Transporte.dto.veiculo.VeiculoResponseDTO;
import Gestao_Transporte.entity.Viagem;

import java.time.LocalDateTime;

public record ViagemResponseDTO(
        Long id,
        String origem,
        String destino,
        LocalDateTime dataSaida,
        LocalDateTime dataChegadaPrevista,
        LocalDateTime dataRealChegada,
        Double kmPercorrido,
        StatusViagem statusViagem,
        MotoristaResponseDTO motoristaResponseDTO,
        VeiculoResponseDTO veiculoResponseDTO

) {
    public static ViagemResponseDTO fromViagem(Viagem viagem)
    {
        return new ViagemResponseDTO(viagem.getId(), viagem.getOrigem(), viagem.getDestino(), viagem.getDataSaida(),
                viagem.getDataChegadaReal(),viagem.getDataChegadaPrevista(),viagem.getKmPercorrido(),viagem.getStatus(),
                viagem.getMotorista() != null ? MotoristaResponseDTO.fromMotorista(viagem.getMotorista()) : null,
                viagem.getVeiculo() != null ? VeiculoResponseDTO.fromVeiculo(viagem.getVeiculo()) : null);
    }
}
