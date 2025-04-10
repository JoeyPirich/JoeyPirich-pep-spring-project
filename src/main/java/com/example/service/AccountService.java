package com.example.service;

import org.apache.tomcat.jni.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.UsernameTakenException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    /**
     * Register a new account
     * 
     * @param account
     * @return the given account with its generated ID if registration
     * successful, else null
     * @throws UsernameTakenException if the given username is already taken
     */
    public Account registerAccount(Account account)
        throws UsernameTakenException {
        if (!account.getUsername().isEmpty() 
            && account.getPassword().length() >= 4) {
            if (accountRepository.findAccountByUsername(account.getUsername())
                != null) {
                throw new UsernameTakenException();
            } else {
                return accountRepository.save(account);
            }
        } else {  // fail and return null if conditions not met
            return null;
        }
    }

    /**
     * Verify a login by comparing the given account against the database,
     * returning a matching account if one is found with the same username and
     * password
     * 
     * @param account
     * @return account with matching username and password if found, else null
     */
    public Account verifyLogin(Account account) {
        return accountRepository.findAccountByUsernameAndPassword(
            account.getUsername(), account.getPassword());
    }
}
