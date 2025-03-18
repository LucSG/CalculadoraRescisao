package calculador.rescisao.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RescisaoRequest {

    @NotNull(message = "O Salário não pode ser nulo")
    private BigDecimal salario;

    @NotNull(message = "O tipo de rescisão não pode ser nulo")
    private TipoRescisao tipoRescisao;

    @NotNull(message = "A data de Admissao não pode ser nula")
    private LocalDate dataAdmissao;

    @NotNull(message = "A data de desligamento não pode ser nula")
    private LocalDate dataDesligamento;

    @NotNull(message = "Informe se o aviso previo é trabalhado ou indenizado")
    private boolean avisoPrevio;

    @NotNull(message = "O empregado tem ferias vencidas?")
    private boolean feriasVencidas;
}