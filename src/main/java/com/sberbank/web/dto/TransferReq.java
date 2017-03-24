package com.sberbank.web.dto;

import javax.validation.GroupSequence;
import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
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

    @AssertFalse(message = "Transfer on the same account is not allowed!", groups = FinalValidation.class)
    public boolean isTransferOnTheSameAccount() {
        return accountFrom.equals(accountTo);
    }

    public interface FinalValidation {
    }

    @GroupSequence({Default.class, FinalValidation.class})
    public interface ValidationSequence {
    }

}


