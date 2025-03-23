package calculador.rescisao.service.calculo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import calculador.rescisao.model.RescisaoRequest;

public class AvisoPrevioService {

    public static int diasAvisoPrevio(RescisaoRequest request) {


        //Verifica quantos anos foram trabalhados para adicionar os 3 dias por ano trabalhado
        long anosTrabalhados = ChronoUnit.YEARS.between(request.getDataAdmissao(), request.getDataDesligamento());
        LocalDate aniversarioAdmissao = request.getDataAdmissao().plusYears(anosTrabalhados);
        
        if(request.getDataDesligamento().isBefore(aniversarioAdmissao)){
            anosTrabalhados--;
            if(anosTrabalhados < 0){
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

    public static BigDecimal valorAvisoPrevio(RescisaoRequest request){
        return request.getSalario().divide(BigDecimal.valueOf(30), 5, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(diasAvisoPrevio(request)));
    }

}