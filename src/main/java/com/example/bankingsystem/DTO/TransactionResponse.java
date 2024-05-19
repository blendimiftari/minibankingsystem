package com.example.bankingsystem.DTO;



import java.time.LocalDateTime;

public class TransactionResponse {
    private Long id;
    private Double amount;
    private Long originatingAccountId;
    private Long resultingAccountId;
    private String reason;
    private LocalDateTime timestamp;
    private boolean isFlatFee;
    private AccountResponse originatingAccount;
    private AccountResponse resultingAccount;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
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

    public AccountResponse getOriginatingAccount() {
        return originatingAccount;
    }

    public void setOriginatingAccount(AccountResponse originatingAccount) {
        this.originatingAccount = originatingAccount;
    }

    public AccountResponse getResultingAccount() {
        return resultingAccount;
    }

    public void setResultingAccount(AccountResponse resultingAccount) {
        this.resultingAccount = resultingAccount;
    }

    public boolean isFlatFee() {
        return isFlatFee;
    }

    public void setFlatFee(boolean flatFee) {
        this.isFlatFee = flatFee;
    }

}
