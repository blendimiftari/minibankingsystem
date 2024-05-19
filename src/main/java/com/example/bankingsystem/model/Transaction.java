package com.example.bankingsystem.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double amount;
    private Long originatingAccountId;
    private Long resultingAccountId;
    private String reason;
    private LocalDateTime timestamp;
    private boolean isFlatFee;


    @ManyToOne
    @JoinColumn(name = "originatingAccountId", insertable = false, updatable = false)
    private Account originatingAccount;

    @ManyToOne
    @JoinColumn(name = "resultingAccountId", insertable = false, updatable = false)
    private Account resultingAccount;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Long getOriginatingAccountId() {
        return originatingAccountId;
    }

    public void setOriginatingAccountId(Long originatingAccountId) {
        this.originatingAccountId = originatingAccountId;
    }

    public Long getResultingAccountId() {
        return resultingAccountId;
    }

    public void setResultingAccountId(Long resultingAccountId) {
        this.resultingAccountId = resultingAccountId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Account getOriginatingAccount() {
        return originatingAccount;
    }

    public void setOriginatingAccount(Account originatingAccount) {
        this.originatingAccount = originatingAccount;
    }

    public Account getResultingAccount() {
        return resultingAccount;
    }

    public void setResultingAccount(Account resultingAccount) {
        this.resultingAccount = resultingAccount;
    }

    public boolean isFlatFee() {
        return isFlatFee;
    }

    public void setFlatFee(boolean flatFee) {
        this.isFlatFee = flatFee;
    }




}
