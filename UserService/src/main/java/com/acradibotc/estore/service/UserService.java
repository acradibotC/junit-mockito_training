package com.acradibotc.estore.service;

import com.acradibotc.estore.model.User;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
/**
 *
 * @author Anhnt206
 */
public interface UserService {
    User createUser(String firstName, String lastName, String email, String password, String reapeatPassword);
}
