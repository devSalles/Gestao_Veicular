package Gestao_Transporte.service;

import Gestao_Transporte.Enum.motoristaEnum.StatusMotorista;
import Gestao_Transporte.Enum.veiculoEnum.StatusVeiculo;
import Gestao_Transporte.Enum.StatusViagem;
import Gestao_Transporte.core.exception.*;
import Gestao_Transporte.core.exception.motorista.MotoristaIndisponivelException;
import Gestao_Transporte.core.exception.motorista.VeiculoNaoVinculadoException;
import Gestao_Transporte.core.exception.veiculo.VeiculoIndisponivelException;
import Gestao_Transporte.core.exception.viagem.*;
import Gestao_Transporte.dto.viagem.AgendarViagemRequestDTO;
import Gestao_Transporte.dto.viagem.ViagemResponseDTO;
import Gestao_Transporte.entity.Motorista;
import Gestao_Transporte.entity.Veiculo;
import Gestao_Transporte.entity.Viagem;
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
import java.util.function.BiFunction;

@Service
@RequiredArgsConstructor
public class ViagemService {

    private final ViagemRepository viagemRepository;
    private final VeiculoRespoitory veiculoRespoitory;
    private final MotoristaService motoristaService;

    @Transactional
    public ViagemResponseDTO agendarViagem(AgendarViagemRequestDTO dto)
    {
        Motorista motoristaID = this.motoristaService.buscarID(dto.idMotorista());
        Veiculo veiculoID = this.veiculoRespoitory.findById(dto.idVeiculo()).orElseThrow(() -> new IdNaoEncontradoException("ID de veículo não encontrado"));

        if(!motoristaID.getVeiculos().contains(veiculoID))
        {
            throw new VeiculoNaoVinculadoException();
        }

        validarDataHora(dto);

        boolean motoristaOcupado = this.viagemRepository.existsByMotoristaIdAndStatusIn(motoristaID.getId(),
                List.of(StatusViagem.AGENDADA,StatusViagem.EM_ANDAMENTO));
        if(motoristaOcupado)
        {
            throw new MotoristaIndisponivelException("Motorista já possui uma viagem agendada ou em andamento");
        }

        boolean veiculoOcupado = this.viagemRepository.existsByVeiculoIdAndStatusIn(veiculoID.getId(),List.of(StatusViagem.EM_ANDAMENTO,StatusViagem.AGENDADA));
        if(veiculoOcupado)
        {
            throw new VeiculoIndisponivelException("Veículo Já está em uma viagem ou já possui uma viagem agendada");
        }

        if(!veiculoID.getStatus().equals(StatusVeiculo.DISPONIVEL))
        {
            throw new VeiculoIndisponivelException();
        }

        // Valida se o motorista está ativo e se a CNH é compatível com o veículo
        motoristaService.validarViagens(dto.idMotorista(),veiculoID);

        Viagem viagem = dto.toViagem(motoristaID,veiculoID);
        viagem.setStatus(StatusViagem.AGENDADA);

        this.viagemRepository.save(viagem);
        return ViagemResponseDTO.fromViagem(viagem);
    }

