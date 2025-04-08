package ru.microarch.delivery.infrastructure.adapter.postgres.mapper;

import ru.microarch.delivery.core.domain.model.orderaggregate.Order;
import ru.microarch.delivery.infrastructure.adapter.postgres.entity.OrderEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order toModel(OrderEntity entity);

    OrderEntity toEntity(Order model);
}
