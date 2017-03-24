package com.sberbank.repository.impl;

import com.sberbank.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static java.lang.String.format;

@Repository
public class AccountRepositoryImpl implements AccountRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountRepositoryImpl.class);
    private final JdbcTemplate template;

    @Autowired
    public AccountRepositoryImpl(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    @Transactional
    public void putMoney(String account, BigDecimal money) {
        LOGGER.debug("Putting money on account {}", account);
        int updatedRows = template.update("UPDATE ACCOUNT SET MONEY = MONEY+? WHERE NUMBER = ? ", money, account);
        if (updatedRows == 0) {
            throw new DataIntegrityViolationException(format("Account [%s] not found", account));
        }
    }

    @Override
    @Transactional
    public void withdrawMoney(String account, BigDecimal money) {
        try {
            LOGGER.debug("Withdraw money from account {}", account);
            int updatedRows = template.update("UPDATE ACCOUNT SET MONEY = MONEY-? WHERE NUMBER = ? ", money, account);
            if (updatedRows == 0) {
                throw new DataIntegrityViolationException(format("Account [%s] not found", account));
            }
        } catch (DataIntegrityViolationException e) {
            checkForMinMoneyConstraint(account, e);
            throw e;
        }
    }

    @Override
    @Transactional
    public void transferMoney(String accountFrom, String accountTo, BigDecimal amount) {
        LOGGER.debug("Transfer money from account {} to account {}", accountFrom, accountTo);
        withdrawMoney(accountFrom, amount);
        putMoney(accountTo, amount);
    }

    private static void checkForMinMoneyConstraint(String account, DataIntegrityViolationException e) {
        if (e.getMessage().contains("MIN_MONEY")) {
            //на всякий случай логируем
            LOGGER.warn(e.getMessage(), e);
            throw new DataIntegrityViolationException(format("Not enough money on account [%s]", account));
        }
    }

}
