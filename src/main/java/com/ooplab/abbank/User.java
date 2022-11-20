package com.ooplab.abbank;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@ToString
@Document(collection = "users")
public class User implements UserDetails {

    @Id
    @ToString.Exclude()
    private String id;
    @ToString.Include()
    private String username;
    @ToString.Include()
    private String email;
    @ToString.Exclude()
    private String password;
    @ToString.Include()
    private String firstName;
    @ToString.Include()
    private String lastName;
    @ToString.Include(name = "birthDate")
    private LocalDate DOB;
    @ToString.Exclude()
    private String PIN;
    @ToString.Include()
    private String phoneNumber;
    @ToString.Exclude()
    private String userRole;
    @ToString.Include()
    private Boolean emailVerified = false;
    @ToString.Exclude()
    private Boolean enabled = false;
    @ToString.Exclude()
    private LocalDateTime creationDate;
    @DBRef
    @ToString.Include()
    private Address address;
    @DBRef
    @ToString.Exclude()
    private Salary salary;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.creationDate = LocalDateTime.now();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRole);
        return Collections.singleton(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !emailVerified;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}