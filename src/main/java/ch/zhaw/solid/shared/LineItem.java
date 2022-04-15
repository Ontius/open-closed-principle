package ch.zhaw.solid.shared;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record LineItem(@NotNull @NotBlank String description, @NotNull @Min(0) Float price,
                       @NotNull @Min(1) Integer quantity, @NotNull @Min(0) Float weight) { }
