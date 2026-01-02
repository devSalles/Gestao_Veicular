package Gestao_Transporte.service;

import Gestao_Transporte.Enum.motoristaEnum.StatusMotorista;
import Gestao_Transporte.core.exception.*;
import Gestao_Transporte.dto.motorista.MotoristaRequestDTO;
import Gestao_Transporte.dto.motorista.MotoristaResponseDTO;
import Gestao_Transporte.dto.motorista.MotoristaUpdateDTO;
import Gestao_Transporte.entity.Motorista;
import Gestao_Transporte.entity.Veiculo;
import Gestao_Transporte.repository.MotoristaRepository;
import Gestao_Transporte.repository.VeiculoRespoitory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MotoristaService {

    private final MotoristaRepository motoristaRepository;
    private final VeiculoRespoitory veiculoRespoitory;

    public MotoristaResponseDTO salvarMotorista(MotoristaRequestDTO motoristaRequestDTO)
    {
        if(this.motoristaRepository.existsByCnh(motoristaRequestDTO.getCnh()))
        {
            throw new CnhRepetidaException();
        }

        if(this.motoristaRepository.existsByCpf(motoristaRequestDTO.getCpf()))
        {
            throw new CpfRepetidoException();
        }

        Motorista motorista = motoristaRequestDTO.salvarMotorista();

        this.motoristaRepository.save(motorista);

        return MotoristaResponseDTO.fromMotorista(motorista);
    }

    public Motorista atualizarMotorista(Long id, MotoristaUpdateDTO motoristaUpdateDTO)
    {
        Motorista motoristaID = buscarID(id);

        Motorista motoristaAtualizado = motoristaUpdateDTO.updateMotorista(motoristaID);

        return this.motoristaRepository.save(motoristaAtualizado);
    }

    public Motorista vincularVeiculo(Long idMotorista, Long idVeiculo)
    {
        Motorista motoristaID = buscarID(idMotorista);
        Veiculo veiculo = this.veiculoRespoitory.findById(idVeiculo).orElseThrow(()->new IdNaoEncontradoException("ID de veículo não encontrado"));

        if(!motoristaID.getCategoria().isCompativelCom(veiculo.getTipoVeiculo()))
        {
            throw new CnhIncompativelException();
        }

        if(motoristaID.getVeiculos().contains(veiculo))
        {
            throw new VeiculoVinculadoException();
        }

        motoristaID.getVeiculos().add(veiculo);

        return this.motoristaRepository.save(motoristaID);
    }

    public List<MotoristaResponseDTO> listarTodos()
    {
        List<Motorista>motoristas = this.motoristaRepository.findAll();
        if(motoristas.isEmpty())
        {
            throw new NenhumCadastroException("Nenhum registro salvo");
        }

        return motoristas.stream().map(MotoristaResponseDTO::fromMotorista).toList();
    }

    public MotoristaResponseDTO exibirPorID(Long id)
    {

        Motorista motoristaID = buscarID(id);
        return MotoristaResponseDTO.fromMotorista(motoristaID);
    }

    public MotoristaResponseDTO exibirPorCPF(String cpf)
    {
        Motorista motoristaCpf = buscarCPF(cpf);
        return MotoristaResponseDTO.fromMotorista(motoristaCpf);
    }

//    public boolean excluirPorId(Long id)
//    {
//        Motorista motoristaID = buscarID(id);
//        this.motoristaRepository.delete(motoristaID);
//        return true;
//    }

    //-------------- Metodos auxiliares --------------

    //Metodo responsável po realizar busca por CPF
    public Motorista buscarCPF(String cpf)
    {
        Motorista motoristaCPF = this.motoristaRepository.findByCpf(cpf);
        if(motoristaCPF == null)
        {
            throw new CpfNaoEncontradoException();
        }

        return motoristaCPF;
    }

    //Metodo para validação de veículo e motorista para iniciar viagem
    public void validarViagens(Long idMotorista, Veiculo veiculo)
    {
        Motorista motoristaID = buscarID(idMotorista);

        if(!motoristaID.getStatusMotorista().equals(StatusMotorista.ATIVO))
        {
            throw new MotoristaIndisponivelException();
        }

        if(!motoristaID.getCategoria().isCompativelCom(veiculo.getTipoVeiculo()))
        {
            throw new CnhIncompativelException();
        }
    }

    //Responsável por buscar ID
    public Motorista buscarID(Long id)
    {
        return this.motoristaRepository.findById(id).orElseThrow(()->new IdNaoEncontradoException("ID de motorista não encontrado"));
    }

}