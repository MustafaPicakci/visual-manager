package com.application.backend.controllers;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.application.backend.models.ERole;
import com.application.backend.models.Role;
import com.application.backend.models.User;
import com.application.backend.payload.request.LoginRequest;
import com.application.backend.payload.request.SignupRequest;
import com.application.backend.payload.response.MessageResponse;
import com.application.backend.repository.ConfirmationTokenRepository;
import com.application.backend.repository.RoleRepository;
import com.application.backend.repository.UserRepository;

import com.application.backend.security.services.UserDetailsServiceImpl;
import com.application.backend.services.EmailSenderService;
import com.application.backend.services.UserServiceImpl;

import com.application.backend.models.ConfirmationToken;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired AuthenticationManager authenticationManager;

  @Autowired UserRepository userRepository;
  @Autowired EmailSenderService emailSenderService;
  @Autowired RoleRepository roleRepository;
  @Autowired ConfirmationTokenRepository confirmationTokenRepository;

  @Autowired PasswordEncoder encoder;

  @Autowired UserDetailsServiceImpl userDetailsServiceImpl;
  @Autowired UserServiceImpl userServiceImpl;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

    User user = userServiceImpl.findByUsername(loginRequest.getUsername());
    if (user == null) {
      return new ResponseEntity<>(
          new MessageResponse("Bu bilgilere sahip bir hesap bulunamad??! "), HttpStatus.FORBIDDEN);
    } else if (user.isActive()) {
      return userDetailsServiceImpl.AuthenticateUser(
          loginRequest.getUsername(), loginRequest.getPassword());
    } else {
      return new ResponseEntity<>(
          new MessageResponse("Hesab??n??z Aktif durumda de??il! "), HttpStatus.FORBIDDEN);
    }
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity.badRequest()
          .body(new MessageResponse("Error: Bu kullan??c?? ad?? daha ??nce al??nm????!"));
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity.badRequest()
          .body(new MessageResponse("Error: Bu E-posta daha ??nce al??nm????!"));
    }

    // Create new user's account
    User user =
        new User(
            signUpRequest.getUsername(),
            signUpRequest.getEmail(),
            encoder.encode(signUpRequest.getPassword()));

    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role userRole =
          roleRepository
              .findByName(ERole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(
          role -> {
            switch (role) {
              case "admin":
                Role adminRole =
                    roleRepository
                        .findByName(ERole.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(adminRole);

                break;
              case "mod":
                Role modRole =
                    roleRepository
                        .findByName(ERole.ROLE_MODERATOR)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(modRole);

                break;
              default:
                Role userRole =
                    roleRepository
                        .findByName(ERole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
            }
          });
    }

    user.setRoles(roles);
    userRepository.save(user);

    ConfirmationToken confirmationToken = new ConfirmationToken(user);

    confirmationTokenRepository.save(confirmationToken);

    /*new Thread(
        new Runnable() {

          @Override
          public void run() {
            try {
              SimpleMailMessage mailMessage = new SimpleMailMessage();
              mailMessage.setTo(user.getEmail());
              mailMessage.setSubject("Hesap aktivasyonunu ger??ekle??tirin!");
              mailMessage.setFrom("info.VisualManager@gmail.com");
              mailMessage.setText(
                  "Hesab??n??z?? aktifle??tirmek i??in l??tfen t??klay??n : "
                      + "http://localhost:8080/confirm-account?token="
                      + confirmationToken.getConfirmationToken());

              mailSender.send(mailMessage);
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        })
    .start();*/

    SimpleMailMessage mailMessage = new SimpleMailMessage();
    mailMessage.setTo(user.getEmail());
    mailMessage.setSubject("Hesap aktivasyonunu ger??ekle??tirin!");
    mailMessage.setFrom("info.VisualManager@gmail.com");
    mailMessage.setText(
        "Hesab??n??z?? aktifle??tirmek i??in l??tfen t??klay??n : "
            + "http://localhost:8080/confirm-account?token="
            + confirmationToken.getConfirmationToken());

    emailSenderService.sendEmail(mailMessage);

    return ResponseEntity.ok(
        new MessageResponse(
            "Kay??t olma i??lemi ba??ar??l??. Aktivasyon maili E-postan??za g??nderildi."));
  }

  @PostMapping("/confirm-account/{token}")
  public ResponseEntity<?> confirmAccount(@PathVariable String token) {
    ConfirmationToken confirmationToken =
        confirmationTokenRepository.findByConfirmationToken(token);

    if (confirmationToken == null) {
      return ResponseEntity.badRequest().body(new MessageResponse("Kullan??c?? bulunamad?? !"));

    } else {
      try {
        userServiceImpl.confirmAccount(confirmationToken);
      } catch (Exception e) {
        return ResponseEntity.badRequest()
            .body(new MessageResponse("Error: Aktivasyon s??ras??nda bir sorun meydana geldi!" + e));
      }

      return ResponseEntity.ok().body(new MessageResponse("Hesap aktivasyonu ba??ar??l?? !"));
    }
  }
}
