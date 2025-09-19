package com.svm.journalapp.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {
    @Autowired
    private EmailService emailService;

    @Test
    public void testSendMail(){
        emailService.sendMail(
                "myjeegoal@gmail.com",
                "Testing Java Mail Sender",
                "Hi, Shubham Malve from this side!");
    }
}
