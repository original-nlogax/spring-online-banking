package com.nlogax.banking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.nlogax.banking.model.User;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_accounts")
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private float balance;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)  // todo why does EAGER crashes?
    @JoinColumn(name="user_id")
    private User user;

    public Account(String name) {
        super();   //fixme
        this.name = name;
    }
}
