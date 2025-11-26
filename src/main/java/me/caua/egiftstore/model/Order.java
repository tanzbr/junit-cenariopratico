package me.caua.egiftstore.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity @Table(name="Order_")
public class Order extends DefaultEntity {

    @Column()
    private Double totalPrice;
    @Column()
    private LocalDateTime orderDate;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_pedido")
    private List<OrderItem> items;
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
