package Gestao_Transporte.repository;

import Gestao_Transporte.Enum.StatusVeiculo;
import Gestao_Transporte.Enum.StatusViagem;
import Gestao_Transporte.entity.Viagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ViagemRepository extends JpaRepository<Viagem,Long> {

    List<Viagem> findByStatus(StatusViagem statusViagem);

    List<Viagem> findByMotoristaId(Long idMotorista);

    List<Viagem> findByVeiculoId(Long idVeiculo);
}
