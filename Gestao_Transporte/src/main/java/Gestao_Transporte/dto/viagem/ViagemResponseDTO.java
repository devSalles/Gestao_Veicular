package Gestao_Transporte.dto.viagem;

import Gestao_Transporte.Enum.StatusViagem;
import Gestao_Transporte.entity.Viagem;

import java.time.LocalDateTime;

public record ViagemResponseDTO(
        Long id,
        String origem,
        String destino,
        LocalDateTime dataSaida,
        LocalDateTime dataChegadaPrevista,
        LocalDateTime dataRealChegada,
        Long atraso,
        Double kmPercorrido,
        StatusViagem statusViagem

) {
    public static ViagemResponseDTO fromViagem(Viagem viagem) {
        return new ViagemResponseDTO(viagem.getId(), viagem.getOrigem(), viagem.getDestino(), viagem.getDataSaida(),
                viagem.getDataChegadaReal(), viagem.getDataChegadaPrevista(), viagem.getAtraso(),viagem.getKmPercorrido(), viagem.getStatus());
    }
}
