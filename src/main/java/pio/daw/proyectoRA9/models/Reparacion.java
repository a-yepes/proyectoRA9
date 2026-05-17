package pio.daw.proyectoRA9.models;

import java.sql.Date;
import java.time.LocalDate;

import org.antlr.v4.runtime.misc.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Reparacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion;
    @NotBlank(message = "La fecha de entrada es obligatoria")
    @JsonFormat(pattern = "dd-MM-yyyy", shape = Shape.STRING)
    private LocalDate fechaEntrada;

   //para salida no uso notblank porque si la reparcion esta sin terminar tiene que verse
    @JsonFormat(pattern = "dd-MM-yyyy", shape = Shape.STRING)
    private LocalDate fechaSalida;

    
    private Double costeEuros;
    private String estado;

    @ManyToOne(optional=false)//muchas reparaciones pueden ser de un mismo cliente y siempre tienen que tener un cliente
    @JsonIgnoreProperties("reparaciones")//para evitar el bucle infinito al serializar 
    private Cliente cliente;

    public Reparacion(String descripcion, LocalDate fechaEntrada, LocalDate fechaSalida, Double costeEuros, String estado, Cliente cliente) {
        this.descripcion = descripcion;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.costeEuros = costeEuros;
        this.estado = estado;
        this.cliente = cliente;
    }

    // getters

    public Long getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public LocalDate getFechaEntrada() {
        return fechaEntrada;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public Double getCosteEuros() {
        return costeEuros;
    }

    public String getEstado() {
        return estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    // setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFechaEntrada(LocalDate fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public void setCosteEuros(Double costeEuros) {
        this.costeEuros = costeEuros;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

}
