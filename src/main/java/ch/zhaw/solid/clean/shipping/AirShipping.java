package ch.zhaw.solid.clean.shipping;

import ch.zhaw.solid.clean.CleanOrder;

public class AirShipping implements Shipping {

    @Override
    public Float getCost(CleanOrder order) {
        return Math.max(20, order.getTotalWeight() * 3);
    }
}
