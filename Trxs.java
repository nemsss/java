package com.teamapt.transfers.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Trxs {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(nullable = false, unique = true)
    private Long ref_number;

    @Column
    private Double amount;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="user_fk", nullable = false)
    private User user;

    @Column
    private TrxType trxType;

    @Column
    private Timestamp timestamp;

    public Long getRef_number() {
        return ref_number;
    }

    public void setRef_number(Long ref_number) {
        this.ref_number = ref_number;
    }

    public TrxType getTrxType() {
        return trxType;
    }

    public void setTrxType(TrxType trxType) {
        this.trxType = trxType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

}
