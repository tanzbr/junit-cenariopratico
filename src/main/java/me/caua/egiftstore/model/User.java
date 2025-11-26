package me.caua.egiftstore.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity @Table(name="User_")
public class User extends DefaultEntity {

    @Column()
    private String name;
    @Column()
    private String cpf;
    @Column()
    private String email;
    @Column()
    private String password;
    @Column()
    private Boolean twoFactor;
    @Column()
    private LocalDate birthDate;

    public User() {
    }

    public User(String name, String cpf, String email, String password, Boolean twoFactor, LocalDate birthDate) {
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.password = password;
        this.twoFactor = twoFactor;
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getTwoFactor() {
        return twoFactor;
    }

    public void setTwoFactor(Boolean twoFactor) {
        this.twoFactor = twoFactor;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
