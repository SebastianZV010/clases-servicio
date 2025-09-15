package co.analisys.gimnasio.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import co.analisys.gimnasio.model.OcupacionClase;

@Service
public class OcupacionClaseConsumer {

    private static final Logger logger = LoggerFactory.getLogger(OcupacionClaseConsumer.class);

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @KafkaListener(topics = "ocupacion-clases", groupId = "monitoreo-grupo")
    public void consumirActualizacionOcupacion(OcupacionClase ocupacion) {
        // Log para verificar el mensaje recibido
        logger.info("Mensaje recibido: {}", ocupacion);

        // Envía la actualización al topic WebSocket "/topic/ocupacion"
        messagingTemplate.convertAndSend("/topic/ocupacion", ocupacion);
    }
}