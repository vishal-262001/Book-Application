package com.example.demo.auth;

import com.example.demo.Email.EmailService;
import com.example.demo.Email.EmailTemplateName;
import com.example.demo.role.RoleRepository;
import com.example.demo.security.JwtService;
import com.example.demo.token.Token;
import com.example.demo.token.TokenRepository;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;


@Service
public class AuthenticationService {

    public AuthenticationService(RoleRepository roleRepository, PasswordEncoder passwordEncoder, UserRepository userRepository, TokenRepository tokenRepository, EmailService emailService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    @Value("${application.mailing.frontend.activation_url}")
    private String activation_url;

    
    public void register(@NotNull ResgistrationRequest request) throws MessagingException {

        var userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new IllegalArgumentException("Role User is Not Initilalized"));
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enables(false)
                .roles(List.of(userRole))
                .build();
        userRepository.save(user);
        sendValidationEmail(user);
    }

    public void sendValidationEmail(User user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user);

        emailService.sendEmail(user.getEmail(), user.fullName()
                , EmailTemplateName.ACTIVATE_ACCOUNT
                , activation_url, newToken
                , "Account Activate");
    }

    public String generateAndSaveActivationToken(User user) {
        String generatedToken = generateActivationToken(6);
        var token = Token.builder()
                .token(generatedToken)
                .createdDateTime(LocalDateTime.now())
                .expireDateTime(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        tokenRepository.save(token);
        return generatedToken;
    }

    private String generateActivationToken(int length) {
        String character = "0123456789";
        StringBuilder builder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(character.length());
            builder.append(character.charAt(randomIndex));
        }
        return builder.toString();
    }

    public AuthenticationRespose authenticate(@Valid AuthenticationRequest request) {
        var auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        var claims = new HashMap<String, Object>();
        var user = ((User) auth.getPrincipal());
        claims.put("fullName", user.fullName());
        var jwtToken = jwtService.generateToken(claims, user);

        return AuthenticationRespose.builder().token(jwtToken).build();
    }
//
//
//    public void activationAccount(String token) throws MessagingException {
//        Token savedToken = tokenRepository.findByToken(token).orElseThrow(() -> new RuntimeException("Invalid Token"));
//        if(LocalDateTime.now().isAfter(savedToken.getExpireDateTime())){
//            sendValidationEmail(savedToken.getUser());
//            throw new RuntimeException("Activation Token Expired .\n A new token has been genarated ");
//        }
//
//        var user = userRepository.findById(savedToken.getUser().getId()).orElseThrow(()-> new UsernameNotFoundException("User Not Found"));
//        user.setEnables(true);
//        userRepository.save(user);
//        savedToken.setValidateDateTime(LocalDateTime.now());
//        tokenRepository.save(savedToken);
//
//    }

    public void activationAccount(String token) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid Token"));

        // Check if token is expired
        if (LocalDateTime.now().isAfter(savedToken.getExpireDateTime())) {
            sendValidationEmail(savedToken.getUser());
            throw new RuntimeException("Activation Token Expired. A new token has been generated.");
        }

        // Activate user account
        var user = userRepository.findById(savedToken.getUser().getId())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        user.setEnables(true);
        userRepository.save(user);

        // Mark token as used
        savedToken.setValidateDateTime(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }

}
