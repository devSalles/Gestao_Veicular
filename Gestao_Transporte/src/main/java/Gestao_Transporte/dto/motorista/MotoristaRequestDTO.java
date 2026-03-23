package Gestao_Transporte.dto.motorista;

import Gestao_Transporte.Enum.motoristaEnum.CategoriaCNH;
import Gestao_Transporte.entity.Motorista;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.br.CPF;

public record MotoristaRequestDTO(

    @NotBlank(message = "Nome obrigatório")
    String nome,

    @NotBlank(message = "CPF obrigatório")
    @CPF(message = "CPF inválido")
    String cpf,

    @NotBlank(message = "CNH obrigatória")
    @Pattern(regexp = "^\\d{11}$", message = "CNH deve conter 11 dígitos")
    String cnh,

    @NotNull(message = "Categoria obrigatória")
    @Enumerated(EnumType.STRING)
    CategoriaCNH categoriaCNH
){
    public Motorista toMotorista()
    {
        Motorista motorista = new Motorista();

        motorista.setNome(this.nome);
        motorista.setCpf(this.cpf);
        motorista.setCnh(this.cnh);
        motorista.setCategoria(this.categoriaCNH);

        return motorista;
    }
}
