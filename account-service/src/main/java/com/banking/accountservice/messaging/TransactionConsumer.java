package com.banking.accountservice.messaging;

import com.banking.accountservice.domain.entity.Account;
import com.banking.accountservice.domain.repository.AccountRepository;
import com.banking.accountservice.messaging.event.TransactionCreatedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TransactionConsumer {

    private final AccountRepository accountRepository;

    public TransactionConsumer(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    @RabbitListener(queues = "transaction.created.queue")
    public void handleTransactionCreated(TransactionCreatedEvent event) {
        Account sender = accountRepository.findByDocumentNumber(event.senderDocumentNumber());
        if (sender == null) {
            System.err.println("Sender account not found: " + event.senderDocumentNumber());
            return;
        }

        Account receiver = accountRepository.findByDocumentNumber(event.receiverDocumentNumber());
        if (receiver == null) {
            System.err.println("Receiver account not found: " + event.receiverDocumentNumber());
            return;
        }

        if (sender.getBalance() < event.amount()) {
            System.err.println("Insufficient funds for document: " + sender.getDocumentNumber());
            return;
        }

        sender.setBalance(sender.getBalance() - event.amount());
        receiver.setBalance(receiver.getBalance() + event.amount());

        accountRepository.save(sender);
        accountRepository.save(receiver);

    }
}