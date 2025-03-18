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

        long diasTrabalhadosNoMes = VerificadoresService.quantidadeDias(request);
        
        if (verificadoresService.salarioInteiro(request)) {//verifico se a pessoa trabalhou o mês inteiro
            return request.getSalario();//se trabalhou retorna o valor do salário inteiro (IMPORTANTE! verificar com Auditor Fiscal do Trabalho, legislação confusa referente a mêses com 30 e 31 dias)
        } else {
            return request.getSalario().divide(BigDecimal.valueOf(30), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(diasTrabalhadosNoMes));//pego o valor do salario divido por 30 dias
            // depois multiplico pelos dias trabalhados( Maioria das convenções coletivas leva 30 dias em consideração)
        }

    }


}
