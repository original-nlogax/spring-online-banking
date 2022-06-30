package com.nlogax.banking.repository;

import com.nlogax.banking.model.Account;
import com.nlogax.banking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    // TODO findByEmail(String email); somehow works? i don't understand why
    // we don't have any implementation of it
    /*
    List<Account> findBy(String email);*/
}