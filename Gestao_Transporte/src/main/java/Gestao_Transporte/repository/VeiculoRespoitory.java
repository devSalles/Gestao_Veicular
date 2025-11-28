package Gestao_Transporte.repository;

import Gestao_Transporte.Enum.StatusVeiculo;
import Gestao_Transporte.dto.veiculo.VeiculoUpdateDTO;
import Gestao_Transporte.entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VeiculoRespoitory extends JpaRepository<Veiculo,Long> {

    boolean existsByPlaca(String placa);

    Veiculo findByPlaca(String placa);

    List<Veiculo> findByStatus(StatusVeiculo statusVeiculo);
}
