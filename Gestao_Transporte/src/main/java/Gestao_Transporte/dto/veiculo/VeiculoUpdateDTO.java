package Gestao_Transporte.dto.veiculo;

import Gestao_Transporte.entity.Veiculo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VeiculoUpdateDTO {

    @NotNull(message = "Marca do veículo obrigatória") @NotBlank(message = "Marca do veículo obrigatória")
    private String marca;

    @NotNull(message = "Modelo do veículo obrigatório") @NotBlank(message = "Modelo do veículo obrigatório")
    private String modelo;

    @NotNull(message = "Ano do veículo obrigatório") @Positive(message = "Ano não pode ser negativo e tem que ser maior que 0")
    private Integer ano;

    public Veiculo updateVeiculo(Veiculo veiculo)
    {
        veiculo.setMarca(this.getMarca());
        veiculo.setModelo(this.getModelo());
        veiculo.setAno(this.getAno());

        return veiculo;
    }

}
