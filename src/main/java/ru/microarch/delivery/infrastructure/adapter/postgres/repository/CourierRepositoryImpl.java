package ru.microarch.delivery.infrastructure.adapter.postgres.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.microarch.delivery.core.domain.model.courieraggregate.Courier;
import ru.microarch.delivery.core.domain.model.courieraggregate.CourierStatus;
import ru.microarch.delivery.core.port.CourierRepository;
import ru.microarch.delivery.infrastructure.adapter.postgres.entity.CourierEntity;
import ru.microarch.delivery.infrastructure.adapter.postgres.mapper.CourierMapper;

@Service
public class CourierRepositoryImpl implements CourierRepository {

    private final CourierJpaRepository courierJpaRepository;
    private final CourierMapper mapper;

    public CourierRepositoryImpl(CourierJpaRepository courierJpaRepository, CourierMapper mapper){
        this.courierJpaRepository = courierJpaRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public void add(Courier courier) {
        CourierEntity entity = mapper.toEntity(courier);
        courierJpaRepository.save(entity);
    }

    @Override
    @Transactional
    public void update(Courier courier) {
        CourierEntity entity = mapper.toEntity(courier);
        courierJpaRepository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Courier getById(UUID id) {
        CourierEntity entity = courierJpaRepository.findById(id).orElse(null);

        return mapper.toModel(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Courier> getAllFreeCouriers() {
        return courierJpaRepository.findAll().stream()
                .map(mapper::toModel)
                .filter(courier -> courier.getStatus() == CourierStatus.FREE)
                .toList();
    }
}
