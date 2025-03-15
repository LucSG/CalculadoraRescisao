package calculador.rescisao.controller;

import calculador.rescisao.model.RescisaoRequest;
import calculador.rescisao.model.RescisaoResponse;
import calculador.rescisao.service.RescisaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rescisao")
public class RescisaoController {

    @Autowired
    private RescisaoService rescisaoService;

    @PostMapping("/calcular")
    public ResponseEntity<RescisaoResponse> calcularRescisao(@Valid @RequestBody RescisaoRequest request) {
        RescisaoResponse response = rescisaoService.calcularRescisao(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}