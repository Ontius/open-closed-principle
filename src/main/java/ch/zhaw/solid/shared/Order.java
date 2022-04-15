package ch.zhaw.solid.shared;

public interface Order<T> {

    String getId();
    Float getTotal();
    Float getTotalWeight();
    Float getShippingCosts();
}
