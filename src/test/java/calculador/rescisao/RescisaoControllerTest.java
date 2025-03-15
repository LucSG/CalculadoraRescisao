package calculador.rescisao;

import calculador.rescisao.model.Empregado;
import calculador.rescisao.model.RescisaoRequest;
import calculador.rescisao.model.TipoRescisao;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootTest
@AutoConfigureMockMvc
public class RescisaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void calcularRescisao_sucesso() throws Exception {
        Empregado empregado = new Empregado();
        empregado.setSalario(new BigDecimal("3000.00"));
        empregado.setDataAdmissao(LocalDate.of(2022, 1, 1));

        RescisaoRequest request = new RescisaoRequest();
        request.setEmpregado(empregado);
        request.setTipoRescisao(TipoRescisao.SEM_JUSTA_CAUSA);
        request.setDataDesligamento(LocalDate.of(2024, 1, 31));

        mockMvc.perform(MockMvcRequestBuilders.post("/rescisao/calcular")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalLiquido").exists());
    }

    @Test
    public void calcularRescisao_salarioInvalido() throws Exception {
        Empregado empregado = new Empregado();
        empregado.setSalario(new BigDecimal("0"));
        empregado.setDataAdmissao(LocalDate.of(2022, 1, 1));

        RescisaoRequest request = new RescisaoRequest();
        request.setEmpregado(empregado);
        request.setTipoRescisao(TipoRescisao.SEM_JUSTA_CAUSA);
        request.setDataDesligamento(LocalDate.of(2024, 1, 31));

        mockMvc.perform(MockMvcRequestBuilders.post("/rescisao/calcular")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}