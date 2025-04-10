package ru.microarch.delivery.infrastructure.adapter.postgres.mapper;

import ru.microarch.delivery.core.domain.model.courieraggregate.Courier;
import ru.microarch.delivery.infrastructure.adapter.postgres.entity.CourierEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourierMapper {

    Courier toModel(CourierEntity entity);

    CourierEntity toEntity(Courier model);
}
