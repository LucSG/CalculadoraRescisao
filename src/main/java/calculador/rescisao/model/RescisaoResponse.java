package calculador.rescisao.model;


import java.math.BigDecimal;

import lombok.Data;

@Data
public class RescisaoResponse {
    private BigDecimal saldoSalario;
    private BigDecimal decimoTerceiroProporcional;
    private BigDecimal feriasVencidas;
    private BigDecimal feriasProporcionais;
    private BigDecimal umTercoFerias;
    private BigDecimal avisoPrevioIndenizado;
    private BigDecimal totalBruto;
    private BigDecimal inss;
    private BigDecimal irrf;
    private BigDecimal totalLiquido;
}