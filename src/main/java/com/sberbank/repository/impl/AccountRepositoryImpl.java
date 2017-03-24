package com.sberbank.repository.impl;

import com.sberbank.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Repository
public class AccountRepositoryImpl implements AccountRepository {

    private final JdbcTemplate template;

    @Autowired
    public AccountRepositoryImpl(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    @Transactional
    public void putMoney(String account, BigDecimal money) {
        template.update("UPDATE ACCOUNT SET MONEY = MONEY+? WHERE NUMBER = ? ", money, account);
    }

    @Override
    @Transactional
    public void withdrawMoney(String account, BigDecimal money) {
        template.update("UPDATE ACCOUNT SET MONEY = MONEY-? WHERE NUMBER = ? ", money, account);
    }

    @Override
    @Transactional
    public void transferMoney(String accountFrom, String accountTo, BigDecimal amount) {
        withdrawMoney(accountFrom, amount);
        putMoney(accountTo, amount);
    }
}
