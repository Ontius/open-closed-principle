package ch.zhaw.solid.shared.transfer;

import ch.zhaw.solid.clean.CleanOrder;
import ch.zhaw.solid.shared.LineItem;
import ch.zhaw.solid.spaghetti.SpaghettiOrder;

import java.util.List;

public record OrderDto(String id, List<LineItem> lineItems,
                       Float total, Float totalWeight, Float shippingCosts,
                       String shippingMethod) {

    public static OrderDto of(CleanOrder order) {
        return new OrderDto(
                order.getId(),
                order.getLineItems(),
                order.getTotal(),
                order.getTotalWeight(),
                order.getShippingCosts(),
                order.getShippingMethod().getClass().getSimpleName()
        );
    }

    public static OrderDto of(SpaghettiOrder order) {
        return new OrderDto(
                order.getId(),
                order.getLineItems(),
                order.getTotal(),
                order.getTotalWeight(),
                order.getShippingCosts(),
                order.getShippingMethod().name()
        );
    }
}
