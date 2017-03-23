package com.sberbank.repository;

import java.math.BigDecimal;

public interface AccountRepository {

    void putMoney(String account, BigDecimal amount);

    void withdrawMoney(String account, BigDecimal amount);

    void transferMoney(String accountFrom, String accountTo, BigDecimal amount);

}
