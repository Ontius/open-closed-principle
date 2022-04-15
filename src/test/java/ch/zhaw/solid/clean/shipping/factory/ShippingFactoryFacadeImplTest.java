package ch.zhaw.solid.clean.shipping.factory;

import ch.zhaw.solid.clean.shipping.*;
import ch.zhaw.solid.shared.ShippingMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShippingFactoryFacadeImplTest {

    private ShippingFactoryFacade shippingFactoryFacade;

    @BeforeEach
    void init() {
        List<ShippingFactory> shippingFactories = new ArrayList<>(Arrays.asList(
                new PostShippingFactory(),
                new DPDShippingFactory(),
                new DHLShippingFactory(),
                new AirShippingFactory()
        ));
        shippingFactoryFacade = new ShippingFactoryFacadeImpl(shippingFactories);
    }

    @Test
    void whenPostShippingProviderIsRetrieved_thenTheCorrectProviderShouldBeReturned() {
        // when
        Shipping shipping = shippingFactoryFacade.createShippingProvider(ShippingMethod.POST);

        // then
        assertEquals(PostShipping.class, shipping.getClass());
    }

    @Test
    void whenDPDShippingProviderIsRetrieved_thenTheCorrectProviderShouldBeReturned() {
        // when
        Shipping shipping = shippingFactoryFacade.createShippingProvider(ShippingMethod.DPD);

        // then
        assertEquals(DPDShipping.class, shipping.getClass());
    }

    @Test
    void whenDHLShippingProviderIsRetrieved_thenTheCorrectProviderShouldBeReturned() {
        // when
        Shipping shipping = shippingFactoryFacade.createShippingProvider(ShippingMethod.DHL);

        // then
        assertEquals(DHLShipping.class, shipping.getClass());
    }

    @Test
    void whenAirShippingProviderIsRetrieved_thenTheCorrectProviderShouldBeReturned() {
        // when
        Shipping shipping = shippingFactoryFacade.createShippingProvider(ShippingMethod.AIR);

        // then
        assertEquals(AirShipping.class, shipping.getClass());
    }
}
