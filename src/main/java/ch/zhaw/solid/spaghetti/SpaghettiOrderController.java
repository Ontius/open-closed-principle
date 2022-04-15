package ch.zhaw.solid.spaghetti;

import ch.zhaw.solid.shared.OrderService;
import ch.zhaw.solid.shared.transfer.CreateOrderDto;
import ch.zhaw.solid.shared.transfer.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api/spaghetti/order")
@RequiredArgsConstructor
@Validated
public class SpaghettiOrderController {

    private final OrderService spaghettiOrderService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDto> getAll() {
        return spaghettiOrderService.all();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto get(@PathVariable @NotNull @NotBlank String id) {
        return spaghettiOrderService.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto create(@RequestBody @Valid CreateOrderDto dto) {
        return spaghettiOrderService.create(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        spaghettiOrderService.delete(id);
    }
}
