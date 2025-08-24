package co.analisys.gimnasio;

import co.analisys.gimnasio.model.Clase;
import co.analisys.gimnasio.repository.ClaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ClaseRepository claseRepository;

    @Override
    public void run(String... args) throws Exception {

        // Cargar clases de ejemplo
        Clase clase1 = new Clase();
        clase1.setNombre("Yoga Matutino");
        clase1.setDescripcion("Clase de yoga relajante para comenzar el día");
        clase1.setDuracionMinutos(60);
        clase1.setCapacidadMaxima(20);
        clase1.setEntrenadorAsignado("Carlos Rodríguez");
        clase1.setFechaHora(LocalDateTime.now().plusDays(1).withHour(8).withMinute(0));
        clase1.setNivelDificultad("PRINCIPIANTE");
        clase1.setPrecio(25.0);
        clase1.setEstado("ACTIVA");
        claseRepository.save(clase1);

        Clase clase2 = new Clase();
        clase2.setNombre("Spinning Intenso");
        clase2.setDescripcion("Clase de spinning de alta intensidad");
        clase2.setDuracionMinutos(45);
        clase2.setCapacidadMaxima(15);
        clase2.setEntrenadorAsignado("Ana Martínez");
        clase2.setFechaHora(LocalDateTime.now().plusDays(1).withHour(18).withMinute(30));
        clase2.setNivelDificultad("AVANZADO");
        clase2.setPrecio(30.0);
        clase2.setEstado("ACTIVA");
        claseRepository.save(clase2);

        Clase clase3 = new Clase();
        clase3.setNombre("Pilates Intermedio");
        clase3.setDescripcion("Clase de pilates para nivel intermedio");
        clase3.setDuracionMinutos(50);
        clase3.setCapacidadMaxima(12);
        clase3.setEntrenadorAsignado("María González");
        clase3.setFechaHora(LocalDateTime.now().plusDays(2).withHour(10).withMinute(0));
        clase3.setNivelDificultad("INTERMEDIO");
        clase3.setPrecio(28.0);
        clase3.setEstado("ACTIVA");
        claseRepository.save(clase3);

        Clase clase4 = new Clase();
        clase4.setNombre("Zumba Fitness");
        clase4.setDescripcion("Clase de baile y ejercicio cardiovascular");
        clase4.setDuracionMinutos(55);
        clase4.setCapacidadMaxima(25);
        clase4.setEntrenadorAsignado("Luis Hernández");
        clase4.setFechaHora(LocalDateTime.now().plusDays(3).withHour(19).withMinute(0));
        clase4.setNivelDificultad("PRINCIPIANTE");
        clase4.setPrecio(22.0);
        clase4.setEstado("ACTIVA");
        claseRepository.save(clase4);

        System.out.println("Datos de ejemplo de clases cargados exitosamente.");
    }
}