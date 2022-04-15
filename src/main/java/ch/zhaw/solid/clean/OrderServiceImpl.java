package ch.zhaw.solid.clean;

import ch.zhaw.solid.clean.shipping.Shipping;
import ch.zhaw.solid.clean.shipping.factory.ShippingFactoryFacade;
import ch.zhaw.solid.shared.OrderService;
import ch.zhaw.solid.shared.error.ResourceNotFoundException;
import ch.zhaw.solid.shared.transfer.CreateOrderDto;
import ch.zhaw.solid.shared.transfer.OrderDto;
import ch.zhaw.solid.shared.util.StreamUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service("cleanOrderService")
@RequiredArgsConstructor
@Validated
public class OrderServiceImpl implements OrderService {

    private final ShippingFactoryFacade shippingFactoryFacade;
    private final Map<String, CleanOrder> orders = new ConcurrentHashMap<>();

    @Override
    public OrderDto create(@Valid CreateOrderDto orderDto) {
        Shipping shipping = shippingFactoryFacade.createShippingProvider(orderDto.shippingMethod());
        CleanOrder order = new CleanOrder(shipping, orderDto.lineItems());
        orders.put(order.getId(), order);
        return OrderDto.of(order);
    }

    @Override
    public List<OrderDto> all() {
        return StreamUtils.collectionStream(orders.values())
                .map(OrderDto::of)
                .toList();
    }

    @Override
    public OrderDto get(@NotNull @NotBlank String id) {
        if (orders.containsKey(id)) {
            return OrderDto.of(orders.get(id));
        }

        throw new ResourceNotFoundException(CleanOrder.class, id);
    }

    @Override
    public void delete(@NotNull @NotBlank String id) {
        orders.remove(id);
    }
}
