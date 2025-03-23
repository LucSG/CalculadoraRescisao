package calculador.rescisao.service.calculo;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;

import calculador.rescisao.model.RescisaoRequest;

@Service
public class FeriasService{

    public BigDecimal calculoFeriasProporcionais(RescisaoRequest request){
        BigDecimal valorFerias;
        valorFerias = request.getSalario().divide(BigDecimal.valueOf(12) , 5, RoundingMode.HALF_UP)
        .multiply(BigDecimal.valueOf(VerificadoresService.calcularMesesPeriodoAquisitivo(request)));
        return valorFerias;
    }

    public BigDecimal calculoTercoFerias(RescisaoRequest request){
        return calculoFeriasProporcionais(request).divide(BigDecimal.valueOf(3),5,RoundingMode.HALF_UP);
    }

    public BigDecimal feriasVencidas(RescisaoRequest request){
        return request.getSalario();
    }

    public BigDecimal tercoFeriasVencidas(RescisaoRequest request){
        return request.getSalario().divide(BigDecimal.valueOf(3), 5, RoundingMode.HALF_UP);
    }
}