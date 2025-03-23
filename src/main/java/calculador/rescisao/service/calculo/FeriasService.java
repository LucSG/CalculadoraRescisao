package calculador.rescisao.service.calculo;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;

import calculador.rescisao.model.RescisaoRequest;

@Service
public class FeriasService{

    public static BigDecimal calculoFeriasProporcionais(RescisaoRequest request){
        BigDecimal valorFerias;
        valorFerias = request.getSalario().divide(BigDecimal.valueOf(12) , 5, RoundingMode.HALF_UP)
        .multiply(BigDecimal.valueOf(VerificadoresService.quantidadeMeses(request)));
        return valorFerias;
    }

    public static BigDecimal calculoTercoFerias(RescisaoRequest request){
        return calculoFeriasProporcionais(request).divide(BigDecimal.valueOf(3),5,RoundingMode.HALF_UP);
    }

    public static BigDecimal feriasVencidas(RescisaoRequest request){
        BigDecimal ferias;
        ferias = request.getSalario().add(request.getSalario().divide(BigDecimal.valueOf(3), 5, RoundingMode.HALF_UP));
        return ferias;
    }

}