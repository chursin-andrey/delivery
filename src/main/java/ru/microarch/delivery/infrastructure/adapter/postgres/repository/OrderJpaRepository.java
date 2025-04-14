package ru.microarch.delivery.infrastructure.adapter.postgres.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.microarch.delivery.infrastructure.adapter.postgres.entity.OrderEntity;
import ru.microarch.delivery.core.domain.model.orderaggregate.OrderStatus;

@Repository
public interface OrderJpaRepository extends JpaRepository<OrderEntity, UUID> {

    Optional<OrderEntity> findFirstByStatus(OrderStatus status);

    List<OrderEntity> findAllByStatusIs(OrderStatus status);
    List<OrderEntity> findAllByStatusIn(List<OrderStatus> status);

    Optional<OrderEntity> findByCourierId(UUID courierId);
}
