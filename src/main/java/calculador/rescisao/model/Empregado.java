
package calculador.rescisao.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class Empregado {
    private BigDecimal salario;
    private LocalDate dataAdmissao;
}