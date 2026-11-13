package ar.edu.huergo.vectorial.calidad.bucher.config.seed;

import ar.edu.huergo.vectorial.calidad.bucher.config.seed.dto.SuscripcionSeed;
import ar.edu.huergo.vectorial.calidad.bucher.entity.subscription.Suscripcion;
import ar.edu.huergo.vectorial.calidad.bucher.repository.subscription.SuscripcionRepository;
import ar.edu.huergo.vectorial.calidad.bucher.service.payment.PrecioHistoricoSuscripcionService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(11)
@RequiredArgsConstructor
public class SuscripcionSeeder implements Seeder {

    private static final String ARCHIVO = "seed/suscripciones.json";

    private final SeedDataReader reader;
    private final SuscripcionRepository suscripcionRepository;
    private final PrecioHistoricoSuscripcionService precioHistoricoSuscripcionService;

    @Override
    public void ejecutar() {
        reader.leer(ARCHIVO, SuscripcionSeed.class).forEach(this::crearSiNoExiste);
    }

    private void crearSiNoExiste(SuscripcionSeed seed) {
        if (suscripcionRepository.findByNombreIgnoreCase(seed.nombre()).isPresent()) {
            return;
        }

        Suscripcion suscripcion = new Suscripcion();
        suscripcion.setNombre(seed.nombre());
        suscripcion.setPorcentajeCobertura(seed.porcentajeCobertura());
        suscripcion.setLimiteLibros(seed.limiteLibros());

        Suscripcion suscripcionGuardada = suscripcionRepository.save(suscripcion);
        precioHistoricoSuscripcionService.crearPrecioHistorico(suscripcionGuardada, seed.precio());
    }
}