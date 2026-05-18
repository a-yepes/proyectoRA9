package pio.daw.proyectoRA9.controllers;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pio.daw.proyectoRA9.models.Cliente;
import pio.daw.proyectoRA9.models.Reparacion;
import pio.daw.proyectoRA9.services.ServicioGeneral;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController //los metodos devuelven json
@RequestMapping("/api") //todos los endpoints empiezan por /api
public class ApiController {

    private final ServicioGeneral servicio;

    public ApiController(ServicioGeneral servicio) {
        this.servicio = servicio;
    }

    @PostMapping("/clientes")
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente crearCliente( @RequestBody Cliente cliente) {
        return servicio.guardarCliente(cliente); //recibe el json con clientes y lo guarda en la bd
    }

    @GetMapping("/clientes")
    public List<Cliente> listarClientes(@RequestParam(required = false) String nombre,
                                        @RequestParam(required = false) String matricula) {
        return servicio.listarClientes(nombre, matricula); //si no se pasan parametros, devuelve todos los clientes. Si se pasan, filtra por nombre o matricula
    }

    @GetMapping("/clientes/{id}")
    public Cliente obtenerCliente(@PathVariable Long id) {
        return servicio.obtenerCliente(id); //devuelve el cliente que corresponda con el id
    }

    @PutMapping("/clientes/{id}")
    public Cliente actualizarCliente(@PathVariable Long id,  @RequestBody Cliente cliente) {
        return servicio.actualizarCliente(id, cliente); //actualiza 
    }

    @DeleteMapping("/clientes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) //cuando lo borre, lanza un mensaje 204 No content (borrado correctamente)
    public void borrarCliente(@PathVariable Long id) {
        servicio.borrarCliente(id);
    }

    @GetMapping("/clientes/{id}/reparaciones")
    public List<Reparacion> reparacionesDeCliente(@PathVariable Long id) {
        return servicio.listarReparacionesCliente(id); //devuelve todas las reparaciones de ese cliente
    }

    @GetMapping("/clientes/{id}/gasto-total")
    public Map<String, Object> gastoTotalCliente(@PathVariable Long id) {
        Cliente cliente = servicio.obtenerCliente(id);
        return Map.of(
                "clienteId", cliente.getId(),
                "cliente", cliente.getNombre(),
                "gastoTotal", servicio.calcularGastoTotalCliente(id)
        );
    }

    @PostMapping("/reparaciones")
    @ResponseStatus(HttpStatus.CREATED) //devuelveun mensaje 201 Created (reparacion creada correctamente)
    public Reparacion crearReparacion(@RequestBody Reparacion reparacion, @RequestParam Long clienteId) {
        return servicio.guardarReparacion(reparacion, clienteId);
    }

    @GetMapping("/reparaciones")
    public List<Reparacion> listarReparaciones(@RequestParam(required = false) String estado, @RequestParam(required = false) Long clienteId,
                                               @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
                                               @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        return servicio.listarReparaciones(estado, clienteId, desde, hasta);
    }

    @GetMapping("/reparaciones/{id}")
    public Reparacion obtenerReparacion(@PathVariable Long id) {
        return servicio.obtenerReparacion(id);
    }

    @PutMapping("/reparaciones/{id}")
    public Reparacion actualizarReparacion(@PathVariable Long id, @RequestBody Reparacion reparacion, @RequestParam(required = false) Long clienteId) {
        return servicio.actualizarReparacion(id, reparacion, clienteId);
    }

    @DeleteMapping("/reparaciones/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void borrarReparacion(@PathVariable Long id) {
        servicio.borrarReparacion(id);
    }

    @GetMapping("/reparaciones/abiertas")
    public List<Reparacion> reparacionesAbiertasEntre(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
                                                      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        return servicio.buscarReparacionesAbiertasEntre(desde, hasta);
    }

    //para que con el NoSuchElementException que he puesto salga un error 404 Not Found 
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Map<String, String>> manejarNoEncontrado(NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", ex.getMessage()));
    }
}
