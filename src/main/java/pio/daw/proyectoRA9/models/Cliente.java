package pio.daw.proyectoRA9.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor // me genera automaticamente el constructor vacio, por eso no lo añado despues.
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")//para validar que no este vacio
    private String nombre;

    @NotBlank(message = "El telefono es obligatorio")
    private String telefono;
    private String email;

    @NotBlank(message = "La matricula es obligatoria")
    @Column(unique = true, nullable = false) // para que no se repita ni este vacia
    private String matricula;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)//un cliente puede tener muchas reparaciones. Si borro un cliente, tambien sus reparaciones y no se pueden quedar reparaciones sin cliente
    @JsonIgnore // para que no se genere un bucle infinito al serializar el cliente
    private List<Reparacion> reparaciones = new ArrayList<>();

    public Cliente(String nombre, String telefono, String email, String matricula) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.matricula = matricula;

    }

    // getters
    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public String getMatricula() {
        return matricula;
    }

    public List<Reparacion> getReparaciones() {
        return reparaciones;
    }

    //setters

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void setReparaciones(List<Reparacion> reparaciones) {
        this.reparaciones = reparaciones;
    }

}
