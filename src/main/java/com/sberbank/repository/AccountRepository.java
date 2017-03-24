package com.sberbank.repository;

import java.math.BigDecimal;

public interface AccountRepository {

    void putMoney(String account, BigDecimal money);

    void withdrawMoney(String account, BigDecimal money);

    void transferMoney(String accountFrom, String accountTo, BigDecimal amount);

}
