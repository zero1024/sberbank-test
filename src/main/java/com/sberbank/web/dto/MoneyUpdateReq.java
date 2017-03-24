package com.sberbank.web.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class MoneyUpdateReq {

    @NotNull(message = "account field cannot be empty!")
    private String account;
    @NotNull(message = "money field cannot be empty!")
    @DecimalMin(value = "0.001", message = "min for money field is 0.001")
    private BigDecimal money;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
