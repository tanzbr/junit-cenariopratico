package me.caua.egiftstore.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import me.caua.egiftstore.dto.product.GiftCompanyDTO;
import me.caua.egiftstore.dto.product.GiftCompanyResponseDTO;
import me.caua.egiftstore.model.GiftCompany;
import me.caua.egiftstore.repository.GiftCompanyRepository;
import me.caua.egiftstore.validation.ValidationException;

import java.util.List;

@ApplicationScoped
public class GiftCompanyServiceImpl implements GiftCompanyService {

    @Inject
    public GiftCompanyRepository giftCompanyRepository;

    @Override
    @Transactional
    public GiftCompanyResponseDTO create(@Valid GiftCompanyDTO giftCompanyDTO) {
        GiftCompany giftCompany = new GiftCompany();

        giftCompany.setName(giftCompanyDTO.name());
        giftCompany.setCnpj(giftCompanyDTO.cnpj());

        giftCompanyRepository.persist(giftCompany);

        return GiftCompanyResponseDTO.valueOf(giftCompany);
    }

    @Override
    @Transactional
    public void update(Long id, @Valid GiftCompanyDTO giftCompanyDTO) {
        validateCompanyExists(id);
        GiftCompany giftCompany = giftCompanyRepository.findById(id);

        giftCompany.setName(giftCompanyDTO.name());
        giftCompany.setCnpj(giftCompanyDTO.cnpj());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        validateCompanyExists(id);
        giftCompanyRepository.deleteById(id);
    }

    @Override
    public GiftCompanyResponseDTO findById(Long id) {
        validateCompanyExists(id);
        return GiftCompanyResponseDTO.valueOf(giftCompanyRepository.findById(id));
    }

    @Override
    public List<GiftCompanyResponseDTO> findAll() {
        return giftCompanyRepository.listAll()
                .stream()
                .map(GiftCompanyResponseDTO::valueOf)
                .toList();
    }

    @Override
    public List<GiftCompanyResponseDTO> findByName(String name) {
        return giftCompanyRepository.findByName(name)
                .stream()
                .map(GiftCompanyResponseDTO::valueOf)
                .toList();
    }

    public void validateCompanyExists(Long id) {
        if (giftCompanyRepository.findById(id) == null)
            throw new ValidationException("id", "GiftCompany not found.");
    }

}
