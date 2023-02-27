/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.acradibotc.estore.data;

import com.acradibotc.estore.model.User;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Anhnt206
 */
public class UserRepositoryImpl implements UserRepository{

    Map<String, User> users = new HashMap<>();
    
    @Override
    public boolean save(User user) {
        
        boolean returnValue = false;
        
        if (!users.containsKey(user.getId())) {
            users.put(user.getId(),user);
            returnValue = true;
        }
        return returnValue;
    }
    
}
