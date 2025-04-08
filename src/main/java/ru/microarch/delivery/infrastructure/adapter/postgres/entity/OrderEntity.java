package ru.microarch.delivery.infrastructure.adapter.postgres.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.UUID;

import ru.microarch.delivery.core.domain.model.orderaggregate.OrderStatus;
import ru.microarch.delivery.core.domain.model.courieraggregate.Courier;

@Entity
@Table(name = "orders", schema = "delivery")
public class OrderEntity {
    @Id
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courier_id", referencedColumnName = "id", nullable = true)
    private Courier assignedCourier;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @Embedded
    private LocationEntity location;
}
