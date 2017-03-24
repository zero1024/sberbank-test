package com.sberbank.web.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class TransferReq {

    @NotNull(message = "accountFrom field cannot be empty!")
    private String accountFrom;
    @NotNull(message = "accountTo field cannot be empty!")
    private String accountTo;
    @NotNull(message = "money field cannot be empty!")
    @DecimalMin(value = "0.001", message = "min for money field is 0.001")
    private BigDecimal money;

    public String getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(String accountFrom) {
        this.accountFrom = accountFrom;
    }

    public String getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(String accountTo) {
        this.accountTo = accountTo;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
