// src/test/java/co/analisys/gimnasio/service/OcupacionClaseKafkaIntegrationTest.java
package co.analisys.gimnasio.clases_service;

import co.analisys.gimnasio.model.OcupacionClase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = { "ocupacion-clases" })
public class OcupacionClaseKafkaIntegrationTest {

	@Autowired
	private KafkaTemplate<String, OcupacionClase> kafkaTemplate;

	private CountDownLatch latch = new CountDownLatch(1);
	private OcupacionClase mensajeRecibido;

	@KafkaListener(topics = "ocupacion-clases", groupId = "test-grupo")
	public void listen(OcupacionClase ocupacion) {
		mensajeRecibido = ocupacion;
		latch.countDown();
	}

	@Test
	public void testEnvioYRecepcionKafka() throws Exception {
		OcupacionClase ocupacion = new OcupacionClase("clase1", 10, java.time.LocalDateTime.now());
		kafkaTemplate.send("ocupacion-clases", "clase1", ocupacion);
		latch.await(5, TimeUnit.SECONDS);
		assert mensajeRecibido != null;
		assert mensajeRecibido.getClaseId().equals("clase1");
	}
}