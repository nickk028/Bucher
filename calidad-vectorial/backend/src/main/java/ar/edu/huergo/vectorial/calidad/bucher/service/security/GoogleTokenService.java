package ar.edu.huergo.vectorial.calidad.bucher.service.security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;

/**
* Servicio responsable de verificar los idToken emitidos por Google.
*
* Conceptos clave: - El idToken es un JWT firmado por Google que certifica la identidad
* del usuario. - Se verifica la firma y el "audience" (client-id de la app) contra los
* servidores de Google antes de confiar en los datos del payload.
*/
@Service
public class GoogleTokenService {

    private final GoogleIdTokenVerifier verifier;

    public GoogleTokenService(@Value("${google.oauth.client-id}") String clientId) {
        this.verifier = new GoogleIdTokenVerifier.Builder(
                new NetHttpTransport(), GsonFactory.getDefaultInstance())
            .setAudience(Collections.singletonList(clientId))
            .build();
    }

    /**
    * Verifica un idToken de Google y devuelve su payload.
    * @param idTokenString El idToken recibido del cliente
    * @return El payload verificado con los datos del usuario de Google
    * @throws IllegalArgumentException Si el token es inválido o no pudo verificarse
    */
    public GoogleIdToken.Payload verificarToken(String idTokenString) {
        try {
            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken == null) {
                throw new IllegalArgumentException("Token de Google inválido");
            }
            return idToken.getPayload();
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalArgumentException("No se pudo verificar el token de Google");
        }
    }
}