package com.example.bankingsystem.repository;

import com.example.bankingsystem.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends JpaRepository<Account, Long> {
}
