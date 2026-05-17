package pio.daw.proyectoRA9.controllers;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pio.daw.proyectoRA9.models.Cliente;
import pio.daw.proyectoRA9.models.Reparacion;
import pio.daw.proyectoRA9.services.ServicioGeneral;

import java.time.LocalDate;

@Controller
public class WebController { //parte visual de thymeleaf, devuelve las plantillas html que estan en resources/templates

    private final ServicioGeneral servicio;

    public WebController(ServicioGeneral servicio) {
        this.servicio = servicio;
    }

    @GetMapping({"/", "/web"}) //localhost:8080 lleva a la lista de clientes
    public String inicio() {
        return "redirect:/web/clientes";
    }

    @GetMapping("/web/clientes") //carga los clientes en la plantilla clientes/lista.html
    public String listarClientes(@RequestParam(required = false) String nombre,
                                 @RequestParam(required = false) String matricula,
                                 Model model) {
        model.addAttribute("clientes", servicio.listarClientes(nombre, matricula));
        model.addAttribute("nombre", nombre);
        model.addAttribute("matricula", matricula);
        return "clientes/lista";
    }

    @GetMapping("/web/clientes/nueva")
    public String nuevoCliente(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "clientes/formulario";
    }

    @PostMapping("/web/clientes/nueva")
    public String guardarCliente(@ModelAttribute Cliente cliente) {
        servicio.guardarCliente(cliente);
        return "redirect:/web/clientes";
    }

    @GetMapping("/web/clientes/editar/{id}")
    public String editarCliente(@PathVariable Long id, Model model) {
        model.addAttribute("cliente", servicio.obtenerCliente(id));
        return "clientes/formulario";
    }

    @PostMapping("/web/clientes/editar/{id}")
    public String actualizarCliente(@PathVariable Long id, @ModelAttribute Cliente cliente) {
        servicio.actualizarCliente(id, cliente);
        return "redirect:/web/clientes";
    }

    @PostMapping("/web/clientes/borrar/{id}") //con post porque los formularios html no soportan delete
    public String borrarCliente(@PathVariable Long id) {
        servicio.borrarCliente(id);
        return "redirect:/web/clientes";
    }

    @GetMapping("/web/clientes/{id}")
    public String detalleCliente(@PathVariable Long id, Model model) {
        model.addAttribute("cliente", servicio.obtenerCliente(id));
        model.addAttribute("reparaciones", servicio.listarReparacionesCliente(id));
        model.addAttribute("gastoTotal", servicio.calcularGastoTotalCliente(id));
        return "clientes/detalle";
    }

    @GetMapping("/web/reparaciones")
    public String listarReparaciones(@RequestParam(required = false) String estado,
                                     @RequestParam(required = false) Long clienteId,
                                     @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
                                     @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta,
                                     Model model) {
        model.addAttribute("reparaciones", servicio.listarReparaciones(estado, clienteId, desde, hasta));
        model.addAttribute("clientes", servicio.listarClientes(null, null));
        model.addAttribute("estado", estado);
        model.addAttribute("clienteId", clienteId);
        model.addAttribute("desde", desde);
        model.addAttribute("hasta", hasta);
        return "reparaciones/lista";
    }

    @GetMapping("/web/reparaciones/nueva")
    public String nuevaReparacion(@RequestParam(required = false) Long clienteId, Model model) {
        Reparacion reparacion = new Reparacion();
        if (clienteId != null) {
            reparacion.setCliente(servicio.obtenerCliente(clienteId));
        }
        model.addAttribute("reparacion", reparacion);
        model.addAttribute("clientes", servicio.listarClientes(null, null));
        return "reparaciones/formulario";
    }

    @PostMapping("/web/reparaciones/nueva")
    public String guardarReparacion(@ModelAttribute Reparacion reparacion, @RequestParam Long clienteId) {
        servicio.guardarReparacion(reparacion, clienteId);
        return "redirect:/web/reparaciones";
    }

    @GetMapping("/web/reparaciones/editar/{id}")
    public String editarReparacion(@PathVariable Long id, Model model) {
        model.addAttribute("reparacion", servicio.obtenerReparacion(id));
        model.addAttribute("clientes", servicio.listarClientes(null, null));
        return "reparaciones/formulario";
    }

    @PostMapping("/web/reparaciones/editar/{id}")
    public String actualizarReparacion(@PathVariable Long id,
                                       @ModelAttribute Reparacion reparacion,
                                       @RequestParam Long clienteId) {
        servicio.actualizarReparacion(id, reparacion, clienteId);
        return "redirect:/web/reparaciones";
    }

    @PostMapping("/web/reparaciones/borrar/{id}")
    public String borrarReparacion(@PathVariable Long id) {
        servicio.borrarReparacion(id);
        return "redirect:/web/reparaciones";
    }

    @GetMapping("/web/reparaciones/{id}")
    public String detalleReparacion(@PathVariable Long id, Model model) {
        model.addAttribute("reparacion", servicio.obtenerReparacion(id));
        return "reparaciones/detalle";
    }
}

