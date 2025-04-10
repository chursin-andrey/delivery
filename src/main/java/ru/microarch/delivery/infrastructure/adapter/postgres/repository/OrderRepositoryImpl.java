package ru.microarch.delivery.infrastructure.adapter.postgres.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.microarch.delivery.core.domain.model.orderaggregate.Order;
import ru.microarch.delivery.core.domain.model.orderaggregate.OrderStatus;
import ru.microarch.delivery.core.port.OrderRepository;
import ru.microarch.delivery.infrastructure.adapter.postgres.entity.OrderEntity;
import ru.microarch.delivery.infrastructure.adapter.postgres.mapper.OrderMapper;

@Service
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;
    private final OrderMapper mapper;

    public OrderRepositoryImpl(OrderJpaRepository orderJpaRepository, OrderMapper mapper){
        this.orderJpaRepository = orderJpaRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public void add(Order order) {
        OrderEntity entity = mapper.toEntity(order);
        orderJpaRepository.save(entity);
    }

    @Override
    @Transactional
    public void update(Order order) {
        OrderEntity entity = mapper.toEntity(order);
        orderJpaRepository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Order getById(UUID id) {
        return mapper.toModel(orderJpaRepository.findById(id).orElse(null));
    }

    @Override
    @Transactional(readOnly = true)
    public Order getAnyCreatedOrder() {
        return mapper.toModel(orderJpaRepository.findFirstByStatus(OrderStatus.CREATED).orElseGet(null));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getAllAssignedOrders() {
        return orderJpaRepository.findAll().stream()
                .map(mapper::toModel)
                .filter(order -> order.getStatus().equals(OrderStatus.ASSIGNED))
                .toList();
    }
}
