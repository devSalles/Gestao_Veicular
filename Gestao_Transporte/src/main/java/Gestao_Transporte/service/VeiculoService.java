package Gestao_Transporte.service;

import Gestao_Transporte.Enum.StatusVeiculo;
import Gestao_Transporte.core.exception.*;
import Gestao_Transporte.dto.veiculo.VeiculoRequestDTO;
import Gestao_Transporte.dto.veiculo.VeiculoResponseDTO;
import Gestao_Transporte.dto.veiculo.VeiculoResponseStatusDTO;
import Gestao_Transporte.dto.veiculo.VeiculoUpdateDTO;
import Gestao_Transporte.entity.Motorista;
import Gestao_Transporte.entity.Veiculo;
import Gestao_Transporte.repository.MotoristaRepository;
import Gestao_Transporte.repository.VeiculoRespoitory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VeiculoService {

    private final VeiculoRespoitory veiculoRespoitory;
    private final MotoristaRepository motoristaRepository;

    @Transactional
    public Veiculo salvarVeiculo(VeiculoRequestDTO veiculoRequestDTO)
    {
        if(veiculoRespoitory.existsByPlaca(veiculoRequestDTO.getPlaca()))
        {
            throw new PlacaDuplicadaException();
        }

        Veiculo veiculoNew = veiculoRequestDTO.novoVeiculo();
        veiculoNew.setPlaca(limparPlaca(veiculoRequestDTO.getPlaca()));
        veiculoNew.setStatus(StatusVeiculo.DISPONIVEL);

        return this.veiculoRespoitory.save(veiculoNew);
    }

    @Transactional
    public Veiculo atualizarVeiculo(Long id , VeiculoUpdateDTO veiculoUpdateDTO)
    {
        Veiculo veiculo = pesquisarID(id);

        if(veiculo.getStatus() == StatusVeiculo.EM_VIAGEM)
        {
            throw new VeiculoEmViagemException();
        }

        veiculoUpdateDTO.updateVeiculo(veiculo);
        return this.veiculoRespoitory.save(veiculo);
    }

    //Metodo para vincular motorista a um veiculo
    @Transactional
    public VeiculoResponseDTO vincularVeiculo(Long idVeiculo, Long idMotorista)
    {
        Veiculo veiculoVinc = pesquisarID(idVeiculo);
        Motorista motoristaVincular = this.motoristaRepository.findById(idMotorista).orElseThrow(()->new  IdNaoEncontradoException("ID de motorista não encontrado"));

        motoristaVincular.getVeiculos().add(veiculoVinc);
        veiculoVinc.getMotoristas().add(motoristaVincular);

        this.veiculoRespoitory.save(veiculoVinc);
        return VeiculoResponseDTO.fromVeiculo(veiculoVinc);
    }

    public List<VeiculoResponseDTO> mostrarTodos()
    {
        List<Veiculo>veiculos=this.veiculoRespoitory.findAll();

        if(veiculos.isEmpty())
        {
            throw new RuntimeException("Nenhum veículo cadastrado");
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
    public Veiculo colocarEmManutencao(Long id)
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
        return this.veiculoRespoitory.save(veiculo);
    }

    public Veiculo retiradaManutencao(Long id)
    {
        Veiculo veiculo = pesquisarID(id);

        if(veiculo.getStatus() != StatusVeiculo.MANUTENCAO)
        {
            throw new EmManutencaoException();
        }

        veiculo.setStatus(StatusVeiculo.DISPONIVEL);
        return this.veiculoRespoitory.save(veiculo);
    }

    public Veiculo iniciarViagem(Long idVeiculo, Long idMotorista)
    {
        Veiculo veiculo = pesquisarID(idVeiculo);
        Motorista motorista = this.motoristaRepository.findById(idMotorista).orElseThrow(() -> new IdNaoEncontradoException("ID de motorista não encontrado"));

        if(veiculo.getStatus()==StatusVeiculo.EM_VIAGEM)
        {
            throw new VeiculoEmViagemException("O veículo já está em viagem");
        }

        if(veiculo.getStatus() != StatusVeiculo.DISPONIVEL)
        {
            throw new VeiculoIndisponivelException();
        }

        if(!motorista.getCategoria().isCompativelCom(veiculo.getTipoVeiculo()))
        {
            throw new CnhIncompativelException();
        }

        veiculo.setStatus(StatusVeiculo.EM_VIAGEM);
        return this.veiculoRespoitory.save(veiculo);
    }

    public Veiculo finalizarViagem(Long id,boolean enviarParaManutencao)
    {
        Veiculo veiculo = pesquisarID(id);

        if(veiculo.getStatus()==StatusVeiculo.DISPONIVEL)
        {
            throw new VeiculoDisponivelException();
        }

        if(enviarParaManutencao)
        {
            veiculo.setStatus(StatusVeiculo.MANUTENCAO);
        }
        else
        {
            veiculo.setStatus(StatusVeiculo.DISPONIVEL);
        }

        return this.veiculoRespoitory.save(veiculo);
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