package calculador.rescisao.model;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RescisaoRequest {

    @NotNull(message = "O empregado não pode ser nulo")
    private Empregado empregado;

    @NotNull(message = "O tipo de rescisão não pode ser nulo")
    private TipoRescisao tipoRescisao;

    @NotNull(message = "A data de desligamento não pode ser nula")
    private LocalDate dataDesligamento;

    @NotNull(message = "A data de Admissao não pode ser nula")
    private LocalDate dataAdmissao;

    private LocalDate dataAvisoPrevio;

    private Integer diasAvisoPrevio;
}