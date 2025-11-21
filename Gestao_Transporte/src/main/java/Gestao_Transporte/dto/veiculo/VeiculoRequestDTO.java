package Gestao_Transporte.dto.veiculo;

import Gestao_Transporte.Enum.StatusVeiculo;
import Gestao_Transporte.entity.Motorista;
import Gestao_Transporte.entity.Veiculo;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VeiculoRequestDTO {

    @NotNull(message = "Placa do veículo obrigatória") @NotBlank(message = "Placa do veículo obrigatória")
    private String placa; //salvo sem formatação

    @NotNull(message = "Marca do veículo obrigatória") @NotBlank(message = "Marca do veículo obrigatória")
    private String marca;

    @NotNull(message = "Modelo do veículo obrigatório") @NotBlank(message = "Modelo do veículo obrigatório")
    private String modelo;

    @NotNull(message = "Ano do veículo obrigatório") @Positive(message = "Ano não pode ser negativo e tem que ser maior que 0")
    private Integer ano;

    @NotNull(message = "Status do veículo obrigatório") @Enumerated(EnumType.STRING)
    @Pattern(regexp = "([A-Z]{3}[0-9]{4} | A-Z]{3}[0-9][A-Z][0-9]{2})") //placa salva sem formatação (modelo Mercosul e modelo antigo)
    private StatusVeiculo statusVeiculo;

    public Veiculo toVeiculo(Motorista motorista)
    {
        Veiculo veiculo = new Veiculo();

        veiculo.setPlaca(this.placa);
        veiculo.setMarca(this.marca);
        veiculo.setModelo(this.modelo);
        veiculo.setAno(this.ano);
        veiculo.setStatus(this.statusVeiculo);

        if(veiculo.getMotoristas() == null)
        {
            veiculo.setMotoristas(new HashSet<>());
        }

        veiculo.getMotoristas().add(motorista);

        return veiculo;
    }
}
