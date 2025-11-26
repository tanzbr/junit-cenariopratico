package me.caua.egiftstore.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import me.caua.egiftstore.dto.product.GiftCodeDTO;
import me.caua.egiftstore.dto.product.GiftCodeResponseDTO;

import java.util.List;

@ApplicationScoped
public interface GiftCodeService {

    GiftCodeResponseDTO create(@Valid GiftCodeDTO giftCodeDTO);
    void update(Long id, @Valid GiftCodeDTO giftCodeDTO);
    void delete(Long id);
    GiftCodeResponseDTO findById(Long id);
    List<GiftCodeResponseDTO> findByGiftCard(Long id);
    List<GiftCodeResponseDTO> findAll();

}
