package com.enigma.bankenigma.controller;

import com.enigma.bankenigma.entity.Transaction;
import com.enigma.bankenigma.service.transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/withdraw")
    public Transaction withdraw(@RequestBody Transaction transaction){
        transaction.setType("Withdraw");
        return transactionService.create(transaction);
    }

    @PostMapping("/topup")
    public Transaction topup(@RequestBody Transaction transaction){
        transaction.setType("Top Up");
        return transactionService.create(transaction);
    }

    @PostMapping("/payment")
    public Transaction payment(@RequestBody Transaction transaction){
        transaction.setType("Payment");
        return transactionService.create(transaction);
    }
}
