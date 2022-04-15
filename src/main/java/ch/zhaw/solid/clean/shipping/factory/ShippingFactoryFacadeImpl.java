package ch.zhaw.solid.clean.shipping.factory;

import ch.zhaw.solid.clean.shipping.Shipping;
import ch.zhaw.solid.shared.ShippingMethod;
import ch.zhaw.solid.shared.error.ShippingException;
import ch.zhaw.solid.shared.util.StreamUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ShippingFactoryFacadeImpl implements ShippingFactoryFacade {

    private final List<ShippingFactory> shippingFactories;

    @Override
    public Shipping createShippingProvider(ShippingMethod shippingMethod) {
        return StreamUtils.collectionStream(shippingFactories)
                .filter(factory -> factory.supports(shippingMethod))
                .findFirst()
                .map(ShippingFactory::createShippingProvider)
                .orElseThrow(() -> new ShippingException("No ShippingFactory found for '%s'".formatted(shippingMethod)));
    }
}
