package ru.microarch.delivery.infrastructure.adapter.postgres.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.microarch.delivery.infrastructure.adapter.postgres.entity.CourierEntity;
import ru.microarch.delivery.core.domain.model.courieraggregate.CourierStatus;

@Repository
public interface CourierJpaRepository extends JpaRepository<CourierEntity, UUID> {
    List<CourierEntity> findAllByStatusIs(CourierStatus status);
}
