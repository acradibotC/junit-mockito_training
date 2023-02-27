/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.acradibotc.estore.service;

import com.acradibotc.estore.data.UserRepository;
import com.acradibotc.estore.model.User;
import java.util.UUID;

/**
 *
 * @author Anhnt206
 */
public class UserServiceImpl implements UserService {

    UserRepository usersRepository;
    EmailVerificationService emailVerificationService;

    public UserServiceImpl(UserRepository usersRepository, EmailVerificationService emailVerificationService) {
        this.usersRepository = usersRepository;
        this.emailVerificationService = emailVerificationService;
    }

    @Override
    public User createUser(String firstName, String lastName, String email, String password, String reapeatPassword) {

        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("User's first name is empty");
        }

        if (lastName == null || lastName.trim().length() == 0) {
            throw new IllegalArgumentException("User's last name is empty");
        }

        if (password == null || password.trim().length() == 0) {
            throw new IllegalArgumentException("Password is empty");
        }

        if (!password.equals(reapeatPassword)) {
            throw new IllegalArgumentException("RepeatPassword does not match Password");
        }
        User user = new User(firstName, lastName, email, UUID.randomUUID().toString());

        boolean isUserCreated;
         
        try {
            isUserCreated = usersRepository.save(user);
        } catch (RuntimeException ex) {
            throw new UserServiceException(ex.getMessage());
        }

        if (!isUserCreated) {
            throw new UserServiceException("Could not create user");
        }
        
        try {
            emailVerificationService.scheduleEmailConfirmation(user);
        } catch (Exception ex) {
            throw new UserServiceException(ex.getMessage());
        }
        

        return user;
    }

}
