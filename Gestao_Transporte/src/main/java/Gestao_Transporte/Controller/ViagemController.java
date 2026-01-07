package Gestao_Transporte.Controller;

import Gestao_Transporte.Enum.StatusViagem;
import Gestao_Transporte.dto.viagem.FinalizarViagemDTO;
import Gestao_Transporte.dto.viagem.IniciarViagemRequestDTO;
import Gestao_Transporte.dto.viagem.AgendarViagemRequestDTO;
import Gestao_Transporte.service.ViagemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@RestController
@RequestMapping("/viagem")
@RequiredArgsConstructor
@Tag(name = "Viagem")
public class ViagemController {

    private final ViagemService viagemService;

    @PostMapping("/agendar-viagem/motorista-id/{idMotorista}/veiculo-id/{idVeiculo}")
    public ResponseEntity<?> agendarViagem(@RequestBody AgendarViagemRequestDTO dto, @PathVariable Long idMotorista, @PathVariable Long idVeiculo)
    {
        return ResponseEntity.ok(this.viagemService.agendarViagem(dto,idMotorista,idVeiculo));
    }

    @PostMapping("/iniciar-viagem/motorista-id/{idMotorista}/veiculo-id/{idVeiculo}")
    public ResponseEntity<?> iniciarViagem(@RequestBody IniciarViagemRequestDTO dto, @PathVariable Long idMotorista, @PathVariable Long idVeiculo)
    {
        return ResponseEntity.ok(this.viagemService.iniciarViagem(dto,idMotorista,idVeiculo));
    }

    @PutMapping("/finalizar-viagem/{idViagem}")
    public ResponseEntity<?> finalizarViagem(@PathVariable Long idViagem, @RequestBody FinalizarViagemDTO viagemDTO)
    {
        return ResponseEntity.ok(this.viagemService.finalizarViagem(idViagem,viagemDTO.kmPercorrido()));
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

    @GetMapping("/consulta-periodo-por-data-saida")
    public ResponseEntity<?> consultarDataSaida(
            @RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam("fim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim
    )
    {
        return ResponseEntity.ok(this.viagemService.consultarDataEntreSaida(inicio,fim));
    }

    @GetMapping("/consulta-periodo-data-chegada-prevista")
    public ResponseEntity<?> consultaChegadaPrevista(
            @RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam("fim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim
    )
    {
        return ResponseEntity.ok(this.viagemService.consultarDataEntreChegadaPrevista(inicio,fim));
    }

    @GetMapping("/consulta-periodo-data-chegada-real")
    public ResponseEntity<?> consultaChegadaReal(
            @RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam("fim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim
    )
    {
        return ResponseEntity.ok(this.viagemService.consultarDataEntreChegadaReal(inicio,fim));
    }

    @DeleteMapping("/cancelar-viagem/{idViagem}")
    public ResponseEntity<?> cancelarViagem(@PathVariable Long idViagem)
    {
        this.viagemService.cancelar(idViagem);
        return ResponseEntity.noContent().build();
    }
}
