package calculador.rescisao.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class RescisaoResponse {

    //Salario
    private BigDecimal saldoSalario;
    private BigDecimal inssSalario;
    private BigDecimal irrfSalario;

    //13°
    private BigDecimal decimoTerceiroProporcional;
    private BigDecimal inss13;
    private BigDecimal irrf13;

    //ferias
    private BigDecimal feriasVencidas;
    private BigDecimal umTercoFerias;
    private BigDecimal feriasProporcionais;
    private BigDecimal umTercoFeriasProporcionais;

    //Outros
    private BigDecimal avisoPrevioIndenizado;
    //total
    private BigDecimal totalBruto;
    private BigDecimal totalLiquido;
}
