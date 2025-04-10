package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.UsernameTakenException;
import com.example.service.AccountService;
import com.example.service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    @Autowired
    AccountService accountService;
    @Autowired
    MessageService messageService;

    /**
     * Handler for account registration.
     * Register the account represented in the request body.
     * The response will have the added account in the response body and status
     * code 200 if successful, or status code 400 otherwise.
     * 
     * @param account
     * @return ResponseEntity with appropriate status code and account in body
     * if successful
     */
    @PostMapping("/register")
    ResponseEntity<?> registerAccount(@RequestBody Account account) {
        try {
            Account returnedAccount = accountService.registerAccount(account);
            if (returnedAccount == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Client error");
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(account);
            }
        } catch (UsernameTakenException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict");
        }
    }

    /**
     * Handler for verifying login credentials.
     * Response will contain the corresponding account in its body with status
     * code 200 if login successful, otherwise gives status code 401.
     * 
     * @param account
     * @return ResponseEntity with appropriate status code and account in body
     * if successful
     */
    @PostMapping("/login")
    ResponseEntity<?> verifyLogin(@RequestBody Account account) {
        Account returnedAccount = accountService.verifyLogin(account);
        if (returnedAccount == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Unauthorized");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(returnedAccount);
        }
    }

    /**
     * Handler for posting a new message.
     * Response body contains the new message in its body with status code 200
     * if successful, otherwise gives status code 400.
     * 
     * @param message
     * @return ResponseEntity with appropriate status code and message in body
     * if successful
     */
    @PostMapping("/messages")
    ResponseEntity<?> postMessage(@RequestBody Message message) {
        Message returnedMessage = messageService.createMessage(message);
        if (returnedMessage == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Client error");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(returnedMessage);
        }
    }

    /**
     * Handler for retrieving all messages.
     * Response body contains a list of all messages with a status code of 200.
     * 
     * @return List of all messages
     */
    @GetMapping("/messages")
    List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

    /**
     * Handler for retrieving a message by its ID.
     * Response body contains a the message if found with status code 200.
     * 
     * @param id
     * @return ResponseEntity with status 200 and the message in body if found,
     * otherwise empty body
     */
    @GetMapping("/messages/{id}")
    ResponseEntity<?> getMessageById(@PathVariable int id) {
        Message returnedMessage = messageService.getMessageById(id);
        return ResponseEntity.status(HttpStatus.OK)
            .body(returnedMessage == null ? "" : returnedMessage);
    }

    /**
     * Handler for deleting a message with the given ID.
     * Response body contains the deleted message if deletion successful, with
     * status code 200.
     * 
     * @param id
     * @return ResponseEntity with status 200 and the number of lines modified,
     * 1, in the body if successful, otherwise empty body
     */
    @DeleteMapping("/messages/{id}")
    ResponseEntity<?> deleteMessageById(@PathVariable int id) {
        Message returnedMessage = messageService.deleteMessageWithId(id);
        return ResponseEntity.status(HttpStatus.OK)
            .body(returnedMessage == null ? "" : 1);
    }

    /**
     * Handler to edit the message text of an existing message with the given ID.
     * Response body contains the edited message with status code 200 if
     * successful, otherwise gives status code 400.
     * 
     * @param id
     * @param message
     * @return ResponseEntity with the appropriate status and the number of
     * lines modified, 1, in the body if successful
     */
    @PatchMapping("/messages/{id}")
    ResponseEntity<?> patchMessageWithId(
        @PathVariable int id, @RequestBody Message message) {
        Message returnedMessage = messageService.editMessageWithId(
            id, message.getMessageText());
        if (returnedMessage == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Client error");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(1);
        }
    }

    /**
     * Handler to retrieve all messages posted by a given user.
     * Response body contains the list of all messages posted by the user with
     * status code 200.
     * 
     * @param accountId
     * @return List of all messages posted by the given account
     */
    @GetMapping("/accounts/{accountId}/messages")
    List<Message> getAllMessagesByUser(@PathVariable int accountId) {
        return messageService.getAllMessagesByUser(accountId);
    }
}
