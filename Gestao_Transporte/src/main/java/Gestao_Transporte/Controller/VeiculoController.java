package Gestao_Transporte.Controller;

import Gestao_Transporte.Enum.StatusVeiculo;
import Gestao_Transporte.dto.veiculo.VeiculoRequestDTO;
import Gestao_Transporte.dto.veiculo.VeiculoUpdateDTO;
import Gestao_Transporte.service.VeiculoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/veiculo")
@RequiredArgsConstructor
@Tag(name = "Veículo")
public class VeiculoController {

    private final VeiculoService veiculoService;

    @PostMapping("/salvar-veiculo")
    public ResponseEntity<?> salvar(@Valid @RequestBody VeiculoRequestDTO veiculoRequestDTO)
    {
        return ResponseEntity.ok(this.veiculoService.salvarVeiculo(veiculoRequestDTO));
    }

    @PostMapping("/vincular-veiculo/{idVeiculo}/motorista/{idMotorista}")
    public ResponseEntity<?> vincularVeiculo(@PathVariable Long idVeiculo,@PathVariable Long idMotorista)
    {
        return ResponseEntity.ok(this.veiculoService.vincularVeiculo(idVeiculo,idMotorista));
    }

    @PostMapping("/iniciar-viagem/id-veiculo/{idVeiculo}/id-motorista/{idMotorista}")
    public ResponseEntity<?> iniciarViagem(@PathVariable Long idVeiculo, @PathVariable Long idMotorista)
    {
        return ResponseEntity.ok(this.veiculoService.iniciarViagem(idVeiculo,idMotorista));
    }

    @PostMapping("/finalizar-viagem/{id}")
    public ResponseEntity<?> finalizarViagem(@PathVariable Long id, @RequestParam(defaultValue = "false") boolean enviarParaManutencao)
    {
        return ResponseEntity.ok(this.veiculoService.finalizarViagem(id,enviarParaManutencao));
    }

    @PutMapping("/atualizar-veiculo/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @Valid @RequestBody VeiculoUpdateDTO veiculoUpdateDTO)
    {
        return ResponseEntity.ok(this.veiculoService.atualizarVeiculo(id,veiculoUpdateDTO));
    }

    @PatchMapping("/colocar-em-manutencao/{id}")
    public ResponseEntity<?> colocarManutencao(@PathVariable Long id)
    {
        return ResponseEntity.ok(this.veiculoService.colocarEmManutencao(id));
    }

    @PatchMapping("/retirar-da-manutencao/{id}")
    public ResponseEntity<?> retiradaManutencao(@PathVariable Long id)
    {
        return ResponseEntity.ok(this.veiculoService.retiradaManutencao(id));
    }

    @GetMapping("/mostar-todos")
    public ResponseEntity<?> exibirTodos()
    {
        return ResponseEntity.ok(this.veiculoService.mostrarTodos());
    }

    @GetMapping("/exibir-por-placa/{placa}")
    public ResponseEntity<?> exibirPorPlaca(@PathVariable String placa)
    {
        return ResponseEntity.ok(this.veiculoService.procurarPorPlaca(placa));
    }

    @GetMapping("/exibir-por-status/{statusVeiculo}")
    public ResponseEntity<?> exibirPorStatus(@PathVariable StatusVeiculo statusVeiculo)
    {
        return ResponseEntity.ok(this.veiculoService.exibirPorStatus(statusVeiculo));
    }

    @DeleteMapping("/desativar-veiculo/{idMotorista}")
    public ResponseEntity<?> desativarVeiculo(@PathVariable Long idMotorista)
    {
        this.veiculoService.desativarVeiculo(idMotorista);
        return ResponseEntity.noContent().build();
    }
}
