package calculador.rescisao.service.tributacao;

import calculador.rescisao.model.TabelaInss;
import calculador.rescisao.repository.TabelaInssRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service //servico Spring
public class InssService {

    @Autowired //injeta o repositório TabelaInssRepository
    private TabelaInssRepository tabelaInssRepository;

    public BigDecimal calcularInss(BigDecimal salario, Integer anoVigencia) {
        //buscar a tabela do INSS para o ano de vigencia especificado ordenado por faixa de inicio
        List<TabelaInss> tabelas = tabelaInssRepository.findByAnoVigenciaOrderByFaixaInicioAsc(anoVigencia);

        if (tabelas == null || tabelas.isEmpty()) {
            throw new IllegalArgumentException("Tabela INSS não encontrada para o ano " + anoVigencia);
        }

        BigDecimal inss = BigDecimal.ZERO;

        for (TabelaInss faixa : tabelas) {
            if (salario.compareTo(faixa.getFaixaInicio()) >= 0 && salario.compareTo(faixa.getFaixaFim()) <= 0) {
                //encontrou a faixa correta
                inss = salario.multiply(faixa.getAliquota().divide(new BigDecimal("100"), 6, RoundingMode.HALF_EVEN))
                        .subtract(faixa.getValorDeduzir())
                        .setScale(2, RoundingMode.HALF_EVEN);
                break; //não precisa continuar procurando
            }
        }

        return inss;
    }

    public List<TabelaInss> listarTabelasInss(Integer anoVigencia) {
        return tabelaInssRepository.findByAnoVigenciaOrderByFaixaInicioAsc(anoVigencia);
    }

    public TabelaInss salvarTabelaInss(TabelaInss tabelaInss) {
        return tabelaInssRepository.save(tabelaInss);
    }
}