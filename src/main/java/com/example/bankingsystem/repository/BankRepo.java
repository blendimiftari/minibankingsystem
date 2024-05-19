package com.example.bankingsystem.repository;

import com.example.bankingsystem.model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepo extends JpaRepository<Bank, Long> {
}
