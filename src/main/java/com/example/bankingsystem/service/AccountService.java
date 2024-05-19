package com.example.bankingsystem.service;

import com.example.bankingsystem.DTO.AccountResponse;
import com.example.bankingsystem.DTO.TransactionResponse;
import com.example.bankingsystem.exception.AccountNotFoundException;
import com.example.bankingsystem.exception.BankNotFoundException;
import com.example.bankingsystem.exception.InsufficientFundsException;
import com.example.bankingsystem.model.Account;
import com.example.bankingsystem.model.Bank;
import com.example.bankingsystem.model.Transaction;
import com.example.bankingsystem.repository.AccountRepo;
import com.example.bankingsystem.repository.BankRepo;
import com.example.bankingsystem.repository.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private BankRepo bankRepo;

    @Autowired
    private TransactionRepo transactionRepo;

    public Account createAccount(Long bankId, String name, double balance) {
        // Retrieve the bank from the database
        Bank bank = bankRepo.findById(bankId)
                .orElseThrow(() -> new BankNotFoundException("Bank not found with ID: " + bankId));

        // Create a new account
        Account account = new Account();
        account.setName(name);
        account.setBalance(balance);
        account.setBank(bank);

        // Save the account to the database
        return accountRepo.save(account);
    }

    public List<Account> getAllAccounts() {
        return accountRepo.findAll();
    }

    public Account getAccountById(Long accountId) {
        return accountRepo.findById(accountId).orElseThrow(() -> new AccountNotFoundException("Account not found"));
    }

    public List<TransactionResponse> getTransactions(Long accountId) {
        List<Transaction> transactions = transactionRepo.findByOriginatingAccountIdOrResultingAccountId(accountId, accountId);
        return transactions.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private TransactionResponse mapToDTO(Transaction transaction) {
        TransactionResponse dto = new TransactionResponse();
        dto.setId(transaction.getId());
        dto.setAmount(transaction.getAmount());
        dto.setOriginatingAccountId(transaction.getOriginatingAccountId());
        dto.setResultingAccountId(transaction.getResultingAccountId());
        dto.setReason(transaction.getReason());
        dto.setTimestamp(transaction.getTimestamp());
        dto.setFlatFee(transaction.isFlatFee());

        if (transaction.getOriginatingAccount() != null) {
            AccountResponse originatingAccount = new AccountResponse();
            originatingAccount.setId(transaction.getOriginatingAccount().getId());
            originatingAccount.setName(transaction.getOriginatingAccount().getName());
            originatingAccount.setBalance(transaction.getOriginatingAccount().getBalance());
            dto.setOriginatingAccount(originatingAccount);
        }

        if (transaction.getResultingAccount() != null) {
            AccountResponse resultingAccount = new AccountResponse();
            resultingAccount.setId(transaction.getResultingAccount().getId());
            resultingAccount.setName(transaction.getResultingAccount().getName());
            resultingAccount.setBalance(transaction.getResultingAccount().getBalance());
            dto.setResultingAccount(resultingAccount);
        }

        return dto;
    }



    @Transactional
    public void performTransaction(Account originatingAccount, Account resultingAccount, double amount, String reason, boolean isFlatFee) {
        double fee = isFlatFee ? originatingAccount.getBank().getTransactionFlatFee() :
                amount * originatingAccount.getBank().getTransactionPercentFee() / 100;

        if (originatingAccount.getBalance() < amount + fee) {
            throw new InsufficientFundsException("Not enough funds");
        }

        originatingAccount.setBalance(originatingAccount.getBalance() - amount - fee);
        resultingAccount.setBalance(resultingAccount.getBalance() + amount);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setOriginatingAccountId(originatingAccount.getId());
        transaction.setResultingAccountId(resultingAccount.getId());
        transaction.setReason(reason);
        transaction.setTimestamp(java.time.LocalDateTime.now());
        transaction.setFlatFee(isFlatFee);

        transactionRepo.save(transaction);

        originatingAccount.getBank().setTotalTransactionFeeAmount(
                originatingAccount.getBank().getTotalTransactionFeeAmount() + fee);
        originatingAccount.getBank().setTotalTransferAmount(
                originatingAccount.getBank().getTotalTransferAmount() + amount);

        accountRepo.save(originatingAccount);
        accountRepo.save(resultingAccount);
    }



//    @Transactional
//    public void performTransaction(Long originatingAccountId, Long resultingAccountId, double amount, String reason, boolean isFlatFee) {
//        Account originatingAccount = getAccountById(originatingAccountId);
//        Account resultingAccount = getAccountById(resultingAccountId);
//
//        double fee = isFlatFee ? originatingAccount.getBank().getTransactionFlatFee() :
//                amount * originatingAccount.getBank().getTransactionPercentFee() / 100;
//
//        if (originatingAccount.getBalance() < amount + fee) {
//            throw new InsufficientFundsException("Not enough funds");
//        }
//
//        originatingAccount.setBalance(originatingAccount.getBalance() - amount - fee);
//        resultingAccount.setBalance(resultingAccount.getBalance() + amount);
//
//        Transaction transaction = new Transaction();
//        transaction.setAmount(amount);
//        transaction.setOriginatingAccountId(originatingAccountId);
//        transaction.setResultingAccountId(resultingAccountId);
//        transaction.setReason(reason);
//        transaction.setTimestamp(java.time.LocalDateTime.now());
//
//        transactionRepo.save(transaction);
//
//        originatingAccount.getBank().setTotalTransactionFeeAmount(
//                originatingAccount.getBank().getTotalTransactionFeeAmount() + fee);
//        originatingAccount.getBank().setTotalTransferAmount(
//                originatingAccount.getBank().getTotalTransferAmount() + amount);
//
//        accountRepo.save(originatingAccount);
//        accountRepo.save(resultingAccount);
//    }

    @Transactional
    public void withdraw(Long accountId, double amount) {
        Account account = getAccountById(accountId);

        if (account.getBalance() < amount) {
            throw new InsufficientFundsException("Not enough funds");
        }

        account.setBalance(account.getBalance() - amount);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setOriginatingAccountId(accountId);
        transaction.setResultingAccountId(null);
        transaction.setReason("Withdrawal");
        transaction.setTimestamp(java.time.LocalDateTime.now());

        transactionRepo.save(transaction);

        accountRepo.save(account);
    }

    @Transactional
    public void deposit(Long accountId, double amount) {
        Account account = getAccountById(accountId);
        account.setBalance(account.getBalance() + amount);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setOriginatingAccountId(null);
        transaction.setResultingAccountId(accountId);
        transaction.setReason("Deposit");
        transaction.setTimestamp(java.time.LocalDateTime.now());

        transactionRepo.save(transaction);

        accountRepo.save(account);
    }

//    public List<Transaction> getTransactions(Long accountId) {
//        return transactionRepo.findByOriginatingAccountIdOrResultingAccountId(accountId, accountId);
//    }


}
