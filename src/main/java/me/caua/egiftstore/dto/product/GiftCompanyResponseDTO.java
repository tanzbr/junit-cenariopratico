package me.caua.egiftstore.dto.product;

import me.caua.egiftstore.model.GiftCompany;

public record GiftCompanyResponseDTO(
        Long id,
        String name,
        String cnpj,
        String logo
) {
    public static GiftCompanyResponseDTO valueOf(GiftCompany giftCompany) {
        return new GiftCompanyResponseDTO(
                giftCompany.getId(),
                giftCompany.getName(),
                giftCompany.getCnpj(),
                giftCompany.getImageName()
        );
    }
}
