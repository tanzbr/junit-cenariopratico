package me.caua.egiftstore.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import me.caua.egiftstore.model.Order;
import me.caua.egiftstore.model.Payment;

import java.util.List;

@ApplicationScoped
public class PaymentRepository implements PanacheRepository<Payment> {

}
