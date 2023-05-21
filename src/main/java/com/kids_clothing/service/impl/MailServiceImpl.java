package com.kids_clothing.service.impl;

import com.kids_clothing.entity.Account;
import com.kids_clothing.entity.Bill;
import com.kids_clothing.repository.AccountDao;
import com.kids_clothing.service.service.MailService;
import com.kids_clothing.utils.FormatMoney;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    AccountDao accountDao;

//    @Autowired
//    CountryRepository countryRepo;

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    PasswordEncoder passwordEncoder;


    public void sendCreateBill(Account account, Bill bill)
            throws UnsupportedEncodingException, MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("kpi@itelecom.vn", "Mami Love Shop");
        helper.setTo(account.getEmail());

        String subject = "Đã tạo đơn hàng!";

        String content = "<p>Xin chào <b>" + account.getUsername() + "!</b></p>"
                + "<p>Đơn hàng của bạn đã được tạo!</p>"
                + "<p>Thông tin đơn hàng.</p>"
                + "<p>Mã đơn hàng: <b>" + bill.getId() + "</b></p>"
                + "<br>"
                + "<p>Họ tên người nhận: <b>" + bill.getFullname() + "</b></p>"
                + "<p>Tổng giá trị đơn hàng: <b>" + FormatMoney.currencyVN(bill.getDowntotal()) + "</b></p>"
                + "<p>Địa chỉ nhận hàng: <b>" + bill.getAddress() + "</b></p>"
                + "<p><b>" + (bill.getPayment() ? "Đã thanh toán" : "Thanh toán khi nhận hàng") + "</b></p>"
                + "<br>"
                + "<p><u>Mọi thắc mắc vui lòng liên hệ:</u> mamilovepro2112@gmail.com</p>";

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);
    }

    @Override
    public void sendCancelBill(Account account, Bill bill) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("kpi@itelecom.vn", "Mami Love Shop");
        helper.setTo(account.getEmail());

        String subject = "Đơn hàng đã hủy!";

        String content = "<p>Xin chào <b>" + account.getUsername() + "!</b></p>"
                + "<p>Đơn hàng của bạn đã được Hủy!</p>"
                + "<p>Mã đơn hàng: <b>" + bill.getId() + "</b></p>"
                + "<br>"
                + "<p><u>Mọi thắc mắc vui lòng liên hệ:</u>kpi@itelecom.vn</p>";

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);
    }

    @Override
    public void sendCreateManagerBill(Account account, Bill bill) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("kpi@itelecom.vn", "Mami Love Shop");
        helper.setTo(account.getEmail());

        String subject = "Đơn hàng đã hủy!";

        String content = "<p>Xin chào <b>" + account.getUsername() + "!</b></p>"
                + "<p>Đơn hàng của bạn đã được shop Hủy!</p>"
                + "<p>Mã đơn hàng: <b>" + bill.getId() + "</b></p>"
                + "<br>"
                + "<p><u>Mọi thắc mắc vui lòng liên hệ:</u> mamilovepro2112@gmail.com</p>";

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);
    }

    @Override
    public void sendConfirmManagerBill(Account account, Bill bill) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("kpi@itelecom.vn" +
                "", "Mami Love Shop");
        helper.setTo(account.getEmail());

        String subject = "Đơn hàng đã gửi bên vận chuyển!";

        String content = "<p>Xin chào <b>" + account.getUsername() + "!</b></p>"
                + "<p>Đơn hàng đã gửi bên vận chuyển!</p>"
                + "<p>Mã đơn hàng: <b>" + bill.getId() + "</b></p>"
                + "<br>"
                + "<p><u>Mọi thắc mắc vui lòng liên hệ:</u> mamilovepro2112@gmail.com</p>";

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);
    }
}
