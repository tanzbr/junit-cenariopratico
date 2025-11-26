package me.caua.egiftstore.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import me.caua.egiftstore.model.GiftCode;

import java.util.List;

@ApplicationScoped
public class GiftCodeRepository implements PanacheRepository<GiftCode> {

    public List<GiftCode> findByGiftCard(Long id) {
        return find("from GiftCode where giftCard.id = ?1", id).list();
    }
    public GiftCode findByCode(String code) {
        return find("UPPER(code) = UPPER(?1)", code).firstResult();
    }
}
