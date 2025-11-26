package me.caua.egiftstore.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import me.caua.egiftstore.model.GiftCard;

import java.util.List;

@ApplicationScoped
public class GiftCardRepository implements PanacheRepository<GiftCard> {

    public List<GiftCard> findByName(String name) {
        return find("UPPER(name) LIKE UPPER(?1)", "%"+name+"%").list();
    }

}
