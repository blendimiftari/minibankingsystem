package com.example.bankingsystem.repository;

import com.example.bankingsystem.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepo extends JpaRepository<Transaction, Long> {
    List<Transaction> findByOriginatingAccountIdOrResultingAccountId(Long originatingAccountId, Long resultingAccountId);
}
