package me.caua.egiftstore.dto.order;

import jakarta.validation.constraints.NotNull;
import me.caua.egiftstore.dto.payment.PaymentDTO;

import java.time.LocalDateTime;
import java.util.List;

public record OrderDTO(
        @NotNull(message = "orderDate cannot be null.")
        LocalDateTime orderDate,
        @NotNull(message = "customerId cannot be null.")
        Long customerId,
        @NotNull(message = "items cannot be null.")
        List<OrderItemDTO> items,
        @NotNull(message = "payment cannot be null.")
        PaymentDTO paymentDTO
) {

}
