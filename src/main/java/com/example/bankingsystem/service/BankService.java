package com.example.bankingsystem.service;

import com.example.bankingsystem.model.Bank;
import com.example.bankingsystem.repository.BankRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankService {

    @Autowired
    private BankRepo bankRepo;

    public Bank createBank(String name, double flatFee, double percentFee) {
        Bank bank = new Bank();
        bank.setName(name);
        bank.setTransactionFlatFee(flatFee);
        bank.setTransactionPercentFee(percentFee);
        return bankRepo.save(bank);
    }

    public List<Bank> getAllBanks() {
        return bankRepo.findAll();
    }

    public Bank getBank(Long bankId) {
        return bankRepo.findById(bankId).orElseThrow(() -> new RuntimeException("Bank not found"));
    }
}
