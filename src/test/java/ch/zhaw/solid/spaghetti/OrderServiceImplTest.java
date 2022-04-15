package ch.zhaw.solid.spaghetti;

import ch.zhaw.solid.shared.LineItem;
import ch.zhaw.solid.shared.OrderService;
import ch.zhaw.solid.shared.ShippingMethod;
import ch.zhaw.solid.shared.error.ResourceNotFoundException;
import ch.zhaw.solid.shared.transfer.CreateOrderDto;
import ch.zhaw.solid.shared.transfer.OrderDto;
import ch.zhaw.solid.shared.util.StreamUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImplTest {

    private static final List<LineItem> lineItems = new ArrayList<>(Arrays.asList(
            new LineItem("iPhone 12 Pro", 999.0f, 1, 1.0f),
            new LineItem("Samsung Galaxy S20 Ultra", 1010.0f, 1, 1.0f)));

    private static final CreateOrderDto createPostOrder = new CreateOrderDto(lineItems, ShippingMethod.POST);
    private static final CreateOrderDto createDPDOrder = new CreateOrderDto(lineItems,ShippingMethod.DPD);
    private static final CreateOrderDto createDHLOrder = new CreateOrderDto(lineItems,ShippingMethod.DHL);
    private static final CreateOrderDto createAirOrder = new CreateOrderDto(lineItems,ShippingMethod.AIR);

    private OrderService orderService;

    @BeforeEach
    void init() {
        orderService = new OrderServiceImpl();
    }

    @Test
    void whenPostOrderIsCreated_thenOrderShouldGetStored() {
        // when
        OrderDto orderDto = orderService.create(createPostOrder);

        // then
        assertTrue(orderService.all().contains(orderDto));
    }

    @Test
    void whenDPDOrderIsCreated_thenOrderShouldGetStored() {
        // when
        OrderDto orderDto = orderService.create(createDPDOrder);

        // then
        assertTrue(orderService.all().contains(orderDto));
    }

    @Test
    void whenDHLOrderIsCreated_thenOrderShouldGetStored() {
        // when
        OrderDto orderDto = orderService.create(createDHLOrder);

        // then
        assertTrue(orderService.all().contains(orderDto));
    }

    @Test
    void whenAirOrderIsCreated_thenOrderShouldGetStored() {
        // when
        OrderDto orderDto = orderService.create(createAirOrder);

        // then
        assertTrue(orderService.all().contains(orderDto));
    }

    @Test
    void givenExistingOrdersInStore_whenAllOrderAreRetrieved_thenResultShouldBeCorrect() {
        // given
        List<OrderDto> createdOrders = new ArrayList<>(Arrays.asList(
                orderService.create(createAirOrder),
                orderService.create(createAirOrder),
                orderService.create(createAirOrder)
        ));

        // when
        List<OrderDto> allOrders = orderService.all();

        // then
        assertEquals(createdOrders.size(), allOrders.size());
        assertEquals(createdOrders.get(0), findOrderById(allOrders, createdOrders.get(0).id()));
        assertEquals(createdOrders.get(1), findOrderById(allOrders, createdOrders.get(1).id()));
        assertEquals(createdOrders.get(2), findOrderById(allOrders, createdOrders.get(2).id()));
    }

    @Test
    void givenExistingOrderIsInStore_whenOrderByIdIsRetrieved_thenResultShouldBeCorrect() {
        // given
        OrderDto createdOrder = orderService.create(createAirOrder);

        // when
        OrderDto order = orderService.get(createdOrder.id());

        // then
        assertEquals(createdOrder, order);
    }

    @Test
    void givenExistingOrdersInStore_whenOrderIsDeleted_thenStoreShouldNotContainOrderAnymore() {
        // given
        List<OrderDto> createdOrders = new ArrayList<>(Arrays.asList(
                orderService.create(createAirOrder),
                orderService.create(createAirOrder),
                orderService.create(createAirOrder)
        ));
        String id = createdOrders.get(0).id();

        // when
        orderService.delete(id);

        // then
        assertEquals(2, orderService.all().size());
        assertThrows(ResourceNotFoundException.class, () -> orderService.get(id));
    }

    @Test
    void whenNonExistingOrderIsRetrieved_thenAResourceNotFoundExceptionShouldBeThrown() {
        // when & then
        assertThrows(ResourceNotFoundException.class, () -> orderService.get("non-existing-id"));
    }

    private OrderDto findOrderById(List<OrderDto> orders, String id) {
        return StreamUtils.collectionStream(orders)
                .filter(order -> order.id().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Order with '%s' does not exist".formatted(id)));
    }
}
