package calculador.rescisao.config;

import calculador.rescisao.model.TabelaInss;
import calculador.rescisao.model.TabelaIrrf;
import calculador.rescisao.repository.TabelaInssRepository;
import calculador.rescisao.repository.TabelaIrrfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    private TabelaInssRepository tabelaInssRepository;

    @Autowired
    private TabelaIrrfRepository tabelaIrrfRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //verificar se a tabela INSS esta vazia
        if (tabelaInssRepository.count() == 0) {
            //inserir faixas da tabela INSS
            List<TabelaInss> tabelasInss = Arrays.asList(
                    criarTabelaInss(2024, new BigDecimal("0.00"), new BigDecimal("1412.00"), new BigDecimal("7.5"), new BigDecimal("0.00")),
                    criarTabelaInss(2024, new BigDecimal("1412.01"), new BigDecimal("2666.68"), new BigDecimal("9.0"), new BigDecimal("21.18")),
                    criarTabelaInss(2024, new BigDecimal("2666.69"), new BigDecimal("4000.03"), new BigDecimal("12.0"), new BigDecimal("91.75")),
                    criarTabelaInss(2024, new BigDecimal("4000.04"), new BigDecimal("7786.02"), new BigDecimal("14.0"), new BigDecimal("171.71"))
            );
            tabelaInssRepository.saveAll(tabelasInss);
        }

        //verificar se a tabela IRRF esta vazia
        if (tabelaIrrfRepository.count() == 0) {
            //inserir faixas da tabela IRRF
            List<TabelaIrrf> tabelasIrrf = Arrays.asList(
                    criarTabelaIrrf(2024, new BigDecimal("0.00"), new BigDecimal("2259.20"), new BigDecimal("0.0"), new BigDecimal("0.00")),
                    criarTabelaIrrf(2024, new BigDecimal("2259.21"), new BigDecimal("2826.65"), new BigDecimal("7.5"), new BigDecimal("169.44")),
                    criarTabelaIrrf(2024, new BigDecimal("2826.66"), new BigDecimal("3751.05"), new BigDecimal("15.0"), new BigDecimal("381.44")),
                    criarTabelaIrrf(2024, new BigDecimal("3751.06"), new BigDecimal("4664.68"), new BigDecimal("22.5"), new BigDecimal("662.77")),
                    criarTabelaIrrf(2024, new BigDecimal("4664.69"), new BigDecimal("99999999999999.00"), new BigDecimal("27.5"), new BigDecimal("896.00"))
            );
            tabelaIrrfRepository.saveAll(tabelasIrrf);
        }
    }

    private TabelaInss criarTabelaInss(Integer anoVigencia, BigDecimal faixaInicio, BigDecimal faixaFim, BigDecimal aliquota, BigDecimal valorDeduzir) {
        TabelaInss tabelaInss = new TabelaInss();
        tabelaInss.setAnoVigencia(anoVigencia);
        tabelaInss.setFaixaInicio(faixaInicio);
        tabelaInss.setFaixaFim(faixaFim);
        tabelaInss.setAliquota(aliquota);
        tabelaInss.setValorDeduzir(valorDeduzir);
        return tabelaInss;
    }

    private TabelaIrrf criarTabelaIrrf(Integer anoVigencia, BigDecimal faixaInicio, BigDecimal faixaFim, BigDecimal aliquota, BigDecimal valorDeduzir) {
        TabelaIrrf tabelaIrrf = new TabelaIrrf();
        tabelaIrrf.setAnoVigencia(anoVigencia);
        tabelaIrrf.setFaixaInicio(faixaInicio);
        tabelaIrrf.setFaixaFim(faixaFim);
        tabelaIrrf.setAliquota(aliquota);
        tabelaIrrf.setValorDeduzir(valorDeduzir);
        return tabelaIrrf;
    }
}