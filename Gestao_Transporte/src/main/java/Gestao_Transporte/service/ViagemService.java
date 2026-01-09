package Gestao_Transporte.service;

import Gestao_Transporte.Enum.veiculoEnum.StatusVeiculo;
import Gestao_Transporte.Enum.StatusViagem;
import Gestao_Transporte.core.exception.*;
import Gestao_Transporte.core.exception.Motorista.MotoristaIndisponivelException;
import Gestao_Transporte.core.exception.Viagem.KmInvalidoException;
import Gestao_Transporte.core.exception.Viagem.ViagemJaFinalizadaException;
import Gestao_Transporte.dto.viagem.IniciarViagemRequestDTO;
import Gestao_Transporte.dto.viagem.AgendarViagemRequestDTO;
import Gestao_Transporte.dto.viagem.ConsultasResponseDTO;
import Gestao_Transporte.dto.viagem.ViagemResponseDTO;
import Gestao_Transporte.entity.Motorista;
import Gestao_Transporte.entity.Veiculo;
import Gestao_Transporte.entity.Viagem;
import Gestao_Transporte.repository.MotoristaRepository;
import Gestao_Transporte.repository.VeiculoRespoitory;
import Gestao_Transporte.repository.ViagemRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ViagemService {

    private final ViagemRepository viagemRepository;
    private final MotoristaRepository motoristaRepository;
    private final VeiculoRespoitory veiculoRespoitory;
    private final MotoristaService motoristaService;

    @Transactional
    public ViagemResponseDTO agendarViagem(AgendarViagemRequestDTO dto, Long idMotorista, Long idVeiculo)
    {
        Motorista motoristaID = this.motoristaRepository.findById(idMotorista).orElseThrow(()->new IdNaoEncontradoException("ID de motorista não encontrado"));
        Veiculo veiculoID = this.veiculoRespoitory.findById(idVeiculo).orElseThrow(() -> new IdNaoEncontradoException("ID de veículo não encontrado"));

        if(dto.getDataChegadaPrevista().isBefore(dto.getDataSaida()))
        {
            throw new DataException("Data de chegada não pode ser anterior que a data de saída");
        }

        boolean motoristaAgendado = this.viagemRepository.existsByMotoristaIdAndStatusIn(motoristaID.getId(),
                List.of(StatusViagem.AGENDADA,StatusViagem.EM_ANDAMENTO));
        if(motoristaAgendado)
        {
            throw new MotoristaIndisponivelException("Motorista já foi alocado em uma viagem agendada");
        }

        motoristaService.validarViagens(idMotorista,veiculoID);

        Viagem viagem = dto.toViagem(motoristaID,veiculoID);
        viagem.setVeiculo(veiculoID);
        viagem.setMotorista(motoristaID);
        viagem.setStatus(StatusViagem.AGENDADA);

        this.viagemRepository.save(viagem);
        return ViagemResponseDTO.fromViagem(viagem);
    }

    @Transactional
    public ViagemResponseDTO iniciarViagem(IniciarViagemRequestDTO dto, Long idMotorista, Long idVeiculo)
    {
        Motorista motoristaID = this.motoristaRepository.findById(idMotorista).orElseThrow(()->new IdNaoEncontradoException("ID de motorista não encontrado"));
        Veiculo veiculoID = this.veiculoRespoitory.findById(idVeiculo).orElseThrow(() -> new IdNaoEncontradoException("ID de veículo não encontrado"));

        if(dto.getDataSaida().isAfter(dto.getDataChegadaPrevista()))
        {
            throw new DataException("Data de saída não pode ser maior que data de chegada prevista");
        }

        boolean motoristaEmViagem = this.viagemRepository.existsByMotoristaIdAndStatusIn(motoristaID.getId(),
                List.of(StatusViagem.EM_ANDAMENTO,StatusViagem.AGENDADA));
        if(motoristaEmViagem)
        {
            throw new MotoristaIndisponivelException("Motorista já está em uma viagem em andamento ou em uma viagem agendada");
        }

        motoristaService.validarViagens(idMotorista,veiculoID);

        Viagem viagem = dto.toViagem(motoristaID,veiculoID);
        viagem.setMotorista(motoristaID);
        viagem.setVeiculo(veiculoID);
        viagem.setStatus(StatusViagem.EM_ANDAMENTO);

        this.viagemRepository.save(viagem);

        return ViagemResponseDTO.fromViagem(viagem);
    }

    @Transactional
    public ViagemResponseDTO finalizarViagem(Long id, Double distanciaPercorrida)
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
            viagemID.setAtraso(atrasoMinutos);
        }

        Double totalKm = viagemID.getKmPercorrido()+distanciaPercorrida;
        viagemID.setKmPercorrido(totalKm);

        Veiculo veiculo = viagemID.getVeiculo();
        veiculo.setStatus(StatusVeiculo.DISPONIVEL);
        viagemID.setStatus(StatusViagem.FINALIZADA);

        this.veiculoRespoitory.save(veiculo);
        this.viagemRepository.save(viagemID);

        return ViagemResponseDTO.fromViagem(viagemID);
    }

    public List<ConsultasResponseDTO> listarTodas()
    {
        List<Viagem>viagemList = this.viagemRepository.findAll();
        if(viagemList.isEmpty())
        {
            throw new NenhumCadastroException("Nenhuma viagem cadastrada realizado");
        }

        return viagemList.stream().map(ConsultasResponseDTO::fromViagem).toList();
    }

    public ConsultasResponseDTO buscarID(Long id)
    {
        Viagem viagemID = this.viagemRepository.findById(id).orElseThrow(()->new IdNaoEncontradoException("Viagem não encontrada"));
        return ConsultasResponseDTO.fromViagem(viagemID);
    }

    public List<ConsultasResponseDTO> buscarVeiculo(Long idVeiculo)
    {
        List<Viagem> viagens=this.viagemRepository.findByVeiculoId(idVeiculo);

        if(viagens.isEmpty())
        {
            throw new IdNaoEncontradoException();
        }

        return viagens.stream().map(ConsultasResponseDTO::fromViagem).toList();
    }

    public List<ConsultasResponseDTO> buscarMotorista(Long idMotorista)
    {
        List<Viagem> viagens=this.viagemRepository.findByMotoristaId(idMotorista);

        if(viagens.isEmpty())
        {
            throw new IdNaoEncontradoException();
        }

        return viagens.stream().map(ConsultasResponseDTO::fromViagem).toList();
    }

    public List<ConsultasResponseDTO> consultaPorStatus(StatusViagem statusViagem)
    {
        List<Viagem> viagens = this.viagemRepository.findByStatus(statusViagem);

        if(viagens.isEmpty())
        {
            throw new NenhumCadastroException("Nenhuma viagem cadastrada com esse status");
        }

        return viagens.stream().map(ConsultasResponseDTO::fromViagem).toList();
    }

    public List<ConsultasResponseDTO> consultarDataEntreSaida(LocalDate inicio, LocalDate fim)
    {
        if(fim.isAfter(inicio))
        {
            throw new DataException();
        }

        LocalDateTime dataInicioFormatada = inicio.atStartOfDay();
        LocalDateTime dataFinalFormatada = fim.atTime(LocalTime.MAX);

        List<Viagem> viagens = this.viagemRepository.findByDataSaidaBetween(dataInicioFormatada,dataFinalFormatada);

        if(viagens.isEmpty())
        {
            throw new NenhumCadastroException("Nenhum cadastro realizado com essas datas");
        }

        return viagens.stream().map(ConsultasResponseDTO::fromViagem).toList();
    }

    public List<ConsultasResponseDTO> consultarDataEntreChegadaPrevista(LocalDate inicio, LocalDate fim)
    {
        if(fim.isAfter(inicio))
        {
            throw new DataException();
        }

        LocalDateTime dataInicioFormatada = inicio.atStartOfDay();
        LocalDateTime dataFinalFormatada = fim.atTime(LocalTime.MAX);

        List<Viagem> viagens = this.viagemRepository.findByDataChegadaPrevistaBetween(dataInicioFormatada,dataFinalFormatada);

        if(viagens.isEmpty())
        {
            throw new NenhumCadastroException("Nenhum cadastro realizado com essas datas");
        }

        return viagens.stream().map(ConsultasResponseDTO::fromViagem).toList();
    }

    public List<ConsultasResponseDTO> consultarDataEntreChegadaReal(LocalDate inicio, LocalDate fim)
    {
        if(fim.isAfter(inicio))
        {
            throw new DataException();
        }

        LocalDateTime dataInicioFormatada = inicio.atStartOfDay();
        LocalDateTime dataFinalFormata = fim.atTime(LocalTime.MAX);

        List<Viagem> viagens = this.viagemRepository.findByDataChegadaRealBetween(dataInicioFormatada,dataFinalFormata);

        if(viagens.isEmpty())
        {
            throw new NenhumCadastroException("Nenhum cadastro realizado com essas datas");
        }

        return viagens.stream().map(ConsultasResponseDTO::fromViagem).toList();
    }

    public void cancelar(Long id)
    {
        Viagem viagemID = this.viagemRepository.findById(id).orElseThrow(()->new IdNaoEncontradoException("A viagem não encontrada"));

        if(viagemID.getStatus() == StatusViagem.FINALIZADA)
        {
            throw new ViagemJaFinalizadaException();
        }

        viagemID.setStatus(StatusViagem.CANCELADA);
        this.viagemRepository.save(viagemID);
    }
}