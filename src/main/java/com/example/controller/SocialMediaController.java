package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.exception.UsernameTakenException;
import com.example.service.AccountService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    @Autowired
    AccountService accountService;

    @PostMapping("/register")
    ResponseEntity<?> registerAccount(@RequestBody Account account) {
        try {
            Account returnedAccount = accountService.registerAccount(account);
            if (returnedAccount == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Client error");
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(account);
            }
        } catch (UsernameTakenException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict");
        }
    }


    @PostMapping("/login")
    ResponseEntity<?> verifyLogin(@RequestBody Account account) {
        Account returnedAccount = accountService.verifyLogin(account);
        if (returnedAccount == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(returnedAccount);
        }
    }
}
