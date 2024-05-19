package com.example.bankingsystem.controller;

import com.example.bankingsystem.model.Bank;
import com.example.bankingsystem.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/banks")
public class BankController {

    @Autowired
    private BankService bankService;

    @PostMapping("/create")
    public ResponseEntity<Bank> createBank(@RequestBody Bank request) {
        Bank bank = bankService.createBank(request.getName(), request.getTransactionFlatFee(), request.getTransactionPercentFee());
        return ResponseEntity.ok(bank);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Bank>> getAllBanks() {
        List<Bank> banks = bankService.getAllBanks();
        return ResponseEntity.ok(banks);
    }

    @GetMapping("/{bankId}")
    public ResponseEntity<Bank> getBank(@PathVariable Long bankId) {
        Bank bank = bankService.getBank(bankId);
        return ResponseEntity.ok(bank);
    }
}
