package pio.daw.proyectoRA9.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pio.daw.proyectoRA9.models.Cliente;

import java.util.List;
import java.util.Optional;

//repository:accede a la base de datos
 
public interface ClienteRepository extends JpaRepository<Cliente, Long> {//para tener los metodos automaticamente
    //buscar un nombre en mayusculas o minusculas
    List<Cliente> findByNombreContainingIgnoreCase(String nombre);

    //buscar por matricula aproximada con mayusculas o minusculas  
    List<Cliente> findByMatriculaContainingIgnoreCase(String matricula);
    //buscar por matricula exacta con mayusculas o minusculas
    Optional<Cliente> findByMatriculaIgnoreCase(String matricula);
}
