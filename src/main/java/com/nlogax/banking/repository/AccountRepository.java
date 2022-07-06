package com.nlogax.banking.repository;

import com.nlogax.banking.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("FROM Account WHERE number = ?1")
    Account getByNumber(@Param("number")  String number);
}
