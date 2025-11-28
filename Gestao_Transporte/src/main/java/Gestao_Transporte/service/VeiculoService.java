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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VeiculoService {

    private final VeiculoRespoitory veiculoRespoitory;
    private final MotoristaRepository motoristaRepository;

    public Veiculo salvarVeiculo(VeiculoRequestDTO veiculoRequestDTO)
    {
        if(veiculoRespoitory.existsByPlaca(veiculoRequestDTO.getPlaca()))
        {
            throw new PlacaDuplicadaException();
        }

        Veiculo veiculoNew = veiculoRequestDTO.Veiculo();
        veiculoNew.setPlaca(limparPlaca(veiculoRequestDTO.getPlaca()));
        veiculoNew.setStatus(StatusVeiculo.DISPONIVEL);

        return this.veiculoRespoitory.save(veiculoNew);
    }

    public VeiculoResponseDTO vincularVeiculo(VeiculoRequestDTO veiculoRequestDTO, Long id)
    {
        if(veiculoRespoitory.existsByPlaca(veiculoRequestDTO.getPlaca()))
        {
            throw new PlacaDuplicadaException();
        }

        Motorista motoristaSalvar = this.motoristaRepository.findById(id).orElseThrow(IdNaoEncontradoException::new);

        Veiculo veiculoSalvar = veiculoRequestDTO.toVeiculo(motoristaSalvar);

        veiculoSalvar.setStatus(StatusVeiculo.DISPONIVEL);
        veiculoSalvar.setPlaca(limparPlaca(veiculoRequestDTO.getPlaca()));

        motoristaSalvar.getVeiculos().add(veiculoSalvar);

        this.veiculoRespoitory.save(veiculoSalvar);

        return VeiculoResponseDTO.fromVeiculo(veiculoSalvar);
    }

    public Veiculo atualizarVeiculo(Long id , VeiculoUpdateDTO veiculoUpdateDTO)
    {
        Veiculo veiculo = this.veiculoRespoitory.findById(id).orElseThrow(IdNaoEncontradoException::new);

        if(veiculo.getStatus() == StatusVeiculo.EM_VIAGEM)
        {
            throw new VeiculoEmViagemException();
        }

        veiculoUpdateDTO.updateVeiculo(veiculo);
        return this.veiculoRespoitory.save(veiculo);
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
        limparPlaca(placa);

        Veiculo veiculo = this.veiculoRespoitory.findByPlaca(placa);
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
            throw new StatusVazioException();
        }

        return veiculo.stream().map(VeiculoResponseStatusDTO::fromVeiculo).toList();
    }

    //Função de formatação de formato de placa(remove espaços e caracteres especiais)
    private String limparPlaca(String placa)
    {
        return placa.replaceAll("[^A-Za-z0-9]","").toUpperCase();
    }

}
