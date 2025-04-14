package ru.microarch.delivery.config;

import java.nio.file.Paths;
import javax.sql.DataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

@TestConfiguration
public class PostgresTestContainersConfiguration {

    private static final String POSTGRES_LATEST_VERSION = "postgres:latest";
    private static final String SUPERUSER_LOGIN = "postgres";
    private static final String SUPERUSER_PASSWORD = "postgres";
    private static final String DATABASE_NAME = "delivery";
    private static final String APPLICATION_CREDENTIALS = "delivery";
    private static final Integer POSTGRES_PORT = 5432;

    @Bean
    @ServiceConnection
    public PostgreSQLContainer<?> postgresContainer() {
        final var postgresContainer = new PostgreSQLContainer<>(DockerImageName.parse(POSTGRES_LATEST_VERSION))
                .withUsername(SUPERUSER_LOGIN)
                .withPassword(SUPERUSER_PASSWORD)
                .withDatabaseName(DATABASE_NAME)
                .withExposedPorts(POSTGRES_PORT)
                .withCopyFileToContainer(
                        MountableFile.forHostPath(
                                Paths.get(".", "/docker/init/init.sql")
                                        .normalize()
                                        .toAbsolutePath()
                        ),
                        "/docker-entrypoint-initdb.d/"
                );

        postgresContainer.start();
        return postgresContainer;
    }

    @Bean
    @Primary
    public DataSource dataSource(PostgreSQLContainer postgreSqlContainer) {
        return DataSourceBuilder.create()
                .driverClassName("org.postgresql.Driver")
                .url("jdbc:postgresql://localhost:" + postgreSqlContainer.getMappedPort(POSTGRES_PORT) + "/" + DATABASE_NAME)
                .username(APPLICATION_CREDENTIALS)
                .password(APPLICATION_CREDENTIALS)
                .build();
    }
}
