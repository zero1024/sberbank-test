package com.sberbank;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.*;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.springframework.http.HttpMethod.POST;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void baseTest() throws Exception {
        //1. кладем деньги
        Map<String, Object> req = new HashMap<>();
        req.put("account", "baseTest1");
        req.put("money", new BigDecimal("100.21"));
        ResponseEntity<Map> res = restTemplate.exchange("/putMoney", POST, new HttpEntity<>(req), Map.class);
        assert res.getStatusCodeValue() == 200;
        assert accountMoney("baseTest1").compareTo(new BigDecimal("100.21")) == 0;

        //2. снимаем деньги
        req.put("money", new BigDecimal("12.98"));
        res = restTemplate.exchange("/withdrawMoney", POST, new HttpEntity<>(req), Map.class);
        assert res.getStatusCodeValue() == 200;
        assert accountMoney("baseTest1").compareTo(new BigDecimal("87.23")) == 0;

        //3. переводим деньги на другой счет
        req = new HashMap<>();
        req.put("accountFrom", "baseTest1");
        req.put("accountTo", "baseTest2");
        req.put("money", new BigDecimal("10.23"));
        res = restTemplate.exchange("/transferMoney", POST, new HttpEntity<>(req), Map.class);
        assert res.getStatusCodeValue() == 200;
        assert accountMoney("baseTest1").compareTo(new BigDecimal("77")) == 0;
        assert accountMoney("baseTest2").compareTo(new BigDecimal("10.23")) == 0;
    }

    @Test
    public void testIncorrectReq() throws Exception {
        //1. пустые запросы
        ResponseEntity<Map> res = restTemplate.exchange("/putMoney", POST, new HttpEntity<>(new HashMap<>()), Map.class);
        assert res.getStatusCodeValue() == 400;
        assert new HashSet<>((Collection<String>) res.getBody().get("messages"))
                .equals(new HashSet<>(asList("account field cannot be empty!", "money field cannot be empty!")));
        res = restTemplate.exchange("/withdrawMoney", POST, new HttpEntity<>(new HashMap<>()), Map.class);
        assert res.getStatusCodeValue() == 400;
        assert new HashSet<>((Collection<String>) res.getBody().get("messages"))
                .equals(new HashSet<>(asList("account field cannot be empty!", "money field cannot be empty!")));
        res = restTemplate.exchange("/transferMoney", POST, new HttpEntity<>(new HashMap<>()), Map.class);
        assert res.getStatusCodeValue() == 400;
        assert new HashSet<>((Collection<String>) res.getBody().get("messages"))
                .equals(new HashSet<>(asList("accountFrom field cannot be empty!", "accountTo field cannot be empty!", "money field cannot be empty!")));

        //2. в запросе указано мало денег
        Map<String, Object> req = new HashMap<>();
        req.put("account", "some");
        req.put("accountFrom", "some1");
        req.put("accountTo", "some2");
        req.put("money", new BigDecimal("00.00"));
        res = restTemplate.exchange("/putMoney", POST, new HttpEntity<>(req), Map.class);
        assert res.getStatusCodeValue() == 400;
        assert res.getBody().get("messages").equals(singletonList("min for money field is 0.001"));
        res = restTemplate.exchange("/withdrawMoney", POST, new HttpEntity<>(req), Map.class);
        assert res.getStatusCodeValue() == 400;
        assert res.getBody().get("messages").equals(singletonList("min for money field is 0.001"));
        res = restTemplate.exchange("/transferMoney", POST, new HttpEntity<>(req), Map.class);
        assert res.getStatusCodeValue() == 400;
        assert res.getBody().get("messages").equals(singletonList("min for money field is 0.001"));

        //3. перевод денег на тот же аккаунт
        req = new HashMap<>();
        req.put("accountFrom", "some");
        req.put("accountTo", "some");
        req.put("money", new BigDecimal("10"));
        res = restTemplate.exchange("/transferMoney", POST, new HttpEntity<>(req), Map.class);
        assert res.getStatusCodeValue() == 400;
        assert res.getBody().get("messages").equals(singletonList("Transfer on the same account is not allowed!"));
    }

    private BigDecimal accountMoney(String account) {
        return jdbcTemplate.queryForObject("SELECT MONEY FROM ACCOUNT WHERE NUMBER = ?", BigDecimal.class, account);
    }
}
