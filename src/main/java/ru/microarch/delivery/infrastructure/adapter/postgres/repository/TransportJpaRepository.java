package ru.microarch.delivery.infrastructure.adapter.postgres.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.microarch.delivery.infrastructure.adapter.postgres.entity.TransportEntity;

@Repository
public interface TransportJpaRepository extends JpaRepository<TransportEntity, UUID> {
}
