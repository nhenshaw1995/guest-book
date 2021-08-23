package com.bt.tech.test.GuestBook;

import java.util.function.UnaryOperator;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.bt.tech.test.GuestBook.entity.UserEntity;
import com.bt.tech.test.GuestBook.repository.UserRepository;
import com.bt.tech.test.GuestBook.roles.Roles;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {

    private final UserRepository userRepository;
    private final UnaryOperator<String> passwordEncoder;

    public void run(ApplicationArguments args) {
        final var user = new UserEntity();
        user.setUsername("admin");
        user.setPassword(passwordEncoder.apply("p@ssword"));
        user.setRole(Roles.ADMIN);

        userRepository.save(user);
    }
}
