package ch.zhaw.solid.clean;

import ch.zhaw.solid.clean.shipping.AirShipping;
import ch.zhaw.solid.clean.shipping.DHLShipping;
import ch.zhaw.solid.clean.shipping.DPDShipping;
import ch.zhaw.solid.clean.shipping.PostShipping;
import ch.zhaw.solid.clean.shipping.factory.ShippingFactoryFacade;
import ch.zhaw.solid.shared.LineItem;
import ch.zhaw.solid.shared.OrderService;
import ch.zhaw.solid.shared.ShippingMethod;
import ch.zhaw.solid.shared.error.ResourceNotFoundException;
import ch.zhaw.solid.shared.transfer.CreateOrderDto;
import ch.zhaw.solid.shared.transfer.OrderDto;
import ch.zhaw.solid.shared.util.StreamUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    private static final List<LineItem> lineItems = new ArrayList<>(Arrays.asList(
            new LineItem("iPhone 12 Pro", 999.0f, 1, 1.0f),
            new LineItem("Samsung Galaxy S20 Ultra", 1010.0f, 1, 1.0f)));

    private static final CreateOrderDto createPostOrder = new CreateOrderDto(lineItems,ShippingMethod.POST);
    private static final CreateOrderDto createDPDOrder = new CreateOrderDto(lineItems,ShippingMethod.DPD);
    private static final CreateOrderDto createDHLOrder = new CreateOrderDto(lineItems,ShippingMethod.DHL);
    private static final CreateOrderDto createAirOrder = new CreateOrderDto(lineItems,ShippingMethod.AIR);

    @Mock
    private ShippingFactoryFacade shippingFactoryFacade;

    private OrderService orderService;

    @BeforeEach
    void init() {
        orderService = new OrderServiceImpl(shippingFactoryFacade);
    }

    @Test
    void givenMocksSetUp_whenPostOrderIsCreated_thenOrderShouldGetStored() {
        // given
        when(shippingFactoryFacade.createShippingProvider(ShippingMethod.POST))
                .thenReturn(new PostShipping());

        // when
        OrderDto orderDto = orderService.create(createPostOrder);

        // then
        assertTrue(orderService.all().contains(orderDto));
    }

    @Test
    void givenMocksSetUp_whenDPDOrderIsCreated_thenOrderShouldGetStored() {
        // given
        when(shippingFactoryFacade.createShippingProvider(ShippingMethod.DPD))
                .thenReturn(new DPDShipping());

        // when
        OrderDto orderDto = orderService.create(createDPDOrder);

        // then
        assertTrue(orderService.all().contains(orderDto));
    }

    @Test
    void givenMocksSetUp_whenDHLOrderIsCreated_thenOrderShouldGetStored() {
        // given
        when(shippingFactoryFacade.createShippingProvider(ShippingMethod.DHL))
                .thenReturn(new DHLShipping());

        // when
        OrderDto orderDto = orderService.create(createDHLOrder);

        // then
        assertTrue(orderService.all().contains(orderDto));
    }

    @Test
    void givenMocksSetUp_whenAirOrderIsCreated_thenOrderShouldGetStored() {
        // given
        when(shippingFactoryFacade.createShippingProvider(ShippingMethod.AIR))
                .thenReturn(new AirShipping());

        // when
        OrderDto orderDto = orderService.create(createAirOrder);

        // then
        assertTrue(orderService.all().contains(orderDto));
    }

    @Test
    void givenExistingOrdersInStore_whenAllOrderAreRetrieved_thenResultShouldBeCorrect() {
        // given
        when(shippingFactoryFacade.createShippingProvider(ShippingMethod.AIR))
                .thenReturn(new AirShipping());
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
        when(shippingFactoryFacade.createShippingProvider(ShippingMethod.AIR))
                .thenReturn(new AirShipping());
        OrderDto createdOrder = orderService.create(createAirOrder);

        // when
        OrderDto order = orderService.get(createdOrder.id());

        // then
        assertEquals(createdOrder, order);
    }

    @Test
    void givenExistingOrdersInStore_whenOrderIsDeleted_thenStoreShouldNotContainOrderAnymore() {
        // given
        when(shippingFactoryFacade.createShippingProvider(ShippingMethod.AIR))
                .thenReturn(new AirShipping());
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
