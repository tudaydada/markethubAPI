package com.markethub.article.repository;

import com.markethub.article.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
    Account findByAccountId(Long id);
    Account findByPhone(String phone);
    Account findFirstByPhone(String phone);
}
