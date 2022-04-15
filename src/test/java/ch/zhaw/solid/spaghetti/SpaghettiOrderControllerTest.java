package ch.zhaw.solid.spaghetti;

import ch.zhaw.solid.shared.LineItem;
import ch.zhaw.solid.shared.ShippingMethod;
import ch.zhaw.solid.shared.transfer.CreateOrderDto;
import ch.zhaw.solid.shared.transfer.OrderDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpaghettiOrderControllerTest {

    private static final List<LineItem> lineItems = new ArrayList<>(Arrays.asList(
            new LineItem("iPhone 12 Pro", 999.0f, 1, 1.0f),
            new LineItem("Samsung Galaxy S20 Ultra", 1010.0f, 1, 1.0f)));

    private static final CreateOrderDto createPostOrder = new CreateOrderDto(lineItems, ShippingMethod.POST);
    private static final CreateOrderDto createDPDOrder = new CreateOrderDto(lineItems,ShippingMethod.DPD);
    private static final CreateOrderDto createDHLOrder = new CreateOrderDto(lineItems,ShippingMethod.DHL);
    private static final CreateOrderDto createAirOrder = new CreateOrderDto(lineItems,ShippingMethod.AIR);

    @Autowired
    private SpaghettiOrderController spaghettiOrderController;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Test
    void controllerShouldExist() {
        assertNotNull(spaghettiOrderController);
    }

    @Test
    void whenCreatingAPostOrder_thenOrderShouldGetRetrievedCorrectly() {
        // when
        ResponseEntity<OrderDto> result = createOrder(createPostOrder);

        // then
        assertEquals(201, result.getStatusCodeValue());
        assertNotNull(result.getBody());
        assertNotNull(result.getBody().id());
        assertEquals(ShippingMethod.POST.name(), result.getBody().shippingMethod());
        assertEquals(2009.0f, result.getBody().total());
        assertEquals(2.0f, result.getBody().totalWeight());
        assertEquals(0.0f, result.getBody().shippingCosts());
    }

    @Test
    void whenCreatingADPDOrder_thenOrderShouldGetRetrievedCorrectly() {
        // when
        ResponseEntity<OrderDto> result = createOrder(createDPDOrder);

        // then
        assertEquals(201, result.getStatusCodeValue());
        assertNotNull(result.getBody());
        assertNotNull(result.getBody().id());
        assertEquals(ShippingMethod.DPD.name(), result.getBody().shippingMethod());
        assertEquals(2009.0f, result.getBody().total());
        assertEquals(2.0f, result.getBody().totalWeight());
        assertEquals(0.0f, result.getBody().shippingCosts());
    }

    @Test
    void whenCreatingADHLDOrder_thenOrderShouldGetRetrievedCorrectly() {
        // when
        ResponseEntity<OrderDto> result = createOrder(createDHLOrder);

        // then
        assertEquals(201, result.getStatusCodeValue());
        assertNotNull(result.getBody());
        assertNotNull(result.getBody().id());
        assertEquals(ShippingMethod.DHL.name(), result.getBody().shippingMethod());
        assertEquals(2009.0f, result.getBody().total());
        assertEquals(2.0f, result.getBody().totalWeight());
        assertEquals(0.0f, result.getBody().shippingCosts());
    }

    @Test
    void whenCreatingAnAirDOrder_thenOrderShouldGetRetrievedCorrectly() {
        // when
        ResponseEntity<OrderDto> result = createOrder(createAirOrder);

        // then
        assertEquals(201, result.getStatusCodeValue());
        assertNotNull(result.getBody());
        assertNotNull(result.getBody().id());
        assertEquals(ShippingMethod.AIR.name(), result.getBody().shippingMethod());
        assertEquals(2009.0f, result.getBody().total());
        assertEquals(2.0f, result.getBody().totalWeight());
        assertEquals(20.0f, result.getBody().shippingCosts());
    }

    @Test
    void givenOrdersAreExisting_whenAllOrdersAreRetrieved_thenOrdersShouldGetRetrievedCorrectly() {
        // given
        createOrder(createPostOrder);
        createOrder(createDPDOrder);
        createOrder(createDHLOrder);
        createOrder(createAirOrder);

        // when
        ResponseEntity<OrderDto[]> result = getAllOrders();

        // then
        assertNotNull(result.getBody());
        assertTrue(result.getBody().length > 0);
    }

    @Test
    void givenOrdersAreExisting_whenOrderByIdIsRetrieved_thenOrderShouldGetRetrievedCorrectly() {
        // given
        OrderDto order = createOrderUnpacked(createPostOrder);

        // when
        String id = order.id();
        ResponseEntity<OrderDto> result = getOrderById(id);

        // then
        assertNotNull(result.getBody());
        assertEquals(200, result.getStatusCodeValue());
        assertEquals(id, result.getBody().id());
        assertEquals(ShippingMethod.POST.name(), result.getBody().shippingMethod());
    }

    @Test
    void whenNonExistingOrderIsRetrieved_thenStatusCodeShouldBeNotFound() {
        // when
        ResponseEntity<OrderDto> result = getOrderById("non-existing-id");

        // then
        assertEquals(404, result.getStatusCodeValue());
    }

    @Test
    void givenOrdersAreExisting_whenOrderIsDeleted_thenStatusCodeShouldBeSetCorrectly() {
        // given
        OrderDto order = createOrderUnpacked(createPostOrder);

        // when
        String id = order.id();
        ResponseEntity<Void> result = deleteOrder(id);

        // then
        assertEquals(204, result.getStatusCodeValue());
    }

    @Test
    void givenOrdersAreExisting_whenOrderIsDeleted_thenOrderShouldNoMoreExist() {
        // given
        OrderDto order = createOrderUnpacked(createPostOrder);

        // when
        String id = order.id();
        ResponseEntity<Void> result = deleteOrder(id);

        // then
        ResponseEntity<OrderDto> getResult = getOrderById(id);
        assertEquals(404, getResult.getStatusCodeValue());
    }

    private ResponseEntity<OrderDto> createOrder(CreateOrderDto dto) {
        String url = "http://localhost:%s/api/spaghetti/order".formatted(port);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<CreateOrderDto> request = new HttpEntity<>(dto, headers);
        return this.restTemplate.postForEntity(url, request, OrderDto.class);
    }

    private OrderDto createOrderUnpacked(CreateOrderDto dto) {
        ResponseEntity<OrderDto> result = createOrder(dto);

        if (result.getBody() == null) {
            throw new RuntimeException("Body cannot be empty");
        }

        return result.getBody();
    }

    private ResponseEntity<OrderDto[]> getAllOrders() {
        String url = "http://localhost:%s/api/spaghetti/order".formatted(port);
        return this.restTemplate.getForEntity(url, OrderDto[].class);
    }

    private ResponseEntity<OrderDto> getOrderById(String id) {
        String url = "http://localhost:%s/api/spaghetti/order/%s".formatted(port, id);
        return this.restTemplate.getForEntity(url, OrderDto.class);
    }

    private ResponseEntity<Void> deleteOrder(String id) {
        String url = "http://localhost:%s/api/spaghetti/order/%s".formatted(port, id);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> request = new HttpEntity<>(null, headers);
        return this.restTemplate.exchange(url, HttpMethod.DELETE, request, Void.class);
    }
}
