package com.sberbank.web;

import com.sberbank.repository.AccountRepository;
import com.sberbank.web.dto.MoneyUpdateReq;
import com.sberbank.web.dto.TransferReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class AccountController {

    private final AccountRepository repository;

    @Autowired
    public AccountController(AccountRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "/putMoney", method = POST, produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public void putMoney(MoneyUpdateReq req) {
        repository.putMoney(req.getNumber(), req.getAmount());
    }

    @RequestMapping(value = "/withdrawMoney", method = POST, produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public void withdrawMoney(MoneyUpdateReq req) {
        repository.withdrawMoney(req.getNumber(), req.getAmount());
    }

    @RequestMapping(value = "/transferMoney", method = POST, produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public void transferMoney(TransferReq req) {
        repository.transferMoney(req.getAccountFrom(), req.getAccountTo(), req.getAmount());
    }


}
