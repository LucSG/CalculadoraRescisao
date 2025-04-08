package calculador.rescisao.service.calculo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;

import calculador.rescisao.model.RescisaoRequest;


@Service
public class AvisoPrevioService {

    public int diasAvisoPrevio(RescisaoRequest request) {
        //Verifica quantos anos foram trabalhados para adicionar os 3 dias por ano trabalhado
        long anosTrabalhados = ChronoUnit.YEARS.between(request.getDataAdmissao(), request.getDataDesligamento());
        LocalDate aniversarioAdmissao = request.getDataAdmissao().plusYears(anosTrabalhados);

        if (request.getDataDesligamento().isBefore(aniversarioAdmissao)) {
            anosTrabalhados--;
            if (anosTrabalhados < 0) {
                anosTrabalhados = 0;
            }
        }
        //Calculo qnt dias do aviso previo
        int diasAvisoPrevio = 30 + ((int) anosTrabalhados * 3);

        //Aviso previo nao pode passar de 90 dias MAX 
        if (diasAvisoPrevio > 90) {
            diasAvisoPrevio = 90;
        }
        return diasAvisoPrevio;
    }

    public BigDecimal valorAvisoPrevio(RescisaoRequest request) {
        return request.getSalario().divide(BigDecimal.valueOf(30), 5, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(diasAvisoPrevio(request)));
    }

    public BigDecimal calcularAvisoPrevio(RescisaoRequest request) {
        switch (request.getTipoRescisao()) {//verifica o tipo de rescisao e retorna o valor do aviso previo
            case PEDIDO_DEMISSAO:
                if (request.isAvisoPrevio()) {
                    return request.getSalario().negate();
                }
                return BigDecimal.ZERO;
            case SEM_JUSTA_CAUSA:
                if (request.isAvisoPrevio()) {
                    return valorAvisoPrevio(request).setScale(2, RoundingMode.HALF_UP);
                }
                if (diasAvisoPrevio(request) > 30) {
                    return request.getSalario().divide(BigDecimal.valueOf(30), 5, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(diasAvisoPrevio(request) - 30))
                    .setScale(2, RoundingMode.HALF_UP);
                }
                return BigDecimal.ZERO;
            case POR_JUSTA_CAUSA:
                return BigDecimal.ZERO;
            case TERMINO_DE_CONTRATO_DE_EXPERIENCIA:
                return BigDecimal.ZERO;
            case RESCISAO_ANTECIPADA_POR_PARTE_DO_EMPREGADO:
                return BigDecimal.ZERO;
            case RESCISAO_ANTECIPADA_POR_PARTE_DO_EMPREGADOR:
                if (request.isAvisoPrevio()) {
                    return valorAvisoPrevio(request).setScale(2, RoundingMode.HALF_UP);
                }
                if (diasAvisoPrevio(request) > 30) {
                    return request.getSalario().divide(BigDecimal.valueOf(30), 5, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(diasAvisoPrevio(request) - 30))
                    .setScale(2, RoundingMode.HALF_UP);
                }
                return BigDecimal.ZERO;
            default:
                throw new IllegalArgumentException("Tipo de rescisão inválido.");
        }
    }
}
