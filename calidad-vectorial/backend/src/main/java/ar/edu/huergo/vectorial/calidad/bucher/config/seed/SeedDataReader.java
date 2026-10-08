package ar.edu.huergo.vectorial.calidad.bucher.config.seed;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
// Lee archivos JSON de seed ubicados en el classpath y los convierte en objetos
public class SeedDataReader {

    private final ObjectMapper objectMapper;
    private final PathMatchingResourcePatternResolver resolver =
        new PathMatchingResourcePatternResolver();

    /**
     * Lee un archivo JSON que contiene una lista de elementos
     * @param ruta Ruta del archivo dentro del classpath
     * @param tipo Tipo de los elementos de la lista
     * @return La lista de elementos leídos
     */
    public <T> List<T> leer(String ruta, Class<T> tipo) {
        return leerRecurso(new ClassPathResource(ruta), tipo);
    }

    /**
     * Lee todos los archivos JSON que coinciden con un patrón, en orden alfabético
     * @param patron Patrón de búsqueda dentro del classpath
     * @param tipo Tipo de los elementos de cada lista
     * @return La lista con los elementos de todos los archivos
     */
    public <T> List<T> leerTodos(String patron, Class<T> tipo) {
        try {
            Resource[] recursos = resolver.getResources("classpath:" + patron);
            Arrays.sort(recursos, Comparator.comparing(Resource::getFilename));
            List<T> elementos = new ArrayList<>();
            for (Resource recurso : recursos) {
                elementos.addAll(leerRecurso(recurso, tipo));
            }
            return elementos;
        } catch (IOException e) {
            throw new IllegalStateException("No se pudieron listar los archivos " + patron, e);
        }
    }

    private <T> List<T> leerRecurso(Resource recurso, Class<T> tipo) {
        try (InputStream entrada = recurso.getInputStream()) {
            JavaType tipoLista = objectMapper
                .getTypeFactory()
                .constructCollectionType(List.class, tipo);
            return objectMapper.readValue(entrada, tipoLista);
        } catch (IOException e) {
            throw new IllegalStateException(
                "Error leyendo el archivo de seed " + recurso.getFilename() + ": " + e.getMessage(),
                e
            );
        }
    }
}