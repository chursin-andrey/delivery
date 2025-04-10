package ru.microarch.delivery.infrastructure.adapter.postgres.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class LocationEntity {
    @Column(name = "location_x")
    private Integer x;

    @Column(name = "location_y")
    private Integer y;
}
