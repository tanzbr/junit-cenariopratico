package me.caua.egiftstore.dto.order;

import me.caua.egiftstore.dto.product.GiftCardResponseDTO;
import me.caua.egiftstore.model.OrderItem;

public record OrderItemResponseDTO(
        Long id,
        Double price,
        Double discount,
        Integer quantity,
        GiftCardResponseDTO giftCardResponseDTO
) {
    public static OrderItemResponseDTO valueOf(OrderItem orderItem) {
        return new OrderItemResponseDTO(
                orderItem.getId(),
                orderItem.getPrice(),
                orderItem.getDiscount(),
                orderItem.getQuantity(),
                GiftCardResponseDTO.valueOf(orderItem.getGiftCard()));
    }
}
