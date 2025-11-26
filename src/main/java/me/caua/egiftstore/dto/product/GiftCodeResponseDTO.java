package me.caua.egiftstore.dto.product;

import me.caua.egiftstore.enums.GiftState;
import me.caua.egiftstore.model.GiftCode;

public record GiftCodeResponseDTO(
        Long id,
        String giftCode,
        GiftState giftState,
        Long giftcardId
) {

    public static GiftCodeResponseDTO valueOf(GiftCode giftCode) {
        return new GiftCodeResponseDTO(
                giftCode.getId(),
                giftCode.getCode(),
                giftCode.getGiftState(),
                giftCode.getGiftCard().getId()
        );
    }
}
