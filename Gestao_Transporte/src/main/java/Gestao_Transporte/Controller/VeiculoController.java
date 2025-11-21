package Gestao_Transporte.Controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(name = "/veiculo")
@RequiredArgsConstructor
@Tag(name = "Veículo")
public class VeiculoController {
}
