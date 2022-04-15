package ch.zhaw.solid.clean.shipping.factory;

import ch.zhaw.solid.clean.shipping.DPDShipping;
import ch.zhaw.solid.clean.shipping.Shipping;
import ch.zhaw.solid.shared.ShippingMethod;
import org.springframework.stereotype.Component;

@Component
public class DPDShippingFactory implements ShippingFactory {

    @Override
    public Shipping createShippingProvider() {
        return new DPDShipping();
    }

    @Override
    public boolean supports(ShippingMethod shippingMethod) {
        return shippingMethod == ShippingMethod.DPD;
    }
}
