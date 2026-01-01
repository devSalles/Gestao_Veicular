package Gestao_Transporte.service;

import Gestao_Transporte.Enum.StatusVeiculo;
import Gestao_Transporte.Enum.StatusViagem;
import Gestao_Transporte.core.exception.IdNaoEncontradoException;
import Gestao_Transporte.core.exception.KmInvalidoException;
import Gestao_Transporte.core.exception.NenhumCadastroException;
import Gestao_Transporte.core.exception.ViagemJaFinalizadaException;
import Gestao_Transporte.dto.viagem.ViagemRequestDTO;
import Gestao_Transporte.dto.viagem.ViagemResponseDTO;
import Gestao_Transporte.entity.Motorista;
import Gestao_Transporte.entity.Veiculo;
import Gestao_Transporte.entity.Viagem;
import Gestao_Transporte.repository.MotoristaRepository;
import Gestao_Transporte.repository.VeiculoRespoitory;
import Gestao_Transporte.repository.ViagemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ViagemService {

    private final ViagemRepository viagemRepository;
    private final MotoristaRepository motoristaRepository;
    private final VeiculoRespoitory veiculoRespoitory;
    private final MotoristaService motoristaService;

    @Transactional
    public ViagemResponseDTO agendarViagem(ViagemRequestDTO dto, Long idMotorista, Long idVeiculo)
    {
        Motorista motoristaID = this.motoristaRepository.findById(idMotorista).orElseThrow(()->new IdNaoEncontradoException("ID de motorista não encontrado"));
        Veiculo veiculoID = this.veiculoRespoitory.findById(idVeiculo).orElseThrow(() -> new IdNaoEncontradoException("ID de veículo não encontrado"));

        Viagem viagem = dto.toViagem(motoristaID,veiculoID);
        motoristaService.validarViagens(idMotorista,veiculoID);
        viagem.setVeiculo(veiculoID);
        viagem.setMotorista(motoristaID);
        viagem.setStatus(StatusViagem.AGENDADA);

        this.viagemRepository.save(viagem);
        return ViagemResponseDTO.fromViagem(viagem);
    }

    @Transactional
    public ViagemResponseDTO iniciarViagem(ViagemRequestDTO dto, Long idMotorista, Long idVeiculo)
    {
        Motorista motoristaID = this.motoristaRepository.findById(idMotorista).orElseThrow(()->new IdNaoEncontradoException("ID de motorista não encontrado"));
        Veiculo veiculoID = this.veiculoRespoitory.findById(idVeiculo).orElseThrow(() -> new IdNaoEncontradoException("ID de veículo não encontrado"));

        Viagem viagem = dto.toViagem(motoristaID,veiculoID);
        motoristaService.validarViagens(idMotorista,veiculoID);
        viagem.setMotorista(motoristaID);
        viagem.setVeiculo(veiculoID);
        viagem.setStatus(StatusViagem.EM_ANDAMENTO);
        this.viagemRepository.save(viagem);

        return ViagemResponseDTO.fromViagem(viagem);
    }

    @Transactional
    public void finalizarViagem(Long id, Double distanciaPercorrida)
    {
        Viagem viagemID = viagemRepository.findById(id).orElseThrow(()->new IdNaoEncontradoException("ID de viagem não encontrado"));

        if(viagemID.getStatus() == StatusViagem.FINALIZADA)
        {
            throw new ViagemJaFinalizadaException();
        }

        if(distanciaPercorrida<0)
        {
            throw new KmInvalidoException();
        }

        LocalDateTime chegadaReal = LocalDateTime.now();
        viagemID.setDataChegadaReal(chegadaReal);

        LocalDateTime chegadaPrevista = viagemID.getDataChegadaPrevista();

        boolean houveAtrasos = chegadaReal.isAfter(chegadaPrevista);

        if(houveAtrasos)
        {
            long atrasoMinutos = Duration.between(chegadaPrevista,chegadaReal).toMinutes();
        }

        Double totalKm = viagemID.getKmPercorrido()+distanciaPercorrida;
        viagemID.setKmPercorrido(totalKm);

        Veiculo veiculo = viagemID.getVeiculo();
        veiculo.setStatus(StatusVeiculo.DISPONIVEL);

        viagemID.setStatus(StatusViagem.FINALIZADA);

        this.veiculoRespoitory.save(veiculo);
        this.viagemRepository.save(viagemID);
    }

    public ViagemResponseDTO cancelar(Long id)
    {
        Viagem viagemID = this.viagemRepository.findById(id).orElseThrow(()->new IdNaoEncontradoException("A viagem não encontrada"));

        if(viagemID.getStatus() == StatusViagem.FINALIZADA)
        {
            throw new ViagemJaFinalizadaException();
        }

        viagemID.setStatus(StatusViagem.CANCELADA);
        this.viagemRepository.save(viagemID);

        return ViagemResponseDTO.fromViagem(viagemID);
    }

    public List<ViagemResponseDTO> listarTodas()
    {
        List<Viagem>viagemList = this.viagemRepository.findAll();
        if(viagemList.isEmpty())
        {
            throw new NenhumCadastroException("Nenhuma viagem cadastrada realizado");
        }

        return viagemList.stream().map(ViagemResponseDTO::fromViagem).toList();
    }

    public ViagemResponseDTO buscarID(Long id)
    {
        Viagem viagemID = this.viagemRepository.findById(id).orElseThrow(()->new IdNaoEncontradoException("Viagem não encontrada"));
        return ViagemResponseDTO.fromViagem(viagemID);
    }

    public List<Viagem> buscarVeiculo(Long idVeiculo)
    {
        List<Viagem> viagemList=this.viagemRepository.findByVeiculoId(idVeiculo);

        if(viagemList.isEmpty())
        {
            throw new IdNaoEncontradoException();
        }

        return viagemList;
    }

    public List<Viagem> buscarMotorista(Long idMotorista)
    {
        List<Viagem> viagemList=this.viagemRepository.findByMotoristaId(idMotorista);

        if(viagemList.isEmpty())
        {
            throw new IdNaoEncontradoException();
        }

        return viagemList;
    }

    public List<Viagem> consultaPorStatus(StatusViagem statusViagem)
    {
        List<Viagem> viagens = this.viagemRepository.findByStatus(statusViagem);

        if(viagens.isEmpty())
        {
            throw new NenhumCadastroException("Nenhuma viagem cadastrada com esse status");
        }

        return viagens;
    }
}
