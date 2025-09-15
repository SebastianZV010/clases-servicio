package co.analisys.gimnasio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import co.analisys.gimnasio.model.OcupacionClase;

@Service
public class OcupacionClaseProducer {
    @Autowired
    private KafkaTemplate<String, OcupacionClase> kafkaTemplate;

    public void actualizarOcupacion(String claseId, int ocupacionActual) {
        OcupacionClase ocupacion = new OcupacionClase(claseId, ocupacionActual, java.time.LocalDateTime.now());
        kafkaTemplate.send("ocupacion-clases", claseId, ocupacion);
    }
}