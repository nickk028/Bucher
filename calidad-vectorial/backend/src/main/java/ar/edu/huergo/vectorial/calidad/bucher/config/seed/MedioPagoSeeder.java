package ar.edu.huergo.vectorial.calidad.bucher.config.seed;

import ar.edu.huergo.vectorial.calidad.bucher.config.seed.dto.MedioPagoSeed;
import ar.edu.huergo.vectorial.calidad.bucher.entity.payment.MedioPago;
import ar.edu.huergo.vectorial.calidad.bucher.repository.payment.MedioPagoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(10)
@RequiredArgsConstructor
public class MedioPagoSeeder implements Seeder {

    private static final String ARCHIVO = "seed/medios-pago.json";

    private final SeedDataReader reader;
    private final MedioPagoRepository medioPagoRepository;

    @Override
    public void ejecutar() {
        reader.leer(ARCHIVO, MedioPagoSeed.class).forEach(this::crearSiNoExiste);
    }

    private void crearSiNoExiste(MedioPagoSeed seed) {
        medioPagoRepository
            .findByNombreIgnoreCase(seed.nombre())
            .orElseGet(() -> medioPagoRepository.save(crearMedioPago(seed)));
    }

    private MedioPago crearMedioPago(MedioPagoSeed seed) {
        MedioPago medioPago = new MedioPago();
        medioPago.setNombre(seed.nombre());
        medioPago.setPorcentajeComision(seed.porcentajeComision());
        return medioPago;
    }
}