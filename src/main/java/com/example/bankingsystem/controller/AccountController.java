package com.example.bankingsystem.controller;

import com.example.bankingsystem.DTO.*;
import com.example.bankingsystem.model.Account;
import com.example.bankingsystem.model.Transaction;
import com.example.bankingsystem.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<Account> createAccount(@RequestBody CreateAccountRequest request) {
        Account account = accountService.createAccount(request.getBankId(), request.getName(), request.getBalance());
        return ResponseEntity.ok(account);
    }

    @GetMapping("/all")
    public List<AccountResponse> getAllAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        return accounts.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private AccountResponse mapToDTO(Account account) {
        AccountResponse dto = new AccountResponse();
        dto.setId(account.getId());
        dto.setName(account.getName());
        dto.setBalance(account.getBalance());

        // Map bank information to DTO
        BankDTO bankDTO = new BankDTO();
        bankDTO.setId(account.getBank().getId());
        bankDTO.setName(account.getBank().getName());
        dto.setBank(bankDTO);

        return dto;
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable Long accountId) {
        Account account = accountService.getAccountById(accountId);
        if (account != null) {
            AccountResponse accountResponse = new AccountResponse();
            accountResponse.setId(account.getId());
            accountResponse.setName(account.getName());
            accountResponse.setBalance(account.getBalance());

            return ResponseEntity.ok(accountResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


//    @PostMapping("/transaction")
//    public ResponseEntity<Void> performTransaction(
//            @RequestBody Transaction transaction) {
//        accountService.performTransaction(transaction.getOriginatingAccountId(), transaction.getResultingAccountId(), transaction.getAmount(), transaction.getReason(),  transaction.isFlatFee());
//        return ResponseEntity.ok().build();
//    }

    @PostMapping("/transaction")
    public ResponseEntity<Void> performTransaction(@RequestBody Transaction transaction) {
        Account originatingAccount = accountService.getAccountById(transaction.getOriginatingAccountId());
        Account resultingAccount = accountService.getAccountById(transaction.getResultingAccountId());
        accountService.performTransaction(originatingAccount, resultingAccount, transaction.getAmount(), transaction.getReason(), transaction.isFlatFee());
        return ResponseEntity.ok().build();
    }




    @PostMapping("/withdraw")
    public ResponseEntity<Void> withdraw(@RequestBody WithdrawRequest withdrawRequest) {
        accountService.withdraw(withdrawRequest.getAccountId(), withdrawRequest.getAmount());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/deposit")
    public ResponseEntity<Void> deposit(@RequestBody DepositRequest depositRequest) {
        accountService.deposit(depositRequest.getAccountId(), depositRequest.getAmount());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<List<TransactionResponse>> getTransactions(@PathVariable Long accountId) {
        List<TransactionResponse> transactions = accountService.getTransactions(accountId);
        return ResponseEntity.ok(transactions);
    }

//    @GetMapping("/{accountId}/transactions")
//    public ResponseEntity<List<Transaction>> getTransactions(@PathVariable Long accountId) {
//        List<Transaction> transactions = accountService.getTransactions(accountId);
//        return ResponseEntity.ok(transactions);
//    }
}
