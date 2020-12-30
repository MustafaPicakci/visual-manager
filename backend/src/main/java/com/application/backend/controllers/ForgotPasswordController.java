package com.application.backend.controllers;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.application.backend.models.User;
import com.application.backend.payload.request.LoginRequest;
import com.application.backend.payload.request.ResetPasswordRequest;
import com.application.backend.payload.response.MessageResponse;
import com.application.backend.security.services.UserDetailsServiceImpl;

import net.bytebuddy.utility.RandomString;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RequestMapping("api/forgot_password")
@RestController
public class ForgotPasswordController {

  @Autowired private JavaMailSender mailSender;

  @Autowired private UserDetailsServiceImpl userDetailsServiceImpl;

  @PostMapping("/email/{email}")
  public ResponseEntity<?> processForgotPassword(@PathVariable("email") String email)
      throws UnsupportedEncodingException {
    String token = RandomString.make(30);
    try {
      userDetailsServiceImpl.updateResetPasswordToken(token, email);
      if (email != null) {
        sendEmail(email, token);
      }
    } catch (Exception ex) {
      System.out.println(ex.getMessage() + "asdasdasd");
      return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
    }
    return ResponseEntity.ok().body(new MessageResponse("Şifre sıfırlama Epostanız gönderildi."));
  }

  public void sendEmail(String recipientEmail, String token)
      throws MessagingException, UnsupportedEncodingException {

    SimpleMailMessage mailMessage = new SimpleMailMessage();
    mailMessage.setTo(recipientEmail);
    mailMessage.setSubject("Visual Manager Destek - Şifre sıfırlama!");
    mailMessage.setFrom("info.VisualManager@gmail.com");
    mailMessage.setText(
        "Şifrenizi sıfırlamak için lütfen linke tıklayınız. "
            + "http://www.localhost:8080/newPassword?token="
            + token);

    mailSender.send(mailMessage);
  }

  @PostMapping("/reset_password")
  public ResponseEntity<?> processResetPassword(
      @RequestBody ResetPasswordRequest resetPasswordRequest) {

    User user = userDetailsServiceImpl.getByResetPasswordToken(resetPasswordRequest.getToken());
    if (user == null) {

      return ResponseEntity.badRequest()
          .body(new MessageResponse("Error: Kullanıcı bilgilerine ulaşılamadı!"));
    } else {
      userDetailsServiceImpl.updatePassword(user, resetPasswordRequest.getPassword());
    }

    return ResponseEntity.ok().body(new MessageResponse("Şifreniz başarı ile değiştirildi!"));
  }
}
