package Gestao_Transporte.dto.motorista;

import Gestao_Transporte.Enum.motoristaEnum.CategoriaCNH;
import Gestao_Transporte.entity.Motorista;
import Gestao_Transporte.entity.Veiculo;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.util.HashSet;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MotoristaRequestDTO {

    @NotNull(message = "Nome obrigatório") @NotBlank(message = "Nome obrigatório")
    private String nome;

    @NotNull(message = "CPF obrigatório") @NotBlank(message = "CPF obrigatório")
    @CPF(message = "Formato de CPF ou CPF inválido")
    private String cpf;

    @NotNull(message = "CNH obrigatória") @NotBlank(message = "CNH obrigatória")
    @Pattern(regexp = "^(\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2})$")//Salvo com ou sem formatação
    private String cnh;

    @NotNull(message = "Categoria obrigatória") @Enumerated(EnumType.STRING)
    private CategoriaCNH categoriaCNH;

    public Motorista toMotorsita(Veiculo veiculo)
    {
        Motorista motorista = new Motorista();

        motorista.setNome(this.nome);
        motorista.setCpf(this.cpf);
        motorista.setCnh(this.cnh);
        motorista.setCategoria(this.categoriaCNH);

        if(motorista.getVeiculos() == null)
        {
            motorista.setVeiculos(new HashSet<>());
        }

        motorista.getVeiculos().add(veiculo);

        return motorista;
    }
}
