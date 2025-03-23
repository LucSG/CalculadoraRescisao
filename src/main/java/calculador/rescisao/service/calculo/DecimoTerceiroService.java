package calculador.rescisao.service.calculo;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;

import calculador.rescisao.model.RescisaoRequest;

@Service
public class DecimoTerceiroService{

    public BigDecimal calculoDecimoTerceiroProporcionail(RescisaoRequest request){
        BigDecimal valorDecimoTerceiro;
        valorDecimoTerceiro = request.getSalario().divide(BigDecimal.valueOf(12) , 5, RoundingMode.HALF_UP)
        .multiply(BigDecimal.valueOf(VerificadoresService.quantidadeMeses(request)).setScale(2, RoundingMode.HALF_UP));
        return valorDecimoTerceiro;
    }


}