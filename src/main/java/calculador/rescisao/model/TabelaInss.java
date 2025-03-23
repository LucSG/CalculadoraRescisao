package calculador.rescisao.model;


import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Table(name = "tabela_inss")
@Data
public class TabelaInss{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ano_vigencia", nullable = false) 
    private Integer anoVigencia;

    @Column(name = "faixa_inicio", nullable = false) 
    private BigDecimal faixaInicio;

    @Column(name = "faixa_fim", nullable = false)
    private BigDecimal faixaFim;

    @Column(name = "aliquota", nullable = false) 
    private BigDecimal aliquota;

    @Column(name = "valor_deduzir", nullable = false) 
    private BigDecimal valorDeduzir;
}