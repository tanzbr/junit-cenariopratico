package me.caua.egiftstore.model;

import jakarta.persistence.*;
import me.caua.egiftstore.enums.Role;

import java.time.LocalDate;

@Entity
public class Employee extends DefaultEntity {

    @Column()
    private Double weeklyHours;
    @Column()
    private Double salary;
    @Column()
    private LocalDate contractDate;
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "user_id", unique = true)
    private User user;
    @Enumerated
    private Role role;

    public Employee() {
    }

    public Employee(Double weeklyHours, Double salary, LocalDate contractDate, User user, Role role) {
        this.weeklyHours = weeklyHours;
        this.salary = salary;
        this.contractDate = contractDate;
        this.user = user;
        this.role = role;
    }

    public Double getWeeklyHours() {
        return weeklyHours;
    }

    public void setWeeklyHours(Double weeklyHours) {
        this.weeklyHours = weeklyHours;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public LocalDate getContractDate() {
        return contractDate;
    }

    public void setContractDate(LocalDate contractDate) {
        this.contractDate = contractDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
