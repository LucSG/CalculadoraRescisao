package calculador.rescisao.service.calculo;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;

import calculador.rescisao.model.RescisaoRequest;


@Service
public class SaldoSalarioService {

    @Autowired
    private VerificadoresService verificadoresService;

    public BigDecimal calcular(RescisaoRequest request) {
        //calcular dias trabalhados / dias do mes
        if(verificadoresService.mesmoMes(request)){
            return request.getSalario().divide(BigDecimal.valueOf(VerificadoresService.quantidadeDiasMes(request)), 5, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(ChronoUnit.DAYS.between(request.getDataAdmissao(), request.getDataDesligamento()) + 1));
        }else{
            return request.getSalario().divide(BigDecimal.valueOf(VerificadoresService.quantidadeDiasMes(request)), 5 , RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(ChronoUnit.DAYS.between(request.getDataDesligamento(), request.getDataDesligamento()) +1));
        }
    }


}
