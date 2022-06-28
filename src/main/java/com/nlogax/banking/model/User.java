package com.nlogax.banking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

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
    // adding user_id in user_roles which will reference this user's id
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)  // todo why does EAGER crashes?

    private Collection<Account> accounts;


    // ? maybe autogenerate
    public <T> User(String firstName, String lastName, String email, String password, String phoneNumber, List<Role> roles, List<Account> accounts) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.roles = roles;
        this.accounts = accounts;
        enabled = true;
    }

    // from UsernamePasswordAuthenticationToken doc:
    // The principal and credentials should be set with an Object that provides the respective property via
    // its Object.toString() method. The simplest such Object to use is String.
    // ( we need it in CustomAuthenticationProvider )
    @Override
    public String toString () {
        return getEmail();
    }
}
