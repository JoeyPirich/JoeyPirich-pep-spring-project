package com.example.repository;

import com.example.entity.Account;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    public Account findAccountByUsername(String username);

    public Account findAccountByUsernameAndPassword(
        String username, String password);
}
