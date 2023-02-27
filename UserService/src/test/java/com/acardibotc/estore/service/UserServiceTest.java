package com.acardibotc.estore.service;

import com.acradibotc.estore.data.UserRepository;
import com.acradibotc.estore.model.User;
import com.acradibotc.estore.service.*;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Anhnt206
 */
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository usersRepository;

    @Mock
    EmailVerificationServiceImpl emailVerificationService;

    String firstName;
    String lastName;
    String email;
    String password;
    String repeatPassword;

    @BeforeEach
    void init() {
        firstName = "Acradibot";
        lastName = "Anhnt";
        email = "test@test.com";
        password = "12345678";
        repeatPassword = "12345678";
    }

    @DisplayName("User object created")
    @Test
    void testCreateUser_whenUserDetailsProvied_returnUserObject() {
        //Arrange
        Mockito.when(usersRepository.save(any(User.class))).thenReturn(true);
        // Act
        User user = userService.createUser(firstName, lastName, email, password, repeatPassword);
        // Assert
        Assertions.assertNotNull(user, "the createUser() should not have returned null");
        Assertions.assertEquals(firstName, user.getFirstName(), "User's first name is incorrect");
        Assertions.assertEquals(lastName, user.getLastName(), "User's last name is incorrect");
        Assertions.assertEquals(email, user.getEmail(), "User's email is incorrect");
        Assertions.assertNotNull(user.getId(), "User id is missing");
        Mockito.verify(usersRepository).save(any(User.class));
    }

    @DisplayName("Empty first name causes correct exception")
    @Test
    void testCreateUser_whenFirstNameIsEmpty_throwsIllegalAgrumentException() {
        //Arange
        String firstName = "";
        String expectedExceptionMessage = "User's first name is empty";

        //Act & Assert
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(firstName, lastName, email, password, repeatPassword);
        }, "Empty first name should have caused an Illegal Argument Exception");

        //Assert
        Assertions.assertEquals(expectedExceptionMessage, thrown.getMessage(),
                "Exception error message is not correct");
    }

    @DisplayName("Empty last name causes correct exception")
    @Test
    void testCreateUser_whenLastNameIsEmpty_throwsIllegalArgumentException() {
        // Arrange
        String lastName = "";
        String expectedExceptionMessage = "User's last name is empty";

        // Act & Assert
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(firstName, lastName, email, password, repeatPassword);
        }, "Empty last name should have caused an Illegal Argument Exception");

        // Assert
        assertEquals(expectedExceptionMessage, thrown.getMessage(),
                "Exception error message is not correct");
    }

    @DisplayName("Empty password causes correct exception")
    @Test
    void testCreateUser_whenPasswordIsEmpty_throwsIllegalArgumentException() {
        // Arrange
        String password = "";
        String expectedExceptionMessage = "Password is empty";

        // Act & Assert
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(firstName, lastName, email, password, repeatPassword);
        }, "Empty password should have caused an Illegal Argument Exception");

        // Assert
        assertEquals(expectedExceptionMessage, thrown.getMessage(),
                "Exception error message is not correct");
    }

    @DisplayName("Password does not Repeatpassword causes correct exception")
    @Test
    void testCreateUser_whenPasswordDoesNotMatchRepeatpassword_throwsIllegalArgumentException() {
        // Arrange
        String password = "123123";
        String repeatPassword = "123456";
        String expectedExceptionMessage = "RepeatPassword does not match Password";

        // Act & Assert
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(firstName, lastName, email, password, repeatPassword);
        }, "Password does not match Repeatpassword should have caused an Illegal Argument Exception");

        // Assert
        assertEquals(expectedExceptionMessage, thrown.getMessage(),
                "Exception error message is not correct");
    }

    @DisplayName("If save() method causes RuntimeException, a UserServiceException is thrown")
    @Test
    void testCreateUser_whenSaveMethodThrowsException_thenThrowsUserServiceException() {
        // Arrange 
        when(usersRepository.save(any(User.class))).thenThrow(RuntimeException.class);
        // Act
        Assertions.assertThrows(UserServiceException.class, () -> {
            userService.createUser(firstName, lastName, email, password, repeatPassword);
        }, "Should have thrown UserServiceException instead");
        // Assert
    }

    @DisplayName("EmailNotificationException is handled")
    @Test
    void testCreateUser_whenEmailNotificationExceptionThrown_throwsUserServiceException() {
        // Arrange
        when(usersRepository.save(any(User.class))).thenReturn(true);

        doThrow(EmailNotificationServiceException.class)
                .when(emailVerificationService).scheduleEmailConfirmation(any(User.class));
        // Act
        Assertions.assertThrows(UserServiceException.class,
                () -> {
                    userService.createUser(firstName, lastName, email, password, repeatPassword);
                }, "Should have thrown UserServiceException instead"
        );

        // Assert
        verify(emailVerificationService, times(1)).scheduleEmailConfirmation(any(User.class));

    }

    @DisplayName("Schedule Email Confirmation is executed")
    @Test
    void testCreateUser_whenUserCreated_scheduleEmailConfirmation() {
        // Arrange
        when(usersRepository.save(any(User.class))).thenReturn(true);
        doCallRealMethod().
            when(emailVerificationService)
                .scheduleEmailConfirmation(any(User.class));

        // Act
        userService.createUser(firstName, lastName, email, password, repeatPassword);
        // Assert
        verify(emailVerificationService,times(1)).scheduleEmailConfirmation(any(User.class));
    
    }
}
