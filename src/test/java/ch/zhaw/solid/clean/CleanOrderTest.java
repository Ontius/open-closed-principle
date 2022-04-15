package ch.zhaw.solid.clean;

import ch.zhaw.solid.clean.shipping.PostShipping;
import ch.zhaw.solid.shared.LineItem;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CleanOrderTest {

    private final static CleanOrder largeOrder = new CleanOrder(
            new PostShipping(),
            new ArrayList<>(Arrays.asList(
                    new LineItem("iPhone 12 Pro", 999.0f, 1, 1.0f),
                    new LineItem("Samsung Galaxy S20 Ultra", 1010.0f, 1, 1.0f)
            ))
    );

    private final static CleanOrder smallOrder = new CleanOrder(
            new PostShipping(),
            new ArrayList<>(Arrays.asList(
                    new LineItem("Apple", 1.0f, 12, 0.5f),
                    new LineItem("Flowers", 12.0f, 2, 1.0f)
            ))
    );

    private final static CleanOrder tinyOrder = new CleanOrder(
            new PostShipping(),
            new ArrayList<>(List.of(
                    new LineItem("Apple", 1.0f, 1, 0.5f)
            ))
    );

    @Test
    void whenTotalIsCalculatedOnLargeOrder_thenTotalShouldGetRetrievedCorrectly() {
        // when
        float total = largeOrder.getTotal();

        // then
        assertEquals(2009.0f, total);
    }

    @Test
    void whenTotalIsCalculatedOnSmallOrder_thenTotalShouldGetRetrievedCorrectly() {
        // when
        float total = smallOrder.getTotal();

        // then
        assertEquals(36.0f, total);
    }

    @Test
    void whenTotalIsCalculatedOnTinyOrder_thenTotalShouldGetRetrievedCorrectly() {
        // when
        float total = tinyOrder.getTotal();

        // then
        assertEquals(1.0f, total);
    }

    @Test
    void whenTotalWeightIsCalculatedOnLargeOrder_thenTotalWeightShouldGetRetrievedCorrectly() {
        // when
        float totalWeight = largeOrder.getTotalWeight();

        // then
        assertEquals(2.0f, totalWeight);
    }

    @Test
    void whenTotalWeightIsCalculatedOnSmallOrder_thenTotalWeightShouldGetRetrievedCorrectly() {
        // when
        float totalWeight = smallOrder.getTotalWeight();

        // then
        assertEquals(8.0f, totalWeight);
    }

    @Test
    void whenTotalWeightIsCalculatedOnTinyOrder_thenTotalWeightShouldGetRetrievedCorrectly() {
        // when
        float totalWeight = tinyOrder.getTotalWeight();

        // then
        assertEquals(0.5f, totalWeight);
    }

    @Test
    void whenShippingCostsAreCalculatedOnLargeOrder_thenShippingCostsAreRetrievedCorrectly() {
        // when
        float shippingCosts = largeOrder.getShippingCosts();

        // then
        assertEquals(0.0f, shippingCosts);
    }

    @Test
    void whenShippingCostsAreCalculatedOnSmallOrder_thenShippingCostsAreRetrievedCorrectly() {
        // when
        float shippingCosts = smallOrder.getShippingCosts();

        // then
        assertEquals(12.0f, shippingCosts);
    }

    @Test
    void whenShippingCostsAreCalculatedOnTinyOrder_thenShippingCostsAreRetrievedCorrectly() {
        // when
        float shippingCosts = tinyOrder.getShippingCosts();

        // then
        assertEquals(10.0f, shippingCosts);
    }
}
