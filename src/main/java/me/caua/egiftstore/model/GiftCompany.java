package me.caua.egiftstore.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class GiftCompany extends DefaultEntity {

    @Column()
    private String name;
    @Column()
    private String cnpj;
    private String imageName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
