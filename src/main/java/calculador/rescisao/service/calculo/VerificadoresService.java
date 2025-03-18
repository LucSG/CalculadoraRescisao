package calculador.rescisao.service.calculo;


import java.time.YearMonth;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;

import calculador.rescisao.model.RescisaoRequest;

@Service

public class VerificadoresService {

    public static boolean validarQuantidadeDias(RescisaoRequest request) { //Validação para saber se a pessoa trabalhou os 
        YearMonth yearMonth = YearMonth.of(request.getDataDesligamento().getYear(), request.getDataDesligamento().getMonth());
        int maximoDiasNoMes = yearMonth.lengthOfMonth();
        return maximoDiasNoMes == request.getDataDesligamento().getDayOfMonth();
    }

    public static boolean mesmoMes(RescisaoRequest request) {//verificador se a pessoa comecou e terminou de trabalhar no mesmo mês
        return request.getDataAdmissao().getYear() == request.getDataDesligamento().getYear() && request.getDataAdmissao().getMonth() == request.getDataDesligamento().getMonth();
    }

    public static boolean salarioInteiro(RescisaoRequest request) {
        if (!validarQuantidadeDias(request)) {
            return false;
        }
        // verifica se as datas de admissão e desligamento estão no mesmo mês,
        // verifica se o dia de admissão é maior que 1. Se for, retorna false.
        // Se passou por todas as verificações, retorna true.
        return !(mesmoMes(request) && request.getDataAdmissao().getDayOfMonth() > 1);
    }

    public static Long quantidadeDias(RescisaoRequest request){
        if(mesmoMes(request)){
            return ChronoUnit.DAYS.between(request.getDataAdmissao(), request.getDataDesligamento()) + 1;
        }else{
            return ChronoUnit.DAYS.between(request.getDataDesligamento().withDayOfMonth(1), request.getDataDesligamento()) + 1;
        }
        
    }
}
