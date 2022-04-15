package ch.zhaw.solid.clean.shipping.factory;

import ch.zhaw.solid.clean.shipping.AirShipping;
import ch.zhaw.solid.clean.shipping.Shipping;
import ch.zhaw.solid.shared.ShippingMethod;
import org.springframework.stereotype.Component;

@Component
public class AirShippingFactory implements ShippingFactory {

    @Override
    public Shipping createShippingProvider() {
        return new AirShipping();
    }

    @Override
    public boolean supports(ShippingMethod shippingMethod) {
        return shippingMethod == ShippingMethod.AIR;
    }
}