    @Transactional
    public ViagemResponseDTO iniciarViagem(Long idViagem)
    {
        Viagem viagem = this.viagemRepository.findById(idViagem).orElseThrow(()-> new IdNaoEncontradoException("Id de viagem não encontrado"));

        Motorista motorista = viagem.getMotorista();
        Veiculo veiculo = viagem.getVeiculo();

        if(!motorista.getVeiculos().contains(veiculo))
        {
            throw new VeiculoNaoVinculadoException();
        }

        if(!viagem.getStatus().equals(StatusViagem.AGENDADA))
        {
            throw new StatusViagemException("A viagem precisa estar AGENDADA para iniciar");
        }

        //Metodo responsável por validar viagens
        validarViagem(viagem);

        if(!motorista.getStatusMotorista().equals(StatusMotorista.ATIVO))
        {
            throw new MotoristaIndisponivelException();
        }

        if(!veiculo.getStatus().equals(StatusVeiculo.DISPONIVEL))
        {
            throw new VeiculoIndisponivelException();
        }

        viagem.setStatus(StatusViagem.EM_ANDAMENTO);
        viagem.setDataSaida(LocalDateTime.now());

        veiculo.setStatus(StatusVeiculo.EM_VIAGEM);

        this.veiculoRespoitory.save(veiculo);
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

        if(viagemID.getStatus() != StatusViagem.EM_ANDAMENTO)
        {
            throw new ViagemEmAndamentoException();
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

        viagemID.setKmPercorrido(0.0);
        Double totalKm = viagemID.getKmPercorrido()+distanciaPercorrida;
        viagemID.setKmPercorrido(totalKm);

        Veiculo veiculo = viagemID.getVeiculo();
        veiculo.setStatus(StatusVeiculo.DISPONIVEL);
        viagemID.setStatus(StatusViagem.FINALIZADA);

        this.veiculoRespoitory.save(veiculo);
        this.viagemRepository.save(viagemID);

        return ViagemResponseDTO.fromViagem(viagemID);
    }

    public List<ViagemResponseDTO> listarTodas()
    {
        List<Viagem>viagemList = this.viagemRepository.findAll();
        if(viagemList.isEmpty())
        {
            throw new NenhumCadastroException("Nenhuma cadastrado de viagem  realizado");
        }

        return viagemList.stream().map(ViagemResponseDTO::fromViagem).toList();
    }

    public ViagemResponseDTO buscarID(Long id)
    {
        Viagem viagemID = this.viagemRepository.findById(id).orElseThrow(()->new IdNaoEncontradoException("Viagem não encontrada"));
        return ViagemResponseDTO.fromViagem(viagemID);
    }

    public List<ViagemResponseDTO> buscarVeiculo(Long idVeiculo)
    {
        List<Viagem> viagens=this.viagemRepository.findByVeiculoId(idVeiculo);

        if(viagens.isEmpty())
        {
            throw new NenhumCadastroException("ID de veículo não encontrado");
        }

        return viagens.stream().map(ViagemResponseDTO::fromViagem).toList();
    }

    public List<ViagemResponseDTO> buscarMotorista(Long idMotorista)
    {
        List<Viagem> viagens=this.viagemRepository.findByMotoristaId(idMotorista);

        if(viagens.isEmpty())
        {
            throw new NenhumCadastroException("ID de motorista não encontrado");
        }

        return viagens.stream().map(ViagemResponseDTO::fromViagem).toList();
    }

    public List<ViagemResponseDTO> consultaPorStatus(StatusViagem statusViagem)
    {
        List<Viagem> viagens = this.viagemRepository.findByStatus(statusViagem);

        if(viagens.isEmpty())
        {
            throw new NenhumCadastroException("Nenhuma viagem cadastrada com esse status");
        }

        return viagens.stream().map(ViagemResponseDTO::fromViagem).toList();
    }

    public List<ViagemResponseDTO> consultarDataEntreSaida(LocalDate inicio, LocalDate fim)
    {
        return consultaEntreDatas(inicio,fim,viagemRepository::findByDataSaidaBetween);
    }

    public List<ViagemResponseDTO> consultarDataEntreChegadaPrevista(LocalDate inicio, LocalDate fim)
    {
        return consultaEntreDatas(inicio,fim,viagemRepository::findByDataChegadaPrevistaBetween);
    }

    public List<ViagemResponseDTO> consultarDataEntreChegadaReal(LocalDate inicio, LocalDate fim)
    {
        return consultaEntreDatas(inicio,fim,viagemRepository::findByDataChegadaRealBetween);
    }

    @Transactional
    public ViagemResponseDTO cancelar(Long id)
    {
        Viagem viagemID = this.viagemRepository.findById(id).orElseThrow(()->new IdNaoEncontradoException("ID de viagem não encontrada"));

        if(viagemID.getStatus() == StatusViagem.FINALIZADA)
        {
            throw new ViagemJaFinalizadaException();
        }

        if(viagemID.getStatus() == StatusViagem.EM_ANDAMENTO)
        {
            Veiculo veiculo = viagemID.getVeiculo();
            veiculo.setStatus(StatusVeiculo.DISPONIVEL);
            this.veiculoRespoitory.save(veiculo);
        }

        viagemID.setStatus(StatusViagem.CANCELADA);
        this.viagemRepository.save(viagemID);

        return ViagemResponseDTO.fromViagem(viagemID);
    }

    //------------ METODO AUXILIAR ------------

    private void validarViagem(Viagem viagem)
    {
        switch (viagem.getStatus())
        {
            case AGENDADA:
                return;

            case FINALIZADA:
                throw new ViagemJaFinalizadaException("Viagem finalizada não pode ser iniciada");

            case CANCELADA:
                throw new ViagemCanceladaException();

            case EM_ANDAMENTO:
                throw new ViagemEmAndamentoException("A viagem já está em andamento");
        }
    }

    private void validarDataHora(AgendarViagemRequestDTO dto)
    {
        if(dto.dataSaida().isBefore(LocalDateTime.now()))
        {
            throw new DataException("Data de saída não pode ser no passado");
        }

        if(dto.dataChegadaPrevista().isBefore(dto.dataSaida()))
        {
            throw new DataException("Data de chegada não pode ser anterior que a data de saída");
        }
    }

    //Metodo responsável por realizar consulta entre datas
    private List<ViagemResponseDTO> consultaEntreDatas(LocalDate inicio, LocalDate fim, BiFunction<LocalDateTime,LocalDateTime,List<Viagem>> consultas)
    {
        if(fim.isBefore(inicio))
        {
            throw new DataException();
        }

        LocalDateTime inicioFormatado = inicio.atStartOfDay();
        LocalDateTime fimFormatado = fim.atTime(LocalTime.MAX);

        //Usado para realizar a consulta e retornar o resultado
        List<Viagem> viagens = consultas.apply(inicioFormatado,fimFormatado);

        if(viagens.isEmpty())
        {
            throw new NenhumCadastroException("Nenhum cadastro realizado com essas datas");
        }

        return viagens.stream().map(ViagemResponseDTO::fromViagem).toList();
    }
}