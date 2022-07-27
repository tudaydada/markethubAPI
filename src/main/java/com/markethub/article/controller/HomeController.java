package com.markethub.article.controller;

import com.markethub.article.form.LoginForm;
import com.markethub.article.jwt.JwtTokenProvider;
import com.markethub.article.model.Account;
import com.markethub.article.services.AccountService;
import com.markethub.article.until.CustomUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class HomeController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AccountService accountService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;


    @PostMapping("/register")
    public ResponseEntity<Account> register(@Valid @RequestBody LoginForm loginForm) {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.register(loginForm));
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@Valid @RequestBody LoginForm loginForm) {
        Authentication authentication ;
        try{
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginForm.getUsername(),
                            loginForm.getPassword()
                    )
            );
        }catch (Exception e){
            Account account = new Account();
            account.setDescription(e.getMessage());
            return ResponseEntity.status(HttpStatus.OK).body(account);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetail customUserDetail = (CustomUserDetail) authentication.getPrincipal();
        Account account = customUserDetail.getAccount();
        String token=jwtTokenProvider.generateToken(customUserDetail);
        return ResponseEntity.status(HttpStatus.OK).body(account);
    }
}
