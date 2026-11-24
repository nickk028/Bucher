package ar.edu.huergo.vectorial.calidad.bucher.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class SuscripcionIntegrationTest extends BaseApiTest {

    private String contratar(long suscripcionId, long medioPagoId, String tarjeta) throws Exception {
        return om.writeValueAsString(Map.of(
            "suscripcionId", suscripcionId, "medioPagoId", medioPagoId,
            "numeroTarjeta", tarjeta, "vencimiento", "12/30",
            "cvv", "123", "titular", "TITULAR DE PRUEBA"));
    }

    @Test
    @DisplayName("No se puede contratar el plan gratuito: 400")
    void gratuito() throws Exception {
        String t = token("lector2@gmail.com", "LectorSuperSegura@123");
        mockMvc.perform(conToken(post("/registro-suscripcion/contratar"), t)
                .contentType(MediaType.APPLICATION_JSON).content(contratar(1, 2, "4111111111111111")))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Tarjeta distinta a la de prueba: 400")
    void tarjetaInvalida() throws Exception {
        String t = token("lector2@gmail.com", "LectorSuperSegura@123");
        mockMvc.perform(conToken(post("/registro-suscripcion/contratar"), t)
                .contentType(MediaType.APPLICATION_JSON).content(contratar(2, 2, "5555555555554444")))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.detail").value("Los datos de la tarjeta no son válidos"));
    }

    @Test
    @DisplayName("Cancelar sin plan pago: 400")
    void cancelarSinPlan() throws Exception {
        String t = token("lector2@gmail.com", "LectorSuperSegura@123");
        mockMvc.perform(conToken(post("/registro-suscripcion/cancelar"), t))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Contratar y cancelar: vuelve al plan gratuito")
    void contratarYCancelar() throws Exception {
        String t = token("lector2@gmail.com", "LectorSuperSegura@123");
        mockMvc.perform(conToken(post("/registro-suscripcion/contratar"), t)
                .contentType(MediaType.APPLICATION_JSON).content(contratar(2, 2, "4111111111111111")))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.montoPagado").value(6048.79));
        mockMvc.perform(conToken(post("/registro-suscripcion/cancelar"), t)).andExpect(status().isOk());
        mockMvc.perform(conToken(get("/registro-suscripcion/activa"), t))
            .andExpect(jsonPath("$.nombre").value("Plan Gratis"));
    }

    @Test 
    @DisplayName("Registro con username ya usado: 400")
    void registroDuplicado() throws Exception {
        mockMvc.perform(post("/usuario/registrar").contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"lector@gmail.com\",\"password\":\"LectorSuperSegura@123\","
                       + "\"verificationPassword\":\"LectorSuperSegura@123\",\"nickname\":\"Otro\"}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Registro nuevo OK: queda con plan gratuito y rol LECTOR")
    void registroNuevo() throws Exception {
        mockMvc.perform(post("/usuario/registrar").contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"nuevo@test.com\",\"password\":\"NuevoSuperSegura@123\","
                       + "\"verificationPassword\":\"NuevoSuperSegura@123\",\"nickname\":\"Nuevo\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.suscripcionActiva.nombre").value("Plan Gratis"));
    }
}