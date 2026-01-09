package Gestao_Transporte.dto.veiculo;

import Gestao_Transporte.Enum.veiculoEnum.TipoVeiculo;
import Gestao_Transporte.entity.Veiculo;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VeiculoRequestDTO {

    @NotNull(message = "Placa do veículo obrigatória") @NotBlank(message = "Placa do veículo obrigatória")
//    @Pattern(regexp = "([A-Z]{3}[0-9]{4} | A-Z]{3}[0-9][A-Z][0-9]{2})",message = " formato de placa inválido") //placa salva sem formatação (modelo Mercosul e modelo antigo)
    private String placa; //salvo sem formatação

    @NotNull(message = "Marca do veículo obrigatória") @NotBlank(message = "Marca do veículo obrigatória")
    private String marca;

    @NotNull(message = "Modelo do veículo obrigatório") @NotBlank(message = "Modelo do veículo obrigatório")
    private String modelo;

    @NotNull(message = "Ano do veículo obrigatório") @Positive(message = "Ano não pode ser negativo e tem que ser maior que 0")
    private Integer ano;

    @NotNull(message = "Tipo de veículo obrigatório") @Enumerated(EnumType.STRING)
    private TipoVeiculo tipoVeiculo;

    public Veiculo novoVeiculo()
    {
        Veiculo veiculo = new Veiculo();

        veiculo.setPlaca(this.placa);
        veiculo.setMarca(this.marca);
        veiculo.setModelo(this.modelo);
        veiculo.setAno(this.ano);
        veiculo.setTipoVeiculo(this.tipoVeiculo);

        return veiculo;
    }
}
