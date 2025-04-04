package ru.microarch.delivery.core.domain.service;

import java.util.List;
import java.util.UUID;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.microarch.delivery.core.domain.model.courieraggregate.Courier;
import ru.microarch.delivery.core.domain.model.courieraggregate.CourierStatus;
import ru.microarch.delivery.core.domain.model.orderaggregate.Order;
import ru.microarch.delivery.core.domain.model.orderaggregate.OrderStatus;
import ru.microarch.delivery.core.domain.model.sharedkernel.Location;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DispatchServiceTest {
    DispatchService dispatchService = new DispatchServiceImpl();

    private List<Courier> freeCouriers;

    @BeforeEach
    void setUp() {
        Courier courier1 = Courier.create("Harry Potter", "Pedestrian", 1, Location.createRandomLocation());
        Courier courier2 = Courier.create("Hermione Granger","Bicycle", 2, Location.createRandomLocation());
        Courier courier3 = Courier.create("Ron Weasley", "Car", 3, Location.createRandomLocation());
        freeCouriers = List.of(courier1, courier2, courier3);
    }

    @Test
    void should_DispatchCreatedOrder() {
        Order order = Order.create(UUID.randomUUID(), Location.createRandomLocation());
        Courier assignedCourier = dispatchService.dispatch(order, freeCouriers);

        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(order.getStatus()).isEqualTo(OrderStatus.ASSIGNED);
        soft.assertThat(order.getCourierId()).isNotNull();
        soft.assertThat(assignedCourier.getStatus()).isEqualTo(CourierStatus.BUSY);
        soft.assertAll();
    }

    @Test
    void should_DispatchOrderForNearestCourier_when_TransportSpeedIsTheSame() {
        Courier courier1 = Courier.create("Hermione Granger", "Bicycle", 1, Location.create(1, 1));
        Courier courier2 = Courier.create("Harry Potter", "Nimbus 2000", 1, Location.create(2, 2));
        Courier courier3 = Courier.create("Ron Weasley", "Car", 1, Location.create(3, 3));

        Order order = Order.create(UUID.randomUUID(), Location.create(2, 2));

        Courier assignedCourier = dispatchService.dispatch(order, List.of(courier1, courier2, courier3));

        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(order.getStatus()).isEqualTo(OrderStatus.ASSIGNED);
        soft.assertThat(order.getCourierId()).isEqualTo(courier2.getId());

        soft.assertThat(assignedCourier).isEqualTo(courier2);

        soft.assertThat(courier1.getStatus()).isEqualTo(CourierStatus.FREE);
        soft.assertThat(courier2.getStatus()).isEqualTo(CourierStatus.BUSY);
        soft.assertThat(courier3.getStatus()).isEqualTo(CourierStatus.FREE);
        soft.assertAll();
    }

    @Test
    void should_DispatchOrderForNearestCourier_when_TransportSpeedIsNotTheSame() {
        Courier courier1 = Courier.create("Harry Potter", "Nimbus 2000", 1, Location.create(1, 2));
        Courier courier2 = Courier.create("Ron Weasley", "Car", 2, Location.create(1, 2));

        Order order = Order.create(UUID.randomUUID(), Location.create(1, 1));

        Courier assignedCourier = dispatchService.dispatch(order, List.of(courier1, courier2));

        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(order.getStatus()).isEqualTo(OrderStatus.ASSIGNED);
        soft.assertThat(order.getCourierId()).isEqualTo(courier1.getId());

        soft.assertThat(assignedCourier).isEqualTo(courier1);

        soft.assertThat(courier1.getStatus()).isEqualTo(CourierStatus.BUSY);
        soft.assertThat(courier2.getStatus()).isEqualTo(CourierStatus.FREE);
        soft.assertAll();
    }

    @Test
    void should_DispatchOrderForFastestCourier() {
        Courier courier1 = Courier.create("Hermione Granger", "Bicycle", 1, Location.create(6, 6));
        Courier courier2 = Courier.create("Harry Potter", "Nimbus 2000", 1, Location.create(6, 6));
        Courier courier3 = Courier.create("Ron Weasley", "Car", 3, Location.create(6, 6));

        Order order = Order.create(UUID.randomUUID(), Location.create(4, 4));

        Courier assignedCourier = dispatchService.dispatch(order, List.of(courier1, courier2, courier3));

        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(order.getStatus()).isEqualTo(OrderStatus.ASSIGNED);
        soft.assertThat(order.getCourierId()).isEqualTo(courier3.getId());

        soft.assertThat(assignedCourier.getStatus()).isEqualTo(CourierStatus.BUSY);
        soft.assertThat(assignedCourier).isEqualTo(courier3);

        soft.assertThat(courier1.getStatus()).isEqualTo(CourierStatus.FREE);
        soft.assertThat(courier2.getStatus()).isEqualTo(CourierStatus.FREE);
        soft.assertThat(courier3.getStatus()).isEqualTo(CourierStatus.BUSY);
        soft.assertAll();
    }

    @Test
    void should_NotDispatchNonCreatedOrder_when_AllCouriersAreFree() {
        Order order = Order.create(UUID.randomUUID(), Location.createRandomLocation());
        order.assign(UUID.randomUUID());

        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(order.getStatus()).isEqualTo(OrderStatus.ASSIGNED);
        soft.assertThat(order.getCourierId()).isNotNull();
        soft.assertThatThrownBy(() -> dispatchService.dispatch(order, freeCouriers));
        soft.assertAll();
    }

    @Test
    void should_NotDispatchCreatedOrder_when_AllCouriersAreBusy() {
        Order order = Order.create(UUID.randomUUID(), Location.createRandomLocation());

        List<Courier> busyCouriers = freeCouriers.stream().peek(Courier::setBusy).toList();

        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(order.getStatus()).isEqualTo(OrderStatus.CREATED);
        soft.assertThat(order.getCourierId()).isNull();
        soft.assertThatThrownBy(() -> dispatchService.dispatch(order, busyCouriers));
        soft.assertAll();
    }

    @Test
    void should_ReturnError_when_OrderIsNull() {
        assertThatThrownBy(() -> dispatchService.dispatch(null, freeCouriers));
    }

    @Test
    void should_ReturnError_when_CourierListIsNull() {
        Order order = Order.create(UUID.randomUUID(), Location.createRandomLocation());

        assertThatThrownBy(() -> dispatchService.dispatch(order, null));
    }

    @Test
    void should_ReturnError_when_CourierListIsEmpty() {
        Order order = Order.create(UUID.randomUUID(), Location.createRandomLocation());

        assertThatThrownBy(() -> dispatchService.dispatch(order, List.of()));
    }

}
