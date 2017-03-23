package com.sberbank.web.dto;

import java.math.BigDecimal;

public class MoneyUpdateReq {

    private String number;
    private BigDecimal amount;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
