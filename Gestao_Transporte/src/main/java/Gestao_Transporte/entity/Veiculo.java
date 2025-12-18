package Gestao_Transporte.entity;

import Gestao_Transporte.Enum.StatusVeiculo;
import Gestao_Transporte.Enum.TipoVeiculo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_veiculo")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Veiculo {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String placa; //salvo sem formatação

    @Column(nullable = false)
    private String marca;

    @Column(nullable = false)
    private String modelo;

    @Column(nullable = false)
    private Integer ano;

    @Column(nullable = false) @Enumerated(EnumType.STRING)
    private TipoVeiculo tipoVeiculo;

    @Column(nullable = false) @Enumerated(EnumType.STRING)
    private StatusVeiculo status;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Veiculo veiculo)) return false;
        return Objects.equals(getId(), veiculo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @ManyToMany(mappedBy = "veiculos")
    @JsonIgnore
    private Set<Motorista> motoristas =new HashSet<>();
}
