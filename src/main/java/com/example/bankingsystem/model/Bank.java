package com.example.bankingsystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import java.util.List;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double transactionFlatFee;
    private double transactionPercentFee;
    private double totalTransactionFeeAmount = 0;
    private double totalTransferAmount = 0;


    @OneToMany(mappedBy = "bank", fetch = FetchType.LAZY)
    private List<Account> accounts;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTransactionFlatFee() {
        return transactionFlatFee;
    }

    public void setTransactionFlatFee(double transactionFlatFee) {
        this.transactionFlatFee = transactionFlatFee;
    }

    public double getTransactionPercentFee() {
        return transactionPercentFee;
    }

    public void setTransactionPercentFee(double transactionPercentFee) {
        this.transactionPercentFee = transactionPercentFee;
    }

    public double getTotalTransactionFeeAmount() {
        return totalTransactionFeeAmount;
    }

    public void setTotalTransactionFeeAmount(double totalTransactionFeeAmount) {
        this.totalTransactionFeeAmount = totalTransactionFeeAmount;
    }

    public double getTotalTransferAmount() {
        return totalTransferAmount;
    }

    public void setTotalTransferAmount(double totalTransferAmount) {
        this.totalTransferAmount = totalTransferAmount;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
