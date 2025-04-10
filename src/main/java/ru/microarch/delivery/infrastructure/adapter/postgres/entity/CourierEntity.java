package ru.microarch.delivery.infrastructure.adapter.postgres.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.microarch.delivery.core.domain.model.courieraggregate.CourierStatus;
import ru.microarch.delivery.core.domain.model.courieraggregate.Transport;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "couriers", schema = "delivery")
public class CourierEntity {
    @Id
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CourierStatus status;

    @Embedded
    private LocationEntity location;

    @OneToOne
    @JoinColumn(name = "transport_id", referencedColumnName = "id")
    private TransportEntity transport;

}
