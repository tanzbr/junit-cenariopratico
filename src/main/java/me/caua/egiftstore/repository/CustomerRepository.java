package me.caua.egiftstore.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import me.caua.egiftstore.model.Customer;

import java.util.List;

@ApplicationScoped
public class CustomerRepository implements PanacheRepository<Customer> {

    public List<Customer> findByName(String name) {
        return find("UPPER(user.name) LIKE UPPER(?1)", "%"+name+"%").list();
    }

    public Customer findByCpf(String cpf) {
        return find("user.cpf = ?1", cpf).firstResult();
    }

    public Customer findByEmail(String email) {
        return find("UPPER(user.email) = UPPER(?1)", email).firstResult();
    }

    public Customer findByEmailAndPass(String email, String password) {
        return find("UPPER(user.email) = UPPER(?1) AND user.password = ?2", email, password).firstResult();
    }

}
