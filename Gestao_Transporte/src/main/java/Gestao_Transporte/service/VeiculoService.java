package Gestao_Transporte.service;

import Gestao_Transporte.Enum.veiculoEnum.StatusVeiculo;
import Gestao_Transporte.Enum.StatusViagem;
import Gestao_Transporte.core.exception.*;
import Gestao_Transporte.core.exception.veiculo.*;
import Gestao_Transporte.core.exception.viagem.ViagemAtivaOuAgendadaException;
import Gestao_Transporte.dto.veiculo.*;
import Gestao_Transporte.entity.Veiculo;
import Gestao_Transporte.repository.MotoristaRepository;
import Gestao_Transporte.repository.VeiculoRespoitory;
import Gestao_Transporte.repository.ViagemRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VeiculoService {

    private final VeiculoRespoitory veiculoRespoitory;
    private final ViagemRepository viagemRepository;
    private final MotoristaRepository motoristaRepository;

    @Transactional
    public VeiculoResponseDTO salvarVeiculo(VeiculoRequestDTO veiculoRequestDTO)
    {
        if(veiculoRespoitory.existsByPlaca(veiculoRequestDTO.getPlaca()))
        {
            throw new PlacaDuplicadaException();
        }

        Veiculo veiculoNew = veiculoRequestDTO.novoVeiculo();
        veiculoNew.setPlaca(limparPlaca(veiculoRequestDTO.getPlaca()));
        veiculoNew.setStatus(StatusVeiculo.DISPONIVEL);

        this.veiculoRespoitory.save(veiculoNew);

        return VeiculoResponseDTO.fromVeiculo(veiculoNew);
    }

    @Transactional
    public VeiculoResponseDTO atualizarVeiculo(Long id , VeiculoUpdateDTO veiculoUpdateDTO)
    {
        Veiculo veiculo = pesquisarID(id);

        if(veiculo.getStatus() == StatusVeiculo.EM_VIAGEM)
        {
            throw new VeiculoEmViagemException();
        }

        veiculoUpdateDTO.updateVeiculo(veiculo);
        this.veiculoRespoitory.save(veiculo);

        return VeiculoResponseDTO.fromVeiculo(veiculo);
    }

    public List<VeiculoResponseDTO> mostrarTodos()
    {
        List<Veiculo>veiculos=this.veiculoRespoitory.findAll();

        if(veiculos.isEmpty())
        {
            throw new NenhumCadastroException("Nenhum veículo cadastrado");
        }

        return veiculos.stream().map(VeiculoResponseDTO::fromVeiculo).toList();
    }

    public Veiculo procurarPorPlaca(String placa)
    {
        String placaLimpa = limparPlaca(placa);

        Veiculo veiculo = this.veiculoRespoitory.findByPlaca(placaLimpa);
        if (veiculo == null)
        {
            throw new PlacaInexistenteException();
        }
        return veiculo;
    }

    public List<VeiculoResponseStatusDTO> exibirPorStatus(StatusVeiculo statusVeiculo)
    {
        List<Veiculo> veiculo = this.veiculoRespoitory.findByStatus(statusVeiculo);

        if(veiculo.isEmpty())
        {
            throw new NenhumVeiculoComStatusException();
        }

        return veiculo.stream().map(VeiculoResponseStatusDTO::fromVeiculo).toList();
    }

    //metodo para colocar o veículo em manutenção
    public VeiculoResponseDTO colocarEmManutencao(Long id)
    {
        Veiculo veiculo = pesquisarID(id);

        if(veiculo.getStatus() == StatusVeiculo.EM_VIAGEM)
        {
            throw new VeiculoEmViagemException();
        }

        if(veiculo.getStatus() == StatusVeiculo.MANUTENCAO)
        {
            throw new EmManutencaoException("O veículo já está em manutenção");
        }

        veiculo.setStatus(StatusVeiculo.MANUTENCAO);
        this.veiculoRespoitory.save(veiculo);

        return VeiculoResponseDTO.fromVeiculo(veiculo);
    }

    public VeiculoResponseDTO retiradaManutencao(Long id)
    {
        Veiculo veiculo = pesquisarID(id);

        if(veiculo.getStatus() != StatusVeiculo.MANUTENCAO)
        {
            throw new EmManutencaoException();
        }

        veiculo.setStatus(StatusVeiculo.DISPONIVEL);
        this.veiculoRespoitory.save(veiculo);

        return VeiculoResponseDTO.fromVeiculo(veiculo);
    }

    @Transactional
    public VeiculoResponseDTO desativarVeiculo(Long id)
    {
        Veiculo veiculo = pesquisarID(id);

        boolean possuiViagensAtivasOuFinalizadas = this.viagemRepository.existsByVeiculoIdAndStatusIn(id,List.of(StatusViagem.AGENDADA,StatusViagem.EM_ANDAMENTO));
        if(possuiViagensAtivasOuFinalizadas)
        {
            throw new ViagemAtivaOuAgendadaException();
        }

        veiculo.setStatus(StatusVeiculo.INDISPONIVEL);
        this.veiculoRespoitory.save(veiculo);

        return VeiculoResponseDTO.fromVeiculo(veiculo);
    }

    // -------- METODOS AUXILIARES --------

    //Função responsável por realizar busca por ID
    public Veiculo pesquisarID(Long id)
    {
        return this.veiculoRespoitory.findById(id).orElseThrow(() -> new IdNaoEncontradoException("ID de veículo não encontrado"));
    }

    //Função de formatação de formato de placa(remove espaços e caracteres especiais)
    private String limparPlaca(String placa)
    {
        return placa.replaceAll("[^A-Za-z0-9]","").toUpperCase();
    }

}