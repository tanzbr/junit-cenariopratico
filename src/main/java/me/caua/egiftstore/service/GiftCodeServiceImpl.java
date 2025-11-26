package me.caua.egiftstore.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import me.caua.egiftstore.dto.product.GiftCodeDTO;
import me.caua.egiftstore.dto.product.GiftCodeResponseDTO;
import me.caua.egiftstore.model.GiftCode;
import me.caua.egiftstore.repository.GiftCardRepository;
import me.caua.egiftstore.repository.GiftCodeRepository;
import me.caua.egiftstore.validation.ValidationException;

import java.util.List;

@ApplicationScoped
public class GiftCodeServiceImpl implements GiftCodeService{

    @Inject
    public GiftCardRepository giftCardRepository;
    @Inject
    public GiftCodeRepository giftCodeRepository;

    @Override
    @Transactional
    public GiftCodeResponseDTO create(@Valid GiftCodeDTO giftCodeDTO) {
        // validate if product exists
        validateGiftCardExists(giftCodeDTO.giftcardId());

        GiftCode giftCode = new GiftCode();
        giftCode.setCode(giftCodeDTO.giftCode());
        giftCode.setGiftState(giftCodeDTO.giftState());
        giftCode.setGiftCard(giftCardRepository.findById(giftCodeDTO.giftcardId()));

        giftCodeRepository.persist(giftCode);

        return GiftCodeResponseDTO.valueOf(giftCode);
    }

    @Override
    @Transactional
    public void update(Long id, @Valid GiftCodeDTO giftCodeDTO) {
        // validate if product exists
        validateGiftCardExists(giftCodeDTO.giftcardId());
        validateGiftCodeExists(id);

        GiftCode giftCode = giftCodeRepository.findById(id);

        giftCode.setCode(giftCodeDTO.giftCode());
        giftCode.setGiftState(giftCodeDTO.giftState());
        giftCode.setGiftCard(giftCardRepository.findById(giftCodeDTO.giftcardId()));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        validateGiftCodeExists(id);
        giftCodeRepository.deleteById(id);
    }

    @Override
    public GiftCodeResponseDTO findById(Long id) {
        validateGiftCodeExists(id);
        return GiftCodeResponseDTO.valueOf(giftCodeRepository.findById(id));
    }

    @Override
    public List<GiftCodeResponseDTO> findByGiftCard(Long id) {
        validateGiftCardExists(id);
        return giftCodeRepository.findByGiftCard(id)
                .stream()
                .map(GiftCodeResponseDTO::valueOf)
                .toList();
    }

    @Override
    public List<GiftCodeResponseDTO> findAll() {
        return giftCodeRepository.listAll()
                .stream()
                .map(GiftCodeResponseDTO::valueOf)
                .toList();
    }

    public void validateGiftCardExists(Long id) {
        if (giftCardRepository.findById(id) == null)
            throw new ValidationException("giftcardId", "Não existe um giftcard com este ID.");
    }

    public void validateGiftCodeExists(Long id) {
        if (giftCodeRepository.findById(id) == null)
            throw new ValidationException("id", "Não existe um giftcode com este ID.");
    }
}
