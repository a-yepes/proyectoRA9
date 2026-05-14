package pio.daw.proyectoRA9.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

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
@Data
@NoArgsConstructor //me genera automaticamente el constructor vacio, por eso no lo añado despues.
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nombre;
    private String telefono;
    private String email;
    @Column(unique=true)
    private String matricula;

    @OneToMany(mappedBy = "reparacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reparacion> reparaciones = new ArrayList<>();

    public Cliente(Long id, String nombre, String telefono, String email, String matricula) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.matricula = matricula;
       
    }
    

    

    

    
}
