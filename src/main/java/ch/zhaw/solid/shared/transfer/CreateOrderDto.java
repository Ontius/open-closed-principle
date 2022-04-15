package ch.zhaw.solid.shared.transfer;

import ch.zhaw.solid.shared.LineItem;
import ch.zhaw.solid.shared.ShippingMethod;

import java.util.List;

public record CreateOrderDto(List<LineItem> lineItems, ShippingMethod shippingMethod) {}
