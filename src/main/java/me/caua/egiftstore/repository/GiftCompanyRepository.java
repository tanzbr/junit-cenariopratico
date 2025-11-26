package me.caua.egiftstore.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import me.caua.egiftstore.model.GiftCompany;

import java.util.List;

@ApplicationScoped
public class GiftCompanyRepository implements PanacheRepository<GiftCompany> {

    public List<GiftCompany> findByName(String name) {
        return find("UPPER(name) LIKE UPPER(?1)", "%"+name+"%").list();
    }

}
