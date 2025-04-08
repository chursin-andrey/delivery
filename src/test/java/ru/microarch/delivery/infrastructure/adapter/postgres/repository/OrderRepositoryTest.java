package ru.microarch.delivery.infrastructure.adapter.postgres.repository;

import org.springframework.boot.test.context.SpringBootTest;
import ru.microarch.delivery.config.PostgresTestContainersConfiguration;

@SpringBootTest(classes = {PostgresTestContainersConfiguration.class})
public class OrderRepositoryTest {
}
