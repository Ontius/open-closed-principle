package ch.zhaw.solid.clean.shipping.factory;

import ch.zhaw.solid.clean.shipping.PostShipping;
import ch.zhaw.solid.clean.shipping.Shipping;
import ch.zhaw.solid.shared.ShippingMethod;
import org.springframework.stereotype.Component;

@Component
public class PostShippingFactory implements ShippingFactory {

    @Override
    public Shipping createShippingProvider() {
        return new PostShipping();
    }

    @Override
    public boolean supports(ShippingMethod shippingMethod) {
        return shippingMethod == ShippingMethod.POST;
    }
}
