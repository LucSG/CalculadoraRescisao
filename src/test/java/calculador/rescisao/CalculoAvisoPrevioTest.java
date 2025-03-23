package calculador.rescisao;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import calculador.rescisao.model.RescisaoRequest;
import calculador.rescisao.service.calculo.AvisoPrevioService;

public class CalculoAvisoPrevioTest {

    @Test
    void testAvisoPrevioMenosDeUmAno() {
        RescisaoRequest request = new RescisaoRequest();
        request.setDataAdmissao(LocalDate.of(2024, 1, 1));
        request.setDataDesligamento(LocalDate.of(2024, 4, 1));
        assertEquals(30, AvisoPrevioService.diasAvisoPrevio(request));
    }

    @Test
    void testAvisoPrevioExatamenteUmAno() {
        RescisaoRequest request = new RescisaoRequest();
        request.setDataAdmissao(LocalDate.of(2023, 5, 10));
        request.setDataDesligamento(LocalDate.of(2024, 5, 10));
        assertEquals(33, AvisoPrevioService.diasAvisoPrevio(request));
    }

    @Test
    void testAvisoPrevioUmDiaAposUmAno() {
        RescisaoRequest request = new RescisaoRequest();
        request.setDataAdmissao(LocalDate.of(2023, 5, 10));
        request.setDataDesligamento(LocalDate.of(2024, 5, 11));
        assertEquals(33, AvisoPrevioService.diasAvisoPrevio(request));
    }

    @Test
    void testAvisoPrevioVariosAnosLimite90Dias() {
        RescisaoRequest request = new RescisaoRequest();
        request.setDataAdmissao(LocalDate.of(2014, 5, 10));
        request.setDataDesligamento(LocalDate.of(2024, 5, 10));
        assertEquals(60, AvisoPrevioService.diasAvisoPrevio(request));
    }

    @Test
    void testValorAvisoPrevioComArredondamento() {
        RescisaoRequest request = new RescisaoRequest();
        request.setSalario(new BigDecimal("3000.00"));
        request.setDataAdmissao(LocalDate.of(2023, 5, 10));
        request.setDataDesligamento(LocalDate.of(2024, 5, 10));
        BigDecimal valorAviso = AvisoPrevioService.valorAvisoPrevio(request);
        assertEquals(new BigDecimal("3300.00000"), valorAviso);

    }

}