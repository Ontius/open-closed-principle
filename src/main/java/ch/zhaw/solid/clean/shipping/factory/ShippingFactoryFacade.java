package ch.zhaw.solid.clean.shipping.factory;

import ch.zhaw.solid.clean.shipping.Shipping;
import ch.zhaw.solid.shared.ShippingMethod;

public interface ShippingFactoryFacade {

    Shipping createShippingProvider(ShippingMethod shippingMethod);
}
