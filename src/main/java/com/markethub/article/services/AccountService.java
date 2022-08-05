package com.markethub.article.services;

import com.markethub.article.form.LoginForm;
import com.markethub.article.model.Account;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface AccountService  {

    Account findById(Long id);
    List<Account> findAll();
    Account register(LoginForm loginForm);
    UserDetails getUserDetailById(Long id);
}
