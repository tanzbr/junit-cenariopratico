package me.caua.egiftstore.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class GiftCard extends DefaultEntity {

    @Column(length = 60, nullable = false)
    private String name;
    @Column(length = 5000)
    private String description;
    @Column(nullable = false)
    private Double price;
    @Column
    private List<String> tags;
    @Column()
    private Boolean visible;
    @OneToMany(mappedBy = "giftCard", cascade=CascadeType.ALL)
    private List<GiftCode> giftCodes;
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "giftcompany_id")
    private GiftCompany giftCompany;
    private String imageName;

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public List<GiftCode> getGiftCodes() {
        return giftCodes;
    }

    public void setGiftCodes(List<GiftCode> giftCodes) {
        if (this.giftCodes != null) this.giftCodes.clear();
        this.giftCodes = giftCodes;
    }

    public GiftCompany getGiftCompany() {
        return giftCompany;
    }

    public void setGiftCompany(GiftCompany giftCompany) {
        this.giftCompany = giftCompany;
    }
}
