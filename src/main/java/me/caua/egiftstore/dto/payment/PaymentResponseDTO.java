package me.caua.egiftstore.dto.payment;

import me.caua.egiftstore.enums.PaymentGateway;
import me.caua.egiftstore.enums.PaymentStatus;
import me.caua.egiftstore.model.Payment;

public record PaymentResponseDTO(
        Long id,
        Double totalPrice,
        PaymentGateway paymentGateway,
        PaymentStatus paymentStatus,
        String externalId,
        String paymentLink
) {

    public static PaymentResponseDTO valueOf(Payment payment) {
        return new PaymentResponseDTO(
                payment.getId(),
                payment.getTotalPrice(),
                payment.getPaymentGateway(),
                payment.getPaymentStatus(),
                payment.getExternalId(),
                payment.getPaymentLink()
        );
    }
}
