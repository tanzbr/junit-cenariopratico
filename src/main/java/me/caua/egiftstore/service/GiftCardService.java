package me.caua.egiftstore.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import me.caua.egiftstore.dto.product.GiftCardDTO;
import me.caua.egiftstore.dto.product.GiftCardResponseDTO;

import java.util.List;

@ApplicationScoped
public interface GiftCardService {

    GiftCardResponseDTO create(@Valid GiftCardDTO giftCardDTO);
    void update(Long id, @Valid GiftCardDTO giftCardDTO);
    void delete(Long id);
    GiftCardResponseDTO findById(Long id);
    List<GiftCardResponseDTO> findByName(String name);
    List<GiftCardResponseDTO> findAll();

}
