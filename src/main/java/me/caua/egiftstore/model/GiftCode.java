package me.caua.egiftstore.model;

import jakarta.persistence.*;
import me.caua.egiftstore.enums.GiftState;

@Entity
public class GiftCode extends DefaultEntity {

    @Column()
    private String code;
    @Enumerated
    private GiftState giftState;
    @ManyToOne(cascade = CascadeType.ALL) @JoinColumn(name = "giftcard_id")
    private GiftCard giftCard;

    public GiftCard getGiftCard() {
        return giftCard;
    }

    public void setGiftCard(GiftCard giftCard) {
        this.giftCard = giftCard;
    }

    public GiftCode(String code, GiftState giftState, GiftCard giftCard) {
        this.code = code;
        this.giftState = giftState;
        this.giftCard = giftCard;
    }

    public GiftCode() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public GiftState getGiftState() {
        return giftState;
    }

    public void setGiftState(GiftState giftState) {
        this.giftState = giftState;
    }
}
