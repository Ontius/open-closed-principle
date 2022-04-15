package ch.zhaw.solid.spaghetti;

import ch.zhaw.solid.shared.LineItem;
import ch.zhaw.solid.shared.ShippingMethod;
import ch.zhaw.solid.shared.error.OrderException;
import ch.zhaw.solid.shared.util.StreamUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Getter
@EqualsAndHashCode
public class SpaghettiOrder implements ch.zhaw.solid.shared.Order<ShippingMethod> {

    @NotNull @NotBlank
    private final String id;

    @NotNull
    private final ShippingMethod shippingMethod;

    @NotNull
    private final List<@Valid LineItem> lineItems;

    public SpaghettiOrder(ShippingMethod shippingMethod, List<@Valid LineItem> lineItems) {
        this.id = UUID.randomUUID().toString();
        this.shippingMethod = shippingMethod;
        this.lineItems = lineItems;
    }

    @Override
    public Float getTotal() {
        return StreamUtils.collectionStream(lineItems)
                .map(lineItem -> lineItem.price() * lineItem.quantity())
                .reduce(0f, Float::sum);
    }

    @Override
    public Float getTotalWeight() {
        return StreamUtils.collectionStream(lineItems)
                .map(lineItem -> lineItem.weight() * lineItem.quantity())
                .reduce(0f, Float::sum);
    }

    @Override
    public Float getShippingCosts() {
        return switch (shippingMethod) {
            case POST, DPD, DHL -> getTotal() > 100 ? 0 : (float) Math.max(10, getTotalWeight() * 1.5);
            case AIR -> Math.max(20, getTotalWeight() * 3);
            default -> throw new OrderException("The ShippingMethod '%s' is not supported.".formatted(shippingMethod));
        };
    }
}
