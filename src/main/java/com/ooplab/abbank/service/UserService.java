package com.ooplab.abbank.service;

import com.ooplab.abbank.Address;
import com.ooplab.abbank.Salary;
import com.ooplab.abbank.User;
import com.ooplab.abbank.dao.AddressRepository;
import com.ooplab.abbank.dao.SalaryRepository;
import com.ooplab.abbank.dao.UserRepository;
import com.ooplab.abbank.serviceinf.UserServiceINF;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Objects;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService, UserServiceINF {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final SalaryRepository salaryRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format("[ERROR] Username '%s' not found!",username)));
        SimpleGrantedAuthority authorities = new SimpleGrantedAuthority(user.getUserRole());
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), Collections.singleton(authorities));
    }

    @Override
    public User getUser(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public String registerUser(User user) {
        try {
            User checkUser = getUser(user.getUsername());
            boolean usernameUsed = Objects.equals(checkUser.getUsername(), user.getUsername());
            if(usernameUsed) return "Username is unavailable!";
        }catch(NullPointerException ignored){}

        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setUserRole("customer");
        userRepository.save(user);
        //sendVerificationEmail(user.getEmail());

        return "Successfully created user! Please verify your email address";
    }

    @Override
    public String deleteUser(String username) {
        User user = getUser(username);
        if(user == null) return "No user exists with the given username!";
        userRepository.deleteByUsername(username);
        return "Successfully deleted user!";
    }

    @Override
    public String setPassword(String username, String password) {
        User user = getUser(username);
        if(user == null) return "No user exists with the given username!";
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return "Successfully set user password!";
    }

    @Override
    public String setPin(String username, String pin) {
        User user = getUser(username);
        if(user == null) return "No user exists with the given username!";
        if(!pin.matches("^[0-9]{4}$")) return "Pin should be a 4-digit number!";
        user.setPIN(pin);
        userRepository.save(user);
        return "Successfully set user PIN!";
    }

    @Override
    public String setRole(String username, String role) {
        User user = getUser(username);
        if(user == null) return "No user exists with the given username!";
        user.setUserRole(role);
        userRepository.save(user);
        return "Successfully set user role!";
    }

    @Override
    public String setEnabled(String username, Boolean enabled) {
        User user = getUser(username);
        if(user == null) return "No user exists with the given username!";
        boolean previous = user.getEnabled();
        user.setEnabled(enabled);
        userRepository.save(user);
        return String.format("Successfully set user status from {%b} -> {%b}", previous, enabled);
    }

    @Override
    public void sendVerificationEmail(String username) {
        //TODO: Send verification email through java mail sender
    }

    @Override
    public void verifyEmail(User user) {
        //TODO: The controller should enable the user from this service function
    }

    @Override
    public String setAddress(String username, Address address) {
        User user = getUser(username);
        if(user == null) return "No user exists with the given username!";
        addressRepository.save(address);
        user.setAddress(address);
        userRepository.save(user);
        return "Successfully set user address!";
    }

    @Override
    public String setDOB(String username, LocalDate DOB) {
        User user = getUser(username);
        if(user == null) return "No user exists with the given username!";
        user.setDOB(DOB);
        userRepository.save(user);
        return "Successfully set user date of birth!";
    }

    @Override
    public String setSalary(String username, Salary salary) {
        User user = getUser(username);
        if(user == null) return "No user exists with the given username!";
        salaryRepository.save(salary);
        user.setSalary(salary);
        userRepository.save(user);
        return "Successfully set user salary information!";
    }

    @Override
    public String getProfile(String username) {
        User user = getUser(username);
        if(user == null) return "No user exists with the given username!";
        return user.toString();
    }
}
