package me.caua.egiftstore.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import me.caua.egiftstore.enums.GiftState;

public record GiftCodeDTO(
        @NotBlank(message = "giftcode cannot be null or empty.") @Size(max = 150, message = "The maximum giftcode size is 150 characters.")
        String giftCode,
        @NotNull(message = "giftState cannot be null.")
        GiftState giftState,
        Long giftcardId
) {
}
