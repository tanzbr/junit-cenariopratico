package me.caua.egiftstore.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import me.caua.egiftstore.dto.product.GiftCardDTO;
import me.caua.egiftstore.dto.product.GiftCardResponseDTO;
import me.caua.egiftstore.model.GiftCard;
import me.caua.egiftstore.model.GiftCode;
import me.caua.egiftstore.repository.GiftCardRepository;
import me.caua.egiftstore.repository.GiftCodeRepository;
import me.caua.egiftstore.repository.GiftCompanyRepository;
import me.caua.egiftstore.validation.ValidationException;

import java.util.List;

@ApplicationScoped
public class GiftCardServiceImpl implements GiftCardService {

    @Inject
    public GiftCardRepository giftCardRepository;
    @Inject
    public GiftCompanyRepository giftCompanyRepository;
    @Inject
    public GiftCodeRepository giftCodeRepository;

    @Override
    @Transactional
    public GiftCardResponseDTO create(@Valid GiftCardDTO giftCardDTO) {
        validateGiftCompanyExists(giftCardDTO.companyId());

        GiftCard giftCard = new GiftCard();
        giftCard.setName(giftCardDTO.name());
        giftCard.setDescription(giftCardDTO.description());
        giftCard.setPrice(giftCardDTO.price());
        giftCard.setTags(giftCardDTO.tags());
        giftCard.setVisible(giftCardDTO.visible());
        giftCard.setGiftCodes(giftCardDTO.giftCodes().stream()
                .map(giftCodeDTO -> {
                    GiftCode giftCode;
                    if (giftCodeRepository.findByCode(giftCodeDTO.giftCode()) == null) {
                        giftCode = new GiftCode();
                    } else {
                        giftCode = giftCodeRepository.findByCode(giftCodeDTO.giftCode());
                    }

                    giftCode.setCode(giftCodeDTO.giftCode());
                    giftCode.setGiftState(giftCodeDTO.giftState());
                    giftCode.setGiftCard(giftCard);

                    return giftCode;
                }).toList());
        giftCard.setGiftCompany(giftCompanyRepository.findById(giftCardDTO.companyId()));

        giftCardRepository.persist(giftCard);

        return GiftCardResponseDTO.valueOf(giftCard);
    }

    @Override
    @Transactional
    public void update(Long id, @Valid GiftCardDTO giftCardDTO) {
        validateGiftCompanyExists(giftCardDTO.companyId());
        validateGiftCardExists(id);

        GiftCard giftCard = giftCardRepository.findById(id);

        giftCard.setName(giftCardDTO.name());
        giftCard.setDescription(giftCardDTO.description());
        giftCard.setPrice(giftCardDTO.price());
        giftCard.setTags(giftCardDTO.tags());
        giftCard.setVisible(giftCardDTO.visible());
        giftCard.setGiftCompany(giftCompanyRepository.findById(giftCardDTO.companyId()));

        giftCard.setGiftCodes(giftCardDTO.giftCodes().stream()
                .map(giftCodeDTO -> new GiftCode(giftCodeDTO.giftCode(), giftCodeDTO.giftState(), giftCard)).toList());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        validateGiftCardExists(id);
        giftCardRepository.deleteById(id);
    }

    @Override
    public GiftCardResponseDTO findById(Long id) {
        validateGiftCardExists(id);
        return GiftCardResponseDTO.valueOf(giftCardRepository.findById(id));
    }

    @Override
    public List<GiftCardResponseDTO> findAll() {
        return giftCardRepository.listAll()
                .stream()
                .map(GiftCardResponseDTO::valueOf)
                .toList();
    }

    @Override
    public List<GiftCardResponseDTO> findByName(String name) {
        return giftCardRepository.findByName(name)
                .stream()
                .map(GiftCardResponseDTO::valueOf)
                .toList();
    }

    public void validateGiftCompanyExists(Long companyId) {
        if (giftCompanyRepository.findById(companyId) == null)
            throw new ValidationException("companyId", "Não existe uma empresa com este ID.");
    }

    public void validateGiftCardExists(Long id) {
        if (giftCardRepository.findById(id) == null)
            throw new ValidationException("id", "GiftCard não encontrado.");
    }
}
