package ru.microarch.delivery;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.microarch.delivery.config.PostgresTestContainersConfiguration;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ActiveProfiles("test")
@SpringBootTest(classes = {PostgresTestContainersConfiguration.class}, webEnvironment = RANDOM_PORT)
public class IntegrationTests {

    @Test
    void contextLoads() {
    }
}
