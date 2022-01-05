package com.enigma.bankenigma.service.transaction;

import com.enigma.bankenigma.entity.Transaction;
import com.enigma.bankenigma.repository.TransactionRepository;
import com.enigma.bankenigma.service.account.UserAccountService;
import com.enigma.bankenigma.service.ewallet.EWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class TransactionServiceDBImpl implements TransactionService{
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UserAccountService userAccountService;

    @Autowired
    EWalletService eWalletService;

    @Override
    public Transaction create(Transaction transaction) {
        transaction.setTimestamp(new Timestamp(System.currentTimeMillis()));
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction checkAccount(String id) {
        return null;
    }

    @Override
    public String deleteAccount(String id) {
        return null;
    }
}
