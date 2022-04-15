package ch.zhaw.solid.clean.shipping.factory;

import ch.zhaw.solid.clean.shipping.DHLShipping;
import ch.zhaw.solid.clean.shipping.Shipping;
import ch.zhaw.solid.shared.ShippingMethod;
import org.springframework.stereotype.Component;

@Component
public class DHLShippingFactory implements ShippingFactory {

    @Override
    public Shipping createShippingProvider() {
        return new DHLShipping();
    }

    @Override
    public boolean supports(ShippingMethod shippingMethod) {
        return shippingMethod == ShippingMethod.DHL;
    }
}
