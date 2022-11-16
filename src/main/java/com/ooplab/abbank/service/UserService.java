package com.ooplab.abbank.service;

import com.ooplab.abbank.User;
import com.ooplab.abbank.dao.UserRepository;
import com.ooplab.abbank.serviceinf.UserServiceINF;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService, UserServiceINF {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format("[ERROR] Username '%s' not found!",username)));
        SimpleGrantedAuthority authorities = new SimpleGrantedAuthority(user.getUserRole());
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), Collections.singleton(authorities));
    }

}
