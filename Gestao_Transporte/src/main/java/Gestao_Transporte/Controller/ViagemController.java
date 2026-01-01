package Gestao_Transporte.Controller;

import Gestao_Transporte.Enum.StatusViagem;
import Gestao_Transporte.dto.viagem.FinalizarViagemDTO;
import Gestao_Transporte.dto.viagem.ViagemRequestDTO;
import Gestao_Transporte.service.ViagemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/viagem")
@RequiredArgsConstructor
@Tag(name = "Viagem")
public class ViagemController {

    private final ViagemService viagemService;

    @PostMapping("/agendar-viagem/motorista-id/{idMotorista}/veiculo-id/{idVeiculo}")
    public ResponseEntity<?> agendarViagem(@RequestBody ViagemRequestDTO dto, @PathVariable Long idMotorista, @PathVariable Long idVeiculo)
    {
        return ResponseEntity.ok(this.viagemService.agendarViagem(dto,idMotorista,idVeiculo));
    }

    @PostMapping("/iniciar-viagem/motorista-id/{idMotorista}/veiculo-id/{idVeiculo}")
    public ResponseEntity<?> iniciarViagem(@RequestBody ViagemRequestDTO dto, @PathVariable Long idMotorista, @PathVariable Long idVeiculo)
    {
        return ResponseEntity.ok(this.viagemService.iniciarViagem(dto,idMotorista,idVeiculo));
    }

    @PutMapping("/finalizar-viagem/{idViagem}")
    public ResponseEntity<?> finalizarViagem(@PathVariable Long idViagem, @RequestBody FinalizarViagemDTO viagemDTO)
    {
        this.viagemService.finalizarViagem(idViagem,viagemDTO.kmPercorrido());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/cancelar-viagem/{idViagem}")
    public ResponseEntity<?> cancelarViagem(@PathVariable Long idViagem)
    {
        return ResponseEntity.ok(this.viagemService.cancelar(idViagem));
    }

    @GetMapping("/consultar-por-status/{statusViagem}")
    public ResponseEntity<?> consultarViagemStatus(@PathVariable StatusViagem statusViagem)
    {
        return ResponseEntity.ok(this.viagemService.consultaPorStatus(statusViagem));
    }

    @GetMapping("/listar-viagens")
    public ResponseEntity<?> listarViagens()
    {
        return ResponseEntity.ok(this.viagemService.listarTodas());
    }

    @GetMapping("/buscar-id/{idViagem}")
    public ResponseEntity<?> buscarID(@PathVariable Long idViagem)
    {
        return ResponseEntity.ok(this.viagemService.buscarID(idViagem));
    }

    @GetMapping("/buscar-veiculo/{idVeiculo}")
    public ResponseEntity<?> buscarVeiculo(@PathVariable Long idVeiculo)
    {
        return ResponseEntity.ok(this.viagemService.buscarVeiculo(idVeiculo));
    }

    @GetMapping("/buscar-motorista/{idMotorista}")
    public ResponseEntity<?> buscarMotorista(@PathVariable Long idMotorista)
    {
        return ResponseEntity.ok(this.viagemService.buscarMotorista(idMotorista));
    }
}
