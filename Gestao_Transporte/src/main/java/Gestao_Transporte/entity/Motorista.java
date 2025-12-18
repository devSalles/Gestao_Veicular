package Gestao_Transporte.entity;

import Gestao_Transporte.Enum.StatusVeiculo;
import Gestao_Transporte.Enum.motoristaEnum.CategoriaCNH;
import Gestao_Transporte.Enum.motoristaEnum.StatusMotorista;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_motorista")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Motorista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true) @CPF
    private String cpf;

    @Column(nullable = false, unique = true)
    private String cnh;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CategoriaCNH categoria;

    @Column
    @Enumerated(EnumType.STRING)
    private StatusMotorista statusMotorista;

    @ManyToMany
    @JoinTable(name = "motorista_veiculo",
            joinColumns = @JoinColumn(name = "motorista_id"),
            inverseJoinColumns = @JoinColumn(name = "veiculo_id")
    )
    private Set<Veiculo>veiculos = new HashSet<>();
}
