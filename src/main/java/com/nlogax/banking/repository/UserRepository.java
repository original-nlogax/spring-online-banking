package com.nlogax.banking.repository;

import com.nlogax.banking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // works, because JpaRepository gets a field with exact name "email" from db
    List<User> findByEmail(String email);
}
