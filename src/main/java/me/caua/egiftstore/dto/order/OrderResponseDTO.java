package me.caua.egiftstore.dto.order;

import me.caua.egiftstore.dto.payment.PaymentResponseDTO;
import me.caua.egiftstore.model.Order;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDTO(
        Long id,
        Double totalPrice,
        LocalDateTime orderDate,
        Long customerId,
        List<OrderItemResponseDTO> items,
        PaymentResponseDTO paymentDTO
) {
    public static OrderResponseDTO valueOf(Order order) {
        return new OrderResponseDTO(
                order.getId(),
                order.getTotalPrice(),
                order.getOrderDate(),
                order.getCustomer().getId(),
                order.getItems().stream().map(OrderItemResponseDTO::valueOf).toList(),
                PaymentResponseDTO.valueOf(order.getPayment())
        );
    }
}
