package co.analisys.gimnasio.controller;

import co.analisys.gimnasio.model.Clase;
import co.analisys.gimnasio.service.ClaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clases")
@CrossOrigin(origins = "*")
public class ClaseController {
    private final ClaseService service;

    public ClaseController(ClaseService service) {
        this.service = service;
    }

    // Endpoints CRUD básicos
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEMBER', 'ROLE_TRAINER')")
    public List<Clase> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEMBER', 'ROLE_TRAINER')")
    public ResponseEntity<Clase> getById(@PathVariable Long id) {
        Optional<Clase> clase = service.findById(id);
        return clase.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TRAINER')")
    public ResponseEntity<Clase> create(@RequestBody Clase clase) {
        try {
            Clase nuevaClase = service.save(clase);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaClase);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TRAINER')")
    public ResponseEntity<Clase> update(@PathVariable Long id, @RequestBody Clase clase) {
        try {
            clase.setId(id);
            Clase claseActualizada = service.save(clase);
            return ResponseEntity.ok(claseActualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TRAINER')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoints de búsqueda específicos
    @GetMapping("/entrenador/{entrenadorAsignado}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEMBER', 'ROLE_TRAINER')")
    public List<Clase> getByEntrenador(@PathVariable String entrenadorAsignado) {
        return service.findByEntrenadorAsignado(entrenadorAsignado);
    }

    @GetMapping("/estado/{estado}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEMBER', 'ROLE_TRAINER')")
    public List<Clase> getByEstado(@PathVariable String estado) {
        return service.findByEstado(estado);
    }

    @GetMapping("/nivel/{nivelDificultad}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEMBER', 'ROLE_TRAINER')")
    public List<Clase> getByNivelDificultad(@PathVariable String nivelDificultad) {
        return service.findByNivelDificultad(nivelDificultad);
    }

    @GetMapping("/activas")
    public List<Clase> getClasesActivas() {
        return service.findClasesActivas();
    }

    @GetMapping("/fechas")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEMBER', 'ROLE_TRAINER')")
    public List<Clase> getByFechaRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        return service.findByFechaHoraBetween(fechaInicio, fechaFin);
    }

    // Endpoints de acciones específicas
    @PutMapping("/{id}/cancelar")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TRAINER')")
    public ResponseEntity<Clase> cancelarClase(@PathVariable Long id) {
        try {
            Clase claseCancelada = service.cancelarClase(id);
            return ResponseEntity.ok(claseCancelada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/completar")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TRAINER')")
    public ResponseEntity<Clase> completarClase(@PathVariable Long id) {
        try {
            Clase claseCompletada = service.completarClase(id);
            return ResponseEntity.ok(claseCompletada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
