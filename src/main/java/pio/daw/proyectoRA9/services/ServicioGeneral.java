package pio.daw.proyectoRA9.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pio.daw.proyectoRA9.models.Cliente;
import pio.daw.proyectoRA9.models.Reparacion;
import pio.daw.proyectoRA9.repositories.ClienteRepository;
import pio.daw.proyectoRA9.repositories.ReparacionRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service // tiene la logica (CRUD)
@Transactional //para que las operaciones se ejecuten dentro de una transaccion
public class ServicioGeneral {

    private final ClienteRepository clienteRepository;
    private final ReparacionRepository reparacionRepository;

    public ServicioGeneral(ClienteRepository clienteRepository, ReparacionRepository reparacionRepository) {
        this.clienteRepository = clienteRepository;
        this.reparacionRepository = reparacionRepository;
    }

    //para clientes

    @Transactional(readOnly = true)
    public List<Cliente> listarClientes(String nombre, String matricula) {
        if (nombre != null && !nombre.isBlank()) {
            return clienteRepository.findByNombreContainingIgnoreCase(nombre.trim());
        }
        if (matricula != null && !matricula.isBlank()) {
            return clienteRepository.findByMatriculaContainingIgnoreCase(matricula.trim());
        }
        return clienteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Cliente obtenerCliente(Long id) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);

        if (clienteOptional.isPresent()) {
            return clienteOptional.get();
        } else {
            throw new NoSuchElementException("No existe el cliente con id " + id);
        }
    }

    public Cliente guardarCliente(Cliente cliente) {
        String matricula = cliente.getMatricula();

        if (matricula != null) {
            matricula = matricula.toUpperCase().trim();
            cliente.setMatricula(matricula);
        } else {
            cliente.setMatricula(null);
        }

        return clienteRepository.save(cliente);
    }

    public Cliente actualizarCliente(Long id, Cliente datos) {
        Cliente cliente = obtenerCliente(id);

        cliente.setNombre(datos.getNombre());
        cliente.setTelefono(datos.getTelefono());
        cliente.setEmail(datos.getEmail());

        String matricula = datos.getMatricula();

        if (matricula != null) {
            matricula = matricula.toUpperCase().trim();
            cliente.setMatricula(matricula);
        } else {
            cliente.setMatricula(null);
        }

        return clienteRepository.save(cliente);
    }

    public void borrarCliente(Long id) {
        Cliente cliente = obtenerCliente(id);
        clienteRepository.delete(cliente);
    }

    //para reparaciones

    @Transactional(readOnly = true)
    public List<Reparacion> listarReparaciones(String estado, Long clienteId, LocalDate desde, LocalDate hasta) {
        return reparacionRepository.buscarConFiltros(normalizarTexto(estado), clienteId, desde, hasta);
    }

    @Transactional(readOnly = true)
    public Reparacion obtenerReparacion(Long id) {
        return reparacionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No existe la reparación con id " + id));
    }

    public Reparacion guardarReparacion(Reparacion reparacion, Long clienteId) {
        Cliente cliente = obtenerCliente(clienteId);
        reparacion.setCliente(cliente);
        return reparacionRepository.save(reparacion);
    }

    public Reparacion actualizarReparacion(Long id, Reparacion datos, Long clienteId) {
        Reparacion reparacion = obtenerReparacion(id);
        reparacion.setDescripcion(datos.getDescripcion());
        reparacion.setFechaEntrada(datos.getFechaEntrada());
        reparacion.setFechaSalida(datos.getFechaSalida());
        reparacion.setCosteEuros(datos.getCosteEuros());
        reparacion.setEstado(datos.getEstado());
        if (clienteId != null) {
            reparacion.setCliente(obtenerCliente(clienteId));
        }
        return reparacionRepository.save(reparacion);
    }

    public void borrarReparacion(Long id) {
        Reparacion reparacion = obtenerReparacion(id);
        reparacionRepository.delete(reparacion);
    }

    @Transactional(readOnly = true)
    public List<Reparacion> listarReparacionesCliente(Long clienteId) {
        obtenerCliente(clienteId);
        return reparacionRepository.findByClienteId(clienteId);
    }

    @Transactional(readOnly = true)
    public List<Reparacion> buscarReparacionesAbiertasEntre(LocalDate desde, LocalDate hasta) {
        return reparacionRepository.buscarAbiertasEntre(desde, hasta);
    }

    @Transactional(readOnly = true)
    public Double calcularGastoTotalCliente(Long clienteId) {
        obtenerCliente(clienteId);
        return reparacionRepository.gastoTotalCliente(clienteId);
    }

    private String normalizarTexto(String texto) {// Para que funcionen bien los filtros
        if (texto == null || texto.isBlank()) {
        return null;
        }

    return texto.trim();
    }
}
