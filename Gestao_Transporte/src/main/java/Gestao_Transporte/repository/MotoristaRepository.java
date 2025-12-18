package Gestao_Transporte.repository;

import Gestao_Transporte.entity.Motorista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MotoristaRepository extends JpaRepository<Motorista,Long> {

    Motorista findByCpf(String cpf);

    boolean existsByCpf(String cpf);

    boolean existsByCnh(String cnh);
}
