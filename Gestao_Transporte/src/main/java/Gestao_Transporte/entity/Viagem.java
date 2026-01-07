package Gestao_Transporte.entity;


import Gestao_Transporte.Enum.StatusViagem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_viagem")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Viagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String origem;

    @Column(nullable = false)
    private String destino;

    @Column(nullable = false)
    private LocalDateTime dataSaida;

    @Column(nullable = false)
    private LocalDateTime dataChegadaPrevista;

    @Column
    private LocalDateTime dataChegadaReal;

    @Column(nullable = false)
    private Double kmPercorrido;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusViagem status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "veiculo_id")
    private Veiculo veiculo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "motorista_id")
    private Motorista motorista;
}
