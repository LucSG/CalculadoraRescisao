package calculador.rescisao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import calculador.rescisao.model.RescisaoRequest;
import calculador.rescisao.model.RescisaoResponse;
import calculador.rescisao.service.calculo.Calculos;


@Service
public class RescisaoService {

    @Autowired
    private Calculos calculos;

    public RescisaoResponse calcularRescisao(RescisaoRequest request) {

        RescisaoResponse response = new RescisaoResponse();
    
        calculos.chamarCalculos(request, response);

        return response;
    }


    
}
