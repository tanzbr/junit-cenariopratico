package me.caua.egiftstore.model;

import jakarta.persistence.*;

@Entity
public class Customer extends DefaultEntity {
    @Column()
    private Boolean acceptMarketing;
    @OneToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    public Boolean getAcceptMarketing() {
        return acceptMarketing;
    }

    public User getUser() {
        return user;
    }

    public void setAcceptMarketing(Boolean acceptMarketing) {
        this.acceptMarketing = acceptMarketing;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
