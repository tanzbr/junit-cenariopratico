package me.caua.egiftstore.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.util.List;

public record GiftCardDTO(
        @NotBlank(message = "name cannot be null or empty") @Size(max = 60, message = "The name cannot be longer than 60 characters")
        String name,
        @Size(max = 5000, message = "The maximum description length is 5000 characters.")
        String description,
        @PositiveOrZero(message = "The price cannot be negative.") @NotNull(message = "price cannot be null")
        Double price,
        @NotNull(message = "companyId cannot be null") @PositiveOrZero(message = "companyId cannot be negative")
        Long companyId,
        @NotNull(message = "tags cannot be null")
        List<String> tags,
        @NotNull(message = "giftcodes cannot be null")
        List<GiftCodeDTO> giftCodes,
        @NotNull(message = "visible cannot be null")
        Boolean visible
) {

    @Override
    public String name() {
        return name;
    }

    @Override
    public String description() {
        return description;
    }

    @Override
    public Double price() {
        return price;
    }

    public Long companyId() {
        return companyId;
    }

    @Override
    public List<String> tags() {
        return tags;
    }

    @Override
    public List<GiftCodeDTO> giftCodes() {
        return giftCodes;
    }

    @Override
    public Boolean visible() {
        return visible;
    }
}
