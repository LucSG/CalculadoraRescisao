package calculador.rescisao.service.calculo;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import calculador.rescisao.model.RescisaoRequest;

@Service
public class FeriasService{

    public static BigDecimal calculoFeriasProporcionais(RescisaoRequest request){
        BigDecimal valorFerias = BigDecimal.valueOf(0.0);
        return valorFerias;

    }

}