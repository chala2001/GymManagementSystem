package com.example.GymManagementBackend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
		"spring.kafka.listener.auto-startup=false",
		"app.outbox.publisher.enabled=false"
})
class GymManagementBackendApplicationTests {

	@Test
	void contextLoads() {
	}

}
