package com.hujiya.transation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TXServiceTest {

    @Autowired
    TXService txService;

    @Test
    public void service() {
        txService.service();
    }
}