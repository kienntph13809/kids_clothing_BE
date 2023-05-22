package com.kids_clothing.controllers;

import com.kids_clothing.entity.Account;
import com.kids_clothing.model.request.ForgotPasswordRequest;
import com.kids_clothing.model.response.Res;
import com.kids_clothing.repository.AccountDao;
import com.kids_clothing.service.service.ForgotPasswordServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/forgotpassword")
public class ForgotPasswordController {

    @Autowired
    AccountDao accountDao;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    ForgotPasswordServices forgotPasswordServices;

    @PostMapping("")
    public ResponseEntity<?> generateCapchar(@RequestBody ForgotPasswordRequest forgotPasswordRequest) throws MessagingException, UnsupportedEncodingException {

        Account account = accountDao.findByEmail(forgotPasswordRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Không tồn tại Email"));
        if (account.getOneTimePassword() != null) {
            if (account.isOTPGenerrate()) {
                return ResponseEntity.ok(new Res(forgotPasswordRequest, "Xin vui lòng đợi 1'", true));
            }
        }
        forgotPasswordServices.generateOneTimePassword(account);
        return ResponseEntity.ok(new Res(forgotPasswordRequest, "Đã gửi mã", true));

    }

    @PostMapping("/change")
    public ResponseEntity<?> updatePassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) throws MessagingException, UnsupportedEncodingException {

        if (!forgotPasswordRequest.getPassword().equals(forgotPasswordRequest.getPasswordConfirm())) {
            return ResponseEntity.ok(new Res(forgotPasswordRequest, "Nhập lại password không khớp", false));
        }
        Account account = accountDao.findByEmail(forgotPasswordRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Không tồn tại Email"));
        if (account.isOTPRequired()) {
            if (encoder.matches(forgotPasswordRequest.getOtp(), account.getOneTimePassword())) {
                account.setPassword(encoder.encode(forgotPasswordRequest.getPassword()));
                account.setOneTimePassword(null);
                accountDao.save(account);
                return ResponseEntity.ok(new Res(account, "oke", true));
            } else {
                return ResponseEntity.ok(new Res(forgotPasswordRequest, "Không trùng mã OTP", false));
            }
        }
        return ResponseEntity.ok(new Res("OTP đã hết hạn", false));

    }

}
