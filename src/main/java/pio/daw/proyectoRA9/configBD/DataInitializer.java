package pio.daw.proyectoRA9.configBD;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pio.daw.proyectoRA9.models.Cliente;
import pio.daw.proyectoRA9.models.Reparacion;
import pio.daw.proyectoRA9.repositories.ClienteRepository;
import pio.daw.proyectoRA9.repositories.ReparacionRepository;

import java.time.LocalDate;


@Component
public class DataInitializer implements CommandLineRunner {//clase para que la base de datos empiece con datos para probarla

    private final ClienteRepository clienteRepository;
    private final ReparacionRepository reparacionRepository;

    public DataInitializer(ClienteRepository clienteRepository, ReparacionRepository reparacionRepository) {
        this.clienteRepository = clienteRepository;
        this.reparacionRepository = reparacionRepository;
    }

    @Override
    public void run(String... args) {
        if (clienteRepository.count() > 0) {
            return;
        }

        Cliente susana = clienteRepository.save(new Cliente("Susana Oria", "600111222", "susana@email.com", "1234ABC"));
        Cliente aitor = clienteRepository.save(new Cliente("Aitor Tilla", "600333444", "aitor@email.com", "5678DEF"));
        Cliente sara = clienteRepository.save(new Cliente("Sara López", "600555666", "sara@email.com", "9012GHI"));

        reparacionRepository.save(new Reparacion("Cambio de aceite y filtros", LocalDate.now().minusDays(12), LocalDate.now().minusDays(11), 95.50, "finalizado", susana));
        reparacionRepository.save(new Reparacion("Revisión de frenos", LocalDate.now().minusDays(5), null, 180.00, "en curso", susana));
        reparacionRepository.save(new Reparacion("Sustitución de batería", LocalDate.now().minusDays(3), LocalDate.now().minusDays(2), 140.00, "finalizado", aitor));
        reparacionRepository.save(new Reparacion("Diagnóstico de motor", LocalDate.now().minusDays(1), null, 60.00, "pendiente", sara));
    }
}