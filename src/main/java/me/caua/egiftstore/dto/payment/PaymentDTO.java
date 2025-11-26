package me.caua.egiftstore.dto.payment;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import me.caua.egiftstore.enums.PaymentGateway;
import me.caua.egiftstore.enums.PaymentStatus;

public record PaymentDTO(
        @PositiveOrZero(message = "totalPrice cannot be negative")
        Double totalPrice,
        @NotNull(message = "paymentGateway cannot be null")
        PaymentGateway paymentGateway,
        @NotNull(message = "paymentStatus cannot be null")
        PaymentStatus paymentStatus
) {

}
