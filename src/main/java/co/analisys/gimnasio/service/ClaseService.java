package co.analisys.gimnasio.service;

import co.analisys.gimnasio.model.Clase;
import co.analisys.gimnasio.repository.ClaseRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ClaseService {
    private final ClaseRepository repository;

    public ClaseService(ClaseRepository repository) {
        this.repository = repository;
    }

    // Operaciones CRUD básicas
    public List<Clase> findAll() {
        return repository.findAll();
    }

    public Optional<Clase> findById(Long id) {
        return repository.findById(id);
    }

    public Clase save(Clase clase) {
        // Validaciones de negocio
        if (clase.getCapacidadMaxima() != null && clase.getCapacidadMaxima() <= 0) {
            throw new IllegalArgumentException("La capacidad máxima debe ser mayor a 0");
        }
        if (clase.getDuracionMinutos() != null && clase.getDuracionMinutos() <= 0) {
            throw new IllegalArgumentException("La duración debe ser mayor a 0 minutos");
        }
        if (clase.getPrecio() != null && clase.getPrecio() < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }
        
        // Si es una nueva clase, establecer estado por defecto
        if (clase.getId() == null && clase.getEstado() == null) {
            clase.setEstado("ACTIVA");
        }
        
        return repository.save(clase);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    // Métodos de búsqueda específicos
    public List<Clase> findByEntrenadorAsignado(String entrenadorAsignado) {
        return repository.findByEntrenadorAsignado(entrenadorAsignado);
    }

    public List<Clase> findByEstado(String estado) {
        return repository.findByEstado(estado);
    }

    public List<Clase> findByNivelDificultad(String nivelDificultad) {
        return repository.findByNivelDificultad(nivelDificultad);
    }

    public List<Clase> findByFechaHoraBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return repository.findByFechaHoraBetween(fechaInicio, fechaFin);
    }

    public List<Clase> findClasesActivas() {
        return repository.findClasesActivas(LocalDateTime.now());
    }

    // Métodos de lógica de negocio
    public Clase cancelarClase(Long id) {
        Optional<Clase> claseOpt = repository.findById(id);
        if (claseOpt.isPresent()) {
            Clase clase = claseOpt.get();
            clase.setEstado("CANCELADA");
            return repository.save(clase);
        }
        throw new RuntimeException("Clase no encontrada con ID: " + id);
    }

    public Clase completarClase(Long id) {
        Optional<Clase> claseOpt = repository.findById(id);
        if (claseOpt.isPresent()) {
            Clase clase = claseOpt.get();
            clase.setEstado("COMPLETA");
            return repository.save(clase);
        }
        throw new RuntimeException("Clase no encontrada con ID: " + id);
    }
}
