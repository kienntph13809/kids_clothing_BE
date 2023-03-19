package com.kids_clothing.service.service;

import com.kids_clothing.entity.Account;
import com.kids_clothing.entity.Bill;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@Service
public interface MailService {
    void sendCancelBill(Account account, Bill bill) throws MessagingException, UnsupportedEncodingException;

    void sendCreateManagerBill(Account account, Bill bill) throws MessagingException, UnsupportedEncodingException;

    void sendConfirmManagerBill(Account account, Bill bill) throws MessagingException, UnsupportedEncodingException;
}
