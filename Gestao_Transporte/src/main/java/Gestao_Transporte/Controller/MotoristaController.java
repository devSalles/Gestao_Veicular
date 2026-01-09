package Gestao_Transporte.Controller;

import Gestao_Transporte.dto.motorista.MotoristaRequestDTO;
import Gestao_Transporte.dto.motorista.MotoristaUpdateDTO;
import Gestao_Transporte.service.MotoristaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "/motorista")
@RequiredArgsConstructor
@Tag(name = "Motorista")
public class MotoristaController {

    private final MotoristaService motoristaService;

    @PostMapping("/salvar-motorista")
    public ResponseEntity<?> salvarMotorista(@Valid @RequestBody MotoristaRequestDTO motoristaRequestDTO)
    {
        return ResponseEntity.ok(this.motoristaService.salvarMotorista(motoristaRequestDTO));
    }

    @PostMapping("/vincular-motorista-veiculo/{idMotorista}/{idVeiculo}")
    public ResponseEntity<?> vincularMotorista(@PathVariable Long idMotorista, @PathVariable Long idVeiculo)
    {
        return ResponseEntity.ok(this.motoristaService.vincularVeiculo(idMotorista,idVeiculo));
    }

    @PutMapping("/atualizar-motorista/{id}")
    public ResponseEntity<?> atualizarMotorista(@PathVariable Long id,@Valid @RequestBody MotoristaUpdateDTO dto)
    {
        return ResponseEntity.ok(this.motoristaService.atualizarMotorista(id,dto));
    }

    @GetMapping("/listar-todos")
    public ResponseEntity<?> listarTodos()
    {
        return ResponseEntity.ok(this.motoristaService.listarTodos());
    }

    @GetMapping("/exibir-por-id/{id}")
    public ResponseEntity<?> exibirPorId(@PathVariable Long id)
    {
        return ResponseEntity.ok(this.motoristaService.exibirPorID(id));
    }

    @GetMapping("/exibir-CPF/{cpf}")
    public ResponseEntity<?> listarCPF(@PathVariable String cpf)
    {
        return ResponseEntity.ok(this.motoristaService.exibirPorCPF(cpf));
    }

    @DeleteMapping("/excluir/{idMotorista}")
    public ResponseEntity<?> excluirMotorista(@PathVariable Long idMotorista)
    {
        this.motoristaService.desativarMotorista(idMotorista);
        return ResponseEntity.noContent().build();
    }
}
