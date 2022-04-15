package ch.zhaw.solid.shared;

import ch.zhaw.solid.shared.transfer.CreateOrderDto;
import ch.zhaw.solid.shared.transfer.OrderDto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface OrderService {

    OrderDto create(@Valid CreateOrderDto createDto);
    List<OrderDto> all();
    OrderDto get(@NotNull @NotBlank String id);
    void delete(@NotNull @NotBlank String id);
}
