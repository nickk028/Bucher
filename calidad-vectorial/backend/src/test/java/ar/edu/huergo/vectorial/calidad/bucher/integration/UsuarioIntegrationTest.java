package ar.edu.huergo.vectorial.calidad.bucher.integration;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.huergo.vectorial.calidad.bucher.entity.security.Rol;
import ar.edu.huergo.vectorial.calidad.bucher.repository.security.RolRepository;

@Transactional
class UsuarioIntegrationTest extends BaseApiTest {

    @Autowired RolRepository rolRepository;

    @Test
    @DisplayName("GET /usuario/propio: devuelve el usuario con su suscripción activa")
    void propio() throws Exception {
        mockMvc.perform(conToken(get("/usuario/propio"), tokenLector()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username").value("lector@gmail.com"))
            .andExpect(jsonPath("$.suscripcionActiva.nombre").value("Plan Gratis"));
        mockMvc.perform(get("/usuario/propio")).andExpect(status().isUnauthorized());
    }

    @Test 
    @DisplayName("PUT /usuario/modificar: actualiza descripción y se refleja en /propio")
    void modificar() throws Exception {
        String t = tokenLector();
        mockMvc.perform(conJson(put("/usuario/modificar"), t, Map.of("descripcion", "Nueva bio", "pronombres", "Ella")))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.descripcion").value("Nueva bio"));
        mockMvc.perform(conToken(get("/usuario/propio"), t))
            .andExpect(jsonPath("$.descripcion").value("Nueva bio"));
    }

    @Test @DisplayName("PUT /usuario/modificar con descripción de 256 caracteres: 400")
    void modificarInvalido() throws Exception {
        mockMvc.perform(conJson(put("/usuario/modificar"), tokenLector(), Map.of("descripcion", "A".repeat(256))))
            .andExpect(status().isBadRequest());
    }

    @Test 
    @DisplayName("PUT /usuario/modificar/rol: admin promueve a ESCRITOR y el cambio es de inmediato")
    void modificarRol() throws Exception {
        Rol lector = rolRepository.findByNombre("LECTOR").orElseThrow();
        Rol escritor = rolRepository.findByNombre("ESCRITOR").orElseThrow();

        mockMvc.perform(conJson(put("/usuario/modificar/rol"), tokenAdmin(),
                Map.of("username", "lector2@gmail.com", "roles", List.of(lector, escritor))))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.roles.length()").value(2));

        mockMvc.perform(conJson(post("/libro/crear"), tokenDe("lector2@gmail.com"),
                Map.of("titulo", "Mi novela", "descripcion", "Una historia", "paginas", 120,
                       "edicion", "Primera", "categorias", List.of("drama"))))
            .andExpect(status().isOk());
    }

    @Test 
    @DisplayName("PUT /usuario/modificar/rol: LECTOR recibe 403; sin roles 400")
    void modificarRolPermisos() throws Exception {
        mockMvc.perform(conJson(put("/usuario/modificar/rol"), tokenLector(), Map.of("username", "lector2@gmail.com")))
            .andExpect(status().isForbidden());
        mockMvc.perform(conJson(put("/usuario/modificar/rol"), tokenAdmin(), Map.of("username", "lector2@gmail.com")))
            .andExpect(status().isBadRequest());
    }

    @Test 
    @DisplayName("GET /usuario/datos-cantidades: cuenta libros, publicaciones y posteos del usuario")
    void datosCantidades() throws Exception {
        mockMvc.perform(conToken(get("/usuario/datos-cantidades"), tokenDe("jrolando@gmail.com")))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.cantLibros").value(4))
            .andExpect(jsonPath("$.cantPrestamos").value(2))
            .andExpect(jsonPath("$.cantPublicacionesSociales").value(1));
    }

    @Test 
    @DisplayName("GET /usuario: solo admin (LECTOR 403)")
    void listado() throws Exception {
        mockMvc.perform(conToken(get("/usuario"), tokenAdmin()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()", greaterThanOrEqualTo(12)));
        mockMvc.perform(conToken(get("/usuario"), tokenLector())).andExpect(status().isForbidden());
    }
}