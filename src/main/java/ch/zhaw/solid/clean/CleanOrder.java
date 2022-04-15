package ch.zhaw.solid.clean;

import ch.zhaw.solid.clean.shipping.Shipping;
import ch.zhaw.solid.shared.LineItem;
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
public class CleanOrder implements ch.zhaw.solid.shared.Order<Shipping> {

    @NotNull @NotBlank
    private final String id;

    @NotNull
    private final Shipping shippingMethod;

    @NotNull
    private final List<@Valid LineItem> lineItems;

    public CleanOrder(Shipping shippingMethod, List<@Valid LineItem> lineItems) {
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
        return shippingMethod.getCost(this);
    }
}
