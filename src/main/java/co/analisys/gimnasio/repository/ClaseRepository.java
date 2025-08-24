package co.analisys.gimnasio.repository;

import co.analisys.gimnasio.model.Clase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface ClaseRepository extends JpaRepository<Clase, Long> {
    
    // Buscar clases por entrenador asignado
    List<Clase> findByEntrenadorAsignado(String entrenadorAsignado);
    
    // Buscar clases por estado
    List<Clase> findByEstado(String estado);
    
    // Buscar clases por nivel de dificultad
    List<Clase> findByNivelDificultad(String nivelDificultad);
    
    // Buscar clases en un rango de fechas
    @Query("SELECT c FROM Clase c WHERE c.fechaHora BETWEEN :fechaInicio AND :fechaFin")
    List<Clase> findByFechaHoraBetween(@Param("fechaInicio") LocalDateTime fechaInicio, 
                                       @Param("fechaFin") LocalDateTime fechaFin);
    
    // Buscar clases activas
    @Query("SELECT c FROM Clase c WHERE c.estado = 'ACTIVA' AND c.fechaHora > :fechaActual ORDER BY c.fechaHora")
    List<Clase> findClasesActivas(@Param("fechaActual") LocalDateTime fechaActual);
}
