package com.nlogax.banking.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

    @JsonManagedReference
    @Fetch(value = FetchMode.SUBSELECT) // bug: https://stackoverflow.com/a/8309458
    @OneToMany(targetEntity=com.nlogax.banking.model.Account.class, mappedBy = "user", fetch = FetchType.EAGER,  cascade = CascadeType.ALL)  // todo why does EAGER crashes?
    private Collection<Account> accounts;

    public <T> User(String firstName, String lastName, String email, String phoneNumber, String password, List<Role> roles, List<Account> accounts) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.roles = roles;
        this.accounts = accounts;
        enabled = true;
    }

    public boolean isAdmin() {
        return getRoles()
                .stream()
                .anyMatch((role) -> role.getName().equals("ROLE_ADMIN"));
    }

    @Override
    public String toString () {
        return getEmail();
    }
}
