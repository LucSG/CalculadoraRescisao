package calculador.rescisao.service.calculo;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;

import calculador.rescisao.model.RescisaoRequest;

@Service

public class VerificadoresService {

    public static int quantidadeDiasMes(RescisaoRequest request) { //pegar a quantidade de dias no mes 
        YearMonth yearMonth = YearMonth.of(request.getDataDesligamento().getYear(), request.getDataDesligamento().getMonth());
        int maximoDiasNoMes = yearMonth.lengthOfMonth();
        return Math.min(30, maximoDiasNoMes);
    }

    public static boolean mesmoMes(RescisaoRequest request) {//verificador se a pessoa comecou e terminou de trabalhar no mesmo mês
        return request.getDataAdmissao().getYear() == request.getDataDesligamento().getYear() && request.getDataAdmissao().getMonth() == request.getDataDesligamento().getMonth();
    }

    public static long quantidadeMeses(RescisaoRequest request) {//pegar quantidade de meses trabalhados
        //se comecou antes do dia 15 ele já conta um mês! && se foi demitido dia 15 ou depois o mês inteiro também conta!
        long meses = 0;
        if (request.getDataAdmissao().getYear() < request.getDataDesligamento().getYear()) {
            meses = ChronoUnit.MONTHS.between(request.getDataDesligamento().withDayOfYear(1), request.getDataDesligamento().withDayOfMonth(1));
        } else {
            meses = ChronoUnit.MONTHS.between(request.getDataAdmissao().withDayOfMonth(1), request.getDataDesligamento().withDayOfMonth(1));//calcula a quantidade de meses
            if (request.getDataAdmissao().getDayOfMonth() > 15)//se entrou depois do dia 15 tira um mes
                meses--;
        }

        if (request.getDataDesligamento().getDayOfMonth() >= 15)//se saiu depois do dia 15 conta mais um mes
            meses++;
        return meses;
    }

    public static int calcularMesesPeriodoAquisitivo(RescisaoRequest request) {
        LocalDate inicioUltimoPeriodoAquisitivo = request.getDataAdmissao().plusYears(ChronoUnit.YEARS.between(request.getDataAdmissao(), request.getDataDesligamento()));
        long meses = ChronoUnit.MONTHS.between(inicioUltimoPeriodoAquisitivo, request.getDataDesligamento());

        if (request.getDataDesligamento().getDayOfMonth() >= 15) {
            meses++;
        }
        return (int) Math.min(meses, 12);
    }
}
