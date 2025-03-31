package calculador.rescisao.service.calculo;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import calculador.rescisao.model.RescisaoRequest;
import calculador.rescisao.model.TipoRescisao;

@Service
public class DecimoTerceiroService{

    @Autowired
    private VerificadoresService verificadoresService;

    public BigDecimal calculoDecimoTerceiroProporcionail(RescisaoRequest request){
        if(request.getTipoRescisao().equals(TipoRescisao.POR_JUSTA_CAUSA))
            return BigDecimal.ZERO;
        BigDecimal valorDecimoTerceiro;
        valorDecimoTerceiro = request.getSalario().divide(BigDecimal.valueOf(12) , 5, RoundingMode.HALF_UP)
        .multiply(BigDecimal.valueOf(verificadoresService.quantidadeMeses(request)).setScale(2, RoundingMode.HALF_UP));
        return valorDecimoTerceiro;
    }


}