package me.caua.egiftstore.dto.order;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record OrderItemDTO(
        @PositiveOrZero(message = "price cannot be negative")
        Double price,
        @PositiveOrZero(message = "discount cannot be negative")
        Double discount,
        @Positive(message = "quantity must be positive")
        Integer quantity,
        @NotNull(message = "giftcardId cannot be null")
        Long giftcardId
) {

}
