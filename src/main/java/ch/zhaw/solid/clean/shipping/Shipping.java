package ch.zhaw.solid.clean.shipping;

import ch.zhaw.solid.clean.CleanOrder;

public interface Shipping {

    Float getCost(CleanOrder order);
}
