package com.bt.tech.test.GuestBook.transformer;

import java.util.Base64;
import java.util.function.UnaryOperator;

import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder implements UnaryOperator<String> {

    @Override
    public String apply(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }

}
