package Gestao_Transporte.repository;

import Gestao_Transporte.Enum.StatusViagem;
import Gestao_Transporte.entity.Viagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ViagemRepository extends JpaRepository<Viagem,Long> {

    List<Viagem> findByStatus(StatusViagem statusViagem);

    List<Viagem> findByMotoristaId(Long idMotorista);

    List<Viagem> findByVeiculoId(Long idVeiculo);

    List<Viagem> findByDataSaidaBetween(LocalDateTime dataInicio, LocalDateTime dateFim);

    List<Viagem> findByDataChegadaPrevistaBetween(LocalDateTime dataInicio, LocalDateTime dataFim);

    List<Viagem> findByDataChegadaRealBetween(LocalDateTime dataInicio, LocalDateTime dataFim);

    //Verifica se motorista tem viagem ativa ou agendada
    boolean existsByMotoristaIdAndStatusIn(Long id, List<StatusViagem> statusViagem);

    //Verifica se o veiculo possui viagem ativa ou finalizada
    boolean existsByVeiculoIdAndStatusIn(Long id, List<StatusViagem> statusViagens);

    @Query("SELECT COUNT(v) > 0 FROM Viagem v WHERE v.veiculo.id = :veiculoId AND v.status IN :status")
    boolean veiculoOcupado(Long veiculoId, List<StatusViagem> status);

}
