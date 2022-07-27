package com.markethub.article.services.Impl;

import com.markethub.article.config.PasswordConfig;
import com.markethub.article.form.LoginForm;
import com.markethub.article.model.Account;
import com.markethub.article.repository.AccountRepository;
import com.markethub.article.services.AccountService;
import com.markethub.article.until.CustomUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService, UserDetailsService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findFirstByPhone(username);
        if (account == null) {
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserDetail(account);
    }

    @Override
    public Account findById(Long id) {
        return accountRepository.findByAccountId(id);
    }

    @Override
    public UserDetails getUserDetailById(Long id){
        Account account = findById(id);
        if (account==null) return null;
        return new CustomUserDetail(account);
    }

    @Override
    public Account register(LoginForm loginForm) {
        Account account = new Account();
        account.setPhone(loginForm.getUsername());
        account.setPassword(passwordEncoder.encode(loginForm.getPassword().trim()));
        account.setRole(loginForm.getRole());
        return accountRepository.save( account);
    }
}
