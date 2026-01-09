package Gestao_Transporte.dto.motorista;

import Gestao_Transporte.Enum.motoristaEnum.CategoriaCNH;
import Gestao_Transporte.Enum.motoristaEnum.StatusMotorista;
import Gestao_Transporte.entity.Motorista;
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


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MotoristaRequestDTO {

    @NotBlank(message = "Nome obrigatório")
    private String nome;

    @NotBlank(message = "CPF obrigatório")
    @CPF(message = "CPF inválido")
    private String cpf;

    @NotBlank(message = "CNH obrigatória")
    @Pattern(regexp = "^\\d{11}$", message = "CNH deve conter 11 dígitos")
    private String cnh;

    @NotNull(message = "Categoria obrigatória")
    @Enumerated(EnumType.STRING)
    private CategoriaCNH categoriaCNH;

    @NotNull(message = "Status de motorista obrigatório")
    @Enumerated(EnumType.STRING)
    private StatusMotorista statusMotorista;



    public Motorista salvarMotorista()
    {
        Motorista motorista = new Motorista();

        motorista.setNome(this.nome);
        motorista.setCpf(this.cpf);
        motorista.setCnh(this.cnh);
        motorista.setCategoria(this.categoriaCNH);
        motorista.setStatusMotorista(this.statusMotorista);

        return motorista;
    }
}
