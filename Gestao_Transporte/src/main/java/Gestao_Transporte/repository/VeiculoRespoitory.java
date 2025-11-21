package Gestao_Transporte.repository;

import Gestao_Transporte.entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VeiculoRespoitory extends JpaRepository<Veiculo,Long> {
}
