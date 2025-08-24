package co.analisys.gimnasio.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
public class Clase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;
    private Integer duracionMinutos;
    private Integer capacidadMaxima;
    private String entrenadorAsignado;
    private LocalDateTime fechaHora;
    private String nivelDificultad; // PRINCIPIANTE, INTERMEDIO, AVANZADO
    private Double precio;
    private String estado; // ACTIVA, CANCELADA, COMPLETA

    public Clase() {
    }

    public Clase(Long id, String nombre, String descripcion, Integer duracionMinutos, 
                 Integer capacidadMaxima, String entrenadorAsignado, LocalDateTime fechaHora,
                 String nivelDificultad, Double precio, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.duracionMinutos = duracionMinutos;
        this.capacidadMaxima = capacidadMaxima;
        this.entrenadorAsignado = entrenadorAsignado;
        this.fechaHora = fechaHora;
        this.nivelDificultad = nivelDificultad;
        this.precio = precio;
        this.estado = estado;
    }
}
