package me.caua.egiftstore.dto.product;

import jakarta.validation.constraints.NotBlank;

public record GiftCompanyDTO(
        @NotBlank(message = "name cannot be null or empty.")
        String name,
        @NotBlank(message = "cnpj cannot be null or empty.")
        String cnpj
) {
}
