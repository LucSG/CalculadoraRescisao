package calculador.rescisao.service.tributacao;


import calculador.rescisao.repository.TabelaIrrfRepository;
import calculador.rescisao.model.TabelaIrrf;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;



@Service //Service Spring
public class IrrfService {

    @Autowired // Injeta o repositório TabelaIrrfRepository
    private TabelaIrrfRepository tabelaIrrfRepository;

   public BigDecimal calcularIrrf(BigDecimal salario, Integer anoVigencia) {
        //Buscar a tabela do IRRF para o ano de vigência especificado, ordenado por faixa de início
        List<TabelaIrrf> tabelas = tabelaIrrfRepository.findByAnoVigenciaOrderByFaixaInicioAsc(anoVigencia);

         if (tabelas == null || tabelas.isEmpty()) {
            throw new IllegalArgumentException("Tabela IRRF não encontrada para o ano " + anoVigencia);
        }

        BigDecimal irrf = BigDecimal.ZERO;

        for (TabelaIrrf faixa : tabelas) {
            if (salario.compareTo(faixa.getFaixaInicio()) >= 0 && salario.compareTo(faixa.getFaixaFim()) <= 0) {
                //Encontrou a faixa correta
                irrf = salario.multiply(faixa.getAliquota().divide(new BigDecimal("100"), 6, RoundingMode.HALF_EVEN))
                        .subtract(faixa.getValorDeduzir())
                        .setScale(2, RoundingMode.HALF_EVEN);
                break; //Não precisa continuar procurando
            }
        }

        return irrf;
    }


    public List<TabelaIrrf> listarTabelasIrrf(Integer anoVigencia) {
        return tabelaIrrfRepository.findByAnoVigenciaOrderByFaixaInicioAsc(anoVigencia);
    }

    public TabelaIrrf salvarTabelaIrrf(TabelaIrrf tabelaIrrf) {
        return tabelaIrrfRepository.save(tabelaIrrf);
    }
}