package pio.daw.proyectoRA9.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pio.daw.proyectoRA9.models.Reparacion;

import java.time.LocalDate;
import java.util.List;

public interface ReparacionRepository extends JpaRepository<Reparacion, Long> {

    //bucar por estado, cliente o ambos en mayusculas o minusculas
    List<Reparacion> findByEstadoContainingIgnoreCase(String estado);
    List<Reparacion> findByClienteId(Long clienteId);
    List<Reparacion> findByClienteIdAndEstadoContainingIgnoreCase(Long clienteId, String estado);

    //consulta para que filtre por estado, cliente y fechas desde la mas reciente
    @Query("""
            select r from Reparacion r
            where (:estado is null or lower(r.estado) like lower(concat('%', :estado, '%')))
              and (:clienteId is null or r.cliente.id = :clienteId)
              and (:desde is null or r.fechaEntrada >= :desde)
              and (:hasta is null or r.fechaEntrada <= :hasta)
            order by r.fechaEntrada desc
            """)
    List<Reparacion> buscarConFiltros(@Param("estado") String estado, @Param("clienteId") Long clienteId,
                                      @Param("desde") LocalDate desde, @Param("hasta") LocalDate hasta);

    //consulta para buscar reparaciones abiertas (sin fecha de salida)
    @Query("""
            select r from Reparacion r
            where r.fechaSalida is null
              and (:desde is null or r.fechaEntrada >= :desde)
              and (:hasta is null or r.fechaEntrada <= :hasta)
            order by r.fechaEntrada desc
            """)
    List<Reparacion> buscarAbiertasEntre(@Param("desde") LocalDate desde, @Param("hasta") LocalDate hasta);
    
    //consulta para sacar el gasto total de un cliente
    @Query("select coalesce(sum(r.costeEuros), 0) from Reparacion r where r.cliente.id = :clienteId")
    Double gastoTotalCliente(@Param("clienteId") Long clienteId);
}
