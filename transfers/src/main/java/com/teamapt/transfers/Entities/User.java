package com.teamapt.transfers.Entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class User implements Serializable {

    @Column
    private String name;

    @Column
    private String email;

    @Id
    @Column(nullable = false, unique = true)
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    Integer acc_number;

    @Column
    Double balance;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAcc_number() {
        return acc_number;
    }

    public void setAcc_number(Integer acc_number) {
        this.acc_number = acc_number;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}