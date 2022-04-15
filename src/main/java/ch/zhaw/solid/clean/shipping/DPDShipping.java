package ch.zhaw.solid.clean.shipping;

import ch.zhaw.solid.clean.CleanOrder;

public class DPDShipping implements Shipping {

    @Override
    public Float getCost(CleanOrder order) {
        return order.getTotal() > 100 ? 0 : (float) Math.max(10, order.getTotalWeight() * 1.5);
    }
}
