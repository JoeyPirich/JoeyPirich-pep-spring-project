package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    private AccountRepository accountRepository;
    private MessageRepository messageRepository;

    @Autowired
    public MessageService(AccountRepository accountRepository, MessageRepository messageRepository){
        this.accountRepository = accountRepository;
        this.messageRepository = messageRepository;
    }
    /**
     * Add a message, only if its text is at least one and no more than 255
     * characters, and the user it is posted by actually exists in the
     * database. Return the message if successful, otherwise return null.
     * 
     * @param message
     * @return the given message with its assigned ID if adding was successful,
     * else null.
     */
    public Message createMessage(Message message) {
        if (!message.getMessageText().isEmpty()
            && message.getMessageText().length() <= 0xFF
            && accountRepository.findById(message.getPostedBy()).isPresent()) {
            return messageRepository.save(message);
        } else {
            return null;
        }
    }

    /**
     * Provides a list of all messages in the database
     * 
     * @return list of messages, or null in case of failure
     */
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    /**
     * Provides the message with the given ID if it is in the database,
     * or null otherwise
     * @param messageId
     * @return message with ID if present, else null
     */
    public Message getMessageById(int messageId) {
        return messageRepository.findById(messageId).orElse(null);
    }
}
