/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.acradibotc.estore.service;

import com.acradibotc.estore.model.User;

/**
 *
 * @author Anhnt206
 */
public interface EmailVerificationService {
    void scheduleEmailConfirmation(User user);
}
