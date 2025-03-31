package calculador.rescisao.config;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import calculador.rescisao.model.TabelaInss;
import calculador.rescisao.model.TabelaIrrf;
import calculador.rescisao.repository.TabelaInssRepository;
import calculador.rescisao.repository.TabelaIrrfRepository;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    private TabelaInssRepository tabelaInssRepository;

    @Autowired
    private TabelaIrrfRepository tabelaIrrfRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        carregarTabelasInss();
        carregarTabelasIrrf();
    }

    private void carregarTabelasInss() {
        if (tabelaInssRepository.count() == 0) {
            List<TabelaInss> tabelasInss = new ArrayList<>();

            tabelasInss.addAll(criarTabelasInssAno(2022,
                    new BigDecimal("0.00"), new BigDecimal("1212.00"), new BigDecimal("7.5"), new BigDecimal("0.00"),
                    new BigDecimal("1212.01"), new BigDecimal("2427.35"), new BigDecimal("9.0"), new BigDecimal("18.18"),
                    new BigDecimal("2427.36"), new BigDecimal("3641.03"), new BigDecimal("12.0"), new BigDecimal("91.00"),
                    new BigDecimal("3641.04"), new BigDecimal("7087.22"), new BigDecimal("14.0"), new BigDecimal("163.82")
            ));

         
            tabelasInss.addAll(criarTabelasInssAno(2023,
                    new BigDecimal("0.00"), new BigDecimal("1302.00"), new BigDecimal("7.5"), new BigDecimal("0.00"),
                    new BigDecimal("1302.01"), new BigDecimal("2571.29"), new BigDecimal("9.0"), new BigDecimal("19.53"),
                    new BigDecimal("2571.30"), new BigDecimal("3856.94"), new BigDecimal("12.0"), new BigDecimal("96.67"),
                    new BigDecimal("3856.95"), new BigDecimal("7507.49"), new BigDecimal("14.0"), new BigDecimal("173.81")
            ));


            tabelasInss.addAll(criarTabelasInssAno(2024,
                    new BigDecimal("0.00"), new BigDecimal("1412.00"), new BigDecimal("7.5"), new BigDecimal("0.00"),
                    new BigDecimal("1412.01"), new BigDecimal("2666.68"), new BigDecimal("9.0"), new BigDecimal("21.18"),
                    new BigDecimal("2666.69"), new BigDecimal("4000.03"), new BigDecimal("12.0"), new BigDecimal("91.75"),
                    new BigDecimal("4000.04"), new BigDecimal("7786.02"), new BigDecimal("14.0"), new BigDecimal("171.71")
            ));

            tabelasInss.addAll(criarTabelasInssAno(2025,
                    new BigDecimal("0.00"), new BigDecimal("1518.00"), new BigDecimal("7.5"), new BigDecimal("0.00"),
                    new BigDecimal("1518.01"), new BigDecimal("2793.88"), new BigDecimal("9.0"), new BigDecimal("22.77"),
                    new BigDecimal("2793.89"), new BigDecimal("4190.83"), new BigDecimal("12.0"), new BigDecimal("106.60"),
                    new BigDecimal("4190.84"), new BigDecimal("8157.41"), new BigDecimal("14.0"), new BigDecimal("190.42")
            ));

            tabelaInssRepository.saveAll(tabelasInss);
        }
    }

    private void carregarTabelasIrrf() {
        if (tabelaIrrfRepository.count() == 0) {
            List<TabelaIrrf> tabelasIrrf = new ArrayList<>();

            tabelasIrrf.addAll(criarTabelasIrrfAno(2022,
                    new BigDecimal("0.00"), new BigDecimal("1903.98"), new BigDecimal("0.0"), new BigDecimal("0.00"),
                    new BigDecimal("1903.99"), new BigDecimal("2826.65"), new BigDecimal("7.5"), new BigDecimal("142.80"),
                    new BigDecimal("2826.66"), new BigDecimal("3751.05"), new BigDecimal("15.0"), new BigDecimal("354.80"),
                    new BigDecimal("3751.06"), new BigDecimal("4664.68"), new BigDecimal("22.5"), new BigDecimal("636.13"),
                    new BigDecimal("4664.69"), new BigDecimal("99999999999999.00"), new BigDecimal("27.5"), new BigDecimal("869.36")
            ));

            tabelasIrrf.addAll(criarTabelasIrrfAno(2023,
                    new BigDecimal("0.00"), new BigDecimal("2112.00"), new BigDecimal("0.0"), new BigDecimal("0.00"),
                    new BigDecimal("2112.01"), new BigDecimal("2826.65"), new BigDecimal("7.5"), new BigDecimal("158.40"),
                    new BigDecimal("2826.66"), new BigDecimal("3751.05"), new BigDecimal("15.0"), new BigDecimal("370.40"),
                    new BigDecimal("3751.06"), new BigDecimal("4664.68"), new BigDecimal("22.5"), new BigDecimal("651.73"),
                    new BigDecimal("4664.69"), new BigDecimal("99999999999999.00"), new BigDecimal("27.5"), new BigDecimal("884.96")
            ));

            tabelasIrrf.addAll(criarTabelasIrrfAno(2024,
                    new BigDecimal("0.00"), new BigDecimal("2259.20"), new BigDecimal("0.0"), new BigDecimal("0.00"),
                    new BigDecimal("2259.21"), new BigDecimal("2826.65"), new BigDecimal("7.5"), new BigDecimal("169.44"),
                    new BigDecimal("2826.66"), new BigDecimal("3751.05"), new BigDecimal("15.0"), new BigDecimal("381.44"),
                    new BigDecimal("3751.06"), new BigDecimal("4664.68"), new BigDecimal("22.5"), new BigDecimal("662.77"),
                    new BigDecimal("4664.69"), new BigDecimal("99999999999999.00"), new BigDecimal("27.5"), new BigDecimal("896.00")
            ));

            tabelasIrrf.addAll(criarTabelasIrrfAno(2025,
                    new BigDecimal("0.00"), new BigDecimal("2259.20"), new BigDecimal("0.0"), new BigDecimal("0.00"),
                    new BigDecimal("2259.21"), new BigDecimal("2826.65"), new BigDecimal("7.5"), new BigDecimal("169.44"),
                    new BigDecimal("2826.66"), new BigDecimal("3751.05"), new BigDecimal("15.0"), new BigDecimal("381.44"),
                    new BigDecimal("3751.06"), new BigDecimal("4664.68"), new BigDecimal("22.5"), new BigDecimal("662.77"),
                    new BigDecimal("4664.69"), new BigDecimal("99999999999999.00"), new BigDecimal("27.5"), new BigDecimal("896.00")
            ));

            tabelaIrrfRepository.saveAll(tabelasIrrf);
        }
    }


    private List<TabelaInss> criarTabelasInssAno(Integer anoVigencia, Object... params) {
       List<TabelaInss> tabelas = new ArrayList<>();
        for (int i = 0; i < params.length; i += 4) {
            BigDecimal faixaInicio = (BigDecimal) params[i];
            BigDecimal faixaFim = (BigDecimal) params[i + 1];
            BigDecimal aliquota = (BigDecimal) params[i + 2];
            BigDecimal valorDeduzir = (BigDecimal) params[i + 3];

        tabelas.add(criarTabelaInss(anoVigencia, faixaInicio, faixaFim, aliquota, (BigDecimal) valorDeduzir));
        }
        return tabelas;
    }

    private List<TabelaIrrf> criarTabelasIrrfAno(Integer anoVigencia, Object... params) {
         List<TabelaIrrf> tabelas = new ArrayList<>();
        for (int i = 0; i < params.length; i += 4) {
            BigDecimal faixaInicio = (BigDecimal) params[i];
            BigDecimal faixaFim = (BigDecimal) params[i + 1];
            BigDecimal aliquota = (BigDecimal) params[i + 2];
            BigDecimal valorDeduzir = (BigDecimal) params[i + 3];

         tabelas.add(criarTabelaIrrf(anoVigencia, faixaInicio, faixaFim, aliquota, (BigDecimal) valorDeduzir));
        }
        return tabelas;
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