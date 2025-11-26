package me.caua.egiftstore.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import me.caua.egiftstore.model.Customer;
import me.caua.egiftstore.model.Order;

import java.util.List;

@ApplicationScoped
public class OrderRepository implements PanacheRepository<Order> {

    public List<Order> findByCustomer(Long id) {
        return find("customer.id = ?1", id).list();
    }
    public List<Order> findByCustomerEmail(String email) {
        return find("customer.user.email = ?1", email).list();
    }

}
