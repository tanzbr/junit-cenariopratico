package me.caua.egiftstore.model;

import jakarta.persistence.*;

@Entity
public class OrderItem extends DefaultEntity {

    @Column()
    private Double price;
    @Column()
    private Double discount;
    @Column()
    private Integer quantity;
    @OneToOne
    @JoinColumn(name = "giftcode_id")
    private GiftCode giftCode;
    @ManyToOne
    @JoinColumn(name = "giftcard_id")
    private GiftCard giftCard;

    public GiftCode getGiftCode() {
        return giftCode;
    }

    public void setGiftCode(GiftCode giftCode) {
        this.giftCode = giftCode;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public GiftCard getGiftCard() {
        return giftCard;
    }

    public void setGiftCard(GiftCard giftCard) {
        this.giftCard = giftCard;
    }
}
