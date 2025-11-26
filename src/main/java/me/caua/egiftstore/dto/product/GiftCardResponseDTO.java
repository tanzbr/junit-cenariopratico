package me.caua.egiftstore.dto.product;

import me.caua.egiftstore.model.GiftCard;

import java.util.List;

public record GiftCardResponseDTO(
        Long id,
        String name,
        String description,
        Double price,
        List<String> tags,
        String imageName,
        Boolean visible,
        GiftCompanyResponseDTO company
) {
    public static GiftCardResponseDTO valueOf(GiftCard giftCard) {
        return new GiftCardResponseDTO(
                giftCard.getId(),
                giftCard.getName(),
                giftCard.getDescription(),
                giftCard.getPrice(),
                giftCard.getTags(),
                giftCard.getImageName(),
                giftCard.getVisible(),
                GiftCompanyResponseDTO.valueOf(giftCard.getGiftCompany()));
    }
}
