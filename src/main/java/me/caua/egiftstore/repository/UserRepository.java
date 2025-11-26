package me.caua.egiftstore.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import me.caua.egiftstore.model.Customer;
import me.caua.egiftstore.model.User;

import java.util.List;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

    public List<User> findByName(String name) {
        return find("UPPER(name) LIKE UPPER(?1)", "%"+name+"%").list();
    }
    public User findByCpf(String cpf) {
        return find("UPPER(cpf) = UPPER(?1)", cpf).firstResult();
    }
    public User findByEmail(String email) {
        return find("UPPER(email) = UPPER(?1)", email).firstResult();
    }

}
