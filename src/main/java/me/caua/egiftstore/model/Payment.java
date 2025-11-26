package me.caua.egiftstore.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import me.caua.egiftstore.enums.PaymentGateway;
import me.caua.egiftstore.enums.PaymentStatus;

@Entity
public class Payment extends DefaultEntity {

    @Column()
    private Double totalPrice;
    @Enumerated
    private PaymentStatus paymentStatus;
    @Enumerated
    private PaymentGateway paymentGateway;
    private String externalId;
    private String paymentLink;

    public Payment() {
    }

    public Payment(Double totalPrice, PaymentStatus paymentStatus, PaymentGateway paymentGateway) {
        this.totalPrice = totalPrice;
        this.paymentStatus = paymentStatus;
        this.paymentGateway = paymentGateway;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public PaymentGateway getPaymentGateway() {
        return paymentGateway;
    }

    public void setPaymentGateway(PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    public String getPaymentLink() {
        return paymentLink;
    }

    public void setPaymentLink(String paymentLink) {
        this.paymentLink = paymentLink;
    }
}
