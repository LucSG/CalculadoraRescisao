package calculador.rescisao.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import calculador.rescisao.model.RescisaoRequest;
import calculador.rescisao.model.RescisaoResponse;
import calculador.rescisao.service.calculo.SaldoSalarioService;
import calculador.rescisao.service.calculo.VerificadoresService;

@Service
public class RescisaoService {

    @Autowired
    private SaldoSalarioService saldoSalarioService;

    


    public RescisaoResponse calcularRescisao(RescisaoRequest request) {

       
        RescisaoResponse response = new RescisaoResponse();

        if (request.getSalario() == null || request.getSalario().compareTo(BigDecimal.ZERO) <= 0) {//salario não pode ser menor que 0
            throw new IllegalArgumentException("Salário inválido.");
        }

        //retorno do calculo do saldo de salario em 2 casas decimais
        response.setSaldoSalario(saldoSalarioService.calcular(request).setScale(2, RoundingMode.HALF_UP)); 
        response.setTotalBruto(BigDecimal.valueOf(VerificadoresService.quantidadeMeses(request)));

        //Calculo de ferias
        


       

        //Cálculo do INSS + IRRF Saldo de Salário



        // Cálculo do 13º Salário Proporcional
        long mesesTrabalhadosNoAno = ChronoUnit.MONTHS.between(request.getDataAdmissao().withDayOfMonth(1).withDayOfYear(1), request.getDataDesligamento().withDayOfMonth(1));
        if (request.getDataDesligamento().getDayOfMonth() >= 15) {
            mesesTrabalhadosNoAno++;
        }

        BigDecimal decimoTerceiroProporcional = request.getSalario().divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(mesesTrabalhadosNoAno));
        response.setDecimoTerceiroProporcional(decimoTerceiroProporcional);

        




        // Cálculos das Férias (simples, precisa ser expandido!!!!)
        BigDecimal salarioMensal = request.getSalario();
        //response.setFeriasVencidas(BigDecimal.ZERO); // Adicione a lógica correta se aplicável
        response.setFeriasProporcionais(salarioMensal.divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(mesesTrabalhadosNoAno)));
        response.setUmTercoFerias(response.getFeriasProporcionais().divide(BigDecimal.valueOf(3), 2, RoundingMode.HALF_UP));



        //Aviso prévio indenizado
        response.setAvisoPrevioIndenizado(BigDecimal.ZERO);



        // Cálculo do total bruto
        //BigDecimal totalBruto = saldoSalarioService.add(decimoTerceiroProporcional).add(response.getFeriasProporcionais()).add(response.getUmTercoFerias());
        //response.setTotalBruto(totalBruto);

        // Cálculos de INSS e IRRF (necessário implementar lógica correta)
       // response.setInss(BigDecimal.ZERO); //  Implementar cálculo do INSS
       // response.setIrrf(BigDecimal.ZERO); //  Implementar cálculo do IRRF

        // Cálculo do total líquido
        BigDecimal totalLiquido = BigDecimal.ONE;
        response.setTotalLiquido(totalLiquido);

        return response;
    }
}
