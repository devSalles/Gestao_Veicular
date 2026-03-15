package Gestao_Transporte.dto.viagem;

import Gestao_Transporte.entity.Motorista;
import Gestao_Transporte.entity.Veiculo;
import Gestao_Transporte.entity.Viagem;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AgendarViagemRequestDTO {

    @NotNull(message = "Origem do destino obrigatório") @NotBlank(message = "Origem do destino obrigatório")
    private String origem;

    @NotNull(message = "O destino e obrigatório") @NotBlank(message = "O destino e obrigatório")
    private String destino;

    @NotNull(message = "A data de saída e obrigatória")
    private LocalDateTime dataSaida;

    @NotNull(message = "A data de chegada prevista e obrigatória") @FutureOrPresent(message = "A data de chegada prevista deve ser no futuro")
    private LocalDateTime dataChegadaPrevista;

    @NotNull(message = "ID de motorista obrigatório ") @Positive(message = "Valor não permitido")
    private Long idMotorista;

    @NotNull(message = "ID de veículo obrigatório ") @Positive(message = "Valor não permitido")
    private Long idVeiculo;

    public Viagem toViagem(Motorista motorista, Veiculo veiculo)
    {
        Viagem viagem = new Viagem();

        viagem.setOrigem(this.origem);
        viagem.setDestino(this.destino);
        viagem.setDataSaida(this.dataSaida);
        viagem.setDataChegadaPrevista(this.dataChegadaPrevista);

        if(viagem.getMotorista() == null)
        {
            veiculo.setMotoristas(new HashSet<>());
        }
        veiculo.getMotoristas().add(motorista);

        if(viagem.getVeiculo() == null)
        {
            viagem.setVeiculo(veiculo);
        }

        return viagem;
    }
}
