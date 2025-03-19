package ru.microarch.delivery.core.domain.model;

import java.util.UUID;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import ru.microarch.delivery.core.domain.model.courieraggregate.Courier;
import ru.microarch.delivery.core.domain.model.orderaggregate.Order;
import ru.microarch.delivery.core.domain.model.orderaggregate.OrderStatus;
import ru.microarch.delivery.core.domain.model.sharedkernel.Location;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderTest {

    @Nested
    class WhenCreateOrderTest {

        @Test
        void should_ReturnCreatedStatus_when_OrderIsJustCreated() {
            Order order = Order.create(UUID.randomUUID(), Location.createRandomLocation());

            assertEquals(OrderStatus.CREATED, order.getStatus());
        }

        @Test
        void should_NotHaveCourierId_when_OrderIsJustCreated() {
            Order order = Order.create(UUID.randomUUID(), Location.createRandomLocation());

            Assertions.assertNull(order.getCourierId());
        }
    }

    @Nested
    class WhenAssignOrderTest {

        @Test
        void should_HaveStatusAssigned_and_CourierId_when_OrderAssigned() {
            Order order = Order.create(UUID.randomUUID(), Location.createRandomLocation());

            UUID courierId = UUID.randomUUID();

            order.assign(courierId);

            SoftAssertions soft = new SoftAssertions();
            soft.assertThat(order.getStatus()).isEqualTo(OrderStatus.ASSIGNED);
            soft.assertThat(order.getCourierId()).isEqualTo(courierId);
            soft.assertAll();
        }

        @Test
        void should_NotAssignOrder_when_OrderIsAlreadyAssigned() {
            Order order = Order.create(UUID.randomUUID(), Location.createRandomLocation());
            UUID courierId = UUID.randomUUID();

            order.assign(courierId);

            assertThrows(IllegalStateException.class, () -> order.assign(courierId));
        }

        @Test
        void should_NotAssignOrder_when_OrderHasCompletedStatus() {
            Order order = createCompletedOrder();

            assertThrows(IllegalStateException.class, () -> order.assign(UUID.randomUUID()));
        }
    }

    @Nested
    class WhenCompleteOrderTest {

        @Test
        void should_HaveCompletedStatus() {
            Order order = createCompletedOrder();

            SoftAssertions soft = new SoftAssertions();
            soft.assertThat(order.getStatus()).isEqualTo(OrderStatus.COMPLETED);
            soft.assertThat(order.getCourierId()).isEqualTo(order.getCourierId());
            soft.assertAll();
        }

        @Test
        void should_NotCompleteOrder_when_OrderIsNotAssigned() {
            Order order = Order.create(UUID.randomUUID(), Location.createRandomLocation());

            assertThrows(IllegalStateException.class, () -> order.complete());
        }

        @Test
        void should_NotCompleteOrder_when_OrderIsAlreadyCompleted() {
            Order order = createCompletedOrder();

            assertThrows(IllegalStateException.class, () -> order.complete());
        }
    }

    private Order createCompletedOrder() {
        Order order = Order.create(UUID.randomUUID(), Location.createRandomLocation());
        UUID courierId = UUID.randomUUID();

        order.assign(courierId);
        order.complete();

        return order;
    }

}
