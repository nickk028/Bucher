package ar.edu.huergo.vectorial.calidad.bucher.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public abstract class BaseApiTest {
    @Autowired protected MockMvc mockMvc;
    @Autowired protected ObjectMapper om;

    protected String token(String user, String pass) throws Exception {
        String json = mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(Map.of("username", user, "password", pass))))
            .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        return om.readTree(json).get("token").asText();
    }

    protected MockHttpServletRequestBuilder conToken(MockHttpServletRequestBuilder b, String t) {
        return b.header("Authorization", "Bearer " + t);
    }

        protected String tokenAdmin() throws Exception {
        return token("admin@gmail.com", "AdminSuperSegura@123");
    }

    protected String tokenLector() throws Exception {
        return token("lector@gmail.com", "LectorSuperSegura@123");
    }

    // Cualquier lector
    protected String tokenDe(String user) throws Exception {
        return token(user, "LectorSuperSegura@123");
    }

    protected MockHttpServletRequestBuilder conJson(MockHttpServletRequestBuilder b, String token, Object body)
            throws Exception {
        String json = body instanceof String s ? s : om.writeValueAsString(body);
        return b.header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON).content(json);
    }
}
