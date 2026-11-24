package ar.edu.huergo.vectorial.calidad.bucher.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@SpringBootTest
@AutoConfigureMockMvc
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
}
