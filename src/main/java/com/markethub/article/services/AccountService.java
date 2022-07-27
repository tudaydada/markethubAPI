package com.markethub.article.services;

import com.markethub.article.form.LoginForm;
import com.markethub.article.model.Account;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AccountService  {

    Account findById(Long id);
    Account register(LoginForm loginForm);
    UserDetails getUserDetailById(Long id);
}
