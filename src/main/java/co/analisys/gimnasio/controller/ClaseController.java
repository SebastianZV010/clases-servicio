package co.analisys.gimnasio.controller;

import co.analisys.gimnasio.dto.ErrorResponse;
import co.analisys.gimnasio.model.Clase;
import co.analisys.gimnasio.service.ClaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Clases", description = "Gestión de clases en el gimnasio")
public class ClaseController {

    private final ClaseService service;

    public ClaseController(ClaseService service) {
        this.service = service;
    }

    // ---------- CRUD ----------
    @Operation(
            summary = "Obtener todas las clases",
            description = "Devuelve la lista de todas las clases disponibles.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de clases encontrada"),
                    @ApiResponse(responseCode = "401", description = "No autorizado",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "403", description = "Prohibido",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEMBER', 'ROLE_TRAINER')")
    public List<Clase> getAll() {
        return service.findAll();
    }

    @Operation(
            summary = "Obtener clase por ID",
            description = "Busca una clase por su identificador.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Clase encontrada"),
                    @ApiResponse(responseCode = "404", description = "Clase no encontrada"),
                    @ApiResponse(responseCode = "401", description = "No autorizado",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "403", description = "Prohibido",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEMBER', 'ROLE_TRAINER')")
    public ResponseEntity<Clase> getById(@PathVariable Long id) {
        Optional<Clase> clase = service.findById(id);
        return clase.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Crear una nueva clase",
            description = "Permite a un administrador o entrenador crear una clase.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Clase creada"),
                    @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
                    @ApiResponse(responseCode = "401", description = "No autorizado",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "403", description = "Prohibido",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
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

    @Operation(
            summary = "Actualizar una clase",
            description = "Permite actualizar la información de una clase existente.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Clase actualizada"),
                    @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
                    @ApiResponse(responseCode = "401", description = "No autorizado",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "403", description = "Prohibido",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
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

    @Operation(
            summary = "Eliminar una clase",
            description = "Permite eliminar una clase existente por su ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Clase eliminada"),
                    @ApiResponse(responseCode = "404", description = "Clase no encontrada"),
                    @ApiResponse(responseCode = "401", description = "No autorizado",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "403", description = "Prohibido",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
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

    // ---------- BÚSQUEDAS ----------
    @Operation(summary = "Buscar clases por entrenador asignado")
    @GetMapping("/entrenador/{entrenadorAsignado}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEMBER', 'ROLE_TRAINER')")
    public List<Clase> getByEntrenador(@PathVariable String entrenadorAsignado) {
        return service.findByEntrenadorAsignado(entrenadorAsignado);
    }

    @Operation(summary = "Buscar clases por estado")
    @GetMapping("/estado/{estado}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEMBER', 'ROLE_TRAINER')")
    public List<Clase> getByEstado(@PathVariable String estado) {
        return service.findByEstado(estado);
    }

    @Operation(summary = "Buscar clases por nivel de dificultad")
    @GetMapping("/nivel/{nivelDificultad}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEMBER', 'ROLE_TRAINER')")
    public List<Clase> getByNivelDificultad(@PathVariable String nivelDificultad) {
        return service.findByNivelDificultad(nivelDificultad);
    }

    @Operation(summary = "Obtener clases activas")
    @GetMapping("/activas")
    public List<Clase> getClasesActivas() {
        return service.findClasesActivas();
    }

    @Operation(summary = "Buscar clases por rango de fechas")
    @GetMapping("/fechas")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEMBER', 'ROLE_TRAINER')")
    public List<Clase> getByFechaRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        return service.findByFechaHoraBetween(fechaInicio, fechaFin);
    }

    // ---------- ACCIONES ----------
    @Operation(summary = "Cancelar clase")
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

    @Operation(summary = "Completar clase")
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
