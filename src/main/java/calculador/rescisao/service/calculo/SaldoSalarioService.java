package calculador.rescisao.service.calculo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import calculador.rescisao.model.RescisaoRequest;


@Service
public class SaldoSalarioService {

    @Autowired
    private VerificadoresService verificadoresService;

    public BigDecimal calcular(RescisaoRequest request) {
        //calcular dias trabalhados / dias do mes
        if(verificadoresService.mesmoMes(request)){//a logica e um pouco diferente se o trabalho iniciou e encerrou no mesmo mes
            return request.getSalario().divide(BigDecimal.valueOf(VerificadoresService.quantidadeDiasMes(request)), 5, RoundingMode.HALF_UP)
            .multiply(BigDecimal.valueOf(ChronoUnit.DAYS.between(request.getDataAdmissao(), request.getDataDesligamento()) + 1));//Salario / qnt dias * dias trabalhados(demissao | admissao)
        }else{
            return request.getSalario().divide(BigDecimal.valueOf(VerificadoresService.quantidadeDiasMes(request)), 5, RoundingMode.HALF_UP)
            .multiply(BigDecimal.valueOf(ChronoUnit.DAYS.between(request.getDataDesligamento().withDayOfMonth(1), request.getDataDesligamento()) +1));//Salario / qnt dias * dias trabalhados (demissao | dia 1 do mes) 
        }
    }
}
