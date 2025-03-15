package calculador.rescisao.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;

import calculador.rescisao.model.RescisaoRequest;
import calculador.rescisao.model.RescisaoResponse;

@Service
public class RescisaoService {

    public RescisaoResponse calcularRescisao(RescisaoRequest request) {
        RescisaoResponse response = new RescisaoResponse();

        // Validações (opcional, mas recomendado)
        if (request.getEmpregado().getSalario() == null || request.getEmpregado().getSalario().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Salário inválido.");
        }

        // Cálculo do Saldo de Salário
        long diasTrabalhadosNoMes = ChronoUnit.DAYS.between(request.getDataDesligamento().withDayOfMonth(1), request.getDataDesligamento()) + 1;
        BigDecimal saldoSalario = request.getEmpregado().getSalario().divide(BigDecimal.valueOf(30), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(diasTrabalhadosNoMes));
        if (diasTrabalhadosNoMes == 30) {
            response.setSaldoSalario(request.getEmpregado().getSalario());
        } else {
            
            response.setSaldoSalario(saldoSalario);
        }

        // Cálculo do 13º Salário Proporcional
        long mesesTrabalhadosNoAno = ChronoUnit.MONTHS.between(request.getDataAdmissao().withDayOfMonth(1), request.getDataDesligamento().withDayOfMonth(1));
        if (request.getDataDesligamento().getDayOfMonth() >= 15) {
            mesesTrabalhadosNoAno++;
        }
        
        BigDecimal decimoTerceiroProporcional = request.getEmpregado().getSalario().divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(mesesTrabalhadosNoAno));
        response.setDecimoTerceiroProporcional(decimoTerceiroProporcional);

        // Cálculos das Férias (simples, precisa ser expandido!!!!)
        BigDecimal salarioMensal = request.getEmpregado().getSalario();
        response.setFeriasVencidas(BigDecimal.ZERO); // Adicione a lógica correta se aplicável
        response.setFeriasProporcionais(salarioMensal.divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(mesesTrabalhadosNoAno)));
        response.setUmTercoFerias(response.getFeriasProporcionais().divide(BigDecimal.valueOf(3), 2, RoundingMode.HALF_UP));

        //Aviso prévio indenizado
        response.setAvisoPrevioIndenizado(BigDecimal.ZERO);

        // Cálculo do total bruto
        BigDecimal totalBruto = saldoSalario.add(decimoTerceiroProporcional).add(response.getFeriasProporcionais()).add(response.getUmTercoFerias());
        response.setTotalBruto(totalBruto);

        // Cálculos de INSS e IRRF (necessário implementar lógica correta)
        response.setInss(BigDecimal.ZERO); //  Implementar cálculo do INSS
        response.setIrrf(BigDecimal.ZERO); //  Implementar cálculo do IRRF

        // Cálculo do total líquido
        BigDecimal totalLiquido = totalBruto.subtract(response.getInss()).subtract(response.getIrrf());
        response.setTotalLiquido(totalLiquido);

        return response;
    }
}
