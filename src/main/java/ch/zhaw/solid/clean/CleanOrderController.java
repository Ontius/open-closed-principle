package ch.zhaw.solid.clean;

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
@RequestMapping("/api/clean/order")
@RequiredArgsConstructor
@Validated
public class CleanOrderController {

    private final OrderService cleanOrderService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDto> getAll() {
        return cleanOrderService.all();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto get(@PathVariable @NotNull @NotBlank String id) {
        return cleanOrderService.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto create(@RequestBody @Valid CreateOrderDto dto) {
        return cleanOrderService.create(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        cleanOrderService.delete(id);
    }
}
