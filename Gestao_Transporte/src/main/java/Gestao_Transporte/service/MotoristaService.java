package Gestao_Transporte.service;

import Gestao_Transporte.Enum.StatusViagem;
import Gestao_Transporte.Enum.motoristaEnum.StatusMotorista;
import Gestao_Transporte.core.exception.*;
import Gestao_Transporte.core.exception.motorista.*;
import Gestao_Transporte.core.exception.veiculo.VeiculoVinculadoException;
import Gestao_Transporte.core.exception.viagem.ViagemAtivaOuAgendadaException;
import Gestao_Transporte.dto.motorista.MotoristaRequestDTO;
import Gestao_Transporte.dto.motorista.MotoristaResponseDTO;
import Gestao_Transporte.dto.motorista.MotoristaUpdateDTO;
import Gestao_Transporte.entity.Motorista;
import Gestao_Transporte.entity.Veiculo;
import Gestao_Transporte.repository.MotoristaRepository;
import Gestao_Transporte.repository.VeiculoRespoitory;
import Gestao_Transporte.repository.ViagemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MotoristaService {

    private final MotoristaRepository motoristaRepository;
    private final VeiculoRespoitory veiculoRespoitory;
    private final ViagemRepository viagemRepository;

    @Transactional
    public MotoristaResponseDTO salvarMotorista(MotoristaRequestDTO motoristaRequestDTO)
    {
        String cpfFormatado = formatarCPF(motoristaRequestDTO.getCpf());
        if(this.motoristaRepository.existsByCnh(motoristaRequestDTO.getCnh()))
        {
            throw new CnhRepetidaException();
        }

        if(this.motoristaRepository.existsByCpf(cpfFormatado))
        {
            throw new CpfRepetidoException();
        }

        Motorista motorista = motoristaRequestDTO.toMotorista();
        motorista.setCpf(cpfFormatado);
        motorista.setStatusMotorista(StatusMotorista.ATIVO);

        this.motoristaRepository.save(motorista);

        return MotoristaResponseDTO.fromMotorista(motorista);
    }

    @Transactional
    public MotoristaResponseDTO atualizarMotorista(Long id, MotoristaUpdateDTO motoristaUpdateDTO)
    {
        Motorista motoristaID = buscarID(id);

        Motorista motoristaAtualizado = motoristaUpdateDTO.updateMotorista(motoristaID);

        this.motoristaRepository.save(motoristaAtualizado);

        return MotoristaResponseDTO.fromMotorista(motoristaAtualizado);
    }

    //Metodo para vincular motorista a um veiculo
    @Transactional
    public MotoristaResponseDTO vincularVeiculo(Long idMotorista ,Long idVeiculo)
    {
        Motorista motoristaVinc = buscarID(idMotorista);
        Veiculo veiculoVinc = this.veiculoRespoitory.findById(idVeiculo).orElseThrow(()->new  IdNaoEncontradoException("ID de veículo não encontrado"));

        if(!motoristaVinc.getCategoria().isCompativelCom(veiculoVinc.getTipoVeiculo()))
        {
            throw new CnhIncompativelException();
        }

        if(motoristaVinc.getVeiculos().contains(veiculoVinc))
        {
            throw new VeiculoVinculadoException();
        }

        if(motoristaVinc.getStatusMotorista() == StatusMotorista.SUSPENSO ||motoristaVinc.getStatusMotorista() == StatusMotorista.INATIVO)
        {
            throw new MotoristaIndisponivelException("Motorista com status INATIVO ou SUSPENSO não pode ser vinculado ao veículo");
        }

        motoristaVinc.getVeiculos().add(veiculoVinc);
        veiculoVinc.getMotoristas().add(motoristaVinc);

        this.veiculoRespoitory.save(veiculoVinc);
        return MotoristaResponseDTO.fromMotorista(motoristaVinc);
    }

    @Transactional
    public MotoristaResponseDTO desvincularVeiculo(Long idVeiculo, Long idMotorista)
    {
        Motorista motorista = buscarID(idMotorista);
        Veiculo veiculo = this.veiculoRespoitory.findById(idVeiculo).orElseThrow(()->new IdNaoEncontradoException("Id de veículo não encontrado"));

        if(!motorista.getVeiculos().contains(veiculo))
        {
            throw new VeiculoNaoVinculadoException();
        }

        motorista.getVeiculos().remove(veiculo);
        veiculo.getMotoristas().remove(motorista);

        this.motoristaRepository.save(motorista);
        this.veiculoRespoitory.save(veiculo);

        return MotoristaResponseDTO.fromMotorista(motorista);
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
        String cpfFormatado = formatarCPF(cpf);
        Motorista motoristaCpf = buscarCPF(cpfFormatado);
        return MotoristaResponseDTO.fromMotorista(motoristaCpf);
    }

    @Transactional
    public MotoristaResponseDTO desativarMotorista(Long id)
    {
        Motorista motoristaID = buscarID(id);

        boolean possuiViagemAtivaOuAgendada = viagemRepository.existsByMotoristaIdAndStatusIn(id,List.of(StatusViagem.AGENDADA,StatusViagem.EM_ANDAMENTO));
        if(possuiViagemAtivaOuAgendada)
        {
            throw new ViagemAtivaOuAgendadaException();
        }

        motoristaID.setStatusMotorista(StatusMotorista.INATIVO);
        this.motoristaRepository.save(motoristaID);

        return MotoristaResponseDTO.fromMotorista(motoristaID);
    }

    //-------------- METODOS AUXILIARES --------------

    //Metodo responsável po realizar busca por CPF
    private Motorista buscarCPF(String cpf)
    {
        String cpfFormatado = formatarCPF(cpf);
        Motorista motoristaCPF = this.motoristaRepository.findByCpf(cpfFormatado);
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

    //Metodo responsável por formatar CPF
    private String formatarCPF(String cpf)
    {
        return cpf.replaceAll("\\D","");
    }

}