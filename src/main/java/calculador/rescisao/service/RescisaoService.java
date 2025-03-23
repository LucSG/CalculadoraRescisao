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

    public RescisaoResponse calcularRescisao(RescisaoRequest request) {

        RescisaoResponse response = new RescisaoResponse();

        if (request.getSalario() == null || request.getSalario().compareTo(BigDecimal.ZERO) <= 0) {//salario não pode ser menor que 0
            throw new IllegalArgumentException("Salário inválido.");
        }
        
        BigDecimal saldoSalario;
        BigDecimal inssSalario;
        BigDecimal irrfSalario;
        BigDecimal feriasProporcionais;
        BigDecimal umTercoFeriasProporcionais;
        BigDecimal feriasVencidas = BigDecimal.ZERO;
        BigDecimal umTercoFerias = BigDecimal.ZERO;
        BigDecimal avisoPrevio = BigDecimal.ZERO;
        BigDecimal decimoTerceiro;
        BigDecimal totalBruto;
        BigDecimal totalLiquido;
        BigDecimal inss13;
        BigDecimal irrf13;

        //retorno do calculo do saldo de salario e INSS e IRRF em 2 casas decimais
        saldoSalario = saldoSalarioService.calcular(request).setScale(2, RoundingMode.HALF_UP); //pego saldoSalario
        inssSalario = inssService.calcularInss(saldoSalario, request.getDataDesligamento().getYear());//vejo o inss
        irrfSalario = irrfService.calcularIrrf((saldoSalario.subtract(inssSalario)), request.getDataDesligamento().getYear());//e ja pego o valor do irrf


        response.setSaldoSalario(saldoSalario);
        response.setInssSalario(inssSalario);
        response.setIrrfSalario(irrfSalario);

        //Calculo de ferias + 1/3
        feriasProporcionais = feriasService.calculoFeriasProporcionais(request).setScale(2, RoundingMode.HALF_UP);
        umTercoFeriasProporcionais = feriasService.calculoTercoFerias(request).setScale(2, RoundingMode.HALF_UP);

        response.setFeriasProporcionais(feriasProporcionais);
        response.setUmTercoFeriasProporcionais(umTercoFeriasProporcionais);


        //ferias vencidas + avisoPrevio
        if (request.isFeriasVencidas()) {
            feriasVencidas = feriasService.feriasVencidas(request).setScale(2, RoundingMode.HALF_UP);
            umTercoFerias = feriasService.tercoFeriasVencidas(request).setScale(2, RoundingMode.HALF_UP);
            response.setFeriasVencidas(feriasVencidas);
            response.setUmTercoFerias(umTercoFerias);
        }

        if (request.isAvisoPrevio()) {
            avisoPrevio = AvisoPrevioService.valorAvisoPrevio(request).setScale(2, RoundingMode.HALF_UP);
            response.setAvisoPrevioIndenizado(avisoPrevio);
        }

        //calculo 13° 
        decimoTerceiro = decimoTerceiroService.calculoDecimoTerceiroProporcionail(request).setScale(2, RoundingMode.HALF_UP); //pego 13
        inss13 = inssService.calcularInss(decimoTerceiro, request.getDataDesligamento().getYear()); //vejo o qnt de inss
        irrf13 = irrfService.calcularIrrf((decimoTerceiro.subtract(inss13)), request.getDataDesligamento().getYear()); //irrf so conta decimo - inss

        response.setDecimoTerceiroProporcional(decimoTerceiro);
        response.setInss13(inss13);
        response.setIrrf13(irrf13);

        totalBruto = saldoSalario.add(feriasProporcionais).add(umTercoFeriasProporcionais).add(feriasVencidas).add(umTercoFerias).add(avisoPrevio).add(decimoTerceiro);
        totalLiquido = totalBruto.subtract(inssSalario).subtract(irrfSalario).subtract(inss13).subtract(irrf13);

        response.setTotalBruto(totalBruto);
        response.setTotalLiquido(totalLiquido);

        return response;
    }
}
