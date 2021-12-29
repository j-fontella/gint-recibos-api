package com.ginc.geradorrecibo.instances;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {

    private static BCryptPasswordEncoder passwordEncoder;

    public static BCryptPasswordEncoder getInstance(){
        if(passwordEncoder == null){
            passwordEncoder = new BCryptPasswordEncoder();
            return passwordEncoder;
        }
        return passwordEncoder;
    }
}
