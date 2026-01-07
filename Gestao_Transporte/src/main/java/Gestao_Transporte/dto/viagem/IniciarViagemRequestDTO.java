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

public class IniciarViagemRequestDTO {

    @NotNull(message = "Origem do destino obrigatório") @NotBlank(message = "Origem do destino obrigatório")
    private String origem;

    @NotNull(message = "O destino e obrigatório") @NotBlank(message = "O destino e obrigatório")
    private String destino;

    @NotNull(message = "A data de saída e obrigatória") @PastOrPresent(message = "A data de saída não pode ser futura")
    private LocalDateTime dataSaida;

    @NotNull(message = "A data de chegada prevista e obrigatória") @Future(message = "A data de chegada prevista deve ser no futuro")
    private LocalDateTime dataChegadaPrevista;

    @NotNull(message = "A quilometragem e obrigatória") @DecimalMin(value = "0.1",message = "o valor deve ser maior que 0.1Km")
    private Double kmPercorrido;

    public Viagem toViagem(Motorista motorista, Veiculo veiculo)
    {
        Viagem viagem = new Viagem();

        viagem.setOrigem(this.origem);
        viagem.setDestino(this.destino);
        viagem.setDataSaida(this.dataSaida);
        viagem.setDataChegadaPrevista(this.dataChegadaPrevista);
        viagem.setKmPercorrido(this.kmPercorrido);

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
