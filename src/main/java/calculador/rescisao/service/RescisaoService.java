package calculador.rescisao.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import calculador.rescisao.model.RescisaoRequest;
import calculador.rescisao.model.RescisaoResponse;
import calculador.rescisao.service.calculo.AvisoPrevioService;
import calculador.rescisao.service.calculo.FeriasService;
import calculador.rescisao.service.calculo.SaldoSalarioService;
import calculador.rescisao.service.calculo.DecimoTerceiroService;
import calculador.rescisao.service.tributacao.InssService;
import calculador.rescisao.service.tributacao.IrrfService;

@Service
public class RescisaoService {

    @Autowired
    private SaldoSalarioService saldoSalarioService;
    @Autowired
    private DecimoTerceiroService decimoTerceiroService;
    @Autowired
    private FeriasService feriasService;
    @Autowired
    private InssService inssService;
    @Autowired
    private IrrfService irrfService;
    @Autowired
    private AvisoPrevioService avisoPrevioService;

    public RescisaoResponse calcularRescisao(RescisaoRequest request) {

        RescisaoResponse response = new RescisaoResponse();

        if (request.getSalario() == null || request.getSalario().compareTo(BigDecimal.ZERO) <= 0) {//salario não pode ser menor que 0
            throw new IllegalArgumentException("Salário inválido.");
        }
    
        calcularSaldoSalarioETributos(request, response);

        calcularFerias(request, response);

        calcularAvisoPrevio(request, response);

        calcularDecimoTerceiroETributos(request, response);

        calcularTotais(response);

        return response;
    }


    private void calcularSaldoSalarioETributos(RescisaoRequest request, RescisaoResponse response) {
        BigDecimal saldoSalario = saldoSalarioService.calcular(request).setScale(2, RoundingMode.HALF_UP);
        BigDecimal inssSalario = inssService.calcularInss(saldoSalario, request.getDataDesligamento().getYear());
        BigDecimal irrfSalario = irrfService.calcularIrrf((saldoSalario.subtract(inssSalario)), request.getDataDesligamento().getYear());

        response.setSaldoSalario(saldoSalario);
        response.setInssSalario(inssSalario);
        response.setIrrfSalario(irrfSalario);
    }

    private void calcularFerias(RescisaoRequest request, RescisaoResponse response) {
        BigDecimal feriasProporcionais = feriasService.calculoFeriasProporcionais(request).setScale(2, RoundingMode.HALF_UP);
        BigDecimal umTercoFeriasProporcionais = feriasService.calculoTercoFerias(request).setScale(2, RoundingMode.HALF_UP);

        response.setFeriasProporcionais(feriasProporcionais);
        response.setUmTercoFeriasProporcionais(umTercoFeriasProporcionais);

        if (request.isFeriasVencidas()) {
            BigDecimal feriasVencidas = feriasService.feriasVencidas(request).setScale(2, RoundingMode.HALF_UP);
            BigDecimal umTercoFerias = feriasService.tercoFeriasVencidas(request).setScale(2, RoundingMode.HALF_UP);
            response.setFeriasVencidas(feriasVencidas);
            response.setUmTercoFerias(umTercoFerias);
        } else {
            response.setFeriasVencidas(BigDecimal.ZERO);
            response.setUmTercoFerias(BigDecimal.ZERO);
        }
    }

    private void calcularAvisoPrevio(RescisaoRequest request, RescisaoResponse response) {
        BigDecimal avisoPrevio = avisoPrevioService.calcularAvisoPrevio(request); 
        response.setAvisoPrevioIndenizado(avisoPrevio);
    }

    private void calcularDecimoTerceiroETributos(RescisaoRequest request, RescisaoResponse response) {
        BigDecimal decimoTerceiro = decimoTerceiroService.calculoDecimoTerceiroProporcionail(request).setScale(2, RoundingMode.HALF_UP);
        BigDecimal inss13 = inssService.calcularInss(decimoTerceiro, request.getDataDesligamento().getYear());
        BigDecimal irrf13 = irrfService.calcularIrrf((decimoTerceiro.subtract(inss13)), request.getDataDesligamento().getYear());

        response.setDecimoTerceiroProporcional(decimoTerceiro);
        response.setInss13(inss13);
        response.setIrrf13(irrf13);
    }

    private void calcularTotais(RescisaoResponse response) {
        BigDecimal totalBruto = response.getSaldoSalario()
                .add(response.getFeriasProporcionais())
                .add(response.getUmTercoFeriasProporcionais())
                .add(response.getFeriasVencidas())
                .add(response.getUmTercoFerias())
                .add(response.getDecimoTerceiroProporcional());
      
        BigDecimal totalLiquido = totalBruto.subtract(response.getInssSalario())
                .subtract(response.getIrrfSalario())
                .subtract(response.getInss13())
                .subtract(response.getIrrf13());

        if (response.getAvisoPrevioIndenizado().compareTo(BigDecimal.ZERO) == 1) {
            totalBruto = totalBruto.add(response.getAvisoPrevioIndenizado());
        }
        if (response.getAvisoPrevioIndenizado().compareTo(BigDecimal.ZERO) == -1) {
            totalLiquido = totalLiquido.add(response.getAvisoPrevioIndenizado());
        }
        response.setTotalBruto(totalBruto);
        response.setTotalLiquido(totalLiquido);
    }
}
