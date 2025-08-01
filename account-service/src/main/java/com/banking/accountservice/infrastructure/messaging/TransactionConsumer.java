package com.banking.accountservice.infrastructure.messaging;

import com.banking.accountservice.application.handler.GetAccountHandler;
import com.banking.accountservice.application.handler.UpdateAccountBalanceHandler;
import com.banking.accountservice.application.query.GetAccountByDocumentQuery;
import com.banking.accountservice.application.command.UpdateAccountBalanceCommand;
import com.banking.accountservice.domain.exception.AccountNotFoundException;
import com.banking.accountservice.domain.exception.InsufficientFundsException;
import com.banking.accountservice.domain.model.Account;
import com.banking.accountservice.domain.model.Balance;
import com.banking.transactionservice.infrastructure.messaging.dto.TransactionCreatedEventDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TransactionConsumer {

    private static final Logger logger = LoggerFactory.getLogger(TransactionConsumer.class);
    
    private final GetAccountHandler getAccountHandler;
    private final UpdateAccountBalanceHandler updateAccountBalanceHandler;

    public TransactionConsumer(GetAccountHandler getAccountHandler,
                             UpdateAccountBalanceHandler updateAccountBalanceHandler) {
        this.getAccountHandler = getAccountHandler;
        this.updateAccountBalanceHandler = updateAccountBalanceHandler;
    }

    @Transactional
    @RabbitListener(queues = "transaction.created.queue")
    public void handleTransactionCreated(TransactionCreatedEventDTO event) {
        logger.info("Processing transaction: {}", event.transactionId());
        
        try {
            Account sender = getAccountHandler.handle(new GetAccountByDocumentQuery(event.senderDocumentNumber()));
            Account receiver = getAccountHandler.handle(new GetAccountByDocumentQuery(event.receiverDocumentNumber()));
            
            Balance transactionAmount = Balance.of(event.amount());
            
            if (!sender.hasSufficientFunds(transactionAmount)) {
                logger.error("Insufficient funds for transaction {}: required {}, available {}", 
                    event.transactionId(), event.amount(), sender.getBalance().amount());
                return;
            }
            
            Balance newSenderBalance = sender.getBalance().subtract(transactionAmount);
            updateAccountBalanceHandler.handle(new UpdateAccountBalanceCommand(
                sender.getId().value(), newSenderBalance.amount()
            ));
            
            Balance newReceiverBalance = receiver.getBalance().add(transactionAmount);
            updateAccountBalanceHandler.handle(new UpdateAccountBalanceCommand(
                receiver.getId().value(), newReceiverBalance.amount()
            ));
            
            logger.info("Transaction {} processed successfully", event.transactionId());
            
        } catch (AccountNotFoundException ex) {
            logger.error("Account not found for transaction {}: {}", event.transactionId(), ex.getMessage());
        } catch (InsufficientFundsException ex) {
            logger.error("Insufficient funds for transaction {}: {}", event.transactionId(), ex.getMessage());
        } catch (Exception ex) {
            logger.error("Error processing transaction {}: {}", event.transactionId(), ex.getMessage(), ex);
        }
    }
}