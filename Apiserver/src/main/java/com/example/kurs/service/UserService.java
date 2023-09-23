package com.example.kurs.service;

import com.example.kurs.dto.SignupDto;
import com.example.kurs.entity.Recipe;
import com.example.kurs.entity.Role;
import com.example.kurs.entity.User;
import com.example.kurs.exceptions.EmailAlreadyExistsException;
import com.example.kurs.exceptions.RecipeNotFoundException;
import com.example.kurs.exceptions.UserAlreadyExistsException;
import com.example.kurs.exceptions.UserNotFoundException;
import com.example.kurs.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Resource
    private UserTransaction userTransaction;


    public void register(SignupDto signupDto) throws UserAlreadyExistsException, EmailAlreadyExistsException, SystemException {
        try {
            userTransaction.begin();
            User user = new User();
            user.setUsername(signupDto.getUsername());
            user.setEmail(signupDto.getEmail());
            user.setRole(Role.USER);
            user.setPassword(passwordEncoder.encode(signupDto.getPassword()));
            if (existsByUsername(user.getUsername())) {
                log.info("User {} registered. Already exists.", user.getUsername());
                throw new UserAlreadyExistsException("User {} registered. Already exists " + user.getUsername());
            }
            if (existsByEmail(user.getEmail())) {
                log.info("User with  email {} already registered", user.getEmail());
                throw new EmailAlreadyExistsException("User with  email " + user.getEmail() + " already registered");
            }
            User registeredUser = userRepo.save(user);
            log.info("Registered user {}.", registeredUser);

            userTransaction.commit();
        } catch (Exception e) {
            if (e instanceof UserAlreadyExistsException) {
                throw (UserAlreadyExistsException) e;
            } else {
                if (e instanceof EmailAlreadyExistsException) {
                    throw (EmailAlreadyExistsException) e;
                }
            }
            if (userTransaction != null) {
                userTransaction.rollback();
            }

        }
    }


    public User getByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = Optional.ofNullable(userRepo.findByUsername(username));
        if (!user.isPresent()) {
            log.info("User with username {} not found.", username);
            throw new UsernameNotFoundException("User with username " + username + " not found.");
        }
        log.info("Found user with username {}.", username);
        return user.get();
    }

    public Boolean existsById(Long id) {
        Optional<User> user = userRepo.findById(id);
        if (!user.isPresent()) {
            log.info("User with id {} not found.", id);
            return false;
        }
        log.info("Found user with id {}.", id);
        return true;
    }

    public User getUser(Long id) throws UserNotFoundException {
        Optional<User> optionalUser = userRepo.findById(id);
        if (!optionalUser.isPresent()) {
            throw new UserNotFoundException("I love you, but we have not this user: " + id);
        }
        return optionalUser.get();
    }

    public Boolean existsByUsername(String username) {
        Optional<User> user = Optional.ofNullable(userRepo.findByUsername(username));
        if (!user.isPresent()) {
            log.info("User with username {} not found.", username);
            return false;
        }
        log.info("Found user with username {}.", username);
        return true;
    }

    public Boolean existsByEmail(String email) {
        Optional<User> user = userRepo.findByEmail(email);
        if (!user.isPresent()) {
            log.info("User with email {} not found.", email);
            return false;
        }
        log.info("Found user with email {}.", email);
        return true;

    }
}
